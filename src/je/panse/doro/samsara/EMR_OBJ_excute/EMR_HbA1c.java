package je.panse.doro.samsara.EMR_OBJ_excute;

import javax.swing.*;

import je.panse.doro.GDSEMR_frame;

import java.awt.*;
import java.awt.event.ActionEvent;

public class EMR_HbA1c extends JFrame {
    private JTextArea textArea;
    private JTextField[] textFields = new JTextField[3];
    private final String[] buttonNames = {"Clear", "Save", "Save and Quit"};
    private final String[] labels = {"FBS / PP2 time", "Glucose mg/dL", "HbA1c %"};

    // Constructor
    public EMR_HbA1c() {
        initializeFrame();
        setupUIComponents();
        setupButtons();
        setupListeners();
    }

    private void initializeFrame() {
        setTitle("HbA1c EMR");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void setupUIComponents() {
        textArea = new JTextArea(4, 20);
        add(textArea, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(3, 2));
        
        for (int i = 0; i < textFields.length; i++) {
            JLabel label = new JLabel(labels[i]);
            textFields[i] = new JTextField();
            styleTextField(textFields[i]);
            
            centerPanel.add(label);
            centerPanel.add(textFields[i]);
        }
        
        return centerPanel;
    }

    private void styleTextField(JTextField textField) {
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 30));
        textField.setHorizontalAlignment(JTextField.CENTER);
    }

    private void setupButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        for (String buttonName : buttonNames) {
            JButton button = new JButton(buttonName);
            button.addActionListener(e -> handleButtonAction(buttonName));
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void handleButtonAction(String buttonName) {
        switch (buttonName) {
            case "Clear":
                clear();
                break;
            case "Save":
                save();
                break;
            case "Save and Quit":
                save();
                dispose();
                break;
        }
    }
    
    private void clear() {
        textArea.setText("");
        for (JTextField textField : textFields) {
            textField.setText("");
        }
    }

    private void save() {
        // Implement the saving logic here.
    }
    
    private void setupListeners() {
        for (int i = 0; i < textFields.length; i++) {
            final int currentFieldIndex = i;
            textFields[i].addActionListener(e -> handleTextFieldAction(currentFieldIndex));
        }
    }

    private void handleTextFieldAction(int currentFieldIndex) {
        String sampleTime = "FBS";
        String textFieldText = textFields[currentFieldIndex].getText();

        if (currentFieldIndex == 0) {
            if (!textFieldText.equals("0")) {
                sampleTime = "PP" + textFieldText;
            }
            textArea.append("   " + sampleTime);
        } else if (currentFieldIndex == 1) {
            textArea.append("  [    " + textFieldText + "   ] mg/dL");
        } else if (currentFieldIndex == 2) {
            textArea.append("   HbA1c       [    " + textFieldText + "   ] %\n");
            double hba1cDouble = Double.parseDouble(textFieldText);
            hba1cCalc(hba1cDouble);
            Timer timer = new Timer(5000, e -> {
                save();
                dispose();
            });
            timer.setRepeats(false); // Ensure the timer only fires once
            timer.start();
        }

        textFields[currentFieldIndex].setText("");

        if (currentFieldIndex < textFields.length - 1) {
            textFields[currentFieldIndex + 1].requestFocus();
        }
    }
    // Calculate the HbA1c value and update the text area
    private void hba1cCalc(double hba1c_perc) {
        double ifcc_hba1c_mmolmol = (hba1c_perc - 2.15) * 10.929;
        double eag_mgdl = (28.7 * hba1c_perc) - 46.7;
        double eag_mmoll = eag_mgdl / 18.01559;

        String hba1cP = String.format(
        		"\n\tIFCC HbA1c: [  %.0f  ] mmol/mol"
        		+ "\n\teAG: [  %.0f  ] mg/dL"
        		+ "\n\teAG: [  %.2f  ] mmol/l\n",
                ifcc_hba1c_mmolmol, eag_mgdl, eag_mmoll);
        textArea.append(hba1cP);
    }

    public static void getGlucoseControlStatus(double HbA1c) {
        String status;
        if (HbA1c > 9.0) {
            status = "Very poor";
        } else if (HbA1c >= 8.5 && HbA1c <= 9.0) {
            status = "Poor";
        } else if (HbA1c >= 7.5 && HbA1c < 8.5) {
            status = "Fair";
        } else if (HbA1c >= 6.5 && HbA1c < 7.5) {
            status = "Good";
        } else {
            status = "Excellent";
        }
        String message = String.format("\n...now [ %s ] controlled glucose status", status);
        GDSEMR_frame.setTextAreaText(8, message);
        System.out.println(message);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EMR_HbA1c emr = new EMR_HbA1c();
            emr.pack();
            emr.setLocationRelativeTo(null);
            emr.setVisible(true);
        });
    }
}
