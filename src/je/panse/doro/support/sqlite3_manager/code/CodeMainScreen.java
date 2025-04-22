package je.panse.doro.support.sqlite3_manager.code;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List; // Required for List.of

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter; // Required for RowSorter
import javax.swing.SortOrder; // Required for SortOrder
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

// Assuming GDSEMR_frame exists in the accessible scope
// import je.panse.doro.GDSEMR_frame; // Example import

public class CodeMainScreen extends JFrame implements ActionListener {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JPanel southPanel;
    private DatabaseModel model; // Assumes DatabaseModel handles DB operations

    public CodeMainScreen() {
        // It's crucial that DatabaseModel methods use the correct column names
        model = new DatabaseModel();
        model.createDatabaseTable(); // Ensure this creates columns: Category, B_code, Diagnosis, reference
        model.createIndexOnBCode(); // Ensure this references B_code correctly

        setupFrame();
        setupTable();
        setupButtons();
        loadData(); // This calls model.getRecordsSortedByBCode() - check its SQL

        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Code/Disease Database");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE if it's not the main app exit point
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    private void setupTable() {
        String[] columnNames = {"Category", "B-code", "Diagnosis", "Reference"};
        // Make table cells non-editable by default
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false; // Cells are not directly editable
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        // Consider a more common font like "Arial" or "SansSerif" if "Consolas" isn't available everywhere
        table.setFont(new Font("Consolas", Font.PLAIN, 12)); // Slightly larger, non-bold for readability

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        // Sort by B-code (index 1) initially
        sorter.setSortKeys(List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING)));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Adjust column widths (these are preferred widths, layout managers can override)
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Category
        table.getColumnModel().getColumn(1).setPreferredWidth(80);  // B-code
        table.getColumnModel().getColumn(2).setPreferredWidth(400); // Diagnosis (likely needs most space)
        table.getColumnModel().getColumn(3).setPreferredWidth(120); // Reference
        setColumnAlignment();

        // Row click listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int selectedRowInView = table.getSelectedRow();
                int modelRow = table.convertRowIndexToModel(selectedRowInView);

