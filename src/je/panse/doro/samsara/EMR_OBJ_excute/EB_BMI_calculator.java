package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.*;	
import java.awt.event.*;
import javax.swing.*;

import je.panse.doro.GDSEMR_frame;

public class EB_BMI_calculator extends JFrame implements ActionListener {
    
    private JLabel[] labels = {new JLabel("Height (cm): "), new JLabel("Weight (kg): "), new JLabel("Waist (cm or inch): "), new JLabel("BMI: ")};
    private JTextField[] fields = {new JTextField(10), new JTextField(10), new JTextField(10)};
    private JButton SaveandQuit;
    private JTextArea outputArea;
    
    public EB_BMI_calculator() {
        setTitle("BMI Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        
        for (int i = 0; i < labels.length; i++) {
            constraints.gridx = 0;
            constraints.gridy = i;
            panel.add(labels[i], constraints);
            
            if (i < fields.length) {
                constraints.gridx = 1;
                constraints.gridy = i;
                panel.add(fields[i], constraints);
            }
        }
        for (int i = 0; i < labels.length; i++) {
            constraints.gridx = 0;
            constraints.gridy = i;
            panel.add(labels[i], constraints);

			    if (i < fields.length) {
			        fields[i].addKeyListener(new KeyAdapter() {
			            @Override
			            public void keyPressed(KeyEvent e) {
			                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			                    int index = -1;
			                    for (int j = 0; j < fields.length; j++) {
			                        if (fields[j] == e.getSource()) {
			                            index = j;
			                            break;
			                        }
			                    }
			                    if (index != -1 && index < fields.length - 1) {
			                        fields[index + 1].requestFocusInWindow();
			                    }
			                }
			            }
			        });
				constraints.gridx = 1;
				constraints.gridy = i;
				panel.add(fields[i], constraints);
	    	}
        }
		       fields[2].addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
					    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					        // Call the method that should be executed on Enter key press
					        // Calculate BMI
					        double[] values = new double[2];
					        for (int i = 0; i < values.length; i++) {
					            values[i] = Double.parseDouble(fields[i].getText());
					        	}
					        double bmi = values[1] / (values[0] / 100.0 * values[0] / 100.0);
					        String result ="";
								if (bmi < 18.5) {
								    result += "Underweight";
								} else if (bmi >= 18.5 && bmi < 25) {
								    result += "Healthy weight";
								} else if (bmi >= 25 && bmi < 30) {
								    result += "Overweight";
								} else {
								    result += "Obese";
								}
					        String height = fields[0].getText();
					        String weight = fields[1].getText();
					        result = String.format("\n" + result + " : "+ "BMI: [ %.2f ]kg/m^2\n", bmi);
					        result = (result + "Height : " + height + " cm   " + "Weight : " + weight +" kg");
						        if (!fields[2].getText().isEmpty()) {	
									String waist = fields[2].getText();
									result = (result + "   Waist : " + waist + " cm");
						        	}
					        outputArea.setText(result);
					        GDSEMR_frame.setTextAreaText(5,result);
					    }
					}
		        });
        SaveandQuit = new JButton("Save & Quit");
        SaveandQuit.addActionListener(this);
        constraints.gridx = 1;
        constraints.gridy = labels.length;
        constraints.gridwidth = 2;
        panel.add(SaveandQuit, constraints);
        
        outputArea = new JTextArea(5, 20);
        constraints.gridx = 1;
        constraints.gridy = labels.length - 1;
        constraints.gridwidth = 1;
        panel.add(outputArea, constraints);
        
        add(panel);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new EB_BMI_calculator();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SaveandQuit) {
            // Handle saving and quitting
            // Call another method to handle saving and quitting
            // For example:
            dispose();
        }
    }
}
