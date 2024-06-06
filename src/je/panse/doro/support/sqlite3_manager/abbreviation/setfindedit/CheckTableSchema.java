package je.panse.doro.support.sqlite3_manager.abbreviation.setfindedit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import je.panse.doro.entry.EntryDir;

public class CheckTableSchema {
    private static String dbURL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3abbreviation/AbbFullDis.db";

    public static void main(String[] args) {
        checkTableSchema("abbreviations");
    }

    private static void checkTableSchema(String tableName) {
        String query = "PRAGMA table_info(" + tableName + ");";

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Column Names in " + tableName + " table:");
            while (rs.next()) {
                String columnName = rs.getString("name");
                System.out.println(columnName);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
