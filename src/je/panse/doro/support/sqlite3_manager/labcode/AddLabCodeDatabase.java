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
            {"Urinalysis", "D2201024Z", "요침사검사 (Others)", "", "."},
            {"Chemistry", "D1880000Z", "A/G ratio", "N/A", "."},
            {"Chemistry", "D1880000Z", "Albumin", "g/dL", "."},
            {"Chemistry", "D1880000Z", "Albumin (fluid)", "g/dL", "."},
            {"Chemistry", "D1920000Z", "Ammonia(NH3)", "μmol/L", "."},
            {"Chemistry", "D2300000Z,D2280000Z", "B/C ratio", "N/A", "."},
            {"Chemistry", "D1820000Z", "D.Bilirubin", "mg/dL", "."},
            {"Chemistry", "D1820000Z,D1830000Z", "I.Bilirubin", "mg/dL", "."},
            {"Chemistry", "D1830000Z", "T.Bilirubin", "mg/dL", "."},
            {"Chemistry", "D2300000Z", "BUN", "mg/dL", "."},
            {"Chemistry", "D2300000Z", "Urine Urea Nitrogen", "mg/24hr", "."},
            {"Chemistry", "D2280000Z", "Creatinine", "mg/dL", "."},
            {"Chemistry", "D2280000Z", "Creatinine(Urine)", "mg/24hr", "."},
            {"Chemistry", "D2321000Z", "Creatinine clearance(CCr)", "mL/min", "."},
            {"Chemistry", "D1880000Z,D1840000Z", "Globulin", "g/dL", "."},
            {"Chemistry", "D1840000Z", "T.Protein", "g/dL", "."},
            
            {"Chemistry", "D1840000Z", "Protein, total(Urine)", "mg/24hr", "."},
            {"Chemistry", "D2310000Z", "Uric acid", "mg/dL", "."},
            {"Chemistry", "D2310000Z", "Uric acid (Urine)", "mg/24hr", "."},
            {"Chemistry", "D2310000Z", "Uric acid (fluid)", "mg/dL", "."},
            {"Chemistry", "D2290000Z", "ACP (Acid phosphatase)", "U/L", "."},
            {"Chemistry", "D2530000Z", "ADA(Adenosine deaminase)", "U/L", "."},
            {"Chemistry", "D2290000Z", "ACP (Acid phosphatase)(Fluid)", "U/L", "."},
            {"Chemistry", "D2510080Z", "Aldolase", "U/L", "."},
            {"Chemistry", "D1870000Z", "ALP", "U/L", "."},
            {"Chemistry", "D1850000Z", "ALT(sGPT)", "U/L", "."},
            {"Chemistry", "D3800020Z", "Amylase", "U/L", "."},
            {"Chemistry", "D3800020Z", "Amylase (fluid)", "U/L", "."},
            {"Chemistry", "D1860000Z", "AST(sGOT)", "U/L", "."},
            {"Chemistry", "D2510090Z", "Cholinesterase", "U/L", "."},
            {"Chemistry", "D2510010Z", "CK, total(CPK)", "U/L", "."},
            {"Chemistry", "D4040000Z", "CK-MB", "ng/mL", "."},
            {"Chemistry", "D0550010Z", "G-6-PDH", "U/L", "."},
            {"Chemistry", "D1890000Z", "GGT(γ-GTP)", "U/L", "."},
            {"Chemistry", "D2510040Z", "LAP", "U/L", "."},
            {"Chemistry", "D2510050Z", "LDH", "U/L", "."},
            {"Chemistry", "D2510050Z", "LDH (fluid)", "U/L", "."},
            {"Chemistry", "D4023020Z", "Troponin-T hs", "ng/mL", "."},
            {"Chemistry", "D3800010Z", "Lipase", "U/L", "."},
            {"Chemistry", "D1870000Z", "ALP (fluid)", "U/L", "."},
            {"Chemistry", "D4023010Z", "Troponin-I", "ng/mL", "."},
            {"Chemistry", "", "Trypsin", "mg/dL", "."},
            {"Chemistry", "D2630020Z", "Apolipoprotein A-1", "mg/dL", "."},
            {"Chemistry", "D2630020Z", "Apolipoprotein A-2", "mg/dL", "."},
            {"Chemistry", "D2630030Z", "Apolipoprotein B", "mg/dL", "."},
            {"Chemistry", "D2630040Z", "Apolipoprotein C-2", "mg/dL", "."},
            {"Chemistry", "D2630040Z", "Apolipoprotein C-3", "mg/dL", "."},
            {"Chemistry", "D2630050Z", "Apolipoprotein E", "mg/dL", "."},
            {"Chemistry", "D2611000Z", "Cholesterol, total", "mg/dL", "."},
            {"Chemistry", "D2264000Z", "Free fatty acid (FFA)", "mEq/L", "."},
            {"Chemistry", "D2613000Z", "HDL-cholesterol", "mg/dL", "."},
            {"Chemistry", "D2614000Z", "LDL-cholesterol", "mg/dL", "."},
            {"Chemistry", "", "Leptin", "ng/mL", "."},
            {"Chemistry", "", "Lipopro lipase", "ng/mL", "."},
            {"Chemistry", "D2620000Z", "LP(a) (Lipoprotein a)", "mg/dL", "."},
            {"Chemistry", "D2261000Z", "Phospholipid", "mg/dL", "."},
            {"Chemistry", "D2263000Z", "Triglyceride", "mg/dL", "."},
            {"Chemistry", "D2630010Z", "β-Lipoprotein", "mg/dL", "."},
            {"Chemistry", "D3040000Z", "Fructosamine", "μmol/L", "."},
            {"Chemistry", "D3022000Z", "Glucose", "mg/dL", "."},
            {"Chemistry", "D3022000Z", "Glucose (U)", "mg/24hr", "."},
              {"Chemistry", "D3022000Z", "Glucose tolerance(50g)", "mg/dL", "."},
            {"Chemistry", "D3063000Z", "HbA1C", "%", "."},
            {"Chemistry", "D2800050Z", "Calcium (Ca)", "mg/dL", "."},
            {"Chemistry", "D2800050Z", "Calcium (Urine)", "mg/24hr", "."},
            {"Chemistry", "D2800030Z", "Chloride (Cl)", "mmol/L", "."},
            {"Chemistry", "", "GFR", "mL/min", "."},
            {"Chemistry", "D2810020Z", "Ca++", "mmol/L", "."},
            {"Chemistry", "D2800010Z", "Magnesium (Mg)", "mg/dL", "."},
            {"Chemistry", "D3022000Z", "GTT100g(임신성 당뇨)", "mg/dL", "."},
            {"Chemistry", "D2820000Z", "Osmolality", "mOsm/kg", "."},
            {"Chemistry", "D2800040Z", "Phosphorus (P)", "mg/dL", "."},
            {"Chemistry", "D2820000Z", "Osmolality (Urine)", "mOsm/kg", "."},
            {"Chemistry", "D2800060Z", "Potassium (K)", "mmol/L", "."},
            {"Chemistry", "D2263000Z", "TG(Fluid)", "mg/dL", "."},
            {"Chemistry", "D2800020Z", "Sodium (Na)", "mmol/L", "."},
            {"Chemistry", "D2800030Z", "Cl (Urine)", "mmol/24hr", "."},
            {"Chemistry", "D2800010Z", "Magnesium (U)", "mg/24hr", "."},
            {"Chemistry", "D0521030Z", "Iron (Fe)", "μg/dL", "."},
            {"Chemistry", "D2800040Z", "P (Urine)", "mg/24hr", "."},
            {"Chemistry", "D1840000Z,D2280000Z", "P/C Ratio", "N/A", "."},
            {"Chemistry", "D4012000Z", "Myoglobin", "ng/mL", "."},
            {"Chemistry", "D4012000Z", "Myoglobin (U)", "ng/mL", "."},
            {"Chemistry", "", "Retinol binding protein(RBP)", "mg/L", "."},
            {"Chemistry", "D0521040Z", "TIBC", "μg/dL", "."},
            {"Chemistry", "D0521040Z,D0521030Z", "Transferrin saturation", "%", "."},
            {"Chemistry", "D0521010Z", "UIBC", "μg/dL", "."},
            {"Chemistry", "D5145006Z,D5146046Z,D5146036Z", "Amino acid", "μmol/L", "."},
            {"Chemistry", "D5151020Z", "Citric acid (U)", "mg/24hr", "."},
            {"Chemistry", "D5147010Z", "Homocysteine, total(mass)", "μmol/L", "."},
            {"Chemistry", "", "Homogentistic acid (HGA)", "mg/L", "."},
              {"Chemistry", "", "Hyaluronic acid", "ng/mL", "."},
            {"Chemistry", "CZ244", "Hydroxyproline, total", "mg/24hr", "."},
            {"Chemistry", "D3011000Z", "Ketone 정성", "N/A", "."},
            {"Chemistry", "D3013006Z", "Ketone body 3분획", "mmol/L", "."},
            {"Chemistry", "D5110000Z", "Lactic acid", "mmol/L", "."},
            {"Chemistry", "D5153016Z", "Methylmalonic acid 정량", "μmol/L", "."},
            {"Chemistry", "D2260000Z", "NAG", "U/g Cr", "."},
            {"Chemistry", "D5154006Z", "Organic acids", "mmol/L", "."},
            {"Chemistry", "D5153026Z", "Oxalic acid(Oxalate)", "mg/24hr", "."},
            {"Chemistry", "D5151030Z", "Pyruvic acid", "mmol/L", "."},
            {"Chemistry", "", "Sialic acid(Sialate)", "mg/L", "."},
            {"Chemistry", "D1900000Z", "Bile acid, total", "μmol/L", "."},
            {"Chemistry", "D5349866Z", "Teicoplanin", "μg/mL", "."},
            {"Chemistry", "D5162000Z", "Coproporphyrin", "μg/24hr", "."},
            {"Chemistry", "D5161000Z", "Coproporphyrin", "μg/g Cr", "."},
            
            {"Chemistry", "D5161000Z", "Porphyrin 정성", "N/A", "."},
            {"Chemistry", "D5163000Z", "Porphobilinogen정량", "μmol/24hr", "."},
            {"Chemistry", "D5161000Z", "Porphobilinogen정성", "N/A", "."},
            {"Chemistry", "D5161000Z", "Uroporphy정성", "N/A", "."},
            {"Chemistry", "D5333010Z", "Acetaminophen", "μg/mL", "."},
            {"Chemistry", "D5333020Z", "Amikacin", "μg/mL", "."},
            {"Chemistry", "D5333080Z", "Carbamazepine", "μg/mL", "."},
            {"Chemistry", "D5349206Z", "Clonazepam", "ng/mL", "."},
            {"Chemistry", "D5333110Z", "Cyclosporine", "ng/mL", "."},
            {"Chemistry", "", "Digitoxin", "ng/mL", "."},
            {"Chemistry", "D5333150Z", "Digoxin", "ng/mL", "."},
            {"Chemistry", "", "Disopyramide", "ng/mL", "."},
            {"Chemistry", "D5333420Z", "FK 506(Tacrolimus)", "ng/mL", "."},
            {"Chemistry", "", "Histamine", "ng/mL", "."},
            {"Chemistry", "D5349386Z", "Imipramine + Desipramine", "ng/mL", "."},

            {"Chemistry", "", "Lidocain", "ng/mL", "."},
            {"Chemistry", "D5333280Z", "Methotrexate(MTX)", "ng/mL", "."},
            {"Chemistry", "", "Cotinine(정성)", "N/A", "."},
            {"Chemistry", "", "Nicotine metabolite(정량)", "ng/mL", "."},
            {"Chemistry", "", "Nitrazepam", "ng/mL", "."},
            {"Chemistry", "D5343590Z", "Paraquat", "ng/mL", "."},
            {"Chemistry", "", "Nicotine(정성)", "N/A", "."},
            {"Chemistry", "D5333340Z", "Phenobarbital", "μg/mL", "."},
            {"Chemistry", "D5333360Z", "Phenytoin", "μg/mL", "."},
            {"Chemistry", "", "Procainamide", "ng/mL", "."},
            {"Chemistry", "", "Propranolol", "ng/mL", "."},
            {"Chemistry", "D5323400Z", "Salicylate(Aspirin)", "mg/L", "."},
            {"Chemistry", "D5333430Z", "Theophylline", "μg/mL", "."},
            {"Chemistry", "D5333450Z", "Valproic acid", "μg/mL", "."},
            {"Chemistry", "D5333470Z", "Vancomycin", "μg/mL", "."},
              {"Chemistry", "D5349996Z", "Zonisamide", "ng/mL", "."},
            {"Chemistry", "", "Amphetamine", "N/A", "."},
            {"Chemistry", "D5331040Z", "Barbiturate", "N/A", "."},
            {"Chemistry", "D5321050Z", "Benzodiazepin", "N/A", "."},
            {"Chemistry", "", "Cannabinoid(Marihuana) 정성", "N/A", "."},
            {"Chemistry", "", "Cocaine", "N/A", "."},
            {"Chemistry", "", "Morphine", "N/A", "."},
            {"Chemistry", "", "Phencyclidine", "N/A", "."},
            {"Chemistry", "", "TBPE 정성", "N/A", "."},
            {"Chemistry", "", "Opiates 정성", "N/A", "."},
            {"Chemistry", "", "MDMA(Ecstasy)", "N/A", "."},
            {"Chemistry", "D4902140Z", "Folate", "ng/mL", "."},
            {"Chemistry", "D4904020Z", "Vitamin A(Retinol)", "μg/dL", "."},
            {"Chemistry", "D4904036Z", "Vitamin B1(Thiamine)", "ng/mL", "."},
            {"Chemistry", "D4904046Z", "Vitamin B2", "ng/mL", "."}
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