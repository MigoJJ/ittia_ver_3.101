package je.panse.doro.support.sqlite3_manager.icd10.editor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import je.panse.doro.entry.EntryDir;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/icd10/editor/components/icd10_full.db";
    private static final String TABLE_NAME = "icd_codes_data";

    public DefaultTableModel loadData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Category");
        model.addColumn("Code");
        model.addColumn("ICD Disease");
        model.addColumn("Description");

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLE_NAME)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("code"),
                        rs.getString("icddisease"),
                        rs.getString("description")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

    // Modified addRecord method to accept icdDisease
    public boolean addRecord(String code, String icdDisease, String description) {
        String sql = "INSERT INTO " + TABLE_NAME + " (category, code, icddisease, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ""); // Category: empty for now, extend InputPanel if needed
            pstmt.setString(2, code);
            pstmt.setString(3, icdDisease);
            pstmt.setString(4, description);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding record: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Modified updateRecord method to include icdDisease
    public boolean updateRecord(int id, String code, String icdDisease, String description) {
        String sql = "UPDATE " + TABLE_NAME + " SET code = ?, icddisease = ?, description = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.setString(2, icdDisease);
            pstmt.setString(3, description);
            pstmt.setInt(4, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No record found with ID: " + id, "Update Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating record: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean deleteRecord(int id) {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete record with ID: " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }

        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No record found with ID: " + id, "Delete Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting record: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}