package je.panse.doro.soap.assessment.icd10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ICD10importCSV_DB extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton importButton;
    private static final String CSV_PATH = "/home/dce040b/git/ittia_ver_3.076/src/je/panse/doro/soap/assessment/icd10/ICD10.csv";
    private static final String DB_URL = "jdbc:sqlite:/home/dce040b/git/ittia_ver_3.076/src/je/panse/doro/soap/assessment/icd10/ICD10Sqlite.db";

    public ICD10importCSV_DB() {
        // Set up the JFrame
        setTitle("ICD10 Database Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialize table model and JTable
        String[] columns = {"Disease Code", "Korean Name", "English Name", "Complete Code Division"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Initialize import button
        importButton = new JButton("Import CSV to Database");
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importCSVtoDB();
                loadDataToTable();
            }
        });

        // Add button to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(importButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create database and table if not exists
        createDatabaseTable();

        // Load existing data into table
        loadDataToTable();
    }

    private void createDatabaseTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS icd10_codes (" +
                "disease_code TEXT NOT NULL, " +
                "korean_name TEXT, " +
                "english_name TEXT, " +
                "complete_code_division TEXT, " +
                "PRIMARY KEY (disease_code, korean_name, english_name)" +
                ")";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error creating table: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importCSVtoDB() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH));
             Connection conn = DriverManager.getConnection(DB_URL)) {
            String insertSQL = "INSERT OR IGNORE INTO icd10_codes (disease_code, korean_name, english_name, complete_code_division) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                // Split CSV line, handling commas correctly
                String[] data = line.split(",", -1); // -1 to include empty fields
                if (data.length >= 4) {
                    pstmt.setString(1, data[0].trim()); // disease_code
                    pstmt.setString(2, data[1].trim().isEmpty() ? null : data[1].trim()); // korean_name
                    pstmt.setString(3, data[2].trim().isEmpty() ? null : data[2].trim()); // english_name
                    pstmt.setString(4, data[3].trim().isEmpty() ? null : data[3].trim()); // complete_code_division
                    pstmt.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(this, "CSV imported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error importing CSV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0); // Clear existing data
        String selectSQL = "SELECT * FROM icd10_codes";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("disease_code"),
                        rs.getString("korean_name"),
                        rs.getString("english_name"),
                        rs.getString("complete_code_division")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ICD10importCSV_DB().setVisible(true);
        });
    }
}