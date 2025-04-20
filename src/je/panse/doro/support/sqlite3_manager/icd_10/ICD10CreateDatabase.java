package je.panse.doro.support.sqlite3_manager.icd_10;

import java.sql.*;
import java.io.*;

public class ICD10CreateDatabase {

    public static void main(String[] args) {
        String dbPath = "icd10.db";
        String dataFilePath = "/home/migowj/git/ittia_ver_3.076/src/je/panse/doro/support/sqlite3_manager/icd_10/ICD10_post.txt";

        try {
            // Connect to SQLite database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            Statement stmt = conn.createStatement();

            // Create ICD-10 table
            stmt.executeUpdate("DROP TABLE IF EXISTS icd10;");
            stmt.executeUpdate("""
                CREATE TABLE icd10 (
                    Category TEXT,
                    Code TEXT,
                    Title TEXT,
                    Comments TEXT
                );
            """);

            // Prepare insert statement
            String insertSQL = "INSERT INTO icd10 (Category, Code, Title, Comments) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            // Read and parse data file
            BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || !line.startsWith("{")) continue;

                // Strip formatting
                line = line.replaceAll("[{}\"]", "").replace(" ,", ",");
                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    String category = parts[0].trim();
                    String codeAndTitle = parts[1].trim();
                    String code = codeAndTitle.split(" ")[0];
                    String title = codeAndTitle.substring(code.length()).trim();
                    String comments = parts[3].trim();

                    pstmt.setString(1, category);
                    pstmt.setString(2, code);
                    pstmt.setString(3, title);
                    pstmt.setString(4, comments);
                    pstmt.executeUpdate();
                }
            }

            reader.close();
            pstmt.close();
            conn.close();

            System.out.println("ICD-10 database created successfully: " + dbPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
