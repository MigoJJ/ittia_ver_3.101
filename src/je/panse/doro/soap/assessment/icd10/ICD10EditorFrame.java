package je.panse.doro.soap.assessment.icd10;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ICD10EditorFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField codeField, koreanField, englishField, divisionField, searchField;
    private JButton clearButton, findButton, editButton, addButton, deleteButton, saveButton, quitButton;
    private final ICD10DatabaseManager dbManager;
    private final ICD10CSVImporter csvImporter;
    private final ICD10DataModel dataModel;

    public ICD10EditorFrame() {
        // Initialize dependencies
        dbManager = new ICD10DatabaseManager();
        csvImporter = new ICD10CSVImporter(dbManager);
        dataModel = new ICD10DataModel();

        // Set up the JFrame
        setTitle("ICD10 Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // North Panel: Four editable JTextFields and search field
        JPanel northPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Disease Code
        gbc.gridx = 0;
        gbc.gridy = 0;
        northPanel.add(new JLabel("Disease Code:"), gbc);
        gbc.gridx = 1;
        codeField = new JTextField(10);
        northPanel.add(codeField, gbc);

        // Korean Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        northPanel.add(new JLabel("Korean Name:"), gbc);
        gbc.gridx = 1;
        koreanField = new JTextField(20);
        northPanel.add(koreanField, gbc);

        // English Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        northPanel.add(new JLabel("English Name:"), gbc);
        gbc.gridx = 1;
        englishField = new JTextField(20);
        northPanel.add(englishField, gbc);

        // Complete Code Division
        gbc.gridx = 0;
        gbc.gridy = 3;
        northPanel.add(new JLabel("Complete Code Division:"), gbc);
        gbc.gridx = 1;
        divisionField = new JTextField(5);
        northPanel.add(divisionField, gbc);

        // Search Field
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        northPanel.add(new JLabel("Search:"), gbc);
        gbc.gridx = 3;
        searchField = new JTextField(20);
        northPanel.add(searchField, gbc);

        add(northPanel, BorderLayout.NORTH);

        // Central Panel: JTable for database
        String[] columns = {"Disease Code", "Korean Name", "English Name", "Complete Code Division"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    codeField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 0)));
                    koreanField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
                    englishField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
                    divisionField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

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

        // Button Actions
        clearButton.addActionListener(e -> clearFields());
        findButton.addActionListener(e -> findRecords());
        editButton.addActionListener(e -> editRecord());
        addButton.addActionListener(e -> addRecord());
        deleteButton.addActionListener(e -> deleteRecord());
        saveButton.addActionListener(e -> importCSV());
        quitButton.addActionListener(e -> System.exit(0));

        // Initialize database and load data
        dbManager.createTable();
        loadDataToTable();
    }

    private void clearFields() {
        codeField.setText("");
        koreanField.setText("");
        englishField.setText("");
        divisionField.setText("");
        searchField.setText("");
        table.clearSelection();
        loadDataToTable();
    }

    private void findRecords() {
        String searchTerm = searchField.getText().trim();
        tableModel.setRowCount(0);
        if (searchTerm.isEmpty()) {
            loadDataToTable();
        } else {
            dataModel.loadSearchResults(tableModel, dbManager.searchRecords(searchTerm));
        }
    }

    private void addRecord() {
        JTextField codeInput = new JTextField(10);
        JTextField koreanInput = new JTextField(20);
        JTextField englishInput = new JTextField(20);
        JTextField divisionInput = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Disease Code:"));
        panel.add(codeInput);
        panel.add(new JLabel("Korean Name:"));
        panel.add(koreanInput);
        panel.add(new JLabel("English Name:"));
        panel.add(englishInput);
        panel.add(new JLabel("Complete Code Division:"));
        panel.add(divisionInput);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Record", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            ICD10DataModel.Record record = new ICD10DataModel.Record(
                    codeInput.getText().trim(),
                    koreanInput.getText().trim().isEmpty() ? null : koreanInput.getText().trim(),
                    englishInput.getText().trim().isEmpty() ? null : englishInput.getText().trim(),
                    divisionInput.getText().trim().isEmpty() ? null : divisionInput.getText().trim()
            );
            if (dbManager.addRecord(record)) {
                JOptionPane.showMessageDialog(this, "Record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Error adding record.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a record to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String oldCode = (String) tableModel.getValueAt(selectedRow, 0);
        String oldKorean = (String) tableModel.getValueAt(selectedRow, 1);
        String oldEnglish = (String) tableModel.getValueAt(selectedRow, 2);
        String oldDivision = (String) tableModel.getValueAt(selectedRow, 3);

        JTextField codeInput = new JTextField(oldCode, 10);
        JTextField koreanInput = new JTextField(oldKorean, 20);
        JTextField englishInput = new JTextField(oldEnglish, 20);
        JTextField divisionInput = new JTextField(oldDivision, 5);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Disease Code:"));
        panel.add(codeInput);
        panel.add(new JLabel("Korean Name:"));
        panel.add(koreanInput);
        panel.add(new JLabel("English Name:"));
        panel.add(englishInput);
        panel.add(new JLabel("Complete Code Division:"));
        panel.add(divisionInput);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Record", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            ICD10DataModel.Record newRecord = new ICD10DataModel.Record(
                    codeInput.getText().trim(),
                    koreanInput.getText().trim().isEmpty() ? null : koreanInput.getText().trim(),
                    englishInput.getText().trim().isEmpty() ? null : englishInput.getText().trim(),
                    divisionInput.getText().trim().isEmpty() ? null : divisionInput.getText().trim()
            );
            ICD10DataModel.Record oldRecord = new ICD10DataModel.Record(oldCode, oldKorean, oldEnglish, oldDivision);
            if (dbManager.updateRecord(oldRecord, newRecord)) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Error updating record.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a record to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ICD10DataModel.Record record = new ICD10DataModel.Record(
                    (String) tableModel.getValueAt(selectedRow, 0),
                    (String) tableModel.getValueAt(selectedRow, 1),
                    (String) tableModel.getValueAt(selectedRow, 2),
                    (String) tableModel.getValueAt(selectedRow, 3)
            );
            if (dbManager.deleteRecord(record)) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadDataToTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting record.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void importCSV() {
        if (csvImporter.importCSV()) {
            JOptionPane.showMessageDialog(this, "CSV imported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Error importing CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        dataModel.loadAllRecords(tableModel, dbManager.getAllRecords());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ICD10EditorFrame().setVisible(true);
        });
    }
}