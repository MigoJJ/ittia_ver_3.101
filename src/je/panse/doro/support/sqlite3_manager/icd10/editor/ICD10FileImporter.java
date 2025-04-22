package je.panse.doro.support.sqlite3_manager.icd10.editor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

import je.panse.doro.entry.EntryDir;

/**
 * ICD10FileImporter: Imports ICD-10 data from a tab-separated text file
 * into an SQLite database.
 */
public class ICD10FileImporter {

    // Configuration - File Paths and Database Details
    private static final String INPUT_FILE = EntryDir.homeDir + "/support/sqlite3_manager/icd10/editor/components/ICD10_pre.txt";
    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/icd10/editor/components/icd10_full.db";

    // Database Table and Column Names
    private static final String TABLE_NAME = "icd_codes_data";
    private static final String COL_CATEGORY = "category";
    private static final String COL_CODE = "code";
    private static final String COL_ICD_DISEASE = "icddisease";
    private static final String COL_DESCRIPTION = "description";

    public static void main(String[] args) {
        ICD10FileImporter importer = new ICD10FileImporter();
        importer.runImportProcess(); // Encapsulate the main flow
    }

    private void runImportProcess() {
        initializeDatabase();
        importDataFromFile();
        printDatabaseContents(); // Verify import
    }

    /**
     * Initializes the database by creating the table if it doesn't exist.
     */
    private void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CATEGORY + " TEXT, " +
                COL_CODE + " TEXT NOT NULL UNIQUE, " + // Enforce unique codes
                COL_ICD_DISEASE + " TEXT, " +
                COL_DESCRIPTION + " TEXT)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database table created/verified successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Imports data from the input text file into the database.
     */
    private void importDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
             Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + COL_CATEGORY + ", " + COL_CODE + ", " + COL_ICD_DISEASE + ", " + COL_DESCRIPTION + ") VALUES (?, ?, ?, ?)")) {

            conn.setAutoCommit(false); // Batch inserts for performance

            String line;
            int recordCount = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Normalize multiple spaces and tabs into a single tab
                String[] parts = line.replaceAll("\\s{2,}", "\t").split("\t");

                // Debug output for line parts
                // System.out.println("Line parts: " + Arrays.toString(parts));

                if (parts.length >= 3) {
                    String category = parts[0].trim().replaceAll("\\p{C}", "");
                    String code = parts[1].trim().replaceAll("\\p{C}", "");
                    String icdDisease = parts[2].trim().replaceAll("\\p{C}", "");
                    String description = (parts.length > 3 && !parts[3].trim().isEmpty()) ? parts[3].trim() : ".";
                    description = description.replaceAll("\\p{C}", "");

                    if (category.isEmpty() || code.isEmpty() || icdDisease.isEmpty()) {
                        System.err.println("Skipping due to empty field(s): category=" + category + ", code=" + code + ", icdDisease=" + icdDisease);
                        continue;
                    }

                    pstmt.setString(1, category);
                    pstmt.setString(2, code);
                    pstmt.setString(3, icdDisease);
                    pstmt.setString(4, description);
                    pstmt.addBatch();

                    recordCount++;
                    if (recordCount % 100 == 0) {
                        pstmt.executeBatch();
                        System.out.println("Imported " + recordCount + " records...");
                    }
                } else {
                    System.err.println("Skipping malformed line: [" + line + "] (Parts: " + parts.length + ")");
                }
            }

            pstmt.executeBatch();
            conn.commit();
            System.out.println("Successfully imported " + recordCount + " records total");

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Prints a sample of the database contents to the console for verification.
     */
    private void printDatabaseContents() {
        String sql = "SELECT * FROM " + TABLE_NAME + " LIMIT 10";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nSample database contents:");
            System.out.println("ID;\tCategory;\tCode;\tICDdisease;\tDescription");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + ";\t" +
                                rs.getString(COL_CATEGORY) + ";\t" +
                                rs.getString(COL_CODE) + ";\t" +
                                rs.getString(COL_ICD_DISEASE) + ";\t" +
                                rs.getString(COL_DESCRIPTION)
                );
            }
        } catch (SQLException e) {
            System.err.println("Error reading database: " + e.getMessage());
        }
    }
}