                // Check modelRow validity (though >= 0 check usually suffices)
                if (modelRow >= 0 && modelRow < tableModel.getRowCount()) {
                    String bCode = (String) tableModel.getValueAt(modelRow, 1);
                    String diagnosis = (String) tableModel.getValueAt(modelRow, 2);
                    // Ensure values are not null before using them
                    if (bCode != null && diagnosis != null) {
                        String output = "\n # " + bCode + "\t:  " + diagnosis;
                        System.out.println("Selected: " + output.trim()); // Log selection
                        // Handle external dependency - ensure GDSEMR_frame is accessible
                        // GDSEMR_frame.setTextAreaText(7, output);
                    }
                }
            }
        });
    }

    private void setColumnAlignment() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        // Center-align the B-code column (index 1)
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        // Optional: Left-align other text columns if needed (default is usually LEFT)
         DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
         leftRenderer.setHorizontalAlignment(JLabel.LEFT);
         table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer); // Category
         table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer); // Diagnosis
         table.getColumnModel().getColumn(3).setCellRenderer(leftRenderer); // Reference
    }

    private void setupButtons() {
        southPanel = new JPanel(); // FlowLayout by default
        String[] buttonLabels = {"Add", "Delete", "Edit", "Refresh", "Exit"}; // Changed Find to Refresh
        for (String label : buttonLabels) {
            southPanel.add(createButton(label));
        }

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Enter search term and press Enter");
        // Removed center alignment for text field, usually left-aligned is standard
        searchField.setFont(new Font("Arial", Font.PLAIN, 14)); // Standard font

        // Action listener for Enter key press in search field
        searchField.addActionListener(e -> {
            findRecords(searchField.getText());
            // Keep the search text in the field after searching for reference
            // searchField.setText("");
        });

        southPanel.add(new JLabel("Search Diagnosis/Code:"));
        southPanel.add(searchField);
        add(southPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        // Optional: set font, margins, etc. for buttons
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setMargin(new java.awt.Insets(5, 10, 5, 10));
        return button;
    }

    private void loadData() {
        // Clear table before loading
        tableModel.setRowCount(0);
        System.out.println("Loading initial data...");
        try (ResultSet rs = model.getRecordsSortedByBCode()) { // Ensure this uses correct SQL
            while (rs != null && rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("Category"), rs.getString("B_code"),
                    rs.getString("Diagnosis"), rs.getString("reference")
                });
            }
            System.out.println("Data loaded. Rows: " + tableModel.getRowCount());
        } catch (SQLException e) {
            // Critical error if data can't be loaded initially
            showError("Fatal Error loading data: " + e.getMessage() + "\nPlease check DatabaseModel SQL and database file.");
            // Optionally disable components or close the window if unusable
             System.err.println("SQL Error in loadData: " + e.getMessage()); // Log detailed error
        } catch (Exception e) {
             // Catch other potential errors (e.g., NullPointerException if model is null)
            showError("Unexpected Error loading data: " + e.getMessage());
             System.err.println("General Error in loadData: " + e.getMessage());
        }
    }

     private void refreshData() {
        loadData(); // Reload all data
        searchField.setText(""); // Clear search field on refresh
        System.out.println("Data refreshed.");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if the source is the search field first (Enter key press)
        if (e.getSource() == searchField) {
            findRecords(searchField.getText());
        } else {
            // Handle button clicks
            String command = e.getActionCommand();
            System.out.println("Action performed: " + command); // Log button clicks
            switch (command) {
                case "Add" -> showAddDialog();
                case "Delete" -> deleteRecord();
                case "Edit" -> editRecord();
                case "Refresh" -> refreshData(); // Changed from Find button action
                case "Exit" -> dispose(); // Closes this window
                default -> System.err.println("Unhandled action command: " + command);
            }
        }
    }

    // This method now relies on the search field's ActionListener for execution.
    // The "Find" button was changed to "Refresh".
    private void findRecords(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            // If search text is empty, refresh to show all records
            refreshData();
            return;
        }
        System.out.println("Finding records containing: " + searchText);
        // Pass necessary parameters to the model's find method.
        // Ensure the model method searches CORRECT columns (e.g., B_code, Diagnosis) NOT "name".
        model.findAndDisplayRecords(searchText.trim(), "ALL", tableModel); // Check SQL in this method!
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            if (modelRow >= 0 && modelRow < tableModel.getRowCount()) {
                String bCodeToDelete = (String) tableModel.getValueAt(modelRow, 1);
                if (bCodeToDelete != null) {
                     int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete record with B-code: " + bCodeToDelete + "?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                     if (confirm == JOptionPane.YES_OPTION) {
                        System.out.println("Deleting record with B-code: " + bCodeToDelete);
                        model.deleteRecord(bCodeToDelete); // Ensure this uses correct SQL (WHERE B_code = ?)
                        loadData(); // Refresh data after deletion
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
             int modelRow = table.convertRowIndexToModel(selectedRow);
            if (modelRow >= 0 && modelRow < tableModel.getRowCount()) {
                // Retrieve current values from the table model
                String currentCategory = (String) tableModel.getValueAt(modelRow, 0);
                String currentBCode = (String) tableModel.getValueAt(modelRow, 1);
                String currentDiagnosis = (String) tableModel.getValueAt(modelRow, 2);
                String currentReference = (String) tableModel.getValueAt(modelRow, 3);

                // Create input fields for the dialog, pre-filled with current values
                JTextField categoryField = new JTextField(currentCategory, 20);
                JTextField bcodeField = new JTextField(currentBCode, 10);
                // Make B-code non-editable if it's the primary key and shouldn't be changed
                // bcodeField.setEditable(false);
                JTextField diagnosisField = new JTextField(currentDiagnosis, 30);
                JTextField referenceField = new JTextField(currentReference, 30);

                // Build the panel for the dialog
                JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5)); // Added gaps
                panel.add(new JLabel("Category:")); panel.add(categoryField);
                panel.add(new JLabel("B-code:")); panel.add(bcodeField);
                panel.add(new JLabel("Diagnosis:")); panel.add(diagnosisField);
                panel.add(new JLabel("Reference:")); panel.add(referenceField);

                // Show the dialog
                int result = JOptionPane.showConfirmDialog(this, panel, "Edit Entry (B-code: " + currentBCode + ")",
                                                         JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                // If user clicks OK, update the record in the database
                if (result == JOptionPane.OK_OPTION) {
                    String newCategory = categoryField.getText().trim();
                    String newBCode = bcodeField.getText().trim(); // Get potentially edited B-code
                    String newDiagnosis = diagnosisField.getText().trim();
                    String newReference = referenceField.getText().trim();

                    // Basic validation (optional)
                    if (newBCode.isEmpty() || newDiagnosis.isEmpty()) {
                        showError("B-code and Diagnosis cannot be empty.");
                        return; // Stay in edit dialog or handle differently
                    }

                    System.out.println("Updating record with original B-code: " + currentBCode);
                    // Pass the *original* B-code to identify the record, and the new values
                    model.updateRecord(
                        currentBCode,   // Key to find the record
                        newCategory,    // New value
                        newBCode,       // New value (handle potential change of key carefully in DBModel)
                        newDiagnosis,   // New value
                        newReference    // New value
                    ); // Ensure this uses correct SQL (UPDATE ... SET ... WHERE B_code = ?)
                    loadData(); // Refresh data after update
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showAddDialog() {
        // Create input fields for the dialog
        JTextField categoryField = new JTextField("General", 20); // Default category
        JTextField bcodeField = new JTextField(10);
        JTextField diagnosisField = new JTextField(30);
        JTextField referenceField = new JTextField("ICD-10", 30); // Default reference

        // Build the panel
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5)); // Added gaps
        panel.add(new JLabel("Category:")); panel.add(categoryField);
        panel.add(new JLabel("B-code:")); panel.add(bcodeField);
        panel.add(new JLabel("Diagnosis:")); panel.add(diagnosisField);
        panel.add(new JLabel("Reference:")); panel.add(referenceField);

        // Show the dialog
        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Entry",
                                                 JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // If user clicks OK, insert the new record
        if (result == JOptionPane.OK_OPTION) {
            String category = categoryField.getText().trim();
            String bcode = bcodeField.getText().trim();
            String diagnosis = diagnosisField.getText().trim();
            String reference = referenceField.getText().trim();

            // Basic validation
            if (bcode.isEmpty() || diagnosis.isEmpty()) {
                showError("B-code and Diagnosis cannot be empty.");
                 // Optionally re-show dialog or return without adding
                return;
            }

            System.out.println("Adding new record: B-code=" + bcode);
            // Ensure model.insertRecord uses correct SQL (INSERT INTO codedis (...) VALUES (...))
            model.insertRecord(category, bcode, diagnosis, reference);
            loadData(); // Refresh data after adding
        }
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread
        SwingUtilities.invokeLater(CodeMainScreen::new);
    }
}