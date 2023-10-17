package je.panse.doro.fourgate.Influenza;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;

import je.panse.doro.GDSEMR_frame;
public class InjectionApp {

    public static void main(String[] string) {
        // Create the main frame.
        final JFrame frame = new JFrame("Injections");  // Make it final
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(8, 1)); // 7 rows and 1 column

        String[] buttonLabels = {
            "Vaxigriptetra pfs inj (4가)",
            "SKY Cellflu (4 가)",
            "Tdap (Tetanus, Diphtheria, Pertussis)",
            "Shingles Vaccine (Shingrix)",
            "Prevena 13",
            "Button 6",
            "Button 7",
            "Quit"
        };

        ActionListener buttonClickListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();
                if ("Quit".equals(clickedButton.getText())) {
                    frame.dispose(); // Close the frame if "Quit" button is clicked
                } else {
                    Injection.main(clickedButton.getText());
                }
            }
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(buttonClickListener);
            frame.add(button);
        }

        // Make the frame visible.
        frame.setVisible(true);
    }
}


class Injection {
    public static void main(String clickedButtonText) {

        // Get the current date in yyyy-MM-dd format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        String CCstring ="for Influenza Vaccination\n";
        String PIstring = "  [ ✔ ]  no allergy to eggs, chicken\n"
                + "        , or any other component of the vaccine.\n"
                + "  [ ✔ ]  no s/p Guillain-Barré syndrome.\n"
                + "  [ ✔ ]  no adverse reactions to previous influenza vaccines.\n"
                + "  [ ✔ ]  no pregnancy, breastfeeding, and immunosuppression.\n";
        String Pstring = "\n   ...Vaccination as scheduled";
        String Influ = CCstring + PIstring;

        GDSEMR_frame.setTextAreaText(0, Influ);
        GDSEMR_frame.setTextAreaText(7, "\n  #" + clickedButtonText + " " + currentDate);
        GDSEMR_frame.setTextAreaText(8, Pstring);
    }
}

