package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import javax.swing.table.*;

import java.util.Vector;
import java.util.logging.Logger;

public class TableManager {
    private static final Logger LOGGER = Logger.getLogger(TableManager.class.getName());
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private InputPanel inputPanel;
    private ButtonPanel buttonPanel;

    public TableManager(InputPanel inputPanel, ButtonPanel buttonPanel) {
        this.inputPanel = inputPanel;
        this.buttonPanel = buttonPanel;
        model = new DefaultTableModel();
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                inputPanel.populateFields(getSelectedRowData());
                buttonPanel.setButtonState(getSelectedRow() >= 0);
            }
        });
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void loadTableData(DatabaseManager dbManager) {
        LOGGER.info("Loading table data");
        Vector<String> columns = new Vector<>();
        Vector<Vector<String>> data = dbManager.loadTableData(columns);
        model.setDataVector(data, columns);
        setColumnWidths();
    }

    public void searchData(DatabaseManager dbManager, String searchText) {
        LOGGER.info("Searching with text: " + searchText);
        Vector<String> columns = new Vector<>();
        Vector<Vector<String>> data = dbManager.searchData(searchText, columns);
        model.setDataVector(data, columns);
        LOGGER.info("Search returned " + data.size() + " rows");
        setColumnWidths();
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = table.getColumnModel();
        if (model.getColumnCount() >= 5) {
            columnModel.getColumn(0).setPreferredWidth(30);  // ID
            columnModel.getColumn(1).setPreferredWidth(30);  // Code
            columnModel.getColumn(2).setPreferredWidth(30); // Category
            columnModel.getColumn(3).setPreferredWidth(300); // Description
            columnModel.getColumn(4).setPreferredWidth(350); // Details
        } else {
            LOGGER.warning("Table has fewer than 5 columns, skipping column width setting");
        }
    }

    public String getSelectedCode() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0 && model.getColumnCount() > 1) {
            Object value = model.getValueAt(selectedRow, 1);
            return value != null ? value.toString() : null;
        }
        return null;
    }

    public String[] getSelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0 && model.getColumnCount() >= 5) {
            String[] data = new String[5];
            for (int i = 0; i < 5; i++) {
                Object value = model.getValueAt(selectedRow, i);
                data[i] = value != null ? value.toString() : "";
            }
            return data;
        }
        return null;
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }
}