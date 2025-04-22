package je.panse.doro.support.sqlite3_manager.icd10.editor.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ICD10CodeExtractorGUI extends JFrame {

    // Database configuration
    private static final String DB_URL = "jdbc:sqlite:" +
            je.panse.doro.entry.EntryDir.homeDir +
            "/support/sqlite3_manager/icd10/editor/components/icd10_full.db";

    private JTable table;
    private DefaultTableModel tableModel;

    public ICD10CodeExtractorGUI() {
        setTitle("ICD-10 Codes with Code Length > 7");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Table setup
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // Load data
        loadData();

        setVisible(true);
    }

    private void loadData() {
        String query = "SELECT * FROM icd_codes_data WHERE LENGTH(code) > 7";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Get column names
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = meta.getColumnName(i);
            }
            tableModel.setColumnIdentifiers(columnNames);

            // Add rows
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Set system look and feel for better UI
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) {}

        // Run GUI
        SwingUtilities.invokeLater(ICD10CodeExtractorGUI::new);
    }
}
