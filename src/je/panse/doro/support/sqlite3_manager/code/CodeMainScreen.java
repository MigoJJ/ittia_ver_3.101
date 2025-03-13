package je.panse.doro.support.sqlite3_manager.code;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import je.panse.doro.entry.EntryDir;


public class CodeMainScreen extends JFrame implements ActionListener {

    private DatabaseModel model;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public CodeMainScreen() {
        model = new DatabaseModel();
        setupFrame();
        setupTable();
        setupButtons();
        model.createDatabaseTable();
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

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(400);
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

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(searchField.getPreferredSize().width, searchField.getPreferredSize().height * 2));
        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setFont(searchField.getFont().deriveFont(Font.BOLD));
        searchField.addActionListener(this);

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
        String sql = "SELECT Category, B_code, name, reference FROM codedis";
        try (Connection conn = DriverManager.getConnection(DatabaseModel.DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString("Category"), rs.getString("B_code"), rs.getString("name"), rs.getString("reference")});
            }
        } catch (SQLException e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e == null) return;  // Extremely unlikely, but good practice

        if (searchField != null && e.getSource() == searchField) { // Null check before comparison
            findRecords(searchField.getText());
            searchField.setText(""); // Clear the search field after search
        } else {  // Handle other button events
            String command = e.getActionCommand();
            if (command == null) return; // Check for null command

            switch (command) {
                case "Add": showAddDialog(); break;
                case "Delete": deleteRecord(); break;
                case "Edit": editRecord(); break;
                case "Find": 
                    findRecords(searchField.getText());
                    searchField.setText(""); // Clear the search field after search
                    break;
                case "Exit": dispose(); break;
                default: 
                    System.err.println("Unknown command: " + command);
            }
        }
    }

    private void findRecords(String searchText) {
        String sql = "SELECT Category, B_code, name, reference FROM codedis WHERE Category LIKE ? OR B_code LIKE ? OR name LIKE ? OR reference LIKE ?";
        try (Connection conn = DriverManager.getConnection(DatabaseModel.DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchText + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{rs.getString("Category"), rs.getString("B_code"), rs.getString("name"), rs.getString("reference")});
                }
            }
        } catch (SQLException e) {
            showError("Error finding records: " + e.getMessage());
        }
    }

    private void showAddDialog() {
        JTextField categoryField = new JTextField(20);
        JTextField bcodeField = new JTextField(10);
        JTextField nameField = new JTextField(30);
        JTextField referenceField = new JTextField(30);

        JPanel panel = createInputPanel(categoryField, bcodeField, nameField, referenceField);

        if (JOptionPane.showConfirmDialog(null, panel, "Add New Entry", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            model.insertRecord(categoryField.getText(), bcodeField.getText(), nameField.getText(), referenceField.getText());
            tableModel.addRow(new Object[]{categoryField.getText(), bcodeField.getText(), nameField.getText(), referenceField.getText()});
        }
    }

    private JPanel createInputPanel(JTextField categoryField, JTextField bcodeField, JTextField nameField, JTextField referenceField) {
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

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            String category = (String) tableModel.getValueAt(modelRow, 0);
            String bcode = (String) tableModel.getValueAt(modelRow, 1);
            String name = (String) tableModel.getValueAt(modelRow, 2);
            String reference = (String) tableModel.getValueAt(modelRow, 3);

            String sql = "DELETE FROM codedis WHERE Category=? AND B_code=? AND name=? AND reference=?";
            try (Connection conn = DriverManager.getConnection(DatabaseModel.DB_URL);
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
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            JTextField categoryField = new JTextField((String) tableModel.getValueAt(modelRow, 0), 20);
            JTextField bcodeField = new JTextField((String) tableModel.getValueAt(modelRow, 1), 10);
            JTextField nameField = new JTextField((String) tableModel.getValueAt(modelRow, 2), 30);
            JTextField referenceField = new JTextField((String) tableModel.getValueAt(modelRow, 3), 30);

            JPanel panel = createInputPanel(categoryField, bcodeField, nameField, referenceField);

            if (JOptionPane.showConfirmDialog(null, panel, "Edit Entry", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                String newCategory = categoryField.getText().trim();
                String newBcode = bcodeField.getText().trim();
                String newName = nameField.getText().trim();
                String newReference = referenceField.getText().trim();

                updateRecord(modelRow, newCategory, newBcode, newName, newReference);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
        }
    }

    private void updateRecord(int row, String category, String bcode, String name, String reference) {
        String sql = "UPDATE codedis SET Category=?, B_code=?, name=?, reference=? WHERE Category=? AND B_code=? AND name=? AND reference=?";
        try (Connection conn = DriverManager.getConnection(DatabaseModel.DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CodeMainScreen::new);
    }
}

class DatabaseModel {
    static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";

    public void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS codedis (Category TEXT, B_code TEXT, name TEXT, reference TEXT)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void insertRecord(String category, String bcode, String name, String reference) {
        String sql = "INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            pstmt.setString(2, bcode);
            pstmt.setString(3, name);
            pstmt.setString(4, reference);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
        }
    }
}
