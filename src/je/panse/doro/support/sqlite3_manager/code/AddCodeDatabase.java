package je.panse.doro.support.sqlite3_manager.code;

import java.sql.*;	
import je.panse.doro.entry.EntryDir;
	
/**
 * A class to append disease code data into a SQLite database and remove duplicates.
 */
public class AddCodeDatabase {

    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";

    /**
     * Appends disease code data to the 'codedis' table.
     */
    public static void addData() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)")) {

            // Existing categories
            addArrhythmia(pstmt);

            System.out.println("Data added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding data: " + e.getMessage());
        }
    }

    private static void addArrhythmia(PreparedStatement pstmt) throws SQLException {
        String category = "Cardiac Arrhythmia";
        String[][] addArrhythmia = {
        		{"I48.0", "arrhythmia : Paroxysmal atrial fibrillation"},
                {"I48.1", "arrhythmia : Persistent atrial fibrillation"},
                {"I48.2", "arrhythmia : Chronic atrial fibrillation"},
                {"I48.3", "arrhythmia : Permanent atrial fibrillation"},
                {"I48.4", "arrhythmia : Typical atrial flutter"},
                {"I48.9", "arrhythmia : Unspecified atrial fibrillation"},
                {"I49.0", "arrhythmia : Ventricular fibrillation and flutter"},
                {"I49.1", "arrhythmia : Atrial premature depolarization"},
                {"I49.2", "arrhythmia : Junctional premature depolarization"},
                {"I49.3", "arrhythmia : Ventricular premature depolarization"},
                {"I49.4", "arrhythmia : Other and unspecified premature depolarization"},
                {"I49.5", "arrhythmia : Sick sinus syndrome"},
                {"I49.8", "arrhythmia : Other specified cardiac arrhythmias"},
                {"I49.9", "arrhythmia : Cardiac arrhythmia, unspecified"},
                {"I47.0", "arrhythmia : Re-entry ventricular arrhythmia"},
                {"I47.1", "arrhythmia : Supraventricular tachycardia"},
                {"I47.2", "arrhythmia : Ventricular tachycardia"},
                {"I47.9", "arrhythmia : Paroxysmal tachycardia, unspecified"},
                {"R00.0", "arrhythmia : Tachycardia, unspecified"},
                {"R00.1", "arrhythmia : Bradycardia, unspecified"},
                {"R00.2", "arrhythmia : Palpitations"}
        };

        for (String[] row : addArrhythmia) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }



    private static void addNeoplasmsOfUnspecifiedBehavior(PreparedStatement pstmt) throws SQLException {
        String category = "Neoplasms";
        String[][] neoplasmsUnspecified = {
            {"D49", "Neoplasm of unspecified behavior"}
        };

        for (String[] row : neoplasmsUnspecified) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    /**
     * Inserts a single row into the 'codedis' table.
     *
     * @param pstmt    The PreparedStatement to execute the insertion.
     * @param category The category of the disease (e.g., "Neoplasms").
     * @param bCode    The ICD-10 code (e.g., "C00").
     * @param name     The name or description of the disease (e.g., "Malignant neoplasm of lip").
     * @param reference The reference standard (e.g., "ICD-10").
     * @throws SQLException If an error occurs during insertion.
     */
    private static void addRow(PreparedStatement pstmt, String category, String bCode, String name, String reference) throws SQLException {
        pstmt.setString(1, category);
        pstmt.setString(2, bCode);
        pstmt.setString(3, name);
        pstmt.setString(4, reference);
        pstmt.executeUpdate();
    }

    /**
     * Removes duplicate entries from the 'codedis' table based on the 'B_code' column.
     * Keeps the first occurrence of each unique 'B_code' value.
     */
    public static void removeDuplicates() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create a temporary table with unique records
            stmt.execute("CREATE TABLE temp_codedis (" +
                    "Category TEXT, " +
                    "B_code TEXT, " +
                    "name TEXT, " +
                    "reference TEXT" +
                    ");");

            // Insert unique records into the temporary table
            stmt.execute("INSERT INTO temp_codedis (Category, B_code, name, reference) " +
                    "SELECT Category, B_code, name, reference " +
                    "FROM codedis " +
                    "WHERE rowid IN (" +
                    "    SELECT MIN(rowid) " +
                    "    FROM codedis " +
                    "    GROUP BY B_code" +
                    ");");

            // Drop the original table
            stmt.execute("DROP TABLE IF EXISTS codedis;");

            // Rename the temporary table to replace the original table
            stmt.execute("ALTER TABLE temp_codedis RENAME TO codedis;");

            System.out.println("Duplicates removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error removing duplicates: " + e.getMessage());
        }
    }

    /**
     * Main method to append data and remove duplicates.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        addData();
        removeDuplicates();
    }
}