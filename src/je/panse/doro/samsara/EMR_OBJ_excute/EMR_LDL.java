package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.chartplate.filecontrol.datetime.Date_current;

public class EMR_LDL extends JFrame implements ActionListener, KeyListener {
    private JTextField[] inputFields; // Array to hold input fields
    private int currentInputFieldIndex = 0; // Tracks the current input field for navigation

    public EMR_LDL() {
        setTitle("EMR Interface for Lipid Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // Centers the frame on the screen
        setLayout(new BorderLayout());

        // Labels for the lipid profile inputs
        String[] labelNames = {"Total Cholesterol", "HDL-C", "Triglyceride", "LDL-C"};
        
        // Create a panel to hold input fields and their labels
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputFields = new JTextField[labelNames.length];

        for (int i = 0; i < labelNames.length; i++) {
            JLabel label = new JLabel(labelNames[i] + ": ", SwingConstants.RIGHT);
            inputFields[i] = new JTextField(10);

            // Set a left margin for better spacing
            Insets insets = new Insets(0, 10, 0, 0);
            inputFields[i].setMargin(insets);

            // Add components to the panel
            inputPanel.add(label);
            inputPanel.add(inputFields[i]);

            // Add KeyListener for navigation between fields
            inputFields[i].addKeyListener(this);
        }

        // Set focus to the first input field
        inputFields[0].requestFocus();
        inputFields[0].setCaretPosition(inputFields[0].getText().length());

        // Add the panel to the frame
        add(inputPanel, BorderLayout.CENTER);
    }

    /**
     * Handles key presses for navigation and data submission.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        String currentDate = Date_current.main("d"); // Fetch the current date
        
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (currentInputFieldIndex < inputFields.length - 1) {
                // Move focus to the next input field
                currentInputFieldIndex++;
                inputFields[currentInputFieldIndex].requestFocus();
                inputFields[currentInputFieldIndex].setCaretPosition(inputFields[currentInputFieldIndex].getText().length());
            } else {
                // Construct and save the result string
                String result = String.format(
                    "TC-HDL-Tg-LDL [ %s - %s - %s - %s ] mg/dL",
                    inputFields[0].getText(), inputFields[1].getText(),
                    inputFields[2].getText(), inputFields[3].getText()
                );

                System.out.println("Result: " + result);

                // Update GDSEMR_frame
                GDSEMR_frame.setTextAreaText(5, "\n" + result);
                GDSEMR_frame.setTextAreaText(7, "\n>  " + result + "   " + currentDate);

                // Close the frame
                dispose();
            }
        }
    }

    // Unused KeyListener methods, required to implement the interface
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EMR_LDL frame = new EMR_LDL();
            frame.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No specific action is implemented here, placeholder for ActionListener
    }
}
