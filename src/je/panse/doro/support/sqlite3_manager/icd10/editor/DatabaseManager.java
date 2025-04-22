package je.panse.doro.support.sqlite3_manager.icd10.editor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class DatabaseManager {

    private String dbUrl = "jdbc:sqlite:/home/dce040b/git/ittia_ver_3.076_001/src/je/panse/doro/support/sqlite3_manager/icd10/editor/components/icd10_data.db";
    private Connection connection;

    // --- Adjust Table and Column Names Here ---
    private static final String TABLE_NAME = "icd_codes_data"; // Adjusted table name
    private static final String COL_ID = "id";             // Adjust ID column name (if exists)
    private static final String COL_CODE = "code";           // Adjust code column name
    private static final String COL_DESC = "description";    // Adjust description column name
    // -----------------------------------------

    public DatabaseManager() {
        // Optional: Load the driver explicitly (often not needed with modern JDBC)
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            showError("SQLite JDBC Driver not found.", e);
            System.exit(1); // Exit if driver is missing
        }
    }

    public Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
             try {
                connection = DriverManager.getConnection(dbUrl);
                System.out.println("Connection to SQLite has been established.");
             } catch (SQLException e) {
                showError("Failed to connect to the database.", e);
                throw e; // Re-throw exception after showing error
             }
        }
        return connection;
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to SQLite has been closed.");
            } catch (SQLException e) {
                showError("Error closing database connection.", e);
            } finally {
                 connection = null; // Ensure connection is marked as null
            }
        }
    }

    // Load data into a TableModel
    public DefaultTableModel loadData() {
        // Define column names for the JTable model
        // IMPORTANT: Make sure the order matches your SELECT query
        Vector<String> columnNames = new Vector<>();
        columnNames.add(COL_ID); // Assuming an ID column exists and is first
        columnNames.add(COL_CODE);
        columnNames.add(COL_DESC);

        Vector<Vector<Object>> data = new Vector<>();
        String sql = "SELECT " + COL_ID + ", " + COL_CODE + ", " + COL_DESC + " FROM " + TABLE_NAME;

        try (Connection conn = connect(); // Use try-with-resources for connection
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Fetch data rows
            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    vector.add(rs.getObject(i));
                }
                data.add(vector);
            }

        } catch (SQLException e) {
            showError("Error loading data from database.", e);
            // Return an empty model on error, but with correct columns
            return new DefaultTableModel(data, columnNames);
        } finally {
             disconnect(); // Ensure disconnection
        }

        // Create and return the table model
        return new DefaultTableModel(data, columnNames) {
             // Optional: Make cells non-editable directly in the table
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
    }

    // Add a new record
    public boolean addRecord(String code, String description) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COL_CODE + ", " + COL_DESC + ") VALUES(?, ?)";
        boolean success = false;

        if (code == null || code.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Code cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.setString(2, description);
            int affectedRows = pstmt.executeUpdate();
            success = affectedRows > 0;
            if(success) {
                 System.out.println("Record added successfully.");
            } else {
                 System.out.println("Record could not be added.");
            }
        } catch (SQLException e) {
            showError("Error adding record.", e);
        } finally {
            disconnect();
        }
        return success;
    }

    // Update an existing record (identified by ID)
    public boolean updateRecord(int id, String code, String description) {
        String sql = "UPDATE " + TABLE_NAME + " SET " + COL_CODE + " = ?, " + COL_DESC + " = ? WHERE " + COL_ID + " = ?";
         boolean success = false;

        if (code == null || code.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Code cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (id <= 0) {
             JOptionPane.showMessageDialog(null, "Invalid ID for update.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.setString(2, description);
            pstmt.setInt(3, id);
            int affectedRows = pstmt.executeUpdate();
            success = affectedRows > 0;
             if(success) {
                 System.out.println("Record updated successfully.");
            } else {
                 System.out.println("Record with ID " + id + " not found or not updated.");
            }
        } catch (SQLException e) {
            showError("Error updating record.", e);
        } finally {
            disconnect();
        }
         return success;
    }

    // Delete a record (identified by ID)
    public boolean deleteRecord(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?";
        boolean success = false;

        if (id <= 0) {
             JOptionPane.showMessageDialog(null, "Invalid ID for deletion.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }


        int confirmation = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to delete the record with ID " + id + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return false; // User cancelled
        }

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            success = affectedRows > 0;
            if(success) {
                 System.out.println("Record deleted successfully.");
            } else {
                 System.out.println("Record with ID " + id + " not found or not deleted.");
            }
        } catch (SQLException e) {
            showError("Error deleting record.", e);
        } finally {
            disconnect();
        }
        return success;
    }

    // Helper to show error messages
    private void showError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        // e.printStackTrace(); // Uncomment for detailed stack trace during development
        JOptionPane.showMessageDialog(
            null, // Parent component (null centers on screen)
            message + "\n" + e.getMessage(), // Error message
            "Database Error", // Dialog title
            JOptionPane.ERROR_MESSAGE // Icon type
        );
    }
}
