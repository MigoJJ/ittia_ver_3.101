package je.panse.doro.support.sqlite3_manager.icd_10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import je.panse.doro.entry.EntryDir;

public class ICD10DataLoader {
    private static final String DB_PATH = EntryDir.homeDir + "/support/sqlite3_manager/icd_10/icd10.db";
    private static final String DATA_FILE_PATH = EntryDir.homeDir + "/support/sqlite3_manager/icd_10/ICD10_post_converted.txt";

    public static void main(String[] args) {
        loadData();
    }

    public static void loadData() {
        try (Connection conn = createConnection()) {
            conn.setAutoCommit(false); // Use transactions for better performance
            
            try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_PATH))) {
                String line;
                int lineNumber = 0;
                int insertedCount = 0;
                
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    line = line.trim();
                    
                    if (!isValidLine(line)) {
                        System.err.println("Skipping invalid line #" + lineNumber + ": " + line);
                        continue;
                    }
                    
                    ICD10Record record = parseLine(line);
                    if (record != null) {
                        if (insertRecord(conn, record)) {
                            insertedCount++;
                        }
                    } else {
                        System.err.println("Failed to parse line #" + lineNumber + ": " + line);
                    }
                    
                    // Commit every 100 records to balance performance and memory usage
                    if (insertedCount % 100 == 0) {
                        conn.commit();
                    }
                }
                
                conn.commit(); // Commit any remaining records
                System.out.println("Successfully loaded " + insertedCount + " ICD-10 records.");
                
            } catch (IOException e) {
                conn.rollback();
                System.err.println("Error reading data file: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static Connection createConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }

    private static boolean isValidLine(String line) {
        return line.startsWith("{\"") && line.endsWith("\" },") || 
               line.startsWith("{\"") && line.endsWith("\" }");
    }

    private static ICD10Record parseLine(String line) {
        try {
            // Remove surrounding braces and trailing comma
            String content = line.substring(1, line.length() - 2).trim();
            
            // Split on commas but ignore commas within quotes
            String[] parts = content.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            
            if (parts.length != 4) {
                return null;
            }
            
            return new ICD10Record(
                cleanField(parts[0]),
                cleanField(parts[1]),
                cleanField(parts[2]),
                cleanField(parts[3])
            );
        } catch (Exception e) {
            return null;
        }
    }

    private static String cleanField(String field) {
        return field.replaceAll("\"", "").trim();
    }

    private static boolean insertRecord(Connection conn, ICD10Record record) {
        String sql = "INSERT INTO icd10 (Category, Code, Title, Comments) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, record.category);
            pstmt.setString(2, record.code);
            pstmt.setString(3, record.title);
            pstmt.setString(4, record.comments);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + record + " - " + e.getMessage());
            return false;
        }
    }

    // Helper class to represent ICD-10 records
    private static class ICD10Record {
        String category;
        String code;
        String title;
        String comments;

        public ICD10Record(String category, String code, String title, String comments) {
            this.category = category;
            this.code = code;
            this.title = title;
            this.comments = comments;
        }

        @Override
        public String toString() {
            return String.format("{\"%s\", \"%s\", \"%s\", \"%s\"}",
                    category, code, title, comments);
        }
    }
}