package je.panse.doro.support.sqlite3_manager.icd10;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import je.panse.doro.entry.EntryDir;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ICD10DatabaseReader extends JFrame {

    private JTextArea statusArea;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    private final String dbPath = EntryDir.homeDir + "/support/sqlite3_manager/icd10/icd10_data.db";

    public ICD10DatabaseReader() {
        setTitle("ICD10 SQLite DB Reader");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton readButton = new JButton("Read ICD10 Data");
        readButton.addActionListener(this::readDatabase);

        statusArea = new JTextArea();
        statusArea.setEditable(false);

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(dataTable);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(readButton);

        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(new JScrollPane(statusArea), BorderLayout.SOUTH);
    }

    private void readDatabase(ActionEvent event) {
        statusArea.setText(""); // Clear previous messages
        tableModel.setRowCount(0); // Clear existing table data
        tableModel.setColumnCount(0); // Clear existing table columns

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT category, code, title, comment FROM icd10_data")) {

            statusArea.append("Connected to database: " + dbPath + "\n");

            // Get column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            // Populate table rows
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tableModel.addRow(row);
            }

            statusArea.append("Successfully read " + tableModel.getRowCount() + " records.\n");

        } catch (SQLException e) {
            statusArea.append("Error reading database: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ICD10DatabaseReader().setVisible(true));
    }
}