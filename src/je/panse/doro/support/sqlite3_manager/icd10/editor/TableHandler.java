package je.panse.doro.support.sqlite3_manager.icd10.editor;

public class TableHandler {
    private DefaultTableModel tableModel;
    private JTable dataTable;

    public TableHandler() {
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        configureTable();
    }

    private void configureTable() { /* column setup logic */ }
    public void loadData(ResultSet rs) { /* data loading */ }
}
