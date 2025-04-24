package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class InputPanel {
    private static final Logger LOGGER = Logger.getLogger(InputPanel.class.getName());
    private JPanel panel;
    private JTextField[] inputFields;
    private JTextField searchField;

    public InputPanel() {
        panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search panel (top)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(120, 25));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);

        // Add Enter key listener for search
        searchField.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            LOGGER.info("Enter pressed in search field with text: " + searchText);
            if (!searchText.isEmpty()) {
                triggerSearch();
            } else {
                JOptionPane.showMessageDialog(panel, "Please enter a search term");
            }
        });

        // Input fields panel (vertical alignment with right-aligned labels)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        inputFields = new JTextField[5];
        String[] labels = {"ID:", "Code:", "Category:", "Description:", "Details:"};
        for (int i = 0; i < 5; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.0;
            gbc.anchor = GridBagConstraints.EAST;
            inputPanel.add(label, gbc);

            inputFields[i] = new JTextField();
            inputFields[i].setPreferredSize(new Dimension(150, 25));
            inputFields[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            if (i == 0) { // Make ID field read-only
                inputFields[i].setEditable(false);
            }
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            inputPanel.add(inputFields[i], gbc);
        }

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
    }

    private void triggerSearch() {
        TableManager tableManager = getTableManager();
        DatabaseManager dbManager = getDatabaseManager();
        if (tableManager != null && dbManager != null) {
            String searchText = searchField.getText().trim();
            tableManager.searchData(dbManager, searchText);
        } else {
            LOGGER.warning("TableManager or DatabaseManager not found");
            JOptionPane.showMessageDialog(panel, "Search functionality unavailable");
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public String[] getInputData() {
        String[] data = new String[5];
        for (int i = 0; i < 5; i++) {
            data[i] = inputFields[i].getText().trim();
        }
        return data;
    }

    public boolean validateInput() {
        String code = inputFields[1].getText().trim();
        String category = inputFields[2].getText().trim();
        String description = inputFields[3].getText().trim();

        // Log input values for debugging
        LOGGER.info("Validating input - Code: " + code + ", Category: " + category + ", Description: " + description);

        // Check required fields
        if (code.isEmpty() || category.isEmpty() || description.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Please fill in all required fields:");
            if (code.isEmpty()) errorMsg.append(" Code");
            if (category.isEmpty()) errorMsg.append(" Category");
            if (description.isEmpty()) errorMsg.append(" Description");
            LOGGER.warning("Validation failed: " + errorMsg.toString());
            JOptionPane.showMessageDialog(panel, errorMsg.toString());
            return false;
        }

        // Normalize code: convert to uppercase, replace hyphen with dot
        code = code.toUpperCase().replace('-', '.');

        // Validate ICD-10 code format (e.g., A00-Z99, with optional decimal)
        // Accepts codes like A12, E11.9, Z99.123, Z998 (normalized to Z99.8)
        if (!code.matches("[A-Z][0-9]{2}(\\.[0-9]{0,3})?")) {
            // Try normalizing codes like Z998 to Z99.8
            if (code.matches("[A-Z][0-9]{3,5}")) {
                String normalized = code.substring(0, 3) + (code.length() > 3 ? "." + code.substring(3) : "");
                if (normalized.matches("[A-Z][0-9]{2}(\\.[0-9]{1,3})?")) {
                    code = normalized;
                } else {
                    LOGGER.warning("Validation failed: Invalid ICD-10 code format: " + code);
                    JOptionPane.showMessageDialog(panel, 
                        "Invalid ICD-10 code format. Use formats like A12, E11.9, or Z99.123.");
                    return false;
                }
            } else {
                LOGGER.warning("Validation failed: Invalid ICD-10 code format: " + code);
                JOptionPane.showMessageDialog(panel, 
                    "Invalid ICD-10 code format. Use formats like A12, E11.9, or Z99.123.");
                return false;
            }
        }

        // Update code field with normalized value
        inputFields[1].setText(code);
        return true;
    }

    public void clearFields() {
        for (JTextField field : inputFields) {
            field.setText("");
        }
        searchField.setText("");
    }

    public void populateFields(String[] data) {
        if (data != null && data.length == 5) {
            for (int i = 0; i < 5; i++) {
                inputFields[i].setText(data[i] != null ? data[i] : "");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to edit");
        }
    }

    protected TableManager getTableManager() {
        Container parent = panel.getParent();
        while (parent != null && !(parent instanceof ICDDiagnosisManager)) {
            parent = parent.getParent();
        }
        return parent != null ? ((ICDDiagnosisManager) parent).getTableManager() : null;
    }

    protected DatabaseManager getDatabaseManager() {
        Container parent = panel.getParent();
        while (parent != null && !(parent instanceof ICDDiagnosisManager)) {
            parent = parent.getParent();
        }
        return parent != null ? ((ICDDiagnosisManager) parent).getDatabaseManager() : null;
    }
}