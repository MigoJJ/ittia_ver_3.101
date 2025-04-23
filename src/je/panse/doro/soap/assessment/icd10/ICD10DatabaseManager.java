package je.panse.doro.soap.assessment.icd10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ICD10DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:/home/dce040b/git/ittia_ver_3.076/src/je/panse/doro/soap/assessment/icd10/ICD10Sqlite.db";

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS icd10_codes (" +
                "disease_code TEXT NOT NULL, " +
                "korean_name TEXT, " +
                "english_name TEXT, " +
                "complete_code_division TEXT, " +
                "PRIMARY KEY (disease_code, korean_name, english_name)" +
                ")";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public boolean addRecord(ICD10DataModel.Record record) {
        String insertSQL = "INSERT INTO icd10_codes (disease_code, korean_name, english_name, complete_code_division) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, record.diseaseCode());
            pstmt.setString(2, record.koreanName());
            pstmt.setString(3, record.englishName());
            pstmt.setString(4, record.completeCodeDivision());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding record: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRecord(ICD10DataModel.Record oldRecord, ICD10DataModel.Record newRecord) {
        String updateSQL = "UPDATE icd10_codes SET disease_code = ?, korean_name = ?, english_name = ?, complete_code_division = ? " +
                "WHERE disease_code = ? AND korean_name = ? AND english_name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newRecord.diseaseCode());
            pstmt.setString(2, newRecord.koreanName());
            pstmt.setString(3, newRecord.englishName());
            pstmt.setString(4, newRecord.completeCodeDivision());
            pstmt.setString(5, oldRecord.diseaseCode());
            pstmt.setString(6, oldRecord.koreanName() == null ? "" : oldRecord.koreanName());
            pstmt.setString(7, oldRecord.englishName() == null ? "" : oldRecord.englishName());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating record: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRecord(ICD10DataModel.Record record) {
        String deleteSQL = "DELETE FROM icd10_codes WHERE disease_code = ? AND korean_name = ? AND english_name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, record.diseaseCode());
            pstmt.setString(2, record.koreanName() == null ? "" : record.koreanName());
            pstmt.setString(3, record.englishName() == null ? "" : record.englishName());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting record: " + e.getMessage());
            return false;
        }
    }

    public List<ICD10DataModel.Record> getAllRecords() {
        List<ICD10DataModel.Record> records = new ArrayList<>();
        String selectSQL = "SELECT * FROM icd10_codes";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                records.add(new ICD10DataModel.Record(
                        rs.getString("disease_code"),
                        rs.getString("korean_name"),
                        rs.getString("english_name"),
                        rs.getString("complete_code_division")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
        return records;
    }

    public List<ICD10DataModel.Record> searchRecords(String searchTerm) {
        List<ICD10DataModel.Record> records = new ArrayList<>();
        String searchSQL = "SELECT * FROM icd10_codes WHERE disease_code LIKE ? OR korean_name LIKE ? OR english_name LIKE ? OR complete_code_division LIKE ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(searchSQL)) {
            String likeTerm = "%" + searchTerm + "%";
            pstmt.setString(1, likeTerm);
            pstmt.setString(2, likeTerm);
            pstmt.setString(3, likeTerm);
            pstmt.setString(4, likeTerm);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                records.add(new ICD10DataModel.Record(
                        rs.getString("disease_code"),
                        rs.getString("korean_name"),
                        rs.getString("english_name"),
                        rs.getString("complete_code_division")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error searching data: " + e.getMessage());
        }
        return records;
    }
}