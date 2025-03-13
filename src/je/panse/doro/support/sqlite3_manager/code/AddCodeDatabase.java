package je.panse.doro.support.sqlite3_manager.code;

import java.sql.*;

import je.panse.doro.entry.EntryDir;

public class AddCodeDatabase {

    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";

    public static void addData() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)")) {

            // Add Disease Categories
            addThyroidDisorders(pstmt);
            addSkinDisorders(pstmt);
            addMusculoskeletalDisorders(pstmt);

            System.out.println("Data added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding data: " + e.getMessage());
        }
    }

    private static void addThyroidDisorders(PreparedStatement pstmt) throws SQLException {
        String category = "Digestive System Disorders";
        String[][] digestiveDisorders = {
            {"K00", "Developmental disorders of teeth"},
            {"K01", "Embedded and impacted teeth"},
            {"K02", "Dental caries"},
            {"K03", "Other diseases of hard tissues of teeth"},
            {"K04", "Diseases of pulp and periapical tissues"},
            {"K05", "Gingivitis and periodontal diseases"},
            {"K06", "Other disorders of gingiva and edentulous alveolar ridge"},
            {"K07", "Dentofacial anomalies (malocclusion)"},
            {"K08", "Other disorders of teeth and supporting structures"},
            {"K09", "Cysts of jaw"},
            {"K10", "Inflammatory conditions of jaws"},
            {"K11", "Diseases of salivary glands"},
            {"K12", "Stomatitis and related lesions"},
            {"K13", "Other diseases of the oral mucosa"},
            {"K14", "Diseases of the tongue"},
            {"K20", "Esophagitis"},
            {"K21", "Gastroesophageal reflux disease (GERD)"},
            {"K22", "Other diseases of the esophagus"},
            {"K25", "Gastric ulcer"},
            {"K26", "Duodenal ulcer"},
            {"K27", "Peptic ulcer, unspecified"},
            {"K29", "Gastritis and duodenitis"},
            {"K30", "Functional dyspepsia"},
            {"K35", "Acute appendicitis"},
            {"K50", "Crohn’s disease (regional enteritis)"},
            {"K51", "Ulcerative colitis"},
            {"K56", "Intestinal obstruction"},
            {"K57", "Diverticular disease of intestine"},
            {"K58", "Irritable bowel syndrome (IBS)"},
            {"K64", "Hemorrhoids"},
            {"K70", "Alcoholic liver disease"},
            {"K72", "Hepatic failure"},
            {"K74", "Fibrosis and cirrhosis of liver"},
            {"K80", "Cholelithiasis (gallstones)"},
            {"K81", "Cholecystitis"},
            {"K85", "Acute pancreatitis"},
            {"K90", "Malabsorption syndromes"},
            {"K92", "Other diseases of the digestive system"}
        };

        for (String[] row : digestiveDisorders) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addSkinDisorders(PreparedStatement pstmt) throws SQLException {
        String category = "Skin and Subcutaneous Tissue Disorders";
        String[][] skinDisorders = {
            {"L00", "Staphylococcal scalded skin syndrome"},
            {"L01", "Impetigo"},
            {"L02", "Cutaneous abscess, furuncle, and carbuncle"},
            {"L08", "Other localized infections of the skin and subcutaneous tissue"},
            {"L10", "Pemphigus"},
            {"L20", "Atopic dermatitis"},
            {"L21", "Seborrheic dermatitis"},
            {"L30", "Other types of dermatitis and eczema"},
            {"L40", "Psoriasis"},
            {"L50", "Urticaria (hives)"},
            {"L55", "Sunburn"},
            {"L60", "Nail disorders"},
            {"L63", "Alopecia areata"},
            {"L64", "Androgenic alopecia"},
            {"L80", "Vitiligo"},
            {"L81", "Pigmentation disorders"},
            {"L90", "Other skin disorders"}
        };

        for (String[] row : skinDisorders) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addMusculoskeletalDisorders(PreparedStatement pstmt) throws SQLException {
        String category = "Musculoskeletal System Disorders";
        String[][] musculoskeletalDisorders = {
            {"M00", "Infectious arthritis"},
            {"M05", "Rheumatoid arthritis"},
            {"M15", "Osteoarthritis"},
            {"M20", "Other joint disorders"},
            {"M30", "Vasculitis and lupus"},
            {"M40", "Spinal curvatures (e.g., scoliosis)"},
            {"M45", "Spondylopathies"},
            {"M50", "Back pain disorders"},
            {"M60", "Myopathies"},
            {"M65", "Fibromyalgia and other soft tissue disorders"}
        };

        for (String[] row : musculoskeletalDisorders) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addGenitourinaryDisorders(PreparedStatement pstmt) throws SQLException {
        String category = "Genitourinary System Disorders";
        String[][] genitourinaryDisorders = {
            {"N00", "Acute nephritic syndrome"},
            {"N10", "Acute pyelonephritis"},
            {"N18", "Chronic kidney disease (CKD)"},
            {"N20", "Kidney and ureteral stones"},
            {"N30", "Cystitis"},
            {"N39", "Urinary tract infections (UTIs)"},
            {"N40", "Benign prostatic hyperplasia"},
            {"N50", "Other male genital disorders"},
            {"N60", "Benign mammary dysplasia"},
            {"N70", "Salpingitis and oophoritis"},
            {"N80", "Endometriosis"},
            {"N95", "Menopausal and other perimenopausal disorders"}
        };

        for (String[] row : genitourinaryDisorders) {
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
