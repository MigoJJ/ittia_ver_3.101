package je.panse.doro.support.sqlite3_manager.icd10.editor;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import je.panse.doro.support.sqlite3_manager.icd10.ICD10DatabaseEditor;

public class ICD10DatabaseEditor extends JFrame {
    private DatabaseManager dbManager;
    private TableHandler tableHandler;
    private ButtonPanel buttonPanel;
    private InputPanel inputPanel;

    public ICD10DatabaseEditor() {
        initializeFrame();
        initializeComponents();
        setupLayout();
        dbManager = new DatabaseManager();
        loadInitialData();
    }
    
    private void initializeComponents() {
        inputPanel = new InputPanel();
        tableHandler = new TableHandler();
        buttonPanel = new ButtonPanel(this, tableHandler, dbManager);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ICD10DatabaseEditor().setVisible(true));
    }
}
