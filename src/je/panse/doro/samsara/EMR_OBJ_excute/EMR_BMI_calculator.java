package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.*;		
import java.awt.event.*;
import javax.swing.*;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.samsara.comm.datetime.Date_current;

public class EMR_BMI_calculator extends JFrame implements ActionListener {
    
//    private JLabel[] labels = {new JLabel("Height (cm): "), new JLabel("Weight (kg): "), new JLabel("Waist (cm or inch): "), new JLabel("BMI: ")};
    private JLabel[] labels = {new JLabel("Height (cm): "), new JLabel("Weight (kg): "), new JLabel("Waist (cm or inch): ")};

    private JTextField[] fields = {new JTextField(10), new JTextField(10), new JTextField(10)};
    private JButton SaveandQuit;
//    private JTextArea outputArea;
    
    public EMR_BMI_calculator() {
        setTitle("BMI Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null); // center the frame on the screen
        positionFrameToTopRight();
        setSize(300, 240);
        setResizable(true);
                 
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
                fields[i].setPreferredSize(new Dimension(15, 30));
                fields[i].setHorizontalAlignment(SwingConstants.CENTER); // Center align the cursor
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
                    // Calculate BMI
                    double[] values = new double[2];
                    for (int i = 0; i < values.length; i++) {
                        values[i] = Double.parseDouble(fields[i].getText());
                    }
                    double bmi = values[1] / (values[0] / 100.0 * values[0] / 100.0);
                    
                    // Determine BMI category
                    String result;
                    if (bmi < 18.5) {
                        result = "Underweight";
                    } else if (bmi < 25) {
                        result = "Healthy weight";
                    } else if (bmi < 30) {
                        result = "Overweight";
                    } else {
                        result = "Obese";
                    }
                    
                    // Build result string
                    String height = fields[0].getText();
                    String weight = fields[1].getText();
                    result = String.format("%s : BMI: [ %.2f ]kg/m^2", result, bmi);
                    String result1 = "\n" + result + "\nHeight : " + height + " cm   Weight : " + weight + " kg";
                    
                    if (!fields[2].getText().isEmpty()) {
                        String waist = fields[2].getText();
                        
                        if (waist.contains("i")) {
                            waist = waist.replace("i", ""); // Remove the "i" character
                            double waistValue = Double.parseDouble(waist);
                            waistValue *= 2.54; // Convert inches to centimeters
                            waist = String.format("%.1f", waistValue); // Format to one decimal place
        
                        }
                        
                        result1 += "   Waist: " + waist + " cm";
                    }

                    
                    // Update text areas
                    String selectedItems = "\n< BMI >\n";
                    GDSEMR_frame.setTextAreaText(5,selectedItems);
//                    outputArea.setText(result1);
                    GDSEMR_frame.setTextAreaText(5, result1);
                    String cdate = Date_current.main("m");
                    GDSEMR_frame.setTextAreaText(7, "\n# " + result + "    " + cdate);
                    dispose();
                    EMR_BMI_calculator.main(null);
                }
            }
        });

        
        SaveandQuit = new JButton("Save & Quit");
        SaveandQuit.addActionListener(this);
        constraints.gridx = 1;
        constraints.gridy = labels.length;
        constraints.gridwidth = 2;
        panel.add(SaveandQuit, constraints);
        
//        outputArea = new JTextArea(5, 15);
//        constraints.gridx = 1;
//        constraints.gridy = labels.length - 1;
//        constraints.gridwidth = 1;
//        panel.add(outputArea, constraints);
        
        add(panel);
//        pack();
        setVisible(true);
    }
    
    private void positionFrameToTopRight() {
        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Calculate the x and y coordinates for the top right corner
        int x = screenWidth - getWidth();
        int y = 0; // y coordinate is 0 for top right

        // Set the frame location
        setLocation(x, 60);
    }
    
    
    public static void main(String[] args) {
        new EMR_BMI_calculator();
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
