package je.panse.doro.samsara.EMR_OBJ_Vitalsign;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class Vitalsign extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;
    private JButton clearButton, saveButton, quitButton;
    private Set<String> validInputs;

    public Vitalsign() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Vital Sign Tracker");
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        initializeValidInputs();
        addComponents();
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

    private void addComponents() {
        inputField = new JTextField(20);
        inputField.setMaximumSize(inputField.getPreferredSize());
        add(inputField);

        outputArea = new JTextArea(5, 20);
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        add(outputScrollPane);

        inputField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleInput(inputField.getText().trim());
                    inputField.setText("");
                }
            }
        });

        addButtons();
    }

    private void addButtons() {
        clearButton = new JButton("Clear");
        saveButton = new JButton("Save");
        quitButton = new JButton("Quit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);
        add(buttonPanel);

        clearButton.addActionListener(e -> clearForm());
        saveButton.addActionListener(e -> saveData());
        quitButton.addActionListener(e -> dispose());
    }

    private void handleInput(String input) {
        if (validInputs.contains(input)) {
            outputArea.setText("Input: " + input);
        } else {
            outputArea.setText("Invalid Input: " + input);
        }
    }

    private void clearForm() {
        inputField.setText("");
        outputArea.setText("");
    }

    private void saveData() {
        // Placeholder for save logic
        System.out.println("Data saved: " + outputArea.getText());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Vitalsign().setVisible(true));
    }
}
