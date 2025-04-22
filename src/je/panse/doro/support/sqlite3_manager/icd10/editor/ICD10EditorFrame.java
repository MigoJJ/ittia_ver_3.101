package je.panse.doro.support.sqlite3_manager.icd10.editor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ICD10EditorFrame extends JFrame {

    private DatabaseManager dbManager;
    private InputPanel inputPanel;
    private ButtonPanel buttonPanel;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;

    public ICD10EditorFrame() {
        super("ICD-10 Database Editor"); // Window title
        dbManager = new DatabaseManager();

        initComponents();
        layoutComponents();
        attachListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(800, 600); // Set initial size
        pack(); // Adjusts size to fit components
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);

        // Initial data load
        loadData();
    }

    private void initComponents() {
        inputPanel = new InputPanel();
        buttonPanel = new ButtonPanel();

        // Initialize table model (columns will be set by loadData)
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only one row selection
        dataTable.setAutoCreateRowSorter(true); // Enable basic sorting by clicking headers
        tableScrollPane = new JScrollPane(dataTable);
        tableScrollPane.setPreferredSize(new Dimension(750, 400)); // Give table reasonable default size
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10)); // Main layout with gaps

        // Add padding around the main content pane
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void attachListeners() {
        // Button Actions
        buttonPanel.addLoadListener(e -> loadData());
        buttonPanel.addAddListener(e -> addRecord());
        buttonPanel.addUpdateListener(e -> updateRecord());
        buttonPanel.addDeleteListener(e -> deleteRecord());
        buttonPanel.addClearListener(e -> clearInputFields());

        // Table Selection Action
        dataTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Process event only when selection finalized
                handleTableSelection();
            }
        });
    }

    private void loadData() {
        // Load data in a background thread if it might take time (optional for simple SQLite)
        // SwingWorker is a good option here for responsiveness on large datasets.
        // For simplicity, we'll do it directly here.
        tableModel = dbManager.loadData();
        dataTable.setModel(tableModel);
         // After loading, no row is selected, so disable edit/delete buttons
        buttonPanel.setEditDeleteEnabled(false);
        clearInputFields(); // Also clear fields after loading
        System.out.println("Data loaded into table.");
    }

    private void addRecord() {
        String code = inputPanel.getCode();
        String description = inputPanel.getDescription();

        if (code.trim().isEmpty()) {
             JOptionPane.showMessageDialog(this, "ICD Code cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
             return;
        }


        boolean success = dbManager.addRecord(code, description);
        if (success) {
            loadData(); // Refresh table data
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Record added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
             // Error message already shown by DatabaseManager
             // JOptionPane.showMessageDialog(this, "Failed to add record.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRecord() {
        int selectedRow = dataTable.getSelectedRow();
         if (selectedRow == -1) {
             JOptionPane.showMessageDialog(this, "Please select a record to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
             return;
         }

        int id = inputPanel.getSelectedId(); // Get ID from the (read-only) ID field
         if (id == -1) {
              JOptionPane.showMessageDialog(this, "Invalid ID selected. Cannot update.", "Error", JOptionPane.ERROR_MESSAGE);
              return;
         }

        String code = inputPanel.getCode();
        String description = inputPanel.getDescription();

        if (code.trim().isEmpty()) {
             JOptionPane.showMessageDialog(this, "ICD Code cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
             return;
        }

        boolean success = dbManager.updateRecord(id, code, description);
        if (success) {
            loadData(); // Refresh table data
             // Optional: re-select the updated row if needed (more complex)
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Error message possibly shown by DatabaseManager or record not found
             // JOptionPane.showMessageDialog(this, "Failed to update record.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRecord() {
        int selectedRow = dataTable.getSelectedRow();
         if (selectedRow == -1) {
             JOptionPane.showMessageDialog(this, "Please select a record to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
             return;
         }

         int id = inputPanel.getSelectedId(); // Get ID from the (read-only) ID field
         if (id == -1) {
              JOptionPane.showMessageDialog(this, "Invalid ID selected. Cannot delete.", "Error", JOptionPane.ERROR_MESSAGE);
              return;
         }


        // Confirmation is handled within dbManager.deleteRecord
        boolean success = dbManager.deleteRecord(id);
        if (success) {
            loadData(); // Refresh table data
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Error message possibly shown by DatabaseManager or user cancelled
            // JOptionPane.showMessageDialog(this, "Failed to delete record or deletion cancelled.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearInputFields() {
        inputPanel.clearFields();
         dataTable.clearSelection(); // Deselect any selected row in the table
         buttonPanel.setEditDeleteEnabled(false); // Disable edit/delete after clearing
    }

    private void handleTableSelection() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            // Convert view row index to model row index in case of sorting
            int modelRow = dataTable.convertRowIndexToModel(selectedRow);

            // Assuming columns are ID, Code, Description in the model (order matters!)
            // Adjust indices based on your actual table model structure
            Object idObj = tableModel.getValueAt(modelRow, 0); // ID assumed at index 0
            Object codeObj = tableModel.getValueAt(modelRow, 1); // Code assumed at index 1
            Object descObj = tableModel.getValueAt(modelRow, 2); // Description assumed at index 2

            inputPanel.setIdText(idObj != null ? idObj.toString() : "");
            inputPanel.setCode(codeObj != null ? codeObj.toString() : "");
            inputPanel.setDescription(descObj != null ? descObj.toString() : "");

            // Enable Update and Delete buttons when a row is selected
            buttonPanel.setEditDeleteEnabled(true);
        } else {
            // No row selected, disable Update and Delete buttons
            buttonPanel.setEditDeleteEnabled(false);
            // Optionally clear fields when selection is lost, or leave them as is
            // clearInputFields(); // Uncomment if you want fields cleared on deselection
        }
    }
}