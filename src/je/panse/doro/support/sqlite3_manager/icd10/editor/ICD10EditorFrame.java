package je.panse.doro.support.sqlite3_manager.icd10.editor;

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

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.setAutoCreateRowSorter(true);

        tableScrollPane = new JScrollPane(dataTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 400));
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

        // Set column widths here
        if (dataTable.getColumnCount() > 0) {
            dataTable.getColumnModel().getColumn(0).setPreferredWidth(20);   // ID column
            dataTable.getColumnModel().getColumn(1).setPreferredWidth(100);  // Category column
            dataTable.getColumnModel().getColumn(2).setPreferredWidth(40);   // Code column
            dataTable.getColumnModel().getColumn(3).setPreferredWidth(250);  // ICD Disease column
            dataTable.getColumnModel().getColumn(4).setPreferredWidth(200);  // Description column
        }

        buttonPanel.setEditDeleteEnabled(false);
        clearInputFields();
        System.out.println("Data loaded into table.");
    }

    private void addRecord() {
        String code = inputPanel.getCode();
        String icdDisease = inputPanel.getICDdisease();
        String description = inputPanel.getDescription();

        if (code.isEmpty() || icdDisease.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ICD Code and ICD Disease cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Assuming your DatabaseManager's addRecord method should look like this:
        // public boolean addRecord(String code, String icdDisease, String description) { ... }
        boolean success = dbManager.addRecord(code, icdDisease, description);
        if (success) {
            loadData();
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Record added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void updateRecord() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to update.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = dataTable.convertRowIndexToModel(selectedRow);
        int id = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());

        String code = inputPanel.getCode();
        String icdDisease = inputPanel.getICDdisease();
        String description = inputPanel.getDescription();

        if (code.isEmpty() || icdDisease.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ICD Code and ICD Disease cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = dbManager.updateRecord(id, code, icdDisease, description);
        if (success) {
            loadData();
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteRecord() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a record to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = dataTable.convertRowIndexToModel(selectedRow);
        int id = Integer.parseInt(tableModel.getValueAt(modelRow, 0).toString());

        boolean success = dbManager.deleteRecord(id);
        if (success) {
            loadData();
            clearInputFields();
            JOptionPane.showMessageDialog(this, "Record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearInputFields() {
        inputPanel.clearFields();
        dataTable.clearSelection();
        buttonPanel.setEditDeleteEnabled(false);
    }

    private void findRecord() {
        String keyword = JOptionPane.showInputDialog(this, "Enter ICD code, disease, or description to search:", "Find ICD Record", JOptionPane.QUESTION_MESSAGE);
        if (keyword == null || keyword.trim().isEmpty()) return;

        keyword = keyword.trim().toLowerCase();
        DefaultTableModel fullModel = dbManager.loadData();
        Vector<Vector> filteredData = new Vector<>();
        Vector<String> columnNames = new Vector<>(java.util.List.of("ID", "Category", "Code", "ICD Disease", "Description"));

        for (int i = 0; i < fullModel.getRowCount(); i++) {
            Object code = fullModel.getValueAt(i, 2);
            Object disease = fullModel.getValueAt(i, 3);
            Object desc = fullModel.getValueAt(i, 4);

            if ((code != null && code.toString().toLowerCase().contains(keyword)) ||
                (disease != null && disease.toString().toLowerCase().contains(keyword)) ||
                (desc != null && desc.toString().toLowerCase().contains(keyword))) {
                filteredData.add((Vector) fullModel.getDataVector().elementAt(i));
            }
        }

        if (filteredData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching ICD records found.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            tableModel.setDataVector(filteredData, columnNames);
            dataTable.setModel(tableModel);
        }
    }

    private void handleTableSelection() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = dataTable.convertRowIndexToModel(selectedRow);
            inputPanel.setCode(tableModel.getValueAt(modelRow, 2).toString());
            inputPanel.setICDdisease(tableModel.getValueAt(modelRow, 3).toString());
            inputPanel.setDescription(tableModel.getValueAt(modelRow, 4).toString());
            buttonPanel.setEditDeleteEnabled(true);
        } else {
            buttonPanel.setEditDeleteEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ICD10EditorFrame::new);
    }
}