package je.panse.doro.soap.assessment.icd10;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class SQLiteViewer extends JFrame {
    private JComboBox<String> tableList;
    private JTable table;
    private Connection conn;

    public SQLiteViewer(String dbPath) {
        setTitle("SQLite Database Viewer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);

        // Connect to SQLite
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to database: " + e.getMessage());
            System.exit(1);
        }

        // Get table names
        Vector<String> tables = new Vector<>();
        try (ResultSet rs = conn.getMetaData().getTables(null, null, "%", new String[]{"TABLE"})) {
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to retrieve tables: " + e.getMessage());
        }

        tableList = new JComboBox<>(tables);
        table = new JTable();

        tableList.addActionListener(e -> loadTable((String) tableList.getSelectedItem()));

        add(tableList, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load first table if available
        if (!tables.isEmpty()) {
            loadTable(tables.get(0));
        }
    }

    private void loadTable(String tableName) {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData meta = rs.getMetaData();
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                columnNames.add(meta.getColumnName(i));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            table.setModel(new DefaultTableModel(data, columnNames));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load table: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String dbPath = "/home/migowj/git/ittia_ver_3.076/src/je/panse/doro/soap/assessment/icd10/ICD10Sqlite.db";
            new SQLiteViewer(dbPath).setVisible(true);
        });
    }
}
