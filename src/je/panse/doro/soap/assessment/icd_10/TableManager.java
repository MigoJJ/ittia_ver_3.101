package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.util.Vector;
import java.util.logging.Logger;

public class TableManager {
    private static final Logger LOGGER = Logger.getLogger(TableManager.class.getName());
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private InputPanel inputPanel;

    public TableManager(InputPanel inputPanel) {
        this.inputPanel = inputPanel;
        model = new DefaultTableModel();
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
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
            columnModel.getColumn(0).setPreferredWidth(50);  // ID
            columnModel.getColumn(1).setPreferredWidth(20);  // Code
            columnModel.getColumn(2).setPreferredWidth(20);  // Code with Separator
            columnModel.getColumn(3).setPreferredWidth(200); // Short
            columnModel.getColumn(4).setPreferredWidth(350); // Long Description
        } else {
            LOGGER.warning("Table has fewer than 5 columns, skipping column width setting");
        }
    }

    public String getSelectedCode() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            return model.getValueAt(selectedRow, 1).toString(); // Use column 1 for code
        }
        return null;
    }

    public String[] getSelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String[] data = new String[5];
            for (int i = 0; i < 5; i++) {
                data[i] = model.getValueAt(selectedRow, i).toString();
            }
            return data;
        }
        return null;
    }
}