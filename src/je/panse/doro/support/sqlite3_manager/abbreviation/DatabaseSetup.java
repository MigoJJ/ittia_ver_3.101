package je.panse.doro.support.sqlite3_manager.abbreviation;

import je.panse.doro.entry.EntryDir;	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseSetup {
//    private static final String DATABASE_URL = "jdbc:sqlite:/home/migowj/git/ittia_ver_4.01/src/je/panse/doro/support/sqlite3/abbreviation/AbbFullDis.db";
    private static String DATABASE_URL = "jdbc:sqlite:/" + EntryDir.homeDir + "/support/sqlite3abbreviation/AbbFullDis.db";
    
    public static void main(String[] args) {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Abbreviation (\n"
                + " id integer PRIMARY KEY,\n"
                + " abbreviation text NOT NULL,\n"
                + " fullText text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            // Create a new table
            stmt.execute(sql);
            System.out.println("Table created successfully or already exists.");
        } catch (Exception e) {
            System.out.println("An error occurred while creating the table: " + e.getMessage());
        }
    }
}
