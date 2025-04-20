package je.panse.doro.support.sqlite3_manager.icd_10;

import java.sql.*;	
import javax.swing.table.DefaultTableModel;

public class ICD10DatabaseModel {
    private String dbPath;

    public ICD10DatabaseModel(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    public ResultSet getAllRecords() {
        try {
            Connection conn = connect();
            String sql = "SELECT * FROM icd10 ORDER BY Code ASC"; // Changed table name to 'icd10' and order by 'Code'
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching all records: " + e.getMessage());
            return null;
        }
    }

    public void searchRecords(String searchText, DefaultTableModel tableModel) {
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM icd10 WHERE Code LIKE ? OR Title LIKE ? ORDER BY Code ASC"; // Changed table name to 'icd10' and search fields
            PreparedStatement stmt = conn.prepareStatement(sql);
            String pattern = "%" + searchText + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            ResultSet rs = stmt.executeQuery();

            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("Category"),
                        rs.getString("Code"),
                        rs.getString("Title"),
                        rs.getString("Comments")
                });
            }
        } catch (SQLException e) {
            System.err.println("Search error: " + e.getMessage());
        }
    }

    public void insertRecord(String category, String bcode, String diagnosis, String reference) {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO icd10 (Category, Code, Title, Comments) VALUES (?, ?, ?, ?)"; // Changed table name to 'icd10' and column names
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            stmt.setString(2, bcode); // Assuming 'bcode' maps to 'Code'
            stmt.setString(3, diagnosis); // Assuming 'diagnosis' maps to 'Title'
            stmt.setString(4, reference); // Assuming 'reference' maps to 'Comments'
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Insert error: " + e.getMessage());
        }
    }

    public void deleteRecord(String bcode) {
        try (Connection conn = connect()) {
            String sql = "DELETE FROM icd10 WHERE Code = ?"; // Changed table name to 'icd10' and using 'Code' for deletion
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, bcode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Delete error: " + e.getMessage());
        }
    }

    public void updateRecord(String originalBCode, String newCategory, String newBCode, String newDiagnosis, String newReference) {
        try (Connection conn = connect()) {
            String sql = "UPDATE icd10 SET Category = ?, Code = ?, Title = ?, Comments = ? WHERE Code = ?"; // Changed table name to 'icd10' and column names
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newCategory);
            stmt.setString(2, newBCode);
            stmt.setString(3, newDiagnosis);
            stmt.setString(4, newReference);
            stmt.setString(5, originalBCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update error: " + e.getMessage());
        }
    }
}