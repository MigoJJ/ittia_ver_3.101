package je.panse.doro.soap.subjective;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class EMR_symptom_main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Select category ...");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        String[] buttonNames = {
                "Diabetes Mellitus",
                "Hyperthyroidism",
                "Hypothyroidism",
                "URI",
                "UTI",
                "Abdominal pain",
                "Atypical chest pain"
        };

        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.setPreferredSize(new Dimension(200, 40)); // Set a fixed size for the button
            button.setMaximumSize(new Dimension(200, 40)); // Set maximum size to enforce the fixed size
            button.setAlignmentX(Box.CENTER_ALIGNMENT); // Align the button to the center horizontally
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Perform the desired action for each button here
                    System.out.println("Button '" + name + "' clicked!");
                }
            });

            frame.add(button);
            frame.add(Box.createVerticalStrut(10)); // Add vertical spacing between buttons
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center the frame on the screen
        frame.pack();
        frame.setVisible(true);
    }
    private String[] returnStr(String name) {
        String[] returnargs = new String[]{};
        // Add your logic to populate the returnargs array
        return returnargs;
    }

}




