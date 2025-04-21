package je.panse.doro.support.sqlite3_manager.icd10;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ICD10DatabaseEditor extends JFrame {

    private JTextField categoryField, codeField, titleField, commentField, findField;
    private JTextArea statusArea;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private Connection conn;
    private int selectedRow = -1; // To track the currently selected row for edit/delete

    private final String dbPath = "/home/migowj/git/ittia_ver_3.076/src/je/panse/doro/support/sqlite3_manager/icd10/icd10_data.db";

    public ICD10DatabaseEditor() {
        setTitle("ICD10 SQLite DB Editor");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Code:"));
        codeField = new JTextField();
        inputPanel.add(codeField);
        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Comment:"));
        commentField = new JTextField();
        inputPanel.add(commentField);
        inputPanel.add(new JLabel("Find Code:"));
        findField = new JTextField();
        inputPanel.add(findField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add New");
        addButton.addActionListener(this::addData);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::saveData);
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this::editData);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteData);
        JButton findButton = new JButton("Find");
        findButton.addActionListener(this::findData);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this::loadData);

        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(findButton);
        buttonPanel.add(refreshButton);

        // Table
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(dataTable);
        dataTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && dataTable.getSelectedRow() != -1) {
                selectedRow = dataTable.getSelectedRow();
                populateFields(selectedRow);
            }
        });

        // Status Area
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        JScrollPane statusScrollPane = new JScrollPane(statusArea);
        statusScrollPane.setPreferredSize(new Dimension(getWidth(), 100));

        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.WEST);
        add(statusScrollPane, BorderLayout.SOUTH);

        // Initialize Database Connection and Load Data
        connectDatabase();
        loadData(null);
    }

    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statusArea.append("Database connected: " + dbPath + "\n");
        } catch (SQLException e) {
            statusArea.append("Error connecting to database: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void closeDatabase() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                statusArea.append("Database connection closed.\n");
            }
        } catch (SQLException e) {
            statusArea.append("Error closing database connection: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void loadData(ActionEvent event) {
        statusArea.setText("");
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        selectedRow = -1;
        clearInputFields();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, category, code, title, comment FROM icd10_data")) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            tableModel.addColumn("ID"); // Changed from "RowID"
            for (int i = 1; i < columnCount; i++) { // Corrected loop condition
                tableModel.addColumn(metaData.getColumnName(i + 1)); // Adjusted column index
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                row[0] = rs.getInt("id"); // Get ID
                for (int i = 1; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1); // Adjusted column index
                }
                tableModel.addRow(row);
            }
            statusArea.append("Data loaded successfully.\n");

        } catch (SQLException e) {
            statusArea.append("Error loading data: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void addData(ActionEvent event) {
        String category = categoryField.getText().trim();
        String code = codeField.getText().trim();
        String title = titleField.getText().trim();
        String comment = commentField.getText().trim();

        if (category.isEmpty() || code.isEmpty() || title.isEmpty()) {
            statusArea.append("Category, Code, and Title cannot be empty.\n");
            return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO icd10_data (category, code, title, comment) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, category);
            pstmt.setString(2, code);
            pstmt.setString(3, title);
            pstmt.setString(4, comment);
            pstmt.executeUpdate();
            statusArea.append("New record added.\n");
            loadData(null); // Refresh the table
            clearInputFields();
        } catch (SQLException e) {
            statusArea.append("Error adding data: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void saveData(ActionEvent event) {
        if (selectedRow == -1) {
            statusArea.append("Please select a row to edit first.\n");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0); // Get ID
        String category = categoryField.getText().trim();
        String code = codeField.getText().trim();
        String title = titleField.getText().trim();
        String comment = commentField.getText().trim();

        if (category.isEmpty() || code.isEmpty() || title.isEmpty()) {
            statusArea.append("Category, Code, and Title cannot be empty.\n");
            return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE icd10_data SET category=?, code=?, title=?, comment=? WHERE id=?")) {
            pstmt.setString(1, category);
            pstmt.setString(2, code);
            pstmt.setString(3, title);
            pstmt.setString(4, comment);
            pstmt.setInt(5, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                statusArea.append("Record updated successfully.\n");
                loadData(null); // Refresh the table
                clearInputFields();
                selectedRow = -1;
            } else {
                statusArea.append("Failed to update record.\n");
            }
        } catch (SQLException e) {
            statusArea.append("Error updating data: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void editData(ActionEvent event) {
        if (selectedRow == -1) {
            statusArea.append("Please select a row to edit.\n");
            return;
        }
        // The fields are already populated when a row is selected.
        // The "Save" button will perform the actual update.
        statusArea.append("Ready to edit the selected row. Click 'Save' to apply changes.\n");
    }

    private void deleteData(ActionEvent event) {
        if (selectedRow == -1) {
            statusArea.append("Please select a row to delete.\n");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0); // Get ID

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM icd10_data WHERE id=?")) {
                pstmt.setInt(1, id);
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    statusArea.append("Record deleted successfully.\n");
                    loadData(null); // Refresh the table
                    clearInputFields();
                    selectedRow = -1;
                } else {
                    statusArea.append("Failed to delete record.\n");
                }
            } catch (SQLException e) {
                statusArea.append("Error deleting data: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
    }

    private void findData(ActionEvent event) {
        String findCode = findField.getText().trim();
        if (findCode.isEmpty()) {
            statusArea.append("Please enter a code to find.\n");
            return;
        }

        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        tableModel.addColumn("ID");
        tableModel.addColumn("Category");
        tableModel.addColumn("Code");
        tableModel.addColumn("Title");
        tableModel.addColumn("Comment");

        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT id, category, code, title, comment FROM icd10_data WHERE code LIKE ?")) {
            pstmt.setString(1, "%" + findCode + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getString("comment")
                };
                tableModel.addRow(row);
            }
            statusArea.append("Found " + tableModel.getRowCount() + " records matching '" + findCode + "'.\n");

        } catch (SQLException e) {
            statusArea.append("Error finding data: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void populateFields(int row) {
        if (row != -1) {
            categoryField.setText(tableModel.getValueAt(row, 1).toString());
            codeField.setText(tableModel.getValueAt(row, 2).toString());
            titleField.setText(tableModel.getValueAt(row, 3).toString());
            commentField.setText(tableModel.getValueAt(row, 4).toString());
        }
    }

    private void clearInputFields() {
        categoryField.setText("");
        codeField.setText("");
        titleField.setText("");
        commentField.setText("");
        findField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ICD10DatabaseEditor().setVisible(true));
    }

    @Override
    protected void finalize() throws Throwable {
        closeDatabase();
        super.finalize();
    }
}