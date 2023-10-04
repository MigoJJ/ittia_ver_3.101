package je.panse.doro.samsara.EMR_OBJ_excute;

import javax.swing.*;
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

    private void setupListeners() {
        for (int i = 0; i < textFields.length; i++) {
            final int currentFieldIndex = i;
            textFields[i].addActionListener(e -> handleTextFieldAction(currentFieldIndex));
        }
    }

    private void handleTextFieldAction(int currentFieldIndex) {
        String textFieldText = textFields[currentFieldIndex].getText();
        textArea.append(textFieldText + "\n");
        textFields[currentFieldIndex].setText("");

        if (currentFieldIndex == 2) {
            Timer timer = new Timer(3000, e -> {
                save();
                dispose();
            });
            timer.setRepeats(false); // ensure the timer only fires once
            timer.start();
        } else if (currentFieldIndex < textFields.length - 1) {
            textFields[currentFieldIndex + 1].requestFocus();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EMR_HbA1c emr = new EMR_HbA1c();
            emr.pack();
            emr.setLocationRelativeTo(null);
            emr.setVisible(true);
        });
    }
}
