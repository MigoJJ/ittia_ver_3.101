package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import java.awt.*;

public class InputPanel {
    private JPanel panel;
    private JTextField[] inputFields;
    private JTextField searchField;

    public InputPanel() {
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search panel (top)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(25);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);

        // Input fields panel (vertical alignment with right-aligned labels)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        inputFields = new JTextField[4];
        String[] labels = {"Code:", "Code with Separator:", "Short:", "Long Description:"};
        for (int i = 0; i < 4; i++) {
            // Label
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.0;
            gbc.anchor = GridBagConstraints.EAST;
            inputPanel.add(label, gbc);

            // Text field
            inputFields[i] = new JTextField(30);
            inputFields[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            inputPanel.add(inputFields[i], gbc);
        }

        // Add panels to main panel
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public String[] getInputData() {
        String[] data = new String[4];
        for (int i = 0; i < 4; i++) {
            data[i] = inputFields[i].getText();
        }
        return data;
    }

    public void clearFields() {
        for (JTextField field : inputFields) {
            field.setText("");
        }
        searchField.setText("");
    }

    public void populateFields(String[] data) {
        if (data != null && data.length == 4) {
            for (int i = 0; i < 4; i++) {
                inputFields[i].setText(data[i]);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to edit");
        }
    }
}