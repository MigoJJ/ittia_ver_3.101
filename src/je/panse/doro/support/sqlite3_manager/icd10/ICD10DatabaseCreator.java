package je.panse.doro.support.sqlite3_manager.icd10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.sql.*;
import java.util.regex.Pattern;

public class ICD10DatabaseCreator extends JFrame {

    private JTextArea statusArea;

    public ICD10DatabaseCreator() {
        setTitle("ICD10 SQLite DB Creator");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton createButton = new JButton("Create SQLite DB");
        createButton.addActionListener(this::createDatabase);

        statusArea = new JTextArea();
        statusArea.setEditable(false);

        add(createButton, BorderLayout.NORTH);
        add(new JScrollPane(statusArea), BorderLayout.CENTER);
    }

    private void createDatabase(ActionEvent event) {
        String dbPath = "/home/migowj/git/ittia_ver_3.076/src/je/panse/doro/support/sqlite3_manager/icd10/icd10_data.db";
        String inputPath = "/home/migowj/git/ittia_ver_3.076/src/je/panse/doro/support/sqlite3_manager/icd_10/ICD10TextFile/ICD10_part_1.txt";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            statusArea.append("Database connected: " + dbPath + "\n");

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS icd10_data");
                stmt.execute("CREATE TABLE icd10_data ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "category TEXT, code TEXT, title TEXT, comment TEXT)");
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO icd10_data (category, code, title, comment) VALUES (?, ?, ?, ?)")) {

                String line;
                int count = 0;

                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    // Split using tabs or multiple spaces
                    String[] parts = line.trim().split("\\t|\\s{2,}");
                    if (parts.length < 4) {
                        statusArea.append("Skipped line: " + line + "\n");
                        continue;
                    }

                    pstmt.setString(1, parts[0].trim());
                    pstmt.setString(2, parts[1].trim());
                    pstmt.setString(3, parts[2].trim());
                    pstmt.setString(4, parts[3].trim());
                    pstmt.executeUpdate();
                    count++;
                }

                statusArea.append("Inserted " + count + " records into icd10_data.\n");
            }
        } catch (Exception e) {
            statusArea.append("Error: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ICD10DatabaseCreator().setVisible(true));
    }
}
