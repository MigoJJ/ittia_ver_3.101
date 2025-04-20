package je.panse.doro.support.sqlite3_manager.icd_10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import je.panse.doro.entry.EntryDir; // Make sure this import is correct for your project

public class ICD10DataLoader {

    private static final String DB_PATH = EntryDir.homeDir + "/support/sqlite3_manager/icd_10/icd10.db";
    private static final String DATA_FILE_PATH = EntryDir.homeDir + "/support/sqlite3_manager/icd_10/ICD10_post.txt";

    public static void main(String[] args) {
        loadData();
    }

    public static void loadData() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
             BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_PATH))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("{") && line.endsWith("},")) {
                    line = line.substring(1, line.length() - 2);
                    String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                 // Inside the while loop, after splitting and trimming:
                    if (parts.length == 4) {
                        String category = parts[0].replaceAll("\"", "").trim();
                        String code = parts[1].replaceAll("\"", "").trim();
                        String title = parts[2].replaceAll("\"", "").trim();
                        String comments = parts[3].replaceAll("\"", "").trim();

                        System.out.println("Category: [" + category + "]");
                        System.out.println("Code: [" + code + "]");
                        System.out.println("Title: [" + title + "]");
                        System.out.println("Comments: [" + comments + "]");
                        
                        
                        String sql = "INSERT INTO icd10 (Category, Code, Title, Comments) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setString(1, category);
                            pstmt.setString(2, code);
                            pstmt.setString(3, title);
                            pstmt.setString(4, comments);
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            System.err.println("Error inserting record: " + line + " - " + e.getMessage());
                        }
                    } else {
                        System.err.println("Skipping invalid line (incorrect number of parts): " + line);
                    }
                } else {
                    System.err.println("Skipping invalid line (incorrect format): " + line);
                }
            }
            System.out.println("ICD-10 data loaded successfully.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading data file: " + e.getMessage());
        }
    }
}