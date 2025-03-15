package je.panse.doro.support.sqlite3_manager.code;

import java.sql.*;
import je.panse.doro.entry.EntryDir;

/**
 * A class to append disease code data into a SQLite database and remove duplicates.
 */
public class AddCodeDatabase {

    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";

    /**
     * Appends disease code data to the 'codedis' table.
     */
    public static void addData() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)")) {

            // Existing categories
            addRheumatoidArthritis(pstmt);
            addGoutDisorders(pstmt);
            addOsteoporosisDisorders(pstmt);
            addBreastDisorders(pstmt);

            // New neoplasm categories
            addMalignantNeoplasms(pstmt);
            addInSituNeoplasms(pstmt);
            addBenignNeoplasms(pstmt);
            addNeoplasmsOfUncertainBehavior(pstmt);
            addNeoplasmsOfUnspecifiedBehavior(pstmt);

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
        String category = "Genitourinary System Disorders";
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

    private static void addMalignantNeoplasms(PreparedStatement pstmt) throws SQLException {
        String category = "Neoplasms";
        String[][] malignantNeoplasms = {
            {"C00-C14", "Malignant neoplasms of lip, oral cavity, and pharynx"},
            {"C00", "Malignant neoplasm of lip"},
            {"C01", "Malignant neoplasm of base of tongue"},
            {"C02", "Malignant neoplasm of other and unspecified parts of tongue"},
            {"C03", "Malignant neoplasm of gum"},
            {"C04", "Malignant neoplasm of floor of mouth"},
            {"C05", "Malignant neoplasm of palate"},
            {"C06", "Malignant neoplasm of other and unspecified parts of mouth"},
            {"C07", "Malignant neoplasm of parotid gland"},
            {"C08", "Malignant neoplasm of other and unspecified major salivary glands"},
            {"C09", "Malignant neoplasm of tonsil"},
            {"C10", "Malignant neoplasm of oropharynx"},
            {"C11", "Malignant neoplasm of nasopharynx"},
            {"C12", "Malignant neoplasm of pyriform sinus"},
            {"C13", "Malignant neoplasm of hypopharynx"},
            {"C14", "Malignant neoplasm of other and ill-defined sites in the lip, oral cavity and pharynx"},
            {"C15-C26", "Malignant neoplasms of digestive organs"},
            {"C15", "Malignant neoplasm of esophagus"},
            {"C16", "Malignant neoplasm of stomach"},
            {"C17", "Malignant neoplasm of small intestine"},
            {"C18", "Malignant neoplasm of colon"},
            {"C19", "Malignant neoplasm of rectosigmoid junction"},
            {"C20", "Malignant neoplasm of rectum"},
            {"C21", "Malignant neoplasm of anus and anal canal"},
            {"C22", "Malignant neoplasm of liver and intrahepatic bile duct"},
            {"C23", "Malignant neoplasm of gallbladder"},
            {"C24", "Malignant neoplasm of other and unspecified parts of biliary tract"},
            {"C25", "Malignant neoplasm of pancreas"},
            {"C26", "Malignant neoplasm of other and ill-defined sites in the digestive system"},
            {"C30-C39", "Malignant neoplasms of respiratory and intrathoracic organs"},
            {"C30", "Malignant neoplasm of nasal cavity and middle ear"},
            {"C31", "Malignant neoplasm of accessory sinuses"},
            {"C32", "Malignant neoplasm of larynx"},
            {"C33", "Malignant neoplasm of trachea"},
            {"C34", "Malignant neoplasm of bronchus and lung"},
            {"C37", "Malignant neoplasm of thymus"},
            {"C38", "Malignant neoplasm of heart, mediastinum and pleura"},
            {"C39", "Malignant neoplasm of other and ill-defined sites in the respiratory system and intrathoracic organs"},
            {"C40-C41", "Malignant neoplasms of bone and articular cartilage"},
            {"C40", "Malignant neoplasm of bone and articular cartilage of limbs"},
            {"C41", "Malignant neoplasm of bone and articular cartilage of other and unspecified sites"},
            {"C43-C44", "Malignant neoplasms of skin"},
            {"C43", "Malignant melanoma of skin"},
            {"C44", "Other malignant neoplasms of skin"},
            {"C45-C49", "Malignant neoplasms of mesothelial and soft tissue"},
            {"C45", "Mesothelioma"},
            {"C46", "Kaposi’s sarcoma"},
            {"C47", "Malignant neoplasm of peripheral nerves and autonomic nervous system"},
            {"C48", "Malignant neoplasm of retroperitoneum and peritoneum"},
            {"C49", "Malignant neoplasm of other connective and soft tissue"},
            {"C50-C58", "Malignant neoplasms of breast and female genital organs"},
            {"C50", "Malignant neoplasm of breast"},
            {"C51", "Malignant neoplasm of vulva"},
            {"C52", "Malignant neoplasm of vagina"},
            {"C53", "Malignant neoplasm of cervix uteri"},
            {"C54", "Malignant neoplasm of corpus uteri"},
            {"C55", "Malignant neoplasm of uterus, part unspecified"},
            {"C56", "Malignant neoplasm of ovary"},
            {"C57", "Malignant neoplasm of other and unspecified female genital organs"},
            {"C58", "Malignant neoplasm of placenta"},
            {"C60-C63", "Malignant neoplasms of male genital organs"},
            {"C60", "Malignant neoplasm of penis"},
            {"C61", "Malignant neoplasm of prostate"},
            {"C62", "Malignant neoplasm of testis"},
            {"C63", "Malignant neoplasm of other and unspecified male genital organs"},
            {"C64-C68", "Malignant neoplasms of urinary organs"},
            {"C64", "Malignant neoplasm of kidney, except renal pelvis"},
            {"C65", "Malignant neoplasm of renal pelvis"},
            {"C66", "Malignant neoplasm of ureter"},
            {"C67", "Malignant neoplasm of bladder"},
            {"C68", "Malignant neoplasm of other and unspecified urinary organs"},
            {"C69-C72", "Malignant neoplasms of eye, brain, and central nervous system"},
            {"C69", "Malignant neoplasm of eye and adnexa"},
            {"C70", "Malignant neoplasm of meninges"},
            {"C71", "Malignant neoplasm of brain"},
            {"C72", "Malignant neoplasm of spinal cord, cranial nerves and other parts of central nervous system"},
            {"C73-C75", "Malignant neoplasms of thyroid and other endocrine glands"},
            {"C73", "Malignant neoplasm of thyroid gland"},
            {"C74", "Malignant neoplasm of adrenal gland"},
            {"C75", "Malignant neoplasm of other and unspecified endocrine glands"},
            {"C76-C80", "Malignant neoplasms of ill-defined, other secondary, and unspecified sites"},
            {"C76", "Malignant neoplasm of other and ill-defined sites"},
            {"C77", "Secondary malignant neoplasm of lymph nodes"},
            {"C78", "Secondary malignant neoplasm of respiratory and digestive organs"},
            {"C79", "Secondary malignant neoplasm of other sites"},
            {"C80", "Malignant neoplasm without specification of site"},
            {"C81-C96", "Malignant neoplasms of lymphoid, hematopoietic and related tissue"},
            {"C81", "Hodgkin’s lymphoma"},
            {"C82", "Follicular non-Hodgkin’s lymphoma"},
            {"C83", "Other non-Hodgkin’s lymphoma"},
            {"C84", "Peripheral T-cell lymphoma"},
            {"C85", "Other and unspecified types of non-Hodgkin’s lymphoma"},
            {"C86", "Other specified types of T/NK-cell lymphoma"},
            {"C88", "Malignant immunoproliferative diseases"},
            {"C90", "Multiple myeloma and malignant plasma cell neoplasms"},
            {"C91", "Lymphoid leukemia"},
            {"C92", "Myeloid leukemia"},
            {"C93", "Monocytic leukemia"},
            {"C94", "Other leukemia"},
            {"C95", "Leukemia of unspecified cell type"},
            {"C96", "Other and unspecified malignant neoplasms of lymphoid, hematopoietic and related tissue"}
        };

        for (String[] row : malignantNeoplasms) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addInSituNeoplasms(PreparedStatement pstmt) throws SQLException {
        String category = "Neoplasms";
        String[][] inSituNeoplasms = {
            {"D00", "Carcinoma in situ of oral cavity, pharynx and larynx"},
            {"D01", "Carcinoma in situ of digestive organs"},
            {"D02", "Carcinoma in situ of respiratory and intrathoracic organs"},
            {"D03", "Melanoma in situ"},
            {"D04", "Carcinoma in situ of skin"},
            {"D05", "Carcinoma in situ of breast"},
            {"D06", "Carcinoma in situ of cervix uteri"},
            {"D07", "Carcinoma in situ of other and unspecified genital organs"},
            {"D09", "Carcinoma in situ of other and unspecified sites"}
        };

        for (String[] row : inSituNeoplasms) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addBenignNeoplasms(PreparedStatement pstmt) throws SQLException {
        String category = "Neoplasms";
        String[][] benignNeoplasms = {
            {"D10", "Benign neoplasm of mouth and pharynx"},
            {"D11", "Benign neoplasm of major salivary glands"},
            {"D12", "Benign neoplasm of colon, rectum, anus and anal canal"},
            {"D13", "Benign neoplasm of other and ill-defined parts of digestive system"},
            {"D14", "Benign neoplasm of middle ear and respiratory system"},
            {"D15", "Benign neoplasm of other and unspecified intrathoracic organs"},
            {"D16", "Benign neoplasm of bone and articular cartilage"},
            {"D17", "Benign lipomatous neoplasm"},
            {"D18", "Haemangioma and lymphangioma (any site)"},
            {"D19", "Benign neoplasm of other and unspecified sites"}
        };

        for (String[] row : benignNeoplasms) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addNeoplasmsOfUncertainBehavior(PreparedStatement pstmt) throws SQLException {
        String category = "Neoplasms";
        String[][] neoplasmsUncertain = {
            {"D37", "Neoplasm of uncertain behavior of oral cavity and pharynx"},
            {"D38", "Neoplasm of uncertain behavior of middle ear and respiratory system"},
            {"D39", "Neoplasm of uncertain behavior of female genital organs"},
            {"D40", "Neoplasm of uncertain behavior of male genital organs"},
            {"D41", "Neoplasm of uncertain behavior of urinary organs"},
            {"D42", "Neoplasm of uncertain behavior of nervous system"},
            {"D43", "Neoplasm of uncertain behavior of endocrine glands"},
            {"D44", "Neoplasm of uncertain behavior of other and unspecified sites"},
            {"D45", "Polycythemia vera"},
            {"D46", "Myelodysplastic syndromes"},
            {"D47", "Other myeloproliferative neoplasms"},
            {"D48", "Neoplasm of uncertain behavior of other and unspecified sites"}
        };

        for (String[] row : neoplasmsUncertain) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addNeoplasmsOfUnspecifiedBehavior(PreparedStatement pstmt) throws SQLException {
        String category = "Neoplasms";
        String[][] neoplasmsUnspecified = {
            {"D49", "Neoplasm of unspecified behavior"}
        };

        for (String[] row : neoplasmsUnspecified) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    /**
     * Inserts a single row into the 'codedis' table.
     *
     * @param pstmt    The PreparedStatement to execute the insertion.
     * @param category The category of the disease (e.g., "Neoplasms").
     * @param bCode    The ICD-10 code (e.g., "C00").
     * @param name     The name or description of the disease (e.g., "Malignant neoplasm of lip").
     * @param reference The reference standard (e.g., "ICD-10").
     * @throws SQLException If an error occurs during insertion.
     */
    private static void addRow(PreparedStatement pstmt, String category, String bCode, String name, String reference) throws SQLException {
        pstmt.setString(1, category);
        pstmt.setString(2, bCode);
        pstmt.setString(3, name);
        pstmt.setString(4, reference);
        pstmt.executeUpdate();
    }

    /**
     * Removes duplicate entries from the 'codedis' table based on the 'B_code' column.
     * Keeps the first occurrence of each unique 'B_code' value.
     */
    public static void removeDuplicates() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create a temporary table with unique records
            stmt.execute("CREATE TABLE temp_codedis (" +
                    "Category TEXT, " +
                    "B_code TEXT, " +
                    "name TEXT, " +
                    "reference TEXT" +
                    ");");

            // Insert unique records into the temporary table
            stmt.execute("INSERT INTO temp_codedis (Category, B_code, name, reference) " +
                    "SELECT Category, B_code, name, reference " +
                    "FROM codedis " +
                    "WHERE rowid IN (" +
                    "    SELECT MIN(rowid) " +
                    "    FROM codedis " +
                    "    GROUP BY B_code" +
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

    /**
     * Main method to append data and remove duplicates.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        addData();
        removeDuplicates();
    }
}