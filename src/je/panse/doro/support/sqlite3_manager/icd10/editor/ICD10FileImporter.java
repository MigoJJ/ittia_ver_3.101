package je.panse.doro.support.sqlite3_manager.icd10.editor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import je.panse.doro.entry.EntryDir;

public class ICD10FileImporter {
    private static final String INPUT_FILE = EntryDir.homeDir + "/support/sqlite3_manager/icd10/editor/components/ICD10_part_1.txt";
    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/icd10/editor/components/icd10.db";
    
    // Database table structure
    private static final String TABLE_NAME = "icd_codes_data";
    private static final String COL_CATEGORY = "category";
    private static final String COL_CODE = "code";
    private static final String COL_DESC = "description";

    public static void main(String[] args) {
        ICD10FileImporter importer = new ICD10FileImporter();
        importer.initializeDatabase();
        importer.importDataFromFile();
    }

    private void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CATEGORY + " TEXT, " +
                COL_CODE + " TEXT NOT NULL, " +
                COL_DESC + " TEXT)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database table created/verified successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    private void importDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
             Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO " + TABLE_NAME + " (" + COL_CATEGORY + ", " + COL_CODE + ", " + COL_DESC + ") VALUES (?, ?, ?)")) {
            
            conn.setAutoCommit(false); // Start transaction for better performance
            
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                // Parse the line into components
                String[] parts = line.split("\t");
                if (parts.length < 3) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }
                
                String category = parts[0].trim();
                String code = parts[1].trim();
                String description = parts[2].trim();
                
                // Insert into database
                pstmt.setString(1, category);
                pstmt.setString(2, code);
                pstmt.setString(3, description);
                pstmt.addBatch();
                
                count++;
                if (count % 100 == 0) {
                    pstmt.executeBatch(); // Execute batch every 100 records
                    System.out.println("Imported " + count + " records...");
                }
            }
            
            // Insert any remaining records
            pstmt.executeBatch();
            conn.commit();
            System.out.println("Successfully imported " + count + " records total");
            
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    // Helper method to print database contents (for verification)
    private void printDatabaseContents() {
        String sql = "SELECT * FROM " + TABLE_NAME + " LIMIT 10";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\nSample database contents:");
            System.out.println("ID\tCategory\tCode\tDescription");
            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + "\t" +
                    rs.getString(COL_CATEGORY) + "\t" +
                    rs.getString(COL_CODE) + "\t" +
                    rs.getString(COL_DESC)
                );
            }
        } catch (SQLException e) {
            System.err.println("Error reading database: " + e.getMessage());
        }
    }
}