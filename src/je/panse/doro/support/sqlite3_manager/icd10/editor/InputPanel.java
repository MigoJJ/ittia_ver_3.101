package je.panse.doro.support.sqlite3_manager.icd10.editor;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    private JTextField codeField;
    private JTextField ICDdiseaseField;
    private JTextField descriptionField;

    public InputPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ICD Code Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ICD Code:"), gbc);
        gbc.gridx = 1;
        codeField = new JTextField(10);
        add(codeField, gbc);

        // ICD Disease Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("ICD Disease:"), gbc);
        gbc.gridx = 1;
        ICDdiseaseField = new JTextField(20);
        add(ICDdiseaseField, gbc);

        // Description Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(30);
        add(descriptionField, gbc);
    }

    public String getCode() {
        return codeField.getText().trim();
    }

    public String getICDdisease() {
        return ICDdiseaseField.getText().trim();
    }

    public String getDescription() {
        return descriptionField.getText().trim();
    }

    public void setCode(String code) {
        codeField.setText(code);
    }

    public void setICDdisease(String icdDisease) {
        ICDdiseaseField.setText(icdDisease);
    }

    public void setDescription(String description) {
        descriptionField.setText(description);
    }

    public void clearFields() {
        codeField.setText("");
        ICDdiseaseField.setText("");
        descriptionField.setText("");
    }
}