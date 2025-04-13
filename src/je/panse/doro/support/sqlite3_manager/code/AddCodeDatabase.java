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
        	addLipidAndDiabetesICD10(pstmt);

            System.out.println("Data added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding data: " + e.getMessage());
        }
    }

    private static void addLipidAndDiabetesICD10(PreparedStatement pstmt) throws SQLException {
        String category = "Lipid & Diabetes";
        String[][] addLipidAndDiabetes = {
            // Lipid metabolism disorders (E78)
            {"E78.0", "Pure hypercholesterolemia", category},
            {"E78.1", "Pure hyperglyceridemia", category},
            {"E78.2", "Mixed hyperlipidemia", category},
            {"E78.3", "Hyperchylomicronemia", category},
            {"E78.4", "Other hyperlipidemia", category},
            {"E78.5", "Hyperlipidemia, unspecified", category},
            {"E78.6", "Lipoprotein deficiency", category},
            {"E78.7", "Disorders of bile acid and cholesterol synthesis", category},
            {"E78.8", "Other disorders of lipoprotein metabolism", category},
            {"E78.9", "Disorder of lipoprotein metabolism, unspecified", category},

            // Diabetes with hyperglycemia or lipid abnormality (E08–E13)
            {"E08.65", "Diabetes mellitus due to underlying condition with hyperglycemia", category},
            {"E09.65", "Drug- or chemical-induced diabetes with hyperglycemia", category},
            {"E10.65", "Type 1 diabetes mellitus with hyperglycemia", category},
            {"E11.65", "Type 2 diabetes mellitus with hyperglycemia", category},
            {"E13.65", "Other specified diabetes mellitus with hyperglycemia", category},
            {"E11.69", "Type 2 diabetes mellitus with other specified complication", category},
            {"E11.9",  "Type 2 diabetes mellitus without complications", category},
            {"E10.9",  "Type 1 diabetes mellitus without complications", category},

            // Related findings and family history
            {"R79.89", "Other specified abnormal findings of blood chemistry", category},
            {"Z83.42", "Family history of familial hypercholesterolemia", category},
            {"Z83.3",  "Family history of diabetes mellitus", category},
            {"Z79.84", "Long-term (current) use of oral hypoglycemic drugs", category},
            {"Z79.4",  "Long-term (current) insulin use", category}
        };

        for (String[] row : addLipidAndDiabetes) {
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