package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;

import je.panse.doro.entry.EntryDir;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/soap/assessment/icd_10/diagnosis.db";
    private String[] columnNames = {"id", "code", "category", "description", "details"};

    public Vector<Vector<String>> loadTableData(Vector<String> columns) {
        LOGGER.info("Loading table data from database: " + DB_URL);
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
            columnNames = columns.toArray(new String[0]);
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

    public void addNewRecord(String[] data) throws SQLException {
        LOGGER.info("Adding new record: " + String.join(", ", data));
        if (data.length != columnNames.length) {
            LOGGER.severe("Data length (" + data.length + ") does not match column count (" + columnNames.length + ")");
            throw new SQLException("Invalid data length for adding record");
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
        }
    }

    public void deleteSelectedRow(String code) throws SQLException {
        LOGGER.info("Deleting record with code: " + code);
        if (code == null) {
            throw new SQLException("No row selected for deletion");
        }
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM diagnosis WHERE code = ?")) {
            pstmt.setString(1, code);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Record deleted successfully!");
            } else {
                LOGGER.warning("No record found with code: " + code);
                JOptionPane.showMessageDialog(null, "No record found to delete.");
            }
        }
    }

    public void saveChanges(String originalCode, String[] data) throws SQLException {
        LOGGER.info("Saving changes for code: " + originalCode);
        if (data.length != columnNames.length) {
            LOGGER.severe("Data length (" + data.length + ") does not match column count (" + columnNames.length + ")");
            throw new SQLException("Invalid data length for saving changes");
        }
        if (originalCode == null) {
            LOGGER.warning("No row selected for saving changes");
            throw new SQLException("No row selected for saving changes");
        }
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String[] assignments = new String[columnNames.length];
            for (int i = 0; i < columnNames.length; i++) {
                assignments[i] = columnNames[i] + " = ?";
            }
            String sql = "UPDATE diagnosis SET " + String.join(", ", assignments) + " WHERE code = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < columnNames.length; i++) {
                pstmt.setString(i + 1, data[i] != null ? data[i] : "");
            }
            pstmt.setString(columnNames.length + 1, originalCode);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Record updated successfully for code: " + originalCode);
                JOptionPane.showMessageDialog(null, "Changes saved successfully!");
            } else {
                LOGGER.warning("No record found with code: " + originalCode);
                JOptionPane.showMessageDialog(null, "No record found to update.");
            }
        }
    }
}