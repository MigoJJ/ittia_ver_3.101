package je.panse.doro.support.sqlite3_manager.icd10.editor;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import je.panse.doro.support.sqlite3_manager.icd10.editor.components.CustomButtonPanel;
import je.panse.doro.support.sqlite3_manager.icd10.editor.components.InputPanel; // Make sure InputPanel exists

public class ICD10DatabaseEditor extends JFrame {
    private DatabaseManager dbManager;
    private TableHandler tableHandler;
    private CustomButtonPanel buttonPanel;
    private InputPanel inputPanel;

    public ICD10DatabaseEditor() {
        initializeFrame(); // You mentioned this earlier
        initializeComponents();
        setupLayout();   // Assuming you have this method to set up layout
        dbManager = new DatabaseManager();
        loadInitialData();
    }

    private void initializeFrame() {
        setTitle("ICD-10 Database Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Example size
        setLocationRelativeTo(null); // Center the frame
    }

    private void initializeComponents() {
        inputPanel = new InputPanel(); // Make sure InputPanel is defined
        tableHandler = new TableHandler();
        buttonPanel = new CustomButtonPanel(this, tableHandler, dbManager);
    }

    private void setupLayout() {
        // Define your layout manager and add components here
        // Example using BorderLayout:
        // getContentPane().setLayout(new BorderLayout());
        // getContentPane().add(inputPanel, BorderLayout.NORTH);
        // getContentPane().add(tableHandler.getDataTable(), BorderLayout.CENTER); // Assuming getTable() or similar
        // getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack(); // Adjusts window size to fit components
    }

    private void loadInitialData() {
        System.out.println("Loading initial data...");
        // Add your database interaction logic here using dbManager
        // and potentially update the table using tableHandler
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ICD10DatabaseEditor().setVisible(true));
    }
}