package je.panse.doro.support.sqlite3_manager.icd10.editor10;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {

    private JTextField idField;
    private JTextField categoryField;
    private JTextField codeField;
    private JTextField descriptionField;

    private JLabel idLabel;
    private JLabel categoryLabel;
    private JLabel codeLabel;
    private JLabel descriptionLabel;

    private static final Font KOREAN_FONT = new Font("Malgun Gothic", Font.PLAIN, 14);

    public InputPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize Labels and Fields with Korean font
        idLabel = new JLabel("ID:");
        idLabel.setFont(KOREAN_FONT);
        idField = new JTextField(5);
        idField.setEditable(false);
        idField.setBackground(Color.LIGHT_GRAY);
        idField.setFont(KOREAN_FONT);

        categoryLabel = new JLabel("카테고리:");
        categoryLabel.setFont(KOREAN_FONT);
        categoryField = new JTextField(20);
        categoryField.setFont(KOREAN_FONT);

        codeLabel = new JLabel("ICD 코드:");
        codeLabel.setFont(KOREAN_FONT);
        codeField = new JTextField(15);
        codeField.setFont(KOREAN_FONT);

        descriptionLabel = new JLabel("설명:");
        descriptionLabel.setFont(KOREAN_FONT);
        descriptionField = new JTextField(30);
        descriptionField.setFont(KOREAN_FONT);

        // Layout setup
        gbc.gridx = 0; gbc.gridy = 0;
        add(idLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 0.1;
        add(idField, gbc);

        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        add(categoryLabel, gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 0.9;
        add(categoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        add(codeLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(codeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        add(descriptionLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(descriptionField, gbc);

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("레코드 정보"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    // Getters
    public String getCategory() { return categoryField.getText(); }
    public String getCode() { return codeField.getText(); }
    public String getDescription() { return descriptionField.getText(); }
    public String getIdText() { return idField.getText(); }

    // Setters
    public void setCategory(String category) { categoryField.setText(category); }
    public void setCode(String code) { codeField.setText(code); }
    public void setDescription(String description) { descriptionField.setText(description); }
    public void setIdText(String id) { idField.setText(id); }

    // Utility
    public void clearFields() {
        idField.setText("");
        categoryField.setText("");
        codeField.setText("");
        descriptionField.setText("");
        categoryField.requestFocusInWindow();
    }

    public int getSelectedId() {
        try {
            return Integer.parseInt(idField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
