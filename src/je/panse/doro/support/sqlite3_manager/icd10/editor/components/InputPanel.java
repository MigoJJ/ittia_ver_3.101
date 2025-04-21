package je.panse.doro.support.sqlite3_manager.icd10.editor.components;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel {
    private JTextField categoryField, codeField, titleField, commentField;
    
    public InputPanel() {
        setLayout(new GridLayout(6, 2));
        initializeFields();
    }
    
    private void initializeFields() { /* field creation logic */ }
}
