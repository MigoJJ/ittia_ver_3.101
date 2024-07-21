package je.panse.doro.fourgate.thyroid.entry;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class EMR_thyroid_mainentry {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Select category ...");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setLocation(0, 610);
        frame.setSize(new Dimension(300, 460));

        String[] buttonNames = {
                "Thyroid Physical examination",
                "Hyperthyroidism Symptom",
                "Hypothyroidism Symptom",
                "Non thyroidal illness",
                "Abnormal TFT on Routine check",
                "Thyroidal nodule",
                "Post operation F/U PTC",

                "Quit"
        };
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.setPreferredSize(new Dimension(200, 40)); // Set a fixed size for the button
            button.setMaximumSize(new Dimension(200, 40)); // Set maximum size to enforce the fixed size
            button.setAlignmentX(Box.CENTER_ALIGNMENT); // Align the button to the center horizontally

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String actionCommand = e.getActionCommand();

                    // Use the actionCommand String as needed
                    if (actionCommand.equals("Quit")) {
                        frame.dispose();
                    } else {
//                        String[] Esrr = EMR_symptom_retStr.returnStr(actionCommand);
//                        EMR_symptom_list.main(Esrr);
                    }
                }
            });

            frame.add(button);
            frame.add(Box.createVerticalStrut(10)); // Add vertical spacing between buttons
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null); // Center the frame on the screen
//        frame.pack();
        frame.setVisible(true);
    }
    private String[] returnStr(String name) {
        String[] returnargs = new String[]{};
        // Add your logic to populate the returnargs array
        return returnargs;
    }

}



