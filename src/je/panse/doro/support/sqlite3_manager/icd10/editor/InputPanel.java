package je.panse.doro.support.sqlite3_manager.icd10.editor;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {

    // --- Adjust based on your columns ---
    private JTextField idField; // Read-only field to show selected ID
    private JTextField codeField;
    private JTextField descriptionField;

    private JLabel idLabel;
    private JLabel codeLabel;
    private JLabel descriptionLabel;
    // -----------------------------------

    public InputPanel() {
        // Use GridBagLayout for flexible positioning
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // --- Initialize Labels and Fields (Adjust as needed) ---
        idLabel = new JLabel("ID:");
        idField = new JTextField(5); // Small field for ID
        idField.setEditable(false); // Make ID field read-only
        idField.setBackground(Color.LIGHT_GRAY); // Indicate read-only status

        codeLabel = new JLabel("ICD Code:");
        codeField = new JTextField(15); // Adjust width as needed

        descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(30); // Adjust width as needed
        // -----------------------------------------------------

        // Add components using GridBagLayout
        // Row 0: ID
        gbc.gridx = 0; gbc.gridy = 0;
        add(idLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 0.1; // Less horizontal space for ID
        add(idField, gbc);

        // Row 0: Code (next to ID)
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; // Reset fill and weight
        add(codeLabel, gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 0.9; // More horizontal space for Code
        add(codeField, gbc);

        // Row 1: Description (spans across multiple columns)
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        add(descriptionLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; // Span across 3 columns (ID field, Code label, Code field)
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; // Take remaining horizontal space
        add(descriptionField, gbc);

         setBorder(BorderFactory.createCompoundBorder(
             BorderFactory.createTitledBorder("Record Details"),
             BorderFactory.createEmptyBorder(5, 5, 5, 5) // Inner padding
         ));
    }

    // --- Getters ---
    public String getCode() {
        return codeField.getText();
    }

    public String getDescription() {
        return descriptionField.getText();
    }

     public String getIdText() {
        return idField.getText();
    }

    // --- Setters ---
    public void setCode(String code) {
        codeField.setText(code);
    }

    public void setDescription(String description) {
        descriptionField.setText(description);
    }

    public void setIdText(String id) {
        idField.setText(id);
    }

    // --- Utility ---
    public void clearFields() {
        idField.setText("");
        codeField.setText("");
        descriptionField.setText("");
        codeField.requestFocusInWindow(); // Set focus back to the first editable field
    }

     // Helper to parse the ID field safely
     public int getSelectedId() {
        try {
            return Integer.parseInt(idField.getText());
        } catch (NumberFormatException e) {
            return -1; // Return an invalid ID if parsing fails or field is empty
        }
    }
}