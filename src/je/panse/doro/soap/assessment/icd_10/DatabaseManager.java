package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Logger;
import je.panse.doro.entry.EntryDir;

public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/soap/assessment/icd_10/diagnosis.db";
    private String[] columnNames = {"id", "code", "category", "description", "details"}; // Adjust based on actual schema

    public Vector<Vector<String>> loadTableData(Vector<String> columns) {
        LOGGER.info("Loading table data from database");
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
            columnNames = columns.toArray(new String[0]); // Update columnNames dynamically
            LOGGER.info("Column names: " + String.join(", ", columnNames));

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            LOGGER.info("Loaded " + data.size() + " rows");
        } catch (SQLException ex) {
            LOGGER.severe("Error loading data: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error loading data: " + ex.getMessage());
        }
        return data;
    }

    public Vector<Vector<String>> searchData(String searchText, Vector<String> columns) {
        LOGGER.info("Executing search with text: " + searchText);
        Vector<Vector<String>> data = new Vector<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            StringBuilder sql = new StringBuilder("SELECT * FROM diagnosis WHERE ");
            for (int i = 0; i < columnNames.length; i++) {
                sql.append("LOWER(").append(columnNames[i]).append(") LIKE ?");
                if (i < columnNames.length - 1) {
                    sql.append(" OR ");
                }
            }
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            String searchPattern = "%" + searchText.toLowerCase() + "%";
            for (int i = 1; i <= columnNames.length; i++) {
                pstmt.setString(i, searchPattern);
            }
            ResultSet rs = pstmt.executeQuery();

            columns.clear();
            for (String col : columnNames) {
                columns.add(col);
            }

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= columnNames.length; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            LOGGER.info("Search returned " + data.size() + " rows");
        } catch (SQLException ex) {
            LOGGER.severe("Search error: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Search error: " + ex.getMessage());
        }
        return data;
    }

    public void addNewRecord(String[] data) {
        if (data.length != columnNames.length) {
            LOGGER.severe("Data length (" + data.length + ") does not match column count (" + columnNames.length + ")");
            JOptionPane.showMessageDialog(null, "Invalid data length for adding record");
            return;
        }
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO diagnosis (" + String.join(", ", columnNames) + ") VALUES (" +
                     String.join(", ", new String[columnNames.length]).replaceAll("[^,]+", "?") + ")")) {
            for (int i = 0; i < columnNames.length; i++) {
                pstmt.setString(i + 1, data[i]);
            }
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record added successfully!");
        } catch (SQLException ex) {
            LOGGER.severe("Error adding record: " + ex.getMessage());
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
            LOGGER.severe("Error deleting record: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error deleting record: " + ex.getMessage());
        }
    }

    public void saveChanges(String originalCode, String[] data) {
        if (data.length != columnNames.length) {
            LOGGER.severe("Data length (" + data.length + ") does not match column count (" + columnNames.length + ")");
            JOptionPane.showMessageDialog(null, "Invalid data length for saving changes");
            return;
        }
        if (originalCode == null) {
            JOptionPane.showMessageDialog(null, "Please select a row to save changes");
            return;
        }
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE diagnosis SET " +
                     String.join(", ", new String[columnNames.length]).replaceAll("[^,]+", "?") +
                     " WHERE code = ?")) {
            for (int i = 0; i < columnNames.length; i++) {
                pstmt.setString(i + 1, data[i]);
            }
            pstmt.setString(columnNames.length + 1, originalCode);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Changes saved successfully!");
        } catch (SQLException ex) {
            LOGGER.severe("Error saving changes: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error saving changes: " + ex.getMessage());
        }
    }
}