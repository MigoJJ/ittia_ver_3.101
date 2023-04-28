package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import je.panse.doro.GDSEMR_frame;

public class EMR_CBC extends JFrame {

    private JTextField[] inputFields;
    private JButton submitButton;
    private JButton clearButton;
    
    public EMR_CBC() {
        
        // Set up the JFrame
        super("EMR CBC Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        
        // Create the input fields
        inputFields = new JTextField[3];
        String[] labels = {"Hb:", "WBC:", "Platelet:"};
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        for (int i = 0; i < 3; i++) {
            JLabel label = new JLabel(labels[i], JLabel.RIGHT);
            inputPanel.add(label);
            inputFields[i] = new JTextField();
            inputFields[i].setHorizontalAlignment(JTextField.CENTER);
            inputFields[i].addActionListener(new InputFieldActionListener());
            inputPanel.add(inputFields[i]);
        }
        // Create the buttons
        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        
        // Add components to the content pane
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(inputPanel, BorderLayout.CENTER);
        c.add(buttonPanel, BorderLayout.SOUTH);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text of all input fields
                for (JTextField inputField : inputFields) {
                    inputField.setText("");
                }
            }
        });
        // Show the JFrame
        setVisible(true);
    }
    
    private class InputFieldActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Move the cursor to the next input field when Enter is pressed
            JTextField source = (JTextField) e.getSource();
            for (int i = 0; i < 3; i++) {
                if (source == inputFields[i]) {
                    if (i < 2) {
                        inputFields[i+1].requestFocus();
                    } else {
                        String result;
                        // Check if WBC is empty or not
                        String hb = inputFields[0].getText().trim();
                        String wbc = inputFields[1].getText().trim();
                        String platelet = inputFields[2].getText().trim();
                    	
                        if (wbc.isEmpty()) {
                            result = "\nHb " + hb +" (g/dl)  \n";
                        } else {
                            result = "\nHb " + hb +" (g/dl)..." + "WBC " + wbc +" (cells/L)..."+ "Platelet " + platelet + " (billion/L)\n";
                        	}
                        // Clear the text of all input fields
                        for (JTextField inputField : inputFields) {
                            inputField.setText("");
                        	}
                        // Set focus back to the first input field
                        GDSEMR_frame.setTextAreaText(5, result);
                        dispose();
                                                
//               inputFields[0].requestFocus();
//               	submitButton.requestFocus();
                    }
                    break;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new EMR_CBC();
    }
}
