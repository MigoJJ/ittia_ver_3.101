package je.panse.doro.support.sqlite3_manager.abbreviation.setfindedit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import je.panse.doro.entry.EntryDir;

public class DatabaseCreator {
    public static void main(String[] args) {
        // Path to the SQLite database
//        String url = "jdbc:sqlite:/home/migowj/git/ittia_ver_4.01/src/je/panse/doro/support/sqlite3/abbreviation/AbbFullDis.db";
        String url = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3abbreviation/AbbFullDis.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS aAbbreviation (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	aAbbreviation text NOT NULL,\n"
                + "	fullText text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("Table created successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}