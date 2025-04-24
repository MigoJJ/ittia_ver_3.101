package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class ICDDiagnosisManager extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(ICDDiagnosisManager.class.getName());
    private DatabaseManager dbManager;
    private TableManager tableManager;
    private InputPanel inputPanel;
    private ButtonPanel buttonPanel;

    public ICDDiagnosisManager() {
        dbManager = new DatabaseManager();
        setTitle("ICD-10 Diagnosis Manager");
        setSize(900, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Initialize components
        inputPanel = new InputPanel();
        buttonPanel = new ButtonPanel();
        tableManager = new TableManager(inputPanel, buttonPanel);

        // Add components to frame
        add(inputPanel.getPanel(), BorderLayout.NORTH);
        add(tableManager.getScrollPane(), BorderLayout.CENTER);
        add(buttonPanel.getPanel(), BorderLayout.SOUTH);

        // Load initial data
        try {
            tableManager.loadTableData(dbManager);
        } catch (Exception ex) {
            LOGGER.severe("Failed to load initial data: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Failed to load data: " + ex.getMessage());
        }

        // Set up button actions
        buttonPanel.getClearButton().addActionListener(e -> {
            LOGGER.info("Clear button clicked");
            inputPanel.clearFields();
        });
        buttonPanel.getFindButton().addActionListener(e -> {
            LOGGER.info("Find button clicked");
            String searchText = inputPanel.getSearchText().trim();
            if (!searchText.isEmpty()) {
                tableManager.searchData(dbManager, searchText);
            } else {
                tableManager.loadTableData(dbManager);
            }
        });
        buttonPanel.getAddButton().addActionListener(e -> {
            LOGGER.info("Add button clicked");
            if (inputPanel.validateInput()) {
                try {
                    dbManager.addNewRecord(inputPanel.getInputData());
                    tableManager.loadTableData(dbManager);
                    inputPanel.clearFields();
                } catch (Exception ex) {
                    LOGGER.severe("Error adding record: " + ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Error adding record: " + ex.getMessage());
                }
            }
        });
        buttonPanel.getDeleteButton().addActionListener(e -> {
            LOGGER.info("Delete button clicked");
            String code = tableManager.getSelectedCode();
            if (code == null) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
                return;
            }
            try {
                dbManager.deleteSelectedRow(code);
                tableManager.loadTableData(dbManager);
                inputPanel.clearFields();
            } catch (Exception ex) {
                LOGGER.severe("Error deleting record: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error deleting record: " + ex.getMessage());
            }
        });
        buttonPanel.getSaveButton().addActionListener(e -> {
            LOGGER.info("Save button clicked");
            String code = tableManager.getSelectedCode();
            if (code == null) {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.");
                return;
            }
            if (!inputPanel.validateInput()) {
                return; // validateInput() shows error message
            }
            try {
                dbManager.saveChanges(code, inputPanel.getInputData());
                tableManager.loadTableData(dbManager);
                inputPanel.clearFields();
            } catch (Exception ex) {
                LOGGER.severe("Error saving changes: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error saving changes: " + ex.getMessage());
            }
        });
        buttonPanel.getQuitButton().addActionListener(e -> {
            LOGGER.info("Quit button clicked");
            System.exit(0);
        });
        buttonPanel.getAppendIttiaButton().addActionListener(e -> {
            LOGGER.info("Append Ittia button clicked");
            try {
                appendIttiaData();
            } catch (Exception ex) {
                LOGGER.severe("Error executing Append Ittia: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error executing Append Ittia: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    private void appendIttiaData() {
        // Placeholder: Log input data from InputPanel
        String[] data = inputPanel.getInputData();
        String logMessage = String.format("Appending Ittia data - ID: %s, Code: %s, Category: %s, Description: %s, Details: %s",
                data[0], data[1], data[2], data[3], data[4]);
        LOGGER.info(logMessage);
        JOptionPane.showMessageDialog(this, "Append Ittia executed: " + logMessage);
        // TODO: Replace with specific method functionality
    }

    public DatabaseManager getDatabaseManager() {
        return dbManager;
    }

    public TableManager getTableManager() {
        return tableManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ICDDiagnosisManager::new);
    }
}