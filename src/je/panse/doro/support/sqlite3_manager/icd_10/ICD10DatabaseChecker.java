package je.panse.doro.support.sqlite3_manager.icd_10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import je.panse.doro.entry.EntryDir; // Make sure this import is correct for your project

public class ICD10DatabaseChecker {

    private static final String DB_PATH = EntryDir.homeDir + "/support/sqlite3_manager/icd10/icd10_data.db";

    public static void main(String[] args) {
        checkDatabase();
    }

    public static void checkDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
             Statement stmt = conn.createStatement()) {

            System.out.println("Successfully connected to the database: " + DB_PATH);

            // Check if the 'icd10' table exists
            ResultSet tableCheckResult = conn.getMetaData().getTables(null, null, "icd10", null);
            if (tableCheckResult.next()) {
                System.out.println("Table 'icd10' exists.");

                // Check the number of rows in the 'icd10' table
                ResultSet rowCountResult = stmt.executeQuery("SELECT COUNT(*) FROM icd10");
                if (rowCountResult.next()) {
                    int rowCount = rowCountResult.getInt(1);
                    System.out.println("Number of rows in 'icd10': " + rowCount);
                    if (rowCount == 0) {
                        System.out.println("The 'icd10' table is empty.");
                    }
                } else {
                    System.out.println("Could not retrieve the row count.");
                }
                rowCountResult.close();

                // Optionally, you can check the column names
                ResultSet columnCheckResult = conn.getMetaData().getColumns(null, null, "icd10", null);
                System.out.println("\nColumns in 'icd10' table:");
                while (columnCheckResult.next()) {
                    String columnName = columnCheckResult.getString("COLUMN_NAME");
                    String columnType = columnCheckResult.getString("TYPE_NAME");
                    System.out.println("- " + columnName + " (" + columnType + ")");
                }
                columnCheckResult.close();

            } else {
                System.out.println("Table 'icd10' does not exist.");
            }
            tableCheckResult.close();

        } catch (SQLException e) {
            System.err.println("Error checking the database: " + e.getMessage());
        }
    }
}