package je.panse.doro.support.sqlite3_manager.icd10.editor10;

import java.io.BufferedReader;	
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import je.panse.doro.entry.EntryDir;

public class ICD10FileImporter {
    private static final String INPUT_FILE = EntryDir.homeDir + "/support/sqlite3_manager/icd10/editor10/20240131.csv";
    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/icd10/editor10/icd10.db";
    
    // Database table structure
    private static final String TABLE_NAME = "disease_codes";
    private static final String COL_CODE = "code";
    private static final String COL_KOREAN_NAME = "korean_name";
    private static final String COL_ENGLISH_NAME = "english_name";
    private static final String COL_COMPLETE_CODE = "complete_code";
    private static final String COL_MAIN_DISEASE = "main_disease";
    private static final String COL_INFECTIOUS = "infectious";
    private static final String COL_GENDER = "gender";
    private static final String COL_MAX_AGE = "max_age";
    private static final String COL_MIN_AGE = "min_age";
    private static final String COL_MEDICAL_TYPE = "medical_type";

    public static void main(String[] args) {
        ICD10FileImporter importer = new ICD10FileImporter();
        importer.initializeDatabase();
        importer.importDataFromFile();
    }

    private void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CODE + " TEXT NOT NULL, " +
                COL_KOREAN_NAME + " TEXT, " +
                COL_ENGLISH_NAME + " TEXT, " +
                COL_COMPLETE_CODE + " TEXT, " +
                COL_MAIN_DISEASE + " TEXT, " +
                COL_INFECTIOUS + " TEXT, " +
                COL_GENDER + " TEXT, " +
                COL_MAX_AGE + " TEXT, " +
                COL_MIN_AGE + " TEXT, " +
                COL_MEDICAL_TYPE + " TEXT)";

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
                     "INSERT INTO " + TABLE_NAME + " (" + 
                     COL_CODE + ", " + 
                     COL_KOREAN_NAME + ", " + 
                     COL_ENGLISH_NAME + ", " + 
                     COL_COMPLETE_CODE + ", " + 
                     COL_MAIN_DISEASE + ", " + 
                     COL_INFECTIOUS + ", " + 
                     COL_GENDER + ", " + 
                     COL_MAX_AGE + ", " + 
                     COL_MIN_AGE + ", " + 
                     COL_MEDICAL_TYPE + 
                     ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            
            conn.setAutoCommit(false); // Start transaction for better performance
            
            String line;
            int count = 0;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                // Skip header line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // Parse the CSV line
                String[] parts = line.split(",");
                if (parts.length < 10) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }
                
                String code = parts[0].trim();
                String koreanName = parts[1].trim();
                String englishName = parts[2].trim();
                String completeCode = parts[3].trim();
                String mainDisease = parts[4].trim();
                String infectious = parts[5].trim();
                String gender = parts[6].trim();
                String maxAge = parts[7].trim();
                String minAge = parts[8].trim();
                String medicalType = parts[9].trim();
                
                // Insert into database
                pstmt.setString(1, code);
                pstmt.setString(2, koreanName);
                pstmt.setString(3, englishName);
                pstmt.setString(4, completeCode);
                pstmt.setString(5, mainDisease);
                pstmt.setString(6, infectious);
                pstmt.setString(7, gender);
                pstmt.setString(8, maxAge);
                pstmt.setString(9, minAge);
                pstmt.setString(10, medicalType);
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
            System.out.println("ID\tCode\tKorean Name\tEnglish Name");
            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + "\t" +
                    rs.getString(COL_CODE) + "\t" +
                    rs.getString(COL_KOREAN_NAME) + "\t" +
                    rs.getString(COL_ENGLISH_NAME)
                );
            }
        } catch (SQLException e) {
            System.err.println("Error reading database: " + e.getMessage());
        }
    }
}