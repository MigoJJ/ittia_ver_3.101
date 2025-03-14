package je.panse.doro.support.sqlite3_manager.code;

import java.sql.*;

import je.panse.doro.entry.EntryDir;

public class AddCodeDatabase {

    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";

    public static void addData() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)")) {

            // Add Rheumatism, Gout, Osteoporosis, and Breast Disorder Categories
            addRheumatoidArthritis(pstmt);
            addGoutDisorders(pstmt);
            addOsteoporosisDisorders(pstmt);
            addBreastDisorders(pstmt);

            System.out.println("Data added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding data: " + e.getMessage());
        }
    }

    private static void addRheumatoidArthritis(PreparedStatement pstmt) throws SQLException {
        String category = "Musculoskeletal System Disorders";
        String[][] rheumatoidArthritis = {
            {"M05", "Rheumatoid arthritis with rheumatoid factor"},
            {"M06", "Other rheumatoid arthritis"},
            {"M06.9", "Rheumatoid arthritis, unspecified"},
            {"M15.0", "Primary generalized osteoarthritis"},
            {"M16.9", "Osteoarthritis of the hip, unspecified"},
            {"M19.90", "Unspecified osteoarthritis, unspecified site"}
        };

        for (String[] row : rheumatoidArthritis) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addGoutDisorders(PreparedStatement pstmt) throws SQLException {
        String category = "Musculoskeletal System Disorders";
        String[][] goutDisorders = {
            {"M10.0", "Gout, unspecified site"},
            {"M10.1-M10.9", "Gout, specific sites (e.g., toe, ankle, knee)"},
            {"M1A00X0", "Idiopathic chronic gout, unspecified site, without tophus"},
            {"M1A00X1", "Idiopathic chronic gout, unspecified site, with tophus"}
        };

        for (String[] row : goutDisorders) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addOsteoporosisDisorders(PreparedStatement pstmt) throws SQLException {
        String category = "Musculoskeletal System Disorders";
        String[][] osteoporosisDisorders = {
            {"M80.0", "Age-related osteoporosis with current pathological fracture"},
            {"M80.00", "Age-related osteoporosis with current pathological fracture, unspecified site"},
            {"M80.01", "Age-related osteoporosis with current pathological fracture, shoulder"},
            {"M80.02", "Age-related osteoporosis with current pathological fracture, upper arm"},
            {"M80.05", "Age-related osteoporosis with current pathological fracture, femur"},
            {"M80.08", "Age-related osteoporosis with current pathological fracture, vertebra"},
            {"M80.8", "Other osteoporosis with current pathological fracture (e.g., drug-induced, idiopathic, disuse, postmenopausal, post-oophorectomy, post-traumatic)"},
            {"M81.0", "Age-related osteoporosis without current pathological fracture"},
            {"M81.8", "Other osteoporosis without current pathological fracture (e.g., drug-induced, idiopathic, disuse, postmenopausal, post-oophorectomy, post-traumatic)"},
            {"M82.0", "Osteoporosis in peripheral vascular disease"},
            {"M82.1", "Osteoporosis in peripheral nerve disease"},
            {"M82.8", "Osteoporosis in other diseases classified elsewhere"}
        };

        for (String[] row : osteoporosisDisorders) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addBreastDisorders(PreparedStatement pstmt) throws SQLException {
        String category = "Genitourinary System Disorders"; // Breast disorders are often classified here in ICD-10
        String[][] breastDisorders = {
            {"N60.0", "Solitary cyst of breast"},
            {"N60.01", "Solitary cyst of right breast"},
            {"N60.02", "Solitary cyst of left breast"},
            {"N60.09", "Solitary cyst of unspecified breast"},
            {"N60.1", "Diffuse cystic mastopathy"},
            {"N60.11", "Diffuse cystic mastopathy of right breast"},
            {"N60.12", "Diffuse cystic mastopathy of left breast"},
            {"N60.19", "Diffuse cystic mastopathy of unspecified breast"},
            {"N60.2", "Fibroadenosis"},
            {"N60.21", "Fibroadenosis of right breast"},
            {"N60.22", "Fibroadenosis of left breast"},
            {"N60.29", "Fibroadenosis of unspecified breast"},
            {"N60.3", "Fibrosclerosis"},
            {"N60.31", "Fibrosclerosis of right breast"},
            {"N60.32", "Fibrosclerosis of left breast"},
            {"N60.39", "Fibrosclerosis of unspecified breast"},
            {"N60.4", "Mammary duct ectasia"},
            {"N60.41", "Mammary duct ectasia of right breast"},
            {"N60.42", "Mammary duct ectasia of left breast"},
            {"N60.49", "Mammary duct ectasia of unspecified breast"},
            {"N60.8", "Other specified benign mammary dysplasias"},
            {"N60.81", "Other benign mammary dysplasias of right breast"},
            {"N60.82", "Other benign mammary dysplasias of left breast"},
            {"N60.89", "Other benign mammary dysplasias of unspecified breast"},
            {"N60.9", "Benign mammary dysplasia, unspecified"},
            {"N60.91", "Unspecified benign mammary dysplasia of right breast"},
            {"N60.92", "Unspecified benign mammary dysplasia of left breast"},
            {"N60.99", "Unspecified benign mammary dysplasia of unspecified breast"},
            {"N61", "Inflammatory disorders of breast"},
            {"N62", "Hypertrophy of breast"},
            {"N63", "Unspecified lump in breast"},
            {"N64.0", "Fissure and fistula of nipple"},
            {"N64.1", "Fat necrosis of breast"},
            {"N64.2", "Atrophy of breast"},
            {"N64.4", "Mastodynia"},
            {"N64.5", "Other signs and symptoms in breast"},
            {"N64.51", "Induration of breast"},
            {"N64.52", "Nipple discharge"},
            {"N64.53", "Retraction of nipple"},
            {"N64.59", "Other signs and symptoms in breast"},
            {"N64.8", "Other specified disorders of breast"},
            {"N64.81", "Ptosis of breast"},
            {"N64.82", "Hypoplasia of breast"},
            {"N64.89", "Other specified disorders of breast"},
            {"N64.9", "Disorder of breast, unspecified"},
            {"N65.0", "Deformity of reconstructed breast"},
            {"N65.1", "Disproportion of reconstructed breast"}
        };

        for (String[] row : breastDisorders) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addRow(PreparedStatement pstmt, String category, String bCode, String name, String reference) throws SQLException {
        pstmt.setString(1, category);
        pstmt.setString(2, bCode);
        pstmt.setString(3, name);
        pstmt.setString(4, reference);
        pstmt.executeUpdate();
    }

    public static void removeDuplicates() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create a temporary table with unique records
            stmt.execute("CREATE TABLE temp_codedis (\n" +
                    "    Category TEXT,\n" +
                    "    B_code TEXT,\n" +
                    "    name TEXT,\n" +
                    "    reference TEXT\n" +
                    ");");

            // Insert unique records into the temporary table
            stmt.execute("INSERT INTO temp_codedis (Category, B_code, name, reference)\n" +
                    "SELECT Category, B_code, name, reference\n" +
                    "FROM codedis\n" +
                    "WHERE rowid IN (\n" +
                    "    SELECT MIN(rowid)\n" +
                    "    FROM codedis\n" +
                    "    GROUP BY B_code\n" +
                    ");");

            // Drop the original table
            stmt.execute("DROP TABLE IF EXISTS codedis;");

            // Rename the temporary table to replace the original table
            stmt.execute("ALTER TABLE temp_codedis RENAME TO codedis;");

            System.out.println("Duplicates removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error removing duplicates: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        addData();
        removeDuplicates();
    }
}