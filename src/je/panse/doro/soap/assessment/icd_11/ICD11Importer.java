package je.panse.doro.soap.assessment.icd_11;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.logging.Logger;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class ICD11Importer extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(ICD11Importer.class.getName());
    private static final String DB_URL = "jdbc:sqlite:/home/migowj/git/ittia_ver_3.100/src/je/panse/doro/soap/assessment/icd_11/icd11.db";
    private static final String CSV_PATH = "/home/migowj/git/ittia_ver_3.100/src/je/panse/doro/soap/assessment/icd_11/icdcodes.csv";
    private Connection conn;

    public ICD11Importer() {
        setTitle("ICD-11 Importer");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton importButton = new JButton("Import ICD-11 Data");
        importButton.addActionListener(e -> new Thread(() -> {
            try {
                connectDatabase();
                importCsvData(CSV_PATH);
                JOptionPane.showMessageDialog(this, "Import completed successfully!");
            } catch (Exception ex) {
                LOGGER.severe("Import error: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }).start());

        add(importButton, BorderLayout.CENTER);
        setVisible(true);
    }

    private void connectDatabase() throws SQLException {
        LOGGER.info("Connecting to database: " + DB_URL);
        conn = DriverManager.getConnection(DB_URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS icd11 (" +
                              "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                              "Mark TEXT, " +
                              "Code TEXT NOT NULL, " +
                              "ICD11Name TEXT NOT NULL, " +
                              "Note TEXT)");
        }
    }

    private void importCsvData(String csvPath) throws SQLException, IOException, CsvValidationException {
        LOGGER.info("Importing CSV data from: " + csvPath);
        try (CSVReader csvReader = new CSVReader(new FileReader(csvPath));
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO icd11 (Mark, Code, ICD11Name, Note) VALUES (?, ?, ?, ?)")) {
            String[] data;
            boolean firstLine = true;
            int rowCount = 0;

            while ((data = csvReader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header (assumed Code,ICD11Name)
                    continue;
                }

                LOGGER.info("Processing row: " + String.join(",", data));

                // CSV has Code,ICD11Name; Mark and Note are optional or missing
                if (data.length < 2) {
                    LOGGER.warning("Skipping invalid row with insufficient columns: " + String.join(",", data));
                    continue;
                }

                String mark = null; // Not present in CSV
                String code = data[0].trim(); // First column: Code
                String icd11Name = data[1].trim(); // Second column: ICD11Name
                String note = data.length > 2 && !data[2].isEmpty() ? data[2].trim() : null; // Optional Note

                if (code.isEmpty() || icd11Name.isEmpty()) {
                    LOGGER.warning("Skipping row with empty Code or ICD11Name: " + String.join(",", data));
                    continue;
                }

                pstmt.setString(1, mark);
                pstmt.setString(2, code);
                pstmt.setString(3, icd11Name);
                pstmt.setString(4, note);
                pstmt.executeUpdate();
                rowCount++;
            }
            LOGGER.info("Imported " + rowCount + " rows into icd11 table");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ICD11Importer::new);
    }
}