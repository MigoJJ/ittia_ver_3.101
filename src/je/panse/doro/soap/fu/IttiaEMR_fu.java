package je.panse.doro.soap.fu;

import javax.swing.*;

import je.panse.doro.GDSEMR_frame;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IttiaEMR_fu {

    public static class followupApp {  // Consider renaming this class to something more descriptive.

        private static final int FRAME_WIDTH = 300;
        private static final int FRAME_HEIGHT = 800;
        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        private static final String[] BUTTON_LABELS = {
            "Sanofi's VaxigripTetra® Vaccine(4가) [유독]",
            "Kovax Influ 4ga PF® vaccine [nip] ",
            "Tdap (Tetanus, Diphtheria, Pertussis)",
            "Td (Tetanus, Diphtheria)",
            "Shingles Vaccine (Shingrix) #1/2",
            "HAV vaccination #1/2",
            "HBV vaccination #1/3",
            "Shingles Vaccine (Shingrix) #1/2",
            "HAV vaccination #2/2",
            "HBV vaccination #2/3",
            "HBV vaccination #3/3",
            "Prevena 13 (pneumococcal vaccine (PCV13))",
            "Side Effect",
            "Quit"
        };

        //  Pstring is not defined within this class. It needs to be either:
        //    1. Passed as an argument to updateInjectionDetails
        //    2. Defined as a static member of this class
        //    3. Accessed from another class (e.g., GDSEMR_frame), but that requires knowledge of that class's structure.
        //
        //  For now, I'll comment it out, as it's a compilation error without more context.
        // private static String Pstring;  // Uncomment if Pstring should be here.

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {  // Use invokeLater for GUI updates on the EDT
                JFrame frame = createMainFrame();
                addButtonsToFrame(frame);
                positionFrameToBottomRight(frame);
                frame.setVisible(true);
            });
        }

        private static JFrame createMainFrame() {
            JFrame frame = new JFrame("Injections");
            frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(BUTTON_LABELS.length, 1));
            return frame;
        }

        private static void addButtonsToFrame(JFrame frame) {
            ActionListener buttonClickListener = e -> {
                String clickedButtonText = ((JButton) e.getSource()).getText();
                updateInjectionDetails(frame, clickedButtonText);  //Removed: Pstring,  add if Pstring is defined here
            };

            for (String label : BUTTON_LABELS) {
                JButton button = new JButton(label);
                button.addActionListener(buttonClickListener);
                frame.add(button);
            }
        }

        private static void positionFrameToBottomRight(JFrame frame) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int xPosition = (int) (screenSize.getWidth() - frame.getWidth());
            int yPosition = (int) (screenSize.getHeight() - frame.getHeight());
            frame.setLocation(xPosition, yPosition);
        }

        private static void updateInjectionDetails(JFrame frame, String clickedButtonText) {  //Removed: String Pstring, add if Pstring is defined here.
            String currentDate = DATE_FORMAT.format(new Date());

            if ("Quit".equals(clickedButtonText)) {
                frame.dispose();
                return;
            }

            try {
                GDSEMR_frame.setTextAreaText(7, "\n  #  " + clickedButtonText + "  [" + currentDate + "]");
                //GDSEMR_frame.setTextAreaText(8, Pstring);  // Uncomment if Pstring should be used.
            } catch (Exception e) {
                System.err.println("Error updating text areas: " + e.getMessage());  //Good practice to log the error
                // Consider displaying an error message to the user using JOptionPane.
            }
        }
    }
}