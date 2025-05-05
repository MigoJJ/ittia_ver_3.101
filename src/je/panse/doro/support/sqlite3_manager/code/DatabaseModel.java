package je.panse.doro.support.sqlite3_manager.code;

import java.sql.*;	
import je.panse.doro.entry.EntryDir;
import javax.swing.table.DefaultTableModel;

class DatabaseModel {
    static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";

    public void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS codedis (Category TEXT, B_code TEXT PRIMARY KEY, name TEXT, reference TEXT)";
        executeSQL(sql);
    }

    public void createIndexOnBCode() {
        String sql = "CREATE INDEX IF NOT EXISTS idx_bcode ON codedis (B_code)";
        executeSQL(sql);
    }

    private void executeSQL(String sql) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public void insertRecord(String category, String bcode, String name, String reference) {
        String sql = "INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            pstmt.setString(2, bcode);
            pstmt.setString(3, name);
            pstmt.setString(4, reference);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
        }
    }

    public void deleteRecord(String bcode) {
        String sql = "DELETE FROM codedis WHERE B_code=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bcode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting record: " + e.getMessage());
        }
    }
    public void updateRecord(String oldBCode, String category, String newBCode, String name, String reference) {
        String sql = "UPDATE codedis SET Category = ?, B_code = ?, name = ?, reference = ? WHERE B_code = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            pstmt.setString(2, newBCode);
            pstmt.setString(3, name);
            pstmt.setString(4, reference);
            pstmt.setString(5, oldBCode); // Use the original B_code to identify the record
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("No record found with B_code: " + oldBCode);
            }
        } catch (SQLException e) {
            System.err.println("Error updating record: " + e.getMessage());
        }
    }

    public ResultSet getRecordsSortedByBCode() {
        String sql = "SELECT Category, B_code, name, reference FROM codedis ORDER BY B_code ASC";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching records: " + e.getMessage());
            return null;
        }
    }

    public void findAndDisplayRecords(String searchText, String column, DefaultTableModel tableModel) {
        String sql = "SELECT Category, B_code, name, reference FROM codedis WHERE ";
        switch (column) {
            case "B_code": sql += "B_code LIKE ?"; break;
            case "name": sql += "name LIKE ?"; break;
            case "reference": sql += "reference LIKE ?"; break;
            default: sql += "Category LIKE ? OR B_code LIKE ? OR name LIKE ? OR reference LIKE ?";
        }

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchText + "%";
            if (column.equals("ALL")) {
                pstmt.setString(1, searchPattern);
                pstmt.setString(2, searchPattern);
                pstmt.setString(3, searchPattern);
                pstmt.setString(4, searchPattern);
            } else {
                pstmt.setString(1, searchPattern);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getString("Category"), rs.getString("B_code"),
                        rs.getString("name"), rs.getString("reference")
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding records: " + e.getMessage());
        }
    }
}