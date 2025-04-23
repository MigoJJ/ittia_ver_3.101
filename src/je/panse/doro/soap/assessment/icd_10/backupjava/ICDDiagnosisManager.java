package je.panse.doro.soap.assessment.icd_10.backupjava;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Vector;
import je.panse.doro.entry.EntryDir;

public class ICDDiagnosisManager extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton clearButton, findButton, editButton, addButton, deleteButton, saveButton, quitButton;
    private JTextField[] inputFields;
    private JTextField searchField;

    public ICDDiagnosisManager() {
        setTitle("ICD-10 Diagnosis Manager");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize table
        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        loadTableData(); // Load data to set up columns first

        // North Panel: Search and Input Fields
        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchField.addActionListener(e -> searchData());

        JPanel inputPanel = new JPanel(new GridLayout(1, 4));
        inputFields = new JTextField[4];
        String[] labels = {"Code", "Category", "Description", "Details"};
        for (int i = 0; i < 4; i++) {
            JPanel fieldPanel = new JPanel(new FlowLayout());
            fieldPanel.add(new JLabel(labels[i] + ": "));
            inputFields[i] = new JTextField(15);
            fieldPanel.add(inputFields[i]);
            inputPanel.add(fieldPanel);
        }
        northPanel.add(searchPanel, BorderLayout.NORTH);
        northPanel.add(inputPanel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);

        // South Panel: Buttons
        JPanel southPanel = new JPanel(new FlowLayout());
        clearButton = new JButton("Clear");
        findButton = new JButton("Find");
        editButton = new JButton("Edit");
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");
        quitButton = new JButton("Quit");

        southPanel.add(clearButton);
        southPanel.add(findButton);
        southPanel.add(editButton);
        southPanel.add(addButton);
        southPanel.add(deleteButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);
        add(southPanel, BorderLayout.SOUTH);

        // Button Action Listeners
        clearButton.addActionListener(e -> clearFields());
        findButton.addActionListener(e -> searchData());
        editButton.addActionListener(e -> editSelectedRow());
        addButton.addActionListener(e -> addNewRecord());
        deleteButton.addActionListener(e -> deleteSelectedRow());
        saveButton.addActionListener(e -> saveChanges());
        quitButton.addActionListener(e -> System.exit(0));

        // Load initial data
        loadTableData();
    }

    private void loadTableData() {
        String url = "jdbc:sqlite:" + EntryDir.homeDir + "/soap/assessment/icd_10/diagnosis.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM diagnosis")) {

            // Clear existing data
            model.setRowCount(0);
            model.setColumnCount(0);

            // Set columns
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Set column widths
            TableColumnModel columnModel = table.getColumnModel();
            if (columnCount >= 4) {
                columnModel.getColumn(0).setPreferredWidth(20);
                columnModel.getColumn(1).setPreferredWidth(20);
                columnModel.getColumn(2).setPreferredWidth(200);
                columnModel.getColumn(3).setPreferredWidth(350);
            }

            // Load rows
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
        }
    }

    private void clearFields() {
        for (JTextField field : inputFields) {
            field.setText("");
        }
        searchField.setText("");
    }

    private void searchData() {
        String searchText = searchField.getText().toLowerCase();
        String url = "jdbc:sqlite:" + EntryDir.homeDir + "/soap/assessment/icd_10/diagnosis.db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM diagnosis WHERE LOWER(code) LIKE ? OR LOWER(category) LIKE ? OR LOWER(description) LIKE ? OR LOWER(details) LIKE ?")) {
            for (int i = 1; i <= 4; i++) {
                pstmt.setString(i, "%" + searchText + "%");
            }
            ResultSet rs = pstmt.executeQuery();

            model.setRowCount(0);
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= 4; i++) {
                    row.add(rs.getString(i));
                }
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage());
        }
    }

    private void editSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            for (int i = 0; i < 4; i++) {
                inputFields[i].setText(model.getValueAt(selectedRow, i).toString());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit");
        }
    }

    private void addNewRecord() {
        String url = "jdbc:sqlite:" + EntryDir.homeDir + "/soap/assessment/icd_10/diagnosis.db";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO diagnosis (code, category, description, details) VALUES (?, ?, ?, ?)")) {
            for (int i = 0; i < 4; i++) {
                pstmt.setString(i + 1, inputFields[i].getText());
            }
            pstmt.executeUpdate();
            loadTableData();
            clearFields();
            JOptionPane.showMessageDialog(this, "Record added successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error adding record: " + ex.getMessage());
        }
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String code = model.getValueAt(selectedRow, 0).toString();
            String url = "jdbc:sqlite:" + EntryDir.homeDir + "/soap/assessment/icd_10/diagnosis.db";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM diagnosis WHERE code = ?")) {
                pstmt.setString(1, code);
                pstmt.executeUpdate();
                loadTableData();
                JOptionPane.showMessageDialog(this, "Record deleted successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting record: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete");
        }
    }

    private void saveChanges() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String code = model.getValueAt(selectedRow, 0).toString();
            String url = "jdbc:sqlite:" + EntryDir.homeDir + "/soap/assessment/icd_10/diagnosis.db";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(
                         "UPDATE diagnosis SET code = ?, category = ?, description = ?, details = ? WHERE code = ?")) {
                for (int i = 0; i < 4; i++) {
                    pstmt.setString(i + 1, inputFields[i].getText());
                }
                pstmt.setString(5, code);
                pstmt.executeUpdate();
                loadTableData();
                clearFields();
                JOptionPane.showMessageDialog(this, "Changes saved successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saving changes: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to save changes");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ICDDiagnosisManager().setVisible(true);
        });
    }
}