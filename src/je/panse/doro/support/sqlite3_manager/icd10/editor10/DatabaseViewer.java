package je.panse.doro.support.sqlite3_manager.icd10.editor10;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import je.panse.doro.entry.EntryDir;

import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class DatabaseViewer extends JFrame {

    private final String dbUrl;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public DatabaseViewer(String dbFilePath) {
        super("SQLite Database Viewer");
        this.dbUrl = "jdbc:sqlite:" + dbFilePath;

        // 명시적으로 한글 폰트 설정
        Font hanFont = new Font("NanumGothic", Font.PLAIN, 12); // Ubuntu에 설치된 한글 폰트 이름으로 변경 필요
        UIManager.put("Table.font", hanFont);
        UIManager.put("TableHeader.font", hanFont);
        UIManager.put("Label.font", hanFont);
        UIManager.put("Button.font", hanFont);
        UIManager.put("TextField.font", hanFont);
        UIManager.put("TextArea.font", hanFont);
        UIManager.put("ComboBox.font", hanFont);

        initComponents();
        loadData();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM disease_codes")) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Set column names
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }
            tableModel.setColumnIdentifiers(columnNames);

            // Load data rows
            while (rs.next()) {
                Vector<Object> rowData = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.add(rs.getObject(i));
                }
                tableModel.addRow(rowData);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String dbFilePath = EntryDir.homeDir + "/support/sqlite3_manager/icd10/editor10/icd10.db";
        SwingUtilities.invokeLater(() -> new DatabaseViewer(dbFilePath));
    }
}