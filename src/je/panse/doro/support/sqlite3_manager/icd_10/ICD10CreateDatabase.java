package je.panse.doro.support.sqlite3_manager.icd_10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import je.panse.doro.entry.EntryDir;

public class ICD10CreateDatabase {
    private static final String DB_PATH = EntryDir.homeDir + "/support/sqlite3_manager/icd_10/icd10.db";
    private static final String DATA_FILE_PATH = EntryDir.homeDir + "/support/sqlite3_manager/icd_10/ICD10_post.txt";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
             Statement stmt = conn.createStatement()) {

            // Drop existing table if it exists
            stmt.executeUpdate("DROP TABLE IF EXISTS icd10;");

            // Create new ICD-10 table
            stmt.executeUpdate("""
                    CREATE TABLE icd10 (
                        Category TEXT,
                        Code TEXT,
                        Title TEXT,
                        Comments TEXT
                    );
                    """);

            System.out.println("ICD-10 database and table created successfully.");

        } catch (SQLException e) {
            System.err.println("SQL error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}