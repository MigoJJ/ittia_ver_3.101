package je.panse.doro.support.sqlite3_manager.code;


import java.sql.*;	
import je.panse.doro.entry.EntryDir;


public class CreateCodeDatabase {

    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = """
                    CREATE TABLE IF NOT EXISTS codedis (
                        Category TEXT,
                        B_code TEXT,
                        name TEXT,
                        reference TEXT
                    )
                    """;
            stmt.executeUpdate(sql);
            System.out.println("Database and table 'codedis' created successfully.");


            //Optional - Insert initial example data.

            String insertSQL = "INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, "Endocrine");
                pstmt.setString(2, "E119");
                pstmt.setString(3, "Type 2 diabetes mellitus without complications");
                pstmt.setString(4, "ICD-10");
                pstmt.executeUpdate();

                pstmt.setString(1, "Cardiovascular");
                pstmt.setString(2, "I10");
                pstmt.setString(3, "Essential (primary) hypertension");
                pstmt.setString(4, "ICD-10");
                pstmt.executeUpdate();

                System.out.println("Initial example data inserted.");
            }


        } catch (SQLException e) {
            System.err.println("Error creating database or table: " + e.getMessage());
        }
    }
}

