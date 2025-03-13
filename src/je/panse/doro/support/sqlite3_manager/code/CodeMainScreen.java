package je.panse.doro.support.sqlite3_manager.code;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;

import je.panse.doro.entry.EntryDir;

public class CodeMainScreen extends JFrame implements ActionListener {

    private DefaultTableModel tableModel;
    private JTable table;
    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";
    private JTextField searchField;


    public CodeMainScreen() {
        setupFrame();
        setupTable();
        setupButtons();
        createDatabaseTable();
        loadData();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Code/Disease Database");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    private void setupTable() {
        String[] columnNames = {"Category", "B-code", "name", "reference"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Consolas", Font.BOLD, 11));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(600);
        table.getColumnModel().getColumn(3).setPreferredWidth(300);
        setColumnAlignment();
    }


    private void setColumnAlignment() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    }
    
    private void setupButtons() {
        JPanel southPanel = new JPanel();
        String[] buttonLabels = {"Add", "Delete", "Edit", "Find", "Exit"};
        for (String label : buttonLabels) {
            southPanel.add(createButton(label));
        }

        // Add search field
        searchField = new JTextField(20);
        searchField.addActionListener(e -> findRecords(searchField.getText())); // Search on Enter
        southPanel.add(new JLabel("Search:"));
        southPanel.add(searchField);

        add(southPanel, BorderLayout.SOUTH);

    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        return button;
    }
    private void loadData() {
        String sql = "SELECT abbreviation, full_text FROM Abbreviations";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString("abbreviation"), rs.getString("full_text")});
            }
        } catch (SQLException e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Abbreviations (" +
                     "abbreviation TEXT PRIMARY KEY, " +
                     "full_text TEXT NOT NULL)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            showError("Error creating table: " + e.getMessage());
        }
    }
    
    private void showAddDialog() {
        // ... (updated for 4 fields)
        JTextField categoryField = new JTextField(20);
        JTextField bcodeField = new JTextField(10);
        JTextField nameField = new JTextField(30);
        JTextField referenceField = new JTextField(30);
        JPanel panel = createInputPanel(categoryField, bcodeField, nameField, referenceField);


        if (JOptionPane.showConfirmDialog(null, panel, "Add New Entry", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            insertRecord(categoryField.getText(), bcodeField.getText(), nameField.getText(), referenceField.getText());
        }
    }


    private void insertRecord(String category, String bcode, String name, String reference) {
    	// ... (updated SQL and tableModel.addRow())
        String sql = "INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            pstmt.setString(2, bcode);
            pstmt.setString(3, name);
            pstmt.setString(4, reference);
            pstmt.executeUpdate();
            tableModel.addRow(new Object[]{category, bcode, name, reference}); // Add to table
            JOptionPane.showMessageDialog(this, "Record added successfully!");
        } catch (SQLException e) {
            showError("Error inserting record: " + e.getMessage());
        }

    }

    private void deleteRecord() {
        // ... (updated to remove row based on all 4 fields - see below)
        int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            String category = (String) tableModel.getValueAt(modelRow, 0);
            String bcode = (String) tableModel.getValueAt(modelRow, 1);
            String name = (String) tableModel.getValueAt(modelRow, 2);
            String reference = (String) tableModel.getValueAt(modelRow, 3);



            String sql = "DELETE FROM codedis WHERE Category=? AND B_code=? AND name=? AND reference=?"; // Match all columns for safety

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {


                pstmt.setString(1, category);
                pstmt.setString(2, bcode);
                pstmt.setString(3, name);
                pstmt.setString(4, reference);
                pstmt.executeUpdate();

                tableModel.removeRow(modelRow);
                JOptionPane.showMessageDialog(this, "Record deleted successfully!");


            } catch (SQLException e) {
                showError("Error deleting record: " + e.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }

    }


    private void editRecord() {
    	// ... (updated for 4 fields)
        int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
        if (selectedRow >= 0) {

            JTextField categoryField = new JTextField((String) tableModel.getValueAt(selectedRow, 0), 20);
            JTextField bcodeField = new JTextField((String) tableModel.getValueAt(selectedRow, 1), 10);
            JTextField nameField = new JTextField((String) tableModel.getValueAt(selectedRow, 2), 30);
            JTextField referenceField = new JTextField((String) tableModel.getValueAt(selectedRow, 3), 30);


            JPanel panel = createInputPanel(categoryField, bcodeField, nameField, referenceField);

            if (JOptionPane.showConfirmDialog(null, panel, "Edit Entry", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                String newCategory = categoryField.getText().trim();
                String newBcode = bcodeField.getText().trim();
                String newName = nameField.getText().trim();
                String newReference = referenceField.getText().trim();

                updateRecord(selectedRow, newCategory, newBcode, newName, newReference);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
        }
    }

    private void updateRecord(int row, String category, String bcode, String name, String reference) {
        String sql = "UPDATE codedis SET Category=?, B_code=?, name=?, reference=? " +
                     "WHERE Category=? AND B_code=? AND name=? AND reference=?"; // Update SQL
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // ... set parameters for the new values and old values for WHERE clause
            pstmt.setString(1, category);
            pstmt.setString(2, bcode);
            pstmt.setString(3, name);
            pstmt.setString(4, reference);

            pstmt.setString(5, (String) tableModel.getValueAt(row, 0));
            pstmt.setString(6, (String) tableModel.getValueAt(row, 1));
            pstmt.setString(7, (String) tableModel.getValueAt(row, 2));
            pstmt.setString(8, (String) tableModel.getValueAt(row, 3));

            pstmt.executeUpdate();

            tableModel.setValueAt(category, row, 0);
            tableModel.setValueAt(bcode, row, 1);
            tableModel.setValueAt(name, row, 2);
            tableModel.setValueAt(reference, row, 3);


            JOptionPane.showMessageDialog(this, "Record updated successfully!");

        } catch (SQLException e) {
            showError("Error updating record: " + e.getMessage());
        }
    }


    private void findRecords(String searchText) {
        // ... (updated for 4 fields - see below)
        String sql = "SELECT Category, B_code, name, reference FROM codedis " +
                     "WHERE Category LIKE ? OR B_code LIKE ? OR name LIKE ? OR reference LIKE ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchText + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{rs.getString("Category"), rs.getString("B_code"),
                            rs.getString("name"), rs.getString("reference")});
                }
            }

        } catch (SQLException e) {
            showError("Error finding records: " + e.getMessage());
        }

    }


    private JPanel createInputPanel(JTextField categoryField, JTextField bcodeField, JTextField nameField, JTextField referenceField) {
    	// ... (updated for 4 fields)
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("B-code:"));
        panel.add(bcodeField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Reference:"));
        panel.add(referenceField);
        return panel;

    }


    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add" -> showAddDialog();
            case "Delete" -> deleteRecord();
            case "Edit" -> editRecord();
            case "Find" -> findRecords(searchField.getText());
            case "Exit" -> dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CodeMainScreen::new);
    }
}
    
    