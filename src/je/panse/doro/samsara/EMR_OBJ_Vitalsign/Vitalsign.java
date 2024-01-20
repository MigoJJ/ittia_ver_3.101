package je.panse.doro.samsara.EMR_OBJ_Vitalsign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class Vitalsign extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;
    private JTextArea descriptionArea;
    private JButton clearButton;
    private JButton saveButton;
    private JButton quitButton;
    private Set<String> validInputs;
    private Integer sbp = null;
    private Integer dbp = null;
    private Integer pulseRate = null;
    private Double bodyTemperature = null;
    private Integer respirationRate = null;

    public Vitalsign() {
        initializeValidInputs();
        createView();
        setTitle("Vital Sign Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 320);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initializeValidInputs() {
        validInputs = new HashSet<>();
        validInputs.add("h");
        validInputs.add("o");
        validInputs.add("g");
        validInputs.add("l");
        validInputs.add("r");
        validInputs.add("i");
        validInputs.add("t");

    }

    private void createView() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        createInputField(panel);
        createDescriptionArea(panel);
        createOutputArea(panel);
        addKeyListenerToInputField();
        createButtons(panel);
    }

    private void createInputField(JPanel panel) {
        inputField = new GradientTextField(20);
        inputField.setHorizontalAlignment(JTextField.CENTER);
        Dimension preferredSize = inputField.getPreferredSize();
        preferredSize.height = 30;
        inputField.setPreferredSize(preferredSize);
        inputField.setMaximumSize(inputField.getPreferredSize());
        inputField.setOpaque(false); // Make the text field transparent so the gradient is visible
        panel.add(inputField);
    }

    private void createDescriptionArea(JPanel panel) {
        descriptionArea = new JTextArea(1, 20);
        descriptionArea.setText("   at GDS : Regular pulse, Right Seated Position");
        descriptionArea.setBorder(BorderFactory.createTitledBorder("Description"));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        panel.add(descriptionScrollPane);
    }

    private void createOutputArea(JPanel panel) {
        outputArea = new JTextArea(5, 20);
        outputArea.setBorder(BorderFactory.createTitledBorder("Output"));
        outputArea.setEditable(true);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        panel.add(outputScrollPane);
    }

    private void addKeyListenerToInputField() {
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleInput();
                }
            }
        });
    }

    private void handleInput() {
        String input = inputField.getText().trim();
       
        if (validInputs.contains(input)) {
            updateDescriptionArea(input);
         }else {
           createBPBTRR(input);
          }
        inputField.setText("");
    }

    private void updateDescriptionArea(String input) {
        String datext = descriptionArea.getText();
        
        switch (input) {
            case "h":
                descriptionArea.setText("   at home by self");
                break;
            case "o":
                descriptionArea.setText("   at Other clinic");
                break;
            case "g":
                descriptionArea.setText("   at GDS : Regular pulse, Right Seated Position");
                break;
            case "l":
                datext = datext.replace("Right", "Left");
                descriptionArea.setText(datext);
                break;
            case "r":
                datext = datext.replace("Left", "Right");
                descriptionArea.setText(datext);
                break;
            case "i":
                datext = datext.replace("Regular", "irRegular");
                descriptionArea.setText(datext);
                break;
            default:
                // Do nothing or handle any default case if required
        	}
        }

    private void createButtons(JPanel panel) {
        JPanel buttonPanel = new JPanel();
        clearButton = new JButton("Clear");
        saveButton = new JButton("Save");
        quitButton = new JButton("Quit");

        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");
                outputArea.setText("");
                descriptionArea.setText("   at GDS : Regular pulse, Right Seated Position");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to save data
                outputArea.setText("Data saved successfully.");
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void createBPBTRR(String input) {
    	
		if (input.toLowerCase().contains("t")) {
			  String newinput = input.substring(1);
			  descriptionArea.setText("   at GDS : Forehead (Temporal Artery) Thermometers:");
			  outputArea.setText("\tBody Temperature [ " + newinput + " ] ℃");
			}
    	
        try {
        	double value = Double.parseDouble(input); // Convert input to integer

            if (sbp == null) {
                // First input - Systolic Blood Pressure
                sbp = (int) value;
                outputArea.setText("\tSBP [" + sbp + "] mmHg");
            } else if (dbp == null) {
                // Second input - Diastolic Blood Pressure
                dbp = (int) value;
                outputArea.setText("    BP [" + sbp + " / " + dbp + "] mmHg");
            } else if (pulseRate == null) {
                // Third input - Pulse Rate
                pulseRate = (int) value;
                outputArea.append("  PR [" + pulseRate + "]/minute");

                // Reset sbp and dbp for the next set of inputs

            } else if (bodyTemperature == null) {
                // Fourth input - Body Temperature
                bodyTemperature = value;
                outputArea.append("\n\tBody Temperature [" + bodyTemperature + "]℃");

                // Reset bodyTemperature for the next input
            } else if (respirationRate == null) {
                // Fifth input - Respiration Rate
                respirationRate = (int) value;
                outputArea.append("\n\tRespiration Rate [" + respirationRate + "]/minute");
                
                sbp = null;
                dbp = null;
                pulseRate = null;
                bodyTemperature = null;
                respirationRate = null;
                // Reset respirationRate for the next input
            } else {
                // Handle additional inputs or provide an error message if necessary
                outputArea.setText("Input exceeded expected data fields.");
            }
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid input. Please enter a number.");
        }
    }

    private void createBodytemp(Double input) {
    	descriptionArea.setText("   at GDS : Forehead (Temporal Artery) Thermometers:");
    	outputArea.setText("\tBody Temperature [ " + input + " ] ℃");
    	
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Vitalsign().setVisible(true);
            }
        });
    }
}
    
    
    