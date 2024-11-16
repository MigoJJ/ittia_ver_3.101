
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
import je.panse.doro.chartplate.filecontrol.datetime.Date_current;

public class EMR_eGFR extends JFrame {

    private JTextField[] inputFields;
    private JButton submitButton;
    private JButton clearButton;
    
    public EMR_eGFR() {
        
        // Set up the JFrame
        super("EMR GFR Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        
        // Create the input fields
        inputFields = new JTextField[3];
        String[] labels = {"Creatinie : ", "eGFR : ", "Albumin / Creatinie ratio : "};
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
                        String result = ""; 
                        String cresult = "";
                        String cr = inputFields[0].getText().trim();
                        String eGFR = inputFields[1].getText().trim();
                        String AC = inputFields[2].getText().trim();
                    	
                        if (eGFR.isEmpty()) {
                            result = "\n\t" + cr + "\tCreatinie (mg/dl)\n";
                        } else {
                            result = "\n\t" + cr + "\tCreatinie (mg/dl)\n"
                            		+"\n\t" + eGFR + "\teGFR ( mL/min/1.73m²)\n"
                            		+"\n\t" + AC + "\t+A/C ratio (mg/g)\n";
                            cresult = EMR_eGFR_Calc(cr, eGFR, AC);
                       	}
                        // Clear the text of all input fields
                        for (JTextField inputField : inputFields) {
                            inputField.setText("");
                       	}

                        GDSEMR_frame.setTextAreaText(5, "\n\t..........\n");
                        String[] results = {result, cresult};
	                        for (String res : results) {
	                            GDSEMR_frame.setTextAreaText(5, res);
	                        }
                        String rresult = EMR_eGFR.EMR_AG(cresult);
                        GDSEMR_frame.setTextAreaText(7, rresult);
                        dispose();
                    }
                    break;
                }
            }
        }
    }
    public static String EMR_AG(String argsCKD) {
		 String[] lines = argsCKD.split("\n");
		 StringBuilder sb = new StringBuilder();
		 for (String line : lines) {
		     int colonIndex = line.indexOf(':');
		     if (colonIndex != -1) {
		         String code = line.substring(0, colonIndex).trim();
		         sb.append(code).append(" ");
		     }
		 }
		 String cdate = Date_current.main("m");
		 String rargsCKD = ">  CKD [ " + sb.toString().trim() + " ] " + cdate;
		 return "\n" + rargsCKD;
    }

    public String EMR_eGFR_Calc(String cr, String egfr, String ac) {
        double C1 = Double.parseDouble(cr.trim());
        double eGFR = Double.parseDouble(egfr.trim());
        double ACratio = Double.parseDouble(ac.trim());
        
        String RACratio = getRACratio(ACratio);
        String ReGFR = getReGFR(eGFR);
        
        return "\t" + RACratio + "\n\t" + ReGFR;
    }

    private String getRACratio(double ACratio) {
        if (ACratio < 30) {
            return "A1  : Normal to mildly increased A/C_ratio";
        } else if (ACratio >= 30 && ACratio <= 300) {
            return "A2  : Moderately increased A/C_ratio";
        } else if (ACratio > 300) {
            return "A3  : Severely increased A/C_ratio";
        } else {
            return "";
        }
    }

    private String getReGFR(double eGFR) {
        if (eGFR >= 90) {
            return "G1  : Normal GFR";
        } else if (eGFR < 90 && eGFR >= 60) {
            return "G2  : Mildly decreased GFR";
        } else if (eGFR < 60 && eGFR >= 45) {
            return "G3a : Mildly to moderately decreased GFR";
        } else if (eGFR < 45 && eGFR >= 30) {
            return "G3b : Moderate to severely decreased GFR";
        } else if (eGFR < 30 && eGFR >= 15) {
            return "G4  : Severely decreased GFR";
        } else if (eGFR < 15) {
            return "G5  : Kidney failure";
        } else {
            return "";
        }
    }

	public static void main(String[] args) {
        new EMR_eGFR();
    }
}
