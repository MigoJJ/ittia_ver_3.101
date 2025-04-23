package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.Vector;

public class TableManager {
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private InputPanel inputPanel;

    public TableManager(InputPanel inputPanel) {
        this.inputPanel = inputPanel;
        model = new DefaultTableModel();
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        // Add table selection listener to update input fields
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                inputPanel.populateFields(getSelectedRowData());
            }
        });
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void loadTableData(DatabaseManager dbManager) {
        Vector<String> columns = new Vector<>();
        Vector<Vector<String>> data = dbManager.loadTableData(columns);
        model.setDataVector(data, columns);

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        if (model.getColumnCount() >= 4) {
            columnModel.getColumn(0).setPreferredWidth(10);
            columnModel.getColumn(1).setPreferredWidth(10);
            columnModel.getColumn(2).setPreferredWidth(20);
            columnModel.getColumn(3).setPreferredWidth(250);
            columnModel.getColumn(4).setPreferredWidth(350);
         }
    }

    public void searchData(DatabaseManager dbManager, String searchText) {
        Vector<String> columns = new Vector<>();
        Vector<Vector<String>> data = dbManager.searchData(searchText, columns);
        model.setDataVector(data, columns);
    }

    public String getSelectedCode() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            return model.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }

    public String[] getSelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String[] data = new String[4];
            for (int i = 0; i < 4; i++) {
                data[i] = model.getValueAt(selectedRow, i).toString();
            }
            return data;
        }
        return null;
    }
}