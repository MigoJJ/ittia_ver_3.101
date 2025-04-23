package je.panse.doro.support.sqlite3_manager.icd10.editor10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import je.panse.doro.entry.EntryDir;

public class DatabaseManager {
    // Database connection URL
    private final String dbUrl;
    private Connection connection;

    // Table and column names
    private static final String TABLE_NAME = "icd_codes_data";
    private static final String COL_ID = "id";
    private static final String COL_CATEGORY = "category";
    private static final String COL_CODE = "code";
    private static final String COL_DESC = "description";

    public DatabaseManager() {
        this.dbUrl = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/icd10/editor10/icd10.db";
        loadDriver();
        initializeDatabase(); // Ensure table exists
    }

    private void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                COL_CATEGORY + " TEXT, " +
                                COL_CODE + " TEXT NOT NULL UNIQUE, " +
                                COL_DESC + " TEXT)";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database table verified/created successfully");
        } catch (SQLException e) {
            showError("Error initializing database", e);
        }
    }

    private void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            showError("SQLite JDBC Driver not found.", e);
            System.exit(1);
        }
    }

    public Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(dbUrl);
                System.out.println("Connection to SQLite has been established.");
            } catch (SQLException e) {
                showError("Failed to connect to the database.", e);
                throw e;
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
                connection = null;
            }
        }
    }

    public DefaultTableModel loadData() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add(COL_ID);
        columnNames.add(COL_CATEGORY);
        columnNames.add(COL_CODE);
        columnNames.add(COL_DESC);

        Vector<Vector<Object>> data = new Vector<>();
        String sql = String.format("SELECT %s, %s, %s, %s FROM %s", COL_ID, COL_CATEGORY, COL_CODE, COL_DESC, TABLE_NAME);

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Vector<Object> vector = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    vector.add(rs.getObject(i));
                }
                data.add(vector);
            }
        } catch (SQLException e) {
            showError("Error loading data from database.", e);
            return new DefaultTableModel(data, columnNames);
        } finally {
            disconnect();
        }

        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public boolean addRecord(String category, String code, String description) {
        if (code == null || code.trim().isEmpty()) {
            showWarning("Code cannot be empty.");
            return false;
        }

        String sql = String.format("INSERT INTO %s(%s, %s, %s) VALUES(?, ?, ?)",
                                 TABLE_NAME, COL_CATEGORY, COL_CODE, COL_DESC);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category != null ? category.trim() : null);
            pstmt.setString(2, code.trim());
            pstmt.setString(3, description != null ? description.trim() : null);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            showError("Error adding record.", e);
            return false;
        } finally {
            disconnect();
        }
    }

    public boolean updateRecord(int id, String category, String code, String description) {
        if (code == null || code.trim().isEmpty()) {
            showWarning("Code cannot be empty.");
            return false;
        }
        if (id <= 0) {
            showWarning("Invalid ID for update.");
            return false;
        }

        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
                                 TABLE_NAME, COL_CATEGORY, COL_CODE, COL_DESC, COL_ID);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category != null ? category.trim() : null);
            pstmt.setString(2, code.trim());
            pstmt.setString(3, description != null ? description.trim() : null);
            pstmt.setInt(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            showError("Error updating record.", e);
            return false;
        } finally {
            disconnect();
        }
    }

    public boolean deleteRecord(int id) {
        if (id <= 0) {
            showWarning("Invalid ID for deletion.");
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
            return false;
        }

        String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, COL_ID);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            showError("Error deleting record.", e);
            return false;
        } finally {
            disconnect();
        }
    }

    private void showError(String message, Exception e) {
        System.err.println(message + ": " + e.getMessage());
        JOptionPane.showMessageDialog(
                null,
                message + "\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Input Error",
                JOptionPane.WARNING_MESSAGE
        );
    }
}