package je.panse.doro.support.sqlite3_manager.labcode;

import java.sql.*;
import je.panse.doro.entry.EntryDir;

/**
 * A class to append simplified laboratory code data into an existing SQLite database
 * and manage duplicates.
 */
public class AddLabCodeDatabase {

    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/labcode/LabCodeFullDis.db";

    /**
     * Appends simplified laboratory data to the 'labcodes' table and removes duplicates.
     */
    public static void appendData() {
        String insertSQL = "INSERT INTO labcodes (Category, B_code, Items, unit, comment) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            addLabData(pstmt);
            System.out.println("Data appended successfully.");
        } catch (SQLException e) {
            System.err.println("Error appending data: " + e.getMessage());
        }
        removeDuplicates(); // Remove duplicates after appending data
    }

    /**
     * Adds the simplified laboratory dataset to the database.
     *
     * @param pstmt The PreparedStatement to execute the insertions.
     * @throws SQLException If an error occurs during data insertion.
     */
    private static void addLabData(PreparedStatement pstmt) throws SQLException {
        String[][] labData = {
            {"Chemistry", "D2280004Z", "Creatinine(Urine)", "mg/dL", "."},
            {"Chemistry", "D3002004Z", "Microalbumin", "μg/mL", "."},
            {"Chemistry", "D3002004Z,D2280004Z", "A/C ratio", "mg/g crea.", "."},
            {"Chemistry", "D1840004Z", "T.Protein", "g/dL", "."},
            {"Chemistry", "D1880004Z", "Albumin", "g/dL", "."},
            {"Chemistry", "D1830004Z", "T.Bilirubin", "mg/dL", "."},
            {"Chemistry", "D2300004Z", "BUN", "mg/dL", "."},
            {"Chemistry", "D2280004Z", "Creatinine", "mg/dL", "."},
            {"Chemistry", "", "GFR", "", "."},
            {"Chemistry", "D2310004Z", "Uric acid", "mg/dL", "."},
            {"Chemistry", "D1860004Z", "AST(sGOT)", "IU/L", "."},
            {"Chemistry", "D1850004Z", "ALT(sGPT)", "IU/L", "."},
            {"Chemistry", "D1870004Z", "ALP", "U/L", "."},
            {"Chemistry", "D1890004Z", "GGT(γ-GTP)", "IU/L", "."},
            {"Chemistry", "D2510054Z", "LDH", "U/L", "."},
            {"Chemistry", "D2510014Z", "CK,total(CPK)", "U/L", "."},
            {"Chemistry", "D3800024Z", "Amylase", "U/L", "."},
            {"Lipid Panel", "D2611004Z", "Cholesterol, total", "mg/dL", "."},
            {"Lipid Panel", "D2263004Z", "Triglyceride", "mg/dL", "."},
            {"Lipid Panel", "D2613004Z", "HDL-cholesterol", "mg/dL", "."},
            {"Lipid Panel", "D2614004Z", "LDL-cholesterol", "mg/dL", "."},
            {"Electrolytes", "D2800054Z", "Calcium (Ca)", "mg/dL", "."},
            {"Electrolytes", "D2800044Z", "Phosphorus (P)", "mg/dL", "."},
            {"Electrolytes", "D2800024Z", "Sodium (Na)", "mmol/L", "."},
            {"Electrolytes", "D2800034Z", "Chloride (Cl)", "mmol/L", "."},
            {"Electrolytes", "D2800064Z", "Potassium (K)", "mmol/L", "."},
            {"Iron Studies", "D0521034Z", "Iron (Fe)", "μg/dL", "."},
            {"Iron Studies", "D0521044Z", "TIBC", "μg/dL", "."},
            {"Glucose", "D3022004Z", "Glucose", "mg/dL", "."},
            {"Glucose", "D3063004Z", "HbA1C", "%", "."},
            {"Blood Type", "D1502004Z", "ABO혈액형(자동화)", "", "."},
            {"Blood Type", "D1512004Z", "Rh type(자동화)", "", "."},
            {"CBC", "D0002014Z", "WBC count", "천/㎕", "."},
            {"CBC", "D0002034Z", "RBC count", "백만/㎕", "."},
            {"CBC", "D0002054Z", "Hemoglobin", "g/㎗", "."},
            {"CBC", "D0002044Z", "Hematocrit", "%", "."},
            {"CBC", "", "MCV", "fL", "."},
            {"CBC", "", "MCH", "pg", "."},
            {"CBC", "", "MCHC", "g/㎗", "."},
            {"CBC", "D0002024Z", "RDW", "%", "."},
            {"CBC", "D0002074Z", "Platelet", "천/㎕", "."},
            {"CBC", "", "MPV", "fL", "."},
            {"CBC", "", "PCT", "%", "."},
            {"CBC", "D0002064Z", "PDW", "fL", "."},
            {"CBC", "D0013004Z", "Differential count (Lymphocyte)", "%", "."},
            {"CBC", "D0013004Z", "Differential count (Monocyte)", "%", "."},
            {"CBC", "D0013004Z", "Differential count (Neutrophil seg.)", "%", "."},
            {"CBC", "D0013004Z", "Differential count (Eosinophil)", "%", "."},
            {"CBC", "D0013004Z", "Differential count (Basophil)", "%", "."},
            {"CBC", "D0013004Z", "Differential count (Other)", "%", "."},
            {"CBC", "D0100014Z", "ESR", "mm/hrs", "."},
            {"Vitamins", "D4902024Z", "25-OH-Vit.D", "ng/mL", "."},
            {"Thyroid", "D3230044Z", "T4", "μg/dL", "."},
            {"Thyroid", "D3250014Z", "TSH", "mIU/L", "."},
            {"Tumor Markers", "D2420024Z", "AFP", "ng/mL", "."},
            {"Tumor Markers", "D4290004Z", "CEA", "ng/mL", "."},
            {"Tumor Markers", "D4350004Z", "CA19-9", "U/mL", "."},
            {"Tumor Markers", "D4300034Z", "PSA (total PSA)", "ng/mL", "."},
            {"Tumor Markers", "D4300014Z", "PSA, free", "ng/mL", "."},
            {"Tumor Markers", "", "Ratio (%)", "%", "."},
            {"Rheumatology", "D7811004Z", "RF 정성", "", "."},
            {"Rheumatology", "D0113004Z", "CRP 정량", "mg/dL", "."},
            {"Infectious Diseases", "D6913004Z", "RPR정밀", "R.U", "."},
            {"Infectious Diseases", "D7212004Z", "HIV Ag/Ab(Combo)", "COI", "."},
            {"Infectious Diseases", "D7011014Z", "HAV-Ab Total(IgG)", "mIU/mL", "."},
            {"Infectious Diseases", "D7015004Z", "HBsAg", "Index", "."},
            {"Infectious Diseases", "D7018004Z", "HBsAb", "mIU/mL", "."},
            {"Infectious Diseases", "D7005004Z", "HCV Ab(일반)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (Specific gravity)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (PH)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (Leukocytes)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (Nitrite)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (Proteins)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (Glucose)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (Ketones)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (Urobilinogen)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (Bilirubin)", "", "."},
            {"Urinalysis", "D2253004Z", "Urine 10종 (Blood)", "", "."},
            {"Urinalysis", "D2201024Z", "요침사검사 (WBC)", "/HPF", "."},
            {"Urinalysis", "D2201024Z", "요침사검사 (RBC)", "/HPF", "."},
            {"Urinalysis", "D2201024Z", "요침사검사 (Epithelial cells)", "/HPF", "."},
            {"Urinalysis", "D2201024Z", "요침사검사 (Bacteria)", "", "."},
            {"Urinalysis", "D2201024Z", "요침사검사 (Crystals)", "", "."},
            {"Urinalysis", "D2201024Z", "요침사검사 (Casts)", "", "."},
            {"Urinalysis", "D2201024Z", "요침사검사 (Others)", "", "."}
        };

        for (String[] row : labData) {
            addRow(pstmt, row[0], row[1], row[2], row[3], row[4]);
        }
    }

    /**
     * Inserts a single row into the 'labcodes' table.
     *
     * @param pstmt    The PreparedStatement to execute the insertion.
     * @param category The category of the lab test (e.g., "Chemistry").
     * @param bCode    The insurance code (e.g., "D2280004Z").
     * @param items    The specific test item (e.g., "Creatinine(Urine)").
     * @param unit     The unit of measurement (e.g., "mg/dL").
     * @param comment  A placeholder comment (e.g., ".").
     * @throws SQLException If an error occurs during insertion.
     */
    private static void addRow(PreparedStatement pstmt, String category, String bCode, String items, String unit, String comment) throws SQLException {
        pstmt.setString(1, category);
        pstmt.setString(2, bCode);
        pstmt.setString(3, items);
        pstmt.setString(4, unit);
        pstmt.setString(5, comment);
        pstmt.executeUpdate();
    }

    /**
     * Removes duplicate entries from the 'labcodes' table based on the 'Items' column.
     * Keeps the first occurrence of each unique 'Items' value.
     */
    public static void removeDuplicates() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Create a temporary table for unique records
            stmt.execute("CREATE TABLE temp_labcodes (" +
                    "Category TEXT, " +
                    "B_code TEXT, " +
                    "Items TEXT, " +
                    "unit TEXT, " +
                    "comment TEXT" +
                    ");");

            // Insert unique records into the temporary table
            stmt.execute("INSERT INTO temp_labcodes (Category, B_code, Items, unit, comment) " +
                    "SELECT Category, B_code, Items, unit, comment " +
                    "FROM labcodes " +
                    "WHERE rowid IN (" +
                    "    SELECT MIN(rowid) " +
                    "    FROM labcodes " +
                    "    GROUP BY Items" +
                    ");");

            // Drop the original table and rename the temporary table
            stmt.execute("DROP TABLE IF EXISTS labcodes;");
            stmt.execute("ALTER TABLE temp_labcodes RENAME TO labcodes;");

            System.out.println("Duplicates removed successfully.");
        } catch (SQLException e) {
            System.err.println("Error removing duplicates: " + e.getMessage());
        }
    }

    /**
     * Main method to append the simplified laboratory data to the database and remove duplicates.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        appendData();
    }
}