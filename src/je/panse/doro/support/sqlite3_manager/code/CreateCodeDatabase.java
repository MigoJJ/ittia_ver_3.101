package je.panse.doro.support.sqlite3_manager.code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; // Added for specific exception handling
import java.nio.file.Paths; // Added for better path handling
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import je.panse.doro.entry.EntryDir;

public class CreateCodeDatabase {

    // Use Paths.get for potentially better cross-platform path construction
    private static final String DB_FILE_PATH = Paths.get(EntryDir.homeDir, "support", "sqlite3_manager", "code", "CodeFullDis.db").toString();
    private static final String DB_URL = "jdbc:sqlite:" + DB_FILE_PATH;
    private static final String TXT_FILE_PATH = Paths.get(EntryDir.homeDir, "support", "sqlite3_manager", "code", "ICD10_Split_001.txt").toString();

    public static void main(String[] args) {
        Connection conn = null; // Declare outside try-with-resources for potential use in finally (though try-with-resources handles closing)
        try {
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false); // Start transaction for potentially faster batch inserts

            // Ensure table exists using try-with-resources for the Statement
            try (Statement stmt = conn.createStatement()) {
                String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS codedis (
                        Category TEXT,
                        B_code TEXT PRIMARY KEY, -- Consider making B_code a primary key if it's unique
                        Diagnosis TEXT,
                        reference TEXT
                    )
                """;
                // Removed UNIQUE constraint as duplicate codes might exist in source or need specific handling
                stmt.executeUpdate(createTableSQL);
                System.out.println("Table 'codedis' ensured in database: " + DB_FILE_PATH);
            } // Statement automatically closed here

            // Prepare statement for data insertion using try-with-resources
            String insertSQL = "INSERT OR IGNORE INTO codedis (Category, B_code, Diagnosis, reference) VALUES (?, ?, ?, ?)";
            // Using "INSERT OR IGNORE" to skip duplicates if B_code is PRIMARY KEY or has a UNIQUE constraint

            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL);
                 BufferedReader reader = new BufferedReader(new FileReader(TXT_FILE_PATH))) {

                String line;
                int batchSize = 1000; // Commit changes in batches for large files
                int count = 0;

                System.out.println("Reading data from: " + TXT_FILE_PATH);
                while ((line = reader.readLine()) != null) {
                    line = line.trim(); // Trim leading/trailing whitespace from the whole line first
                    if (line.isEmpty() || line.startsWith("#")) { // Skip empty lines or lines starting with # (optional comment indicator)
                         continue;
                    }

                    // Split the line at the *first* occurrence of one or more spaces
                    String[] parts = line.split("\\s+", 2);

                    if (parts.length == 2) {
                        String bCode = parts[0].trim();     // Trim the code part
                        String diagnosis = parts[1].trim(); // Trim the diagnosis part

                        // Optional: Add validation if needed (e.g., check if bCode format is valid)
                        if (bCode.isEmpty() || diagnosis.isEmpty()) {
                           System.err.println("Skipping line due to empty code or diagnosis after trim: " + line);
                           continue;
                        }

                        pstmt.setString(1, "General");      // Default category
                        pstmt.setString(2, bCode);          // B_code (trimmed)
                        pstmt.setString(3, diagnosis);      // Diagnosis (trimmed)
                        pstmt.setString(4, "ICD-10");       // Reference
                        pstmt.addBatch();

                        count++;
                        if (count % batchSize == 0) {
                            System.out.println("Executing batch insert #" + (count / batchSize));
                            pstmt.executeBatch();
                            conn.commit(); // Commit the transaction batch
                        }
                    } else {
                        System.err.println("Skipping malformed line (expected code and diagnosis separated by space): " + line);
                    }
                }

                System.out.println("Executing final batch insert...");
                pstmt.executeBatch(); // Execute the remaining batch
                conn.commit();        // Commit the final transaction batch
                System.out.println("Data insertion complete. Total records processed (approx): " + count);

            } catch (IOException e) {
                System.err.println("Error reading file [" + TXT_FILE_PATH + "]: " + e.getMessage());
                if (conn != null) conn.rollback(); // Rollback transaction on file error
            } catch (SQLException e) {
                System.err.println("Error during batch insert/commit: " + e.getMessage());
                 if (conn != null) conn.rollback(); // Rollback transaction on SQL error
            }

        } catch (SQLException e) {
            System.err.println("Database connection or initial setup error: " + e.getMessage());
             // No need to rollback here as the connection likely failed
        } finally {
             if (conn != null) {
                 try {
                    conn.setAutoCommit(true); // Reset auto-commit state
                    conn.close(); // Ensure connection is closed (though try-with-resources usually handles the initial connection variable)
                    System.out.println("Database connection closed.");
                } catch (SQLException ex) {
                    System.err.println("Error closing connection: " + ex.getMessage());
                }
             }
        }
    }
}