package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import java.awt.*;

public class ICDDiagnosisManager extends JFrame {
    private TableManager tableManager;
    private InputPanel inputPanel;
    private ButtonPanel buttonPanel;
    private DatabaseManager dbManager;

    public ICDDiagnosisManager() {
        setTitle("ICD-10 Diagnosis Manager");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        dbManager = new DatabaseManager();
        inputPanel = new InputPanel();
        tableManager = new TableManager(inputPanel); // Pass inputPanel to TableManager
        buttonPanel = new ButtonPanel();

        // Add components to frame
        add(tableManager.getScrollPane(), BorderLayout.CENTER);
        add(inputPanel.getPanel(), BorderLayout.NORTH);
        add(buttonPanel.getPanel(), BorderLayout.SOUTH);

        // Initialize table data
        tableManager.loadTableData(dbManager);

        // Connect button actions
        setupButtonActions();
    }

    private void setupButtonActions() {
        buttonPanel.getClearButton().addActionListener(e -> inputPanel.clearFields());
        buttonPanel.getFindButton().addActionListener(e -> tableManager.searchData(dbManager, inputPanel.getSearchText()));
        buttonPanel.getEditButton().addActionListener(e -> inputPanel.populateFields(tableManager.getSelectedRowData()));
        buttonPanel.getAddButton().addActionListener(e -> {
            dbManager.addNewRecord(inputPanel.getInputData());
            tableManager.loadTableData(dbManager);
            inputPanel.clearFields();
        });
        buttonPanel.getDeleteButton().addActionListener(e -> {
            dbManager.deleteSelectedRow(tableManager.getSelectedCode());
            tableManager.loadTableData(dbManager);
        });
        buttonPanel.getSaveButton().addActionListener(e -> {
            dbManager.saveChanges(tableManager.getSelectedCode(), inputPanel.getInputData());
            tableManager.loadTableData(dbManager);
            inputPanel.clearFields();
        });
        buttonPanel.getQuitButton().addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ICDDiagnosisManager().setVisible(true);
        });
    }
}