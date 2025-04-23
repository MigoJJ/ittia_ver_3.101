package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;
import je.panse.doro.entry.EntryDir;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/soap/assessment/icd_10/diagnosis.db";

    public Vector<Vector<String>> loadTableData(Vector<String> columns) {
        Vector<Vector<String>> data = new Vector<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM diagnosis")) {

            columns.clear();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(metaData.getColumnName(i));
            }

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + ex.getMessage());
        }
        return data;
    }

    public Vector<Vector<String>> searchData(String searchText, Vector<String> columns) {
        Vector<Vector<String>> data = new Vector<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM diagnosis WHERE LOWER(code) LIKE ? OR LOWER(codewithseparator) LIKE ? OR LOWER(short) LIKE ? OR LOWER(long_description) LIKE ?")) {
            for (int i = 1; i <= 4; i++) {
                pstmt.setString(i, "%" + searchText.toLowerCase() + "%");
            }
            ResultSet rs = pstmt.executeQuery();

            columns.clear();
            columns.add("code");
            columns.add("codewithseparator");
            columns.add("short");
            columns.add("long_description");

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= 4; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Search error: " + ex.getMessage());
        }
        return data;
    }

    public void addNewRecord(String[] data) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO diagnosis (code, codewithseparator, short, long_description) VALUES (?, ?, ?, ?)")) {
            for (int i = 0; i < 4; i++) {
                pstmt.setString(i + 1, data[i]);
            }
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record added successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error adding record: " + ex.getMessage());
        }
    }

    public void deleteSelectedRow(String code) {
        if (code == null) {
            JOptionPane.showMessageDialog(null, "Please select a row to delete");
            return;
        }
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM diagnosis WHERE code = ?")) {
            pstmt.setString(1, code);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record deleted successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting record: " + ex.getMessage());
        }
    }

    public void saveChanges(String originalCode, String[] data) {
        if (originalCode == null) {
            JOptionPane.showMessageDialog(null, "Please select a row to save changes");
            return;
        }
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE diagnosis SET code = ?, codewithseparator = ?, short = ?, long_description = ? WHERE code = ?")) {
            for (int i = 0; i < 4; i++) {
                pstmt.setString(i + 1, data[i]);
            }
            pstmt.setString(5, originalCode);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Changes saved successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saving changes: " + ex.getMessage());
        }
    }
}