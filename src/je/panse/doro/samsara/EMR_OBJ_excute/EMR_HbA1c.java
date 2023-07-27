package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import je.panse.doro.GDSEMR_frame;

public class EMR_HbA1c extends JFrame implements ActionListener {
    private JTextField[] inputFields;
    private JTextArea outputArea;
    private JButton clearButton, saveButton, quitButton;

    public EMR_HbA1c() {
        setTitle("EMR Interface for HbA1c Input");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocation(1460, 415);
        setSize(new Dimension(1000, 800));

        inputFields = new JTextField[3];
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        String[] labels = { "FBS/PP2  ", "Glucose [mg/dL]  ", "HbA1c [ % ]  " };
        
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            inputPanel.add(label);

            inputFields[i] = new JTextField();
            inputFields[i].setHorizontalAlignment(JTextField.CENTER);
            inputFields[i].addActionListener(this);
            inputPanel.add(inputFields[i]);

            final int index = i; 
             // Add key listener to inputFields[2] to handle Enter key press
            inputFields[i].addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    	   if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            if (index < 2) {
                                inputFields[index + 1].requestFocus();
                            } else if (index == 2) {
                                printTextOutput();
                                clearFieldArea();
                            }
                        }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
        }

        add(inputPanel, BorderLayout.CENTER);
        outputArea = new JTextArea(4, 30);
        add(outputArea, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        EMR_HbA1c emr = new EMR_HbA1c();
        emr.setSize(250, 250);
        emr.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == clearButton) {
            clearFieldArea();
        } else if (source == saveButton) {
            printTextOutput();
            clearFieldArea();
        } else if (source == quitButton) {
        	 clearFieldArea();
            dispose();
        } else if (source =="") {
        	System.out.println(" Source is empty"); 
        }
    }

    public String myString(double fbspp2) {
        if (fbspp2 == 0) {
            return "FBS";
        }
        return "PP" + fbspp2;
    }

    public static void getGlucoseControlStatus(double HbA1c) {
        String status;
        if (HbA1c > 9.0) {
            status = "Poor";
        } else if (HbA1c >= 7.0 && HbA1c <= 9.0) {
            status = "Fair";
        } else if (HbA1c >= 6.0 && HbA1c < 7.0) {
            status = "Good";
        } else {
            status = "Excellent";
        }

        String message = String.format("\n...now [ %s ] treated DM with current medication", status);
        GDSEMR_frame.setTextAreaText(8, message);
        System.out.println(message);
    }

    public void printTextOutput() {
        double fbs_pp2 = 0.0;
        double glucose_mgdl = 0.0;
        double hba1c_perc = 0.0;

        String fbs_pp2Text = inputFields[0].getText();
        String glucose_mgdlText = inputFields[1].getText();
        String hba1cText = inputFields[2].getText();

        double number = Double.parseDouble(fbs_pp2Text);

        if (!hba1cText.isEmpty()) {
            try {
                hba1c_perc = Double.parseDouble(hba1cText);
                double ifcc_hba1c_mmolmol = (hba1c_perc - 2.15) * 10.929;
                double eag_mgdl = (28.7 * hba1c_perc) - 46.7;
                double eag_mmoll = eag_mgdl / 18.01559;

                // Parse glucose_mgdlText and assign it to glucose_mgdl
                glucose_mgdl = Double.parseDouble(glucose_mgdlText);

                String outputText = String.format("\n" + myString(number) + " [ %.0f ] mg/dL   " + "HbA1c [ %.1f ]%%\n"
                        + "\tIFCC HbA1c: %.0f mmol/mol\n" + "\teAG: %.0f mg/dL\n" + "\teAG: %.1f mmol/l\n",
                        glucose_mgdl, hba1c_perc, ifcc_hba1c_mmolmol, eag_mgdl, eag_mmoll);

                if (outputArea != null) {
                    outputArea.setText(outputText);
                }
                EMR_HbA1c.getGlucoseControlStatus(hba1c_perc);
                GDSEMR_frame.setTextAreaText(5, outputText);

            } catch (NumberFormatException ex) {
                System.err.println("Invalid HbA1c value: " + hba1cText);
                // Additional error handling if needed
            }
        } else {
            String outputText = String.format("\n" + myString(number) + " [ %.0f ] mg/dL   ", glucose_mgdl);
            if (outputArea != null) {
                outputArea.setText(outputText);
            }
            GDSEMR_frame.setTextAreaText(5, outputText);
        }
    }
    private void handleKeyPress(KeyEvent e, int currentInputFieldIndex, int lastIndex) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (currentInputFieldIndex < lastIndex) {
                currentInputFieldIndex++;
                inputFields[currentInputFieldIndex].requestFocus(); // set focus to next input field
                inputFields[currentInputFieldIndex].setCaretPosition(inputFields[currentInputFieldIndex].getText().length()); // set cursor position to end of text
            } else {
                String result = "TC-HDL-Tg-LDL [ ";
                System.out.println(" result" + result);
                GDSEMR_frame.setTextAreaText(5, "\n" + result);
                GDSEMR_frame.setTextAreaText(9, "\n#  " + result);
                dispose();
            }
        }
    }
    
    public void clearFieldArea() {
    	for (int i = 0; i < inputFields.length; i++) {
    		inputFields[i].setText("");
    	}
    	outputArea.setText("");
    }
}
