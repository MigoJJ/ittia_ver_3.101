package je.panse.doro.support.sqlite3_manager.icd_10;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import je.panse.doro.entry.EntryDir;

public class ICD10MainScreen extends JFrame implements ActionListener {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JPanel southPanel;
    private ICD10DatabaseModel model;

    public ICD10MainScreen() {
//        model = new ICD10DatabaseModel(EntryDir.homeDir + "/support/sqlite3_manager/icd10/icd10_data.db");
        model = new ICD10DatabaseModel("/home/migowj/git/ittia_ver_3.076/src/je/panse/doro/support/sqlite3_manager/icd10/icd10_data.db");
//        /home/migowj/git/ittia_ver_3.076/src/je/panse/doro/support/sqlite3_manager/icd10/icd10_data.db
        setupFrame();
        setupTable();
        setupButtons();
        loadData();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("ICD-10 Code/Disease Database");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    private void setupTable() {
        String[] columnNames = {"Category", "Code", "Title", "Comments"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Consolas", Font.PLAIN, 12));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING)));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);

        setColumnAlignment();
    }

    private void setColumnAlignment() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
    }

    private void setupButtons() {
        southPanel = new JPanel();
        String[] buttonLabels = {"Add", "Delete", "Edit", "Refresh", "Exit"};
        for (String label : buttonLabels) {
            southPanel.add(createButton(label));
        }

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setToolTipText("Enter search term and press Enter");
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        searchField.addActionListener(e -> findRecords(searchField.getText()));

        southPanel.add(new JLabel("Search Diagnosis/Code:"));
        southPanel.add(searchField);
        add(southPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setMargin(new Insets(5, 10, 5, 10));
        return button;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try (ResultSet rs = model.getAllRecords()) {
            while (rs != null && rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("Category"),
                    rs.getString("Code"),
                    rs.getString("Title"),
                    rs.getString("Comment")
                });
            }
        } catch (SQLException e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void refreshData() {
        loadData();
        searchField.setText("");
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
            case "Refresh" -> refreshData();
            case "Exit" -> dispose();
        }
    }

    private void findRecords(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            refreshData();
            return;
        }
        model.searchRecords(searchText.trim(), tableModel);
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            String codeToDelete = (String) tableModel.getValueAt(modelRow, 1);
            if (codeToDelete != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Delete Code: " + codeToDelete + "?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.deleteRecord(codeToDelete);
                    loadData();
                }
            }
        }
    }

    private void editRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            String category = (String) tableModel.getValueAt(modelRow, 0);
            String code = (String) tableModel.getValueAt(modelRow, 1);
            String title = (String) tableModel.getValueAt(modelRow, 2);
            String comment = (String) tableModel.getValueAt(modelRow, 3);

            JTextField catField = new JTextField(category);
            JTextField codeField = new JTextField(code);
            JTextField titleField = new JTextField(title);
            JTextField commentField = new JTextField(comment);

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Category:")); panel.add(catField);
            panel.add(new JLabel("Code:")); panel.add(codeField);
            panel.add(new JLabel("Title:")); panel.add(titleField);
            panel.add(new JLabel("Comment:")); panel.add(commentField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Edit Entry", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                model.updateRecord(code, catField.getText(), codeField.getText(), titleField.getText(), commentField.getText());
                loadData();
            }
        }
    }

    private void showAddDialog() {
        JTextField catField = new JTextField("General");
        JTextField codeField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField commentField = new JTextField("ICD-10");

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Category:")); panel.add(catField);
        panel.add(new JLabel("Code:")); panel.add(codeField);
        panel.add(new JLabel("Title:")); panel.add(titleField);
        panel.add(new JLabel("Comment:")); panel.add(commentField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Entry", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            model.insertRecord(catField.getText(), codeField.getText(), titleField.getText(), commentField.getText());
            loadData();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ICD10MainScreen::new);
    }
}
