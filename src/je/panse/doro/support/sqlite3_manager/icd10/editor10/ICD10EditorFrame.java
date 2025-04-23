package je.panse.doro.support.sqlite3_manager.icd10.editor10;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class ICD10EditorFrame extends JFrame {

    private DatabaseManager dbManager;
    private InputPanel inputPanel;
    private ButtonPanel buttonPanel;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;

    public ICD10EditorFrame() {
        super("ICD-10 Database Editor");
        dbManager = new DatabaseManager();

        initComponents();
        layoutComponents();
        attachListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        loadData();
    }

    private void initComponents() {
        inputPanel = new InputPanel();
        buttonPanel = new ButtonPanel();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Code", "Korean Name", "English Name", "Complete Code", "Main Disease", "Infectious", "Gender", "Max Age", "Min Age", "Medical Type"}, 0);
        dataTable = new JTable(tableModel);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.setAutoCreateRowSorter(true);
        tableScrollPane = new JScrollPane(dataTable);
        tableScrollPane.setPreferredSize(new Dimension(900, 400)); // Increased width

        if (dataTable.getColumnModel().getColumnCount() > 0) {
            dataTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            dataTable.getColumnModel().getColumn(0).setMaxWidth(60);
            dataTable.getColumnModel().getColumn(0).setMinWidth(30);
        }
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void attachListeners() {
        buttonPanel.addLoadListener(e -> loadData());
        buttonPanel.addAddListener(e -> addRecord());
        buttonPanel.addUpdateListener(e -> updateRecord());
        buttonPanel.addDeleteListener(e -> deleteRecord());
        buttonPanel.addClearListener(e -> clearInputFields());
        buttonPanel.addFindListener(e -> findRecord());

        dataTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleTableSelection();
            }
        });
    }

    private void loadData() {
        tableModel = dbManager.loadData();
        dataTable.setModel(tableModel);

        if (dataTable.getColumnModel().getColumnCount() > 0) {
            dataTable.getColumnModel().getColumn(0).setPreferredWidth(40);
            dataTable.getColumnModel().getColumn(0).setMaxWidth(60);
            dataTable.getColumnModel().getColumn(0).setMinWidth(30);
        }

        buttonPanel.setEditDeleteEnabled(false);
        clearInputFields();
        System.out.println("Data loaded into table.");
    }

    private void addRecord() {
        String code = inputPanel.getCode();
        String koreanName = inputPanel.getKoreanName();
        String englishName = inputPanel.getEnglishName();
        String completeCode = inputPanel.getCompleteCode();
        String mainDisease = inputPanel.getMainDisease();
        String infectious = inputPanel.getInfectious();
        String gender = inputPanel.getGender();
        String maxAge = inputPanel.getMaxAge();
        String minAge = inputPanel.getMinAge();
        String medicalType = inputPanel.getMedicalType();

        if (code.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ICD Code cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = dbManager.addRecord(code, koreanName, englishName, completeCode, mainDisease, infectious, gender, maxAge, minAge, medicalType);
        if (success) {
            loadData();
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Record added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Error message handled by DatabaseManager
        }
    }

    private void updateRecord() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = inputPanel.getSelectedId();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "Invalid ID selected. Cannot update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String code = inputPanel.getCode();
        String koreanName = inputPanel.getKoreanName();
        String englishName = inputPanel.getEnglishName();
        String completeCode = inputPanel.getCompleteCode();
        String mainDisease = inputPanel.getMainDisease();
        String infectious = inputPanel.getInfectious();
        String gender = inputPanel.getGender();
        String maxAge = inputPanel.getMaxAge();
        String minAge = inputPanel.getMinAge();
        String medicalType = inputPanel.getMedicalType();

        if (code.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ICD Code cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = dbManager.updateRecord(id, code, koreanName, englishName, completeCode, mainDisease, infectious, gender, maxAge, minAge, medicalType);
        if (success) {
            loadData();
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Error message possibly shown by DatabaseManager
        }
    }

    private void deleteRecord() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = inputPanel.getSelectedId();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "Invalid ID selected. Cannot delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = dbManager.deleteRecord(id);
        if (success) {
            loadData();
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Error message possibly shown by DatabaseManager or user cancelled
        }
    }

    private void clearInputFields() {
        inputPanel.clearFields();
        dataTable.clearSelection();
        buttonPanel.setEditDeleteEnabled(false);
    }

    private void findRecord() {
        String keyword = JOptionPane.showInputDialog(this, "Enter code, korean name, or english name to search:", "Find ICD Record", JOptionPane.QUESTION_MESSAGE);

        if (keyword == null || keyword.trim().isEmpty()) {
            return;
        }

        keyword = keyword.trim().toLowerCase();

        DefaultTableModel fullModel = dbManager.loadData();
        Vector<Vector> filteredData = new Vector<>();

        for (int i = 0; i < fullModel.getRowCount(); i++) {
            Object code = fullModel.getValueAt(i, 1);
            Object koreanName = fullModel.getValueAt(i, 2);
            Object englishName = fullModel.getValueAt(i, 3);
            Object completeCode = fullModel.getValueAt(i, 4);
            Object mainDisease = fullModel.getValueAt(i, 5);
            Object infectious = fullModel.getValueAt(i, 6);
            Object gender = fullModel.getValueAt(i, 7);
            Object maxAge = fullModel.getValueAt(i, 8);
            Object minAge = fullModel.getValueAt(i, 9);
            Object medicalType = fullModel.getValueAt(i, 10);

            if ((code != null && code.toString().toLowerCase().contains(keyword)) ||
                (koreanName != null && koreanName.toString().toLowerCase().contains(keyword)) ||
                (englishName != null && englishName.toString().toLowerCase().contains(keyword)) ||
                (completeCode != null && completeCode.toString().toLowerCase().contains(keyword)) ||
                (mainDisease != null && mainDisease.toString().toLowerCase().contains(keyword)) ||
                (infectious != null && infectious.toString().toLowerCase().contains(keyword)) ||
                (gender != null && gender.toString().toLowerCase().contains(keyword)) ||
                (maxAge != null && maxAge.toString().toLowerCase().contains(keyword)) ||
                (minAge != null && minAge.toString().toLowerCase().contains(keyword)) ||
                (medicalType != null && medicalType.toString().toLowerCase().contains(keyword))) {
                Vector<Object> row = new Vector<>();
                for (int j = 0; j < fullModel.getColumnCount(); j++) {
                    row.add(fullModel.getValueAt(i, j));
                }
                filteredData.add(row);
            }
        }

        if (filteredData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching ICD records found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            tableModel.setDataVector(filteredData, new Vector<>(java.util.List.of("id", "code", "korean_name", "english_name", "complete_code", "main_disease", "infectious", "gender", "max_age", "min_age", "medical_type")));
            dataTable.setModel(tableModel);
        }
    }

    private void handleTableSelection() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = dataTable.convertRowIndexToModel(selectedRow);

            inputPanel.setIdText(tableModel.getValueAt(modelRow, 0) != null ? tableModel.getValueAt(modelRow, 0).toString() : "");
            inputPanel.setCode(tableModel.getValueAt(modelRow, 1) != null ? tableModel.getValueAt(modelRow, 1).toString() : "");
            inputPanel.setKoreanName(tableModel.getValueAt(modelRow, 2) != null ? tableModel.getValueAt(modelRow, 2).toString() : "");
            inputPanel.setEnglishName(tableModel.getValueAt(modelRow, 3) != null ? tableModel.getValueAt(modelRow, 3).toString() : "");
            inputPanel.setCompleteCode(tableModel.getValueAt(modelRow, 4) != null ? tableModel.getValueAt(modelRow, 4).toString() : "");
            inputPanel.setMainDisease(tableModel.getValueAt(modelRow, 5) != null ? tableModel.getValueAt(modelRow, 5).toString() : "");
            inputPanel.setInfectious(tableModel.getValueAt(modelRow, 6) != null ? tableModel.getValueAt(modelRow, 6).toString() : "");
            inputPanel.setGender(tableModel.getValueAt(modelRow, 7) != null ? tableModel.getValueAt(modelRow, 7).toString() : "");
            inputPanel.setMaxAge(tableModel.getValueAt(modelRow, 8) != null ? tableModel.getValueAt(modelRow, 8).toString() : "");
            inputPanel.setMinAge(tableModel.getValueAt(modelRow, 9) != null ? tableModel.getValueAt(modelRow, 9).toString() : "");
            inputPanel.setMedicalType(tableModel.getValueAt(modelRow, 10) != null ? tableModel.getValueAt(modelRow, 10).toString() : "");

            buttonPanel.setEditDeleteEnabled(true);
        } else {
            buttonPanel.setEditDeleteEnabled(false);
        }
    }
}