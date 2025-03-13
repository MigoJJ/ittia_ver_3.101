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
            addInfectiousDiseases(pstmt);
            addCirculatoryDiseases(pstmt);

            System.out.println("Data added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding data: " + e.getMessage());
        }
    }

    private static void addThyroidDisorders(PreparedStatement pstmt) throws SQLException {
        String category = "Thyroid Disorders";
        String[][] thyroidDisorders = {
            {"E00", "Congenital iodine-deficiency syndrome"},
            {"E00.0", "Neurological form"},
            {"E00.1", "Myxedematous form"},
            {"E00.2", "Mixed form"},
            {"E00.9", "Unspecified congenital iodine-deficiency syndrome"},
            {"E01", "Iodine-deficiency-related thyroid disorders and goiter"},
            {"E01.0", "Iodine-deficiency-related diffuse goiter"},
            {"E01.1", "Iodine-deficiency-related multinodular goiter"},
            {"E01.2", "Iodine-deficiency-related goiter, unspecified"},
            {"E02", "Subclinical iodine-deficiency hypothyroidism"},
            {"E03.0", "Congenital hypothyroidism with diffuse goiter"},
            {"E03.1", "Congenital hypothyroidism without goiter"},
            {"E03.2", "Hypothyroidism due to medicaments and other exogenous substances"},
            {"E03.3", "Postinfectious hypothyroidism"},
            {"E03.4", "Atrophy of thyroid (Primary hypothyroidism)"},
            {"E03.5", "Myxedema coma"},
            {"E03.8", "Other specified hypothyroidism"},
            {"E03.9", "Hypothyroidism, unspecified"}
        };

        for (String[] row : thyroidDisorders) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addInfectiousDiseases(PreparedStatement pstmt) throws SQLException {
        String category = "Infectious Diseases";
        String[][] infectiousDiseases = {
            {"A00", "Cholera"},
            {"A01", "Typhoid and paratyphoid fevers"},
            {"A02", "Other salmonella infections"},
            {"A03", "Shigellosis"},
            {"A04", "Other bacterial intestinal infections (E. coli, Clostridium difficile)"},
            {"A05", "Bacterial foodborne intoxications"},
            {"A06", "Amebiasis"},
            {"A07", "Other protozoal intestinal diseases (giardiasis, cryptosporidiosis)"},
            {"A08", "Viral intestinal infections (rotavirus, norovirus)"},
            {"A09", "Infectious gastroenteritis and colitis, unspecified"},
            {"B15", "Viral hepatitis A"},
            {"B16", "Viral hepatitis B"},
            {"B17", "Viral hepatitis C"},
            {"B18", "Chronic viral hepatitis"},
            {"B19", "Unspecified viral hepatitis"},
            {"B20", "HIV/AIDS"},
            {"B25", "Cytomegalovirus infections"},
            {"B34", "Other viral infections"}
        };

        for (String[] row : infectiousDiseases) {
            addRow(pstmt, category, row[0], row[1], "ICD-10");
        }
    }

    private static void addCirculatoryDiseases(PreparedStatement pstmt) throws SQLException {
        String category = "Circulatory System Diseases";
        String[][] circulatoryDiseases = {
            {"I00", "Rheumatic fever without heart involvement"},
            {"I01", "Rheumatic fever with heart involvement"},
            {"I02", "Rheumatic chorea (Sydenham’s chorea)"},
            {"I05", "Rheumatic mitral valve diseases"},
            {"I06", "Rheumatic aortic valve diseases"},
            {"I07", "Rheumatic tricuspid valve diseases"},
            {"I08", "Multiple valve involvement"},
            {"I10", "Essential (primary) hypertension"},
            {"I11", "Hypertensive heart disease"},
            {"I12", "Hypertensive kidney disease"},
            {"I13", "Hypertensive heart and kidney disease"},
            {"I15", "Secondary hypertension (due to endocrine, renal, vascular causes)"},
            {"I20", "Angina pectoris"},
            {"I21", "Acute myocardial infarction (heart attack)"},
            {"I22", "Subsequent myocardial infarction"},
            {"I23", "Complications of acute myocardial infarction"},
            {"I24", "Other acute ischemic heart diseases"},
            {"I25", "Chronic ischemic heart disease"},
            {"I26", "Pulmonary embolism"},
            {"I27", "Other pulmonary heart diseases"},
            {"I28", "Other disorders of pulmonary circulation"},
            {"I30", "Acute pericarditis"},
            {"I31", "Other diseases of the pericardium"},
            {"I32", "Pericarditis in other diseases classified elsewhere"},
            {"I33", "Acute and subacute endocarditis"},
            {"I40", "Acute myocarditis"},
            {"I42", "Cardiomyopathy (e.g., dilated, hypertrophic, restrictive)"},
            {"I44", "Atrioventricular and left bundle-branch block"},
            {"I45", "Other conduction disorders"},
            {"I46", "Cardiac arrest"},
            {"I47", "Paroxysmal tachycardia"},
            {"I48", "Atrial fibrillation and flutter"},
            {"I49", "Other cardiac arrhythmias"},
            {"I50", "Heart failure (congestive, systolic, diastolic)"},
            {"I60", "Subarachnoid hemorrhage"},
            {"I61", "Intracerebral hemorrhage"},
            {"I62", "Other nontraumatic intracranial hemorrhages"},
            {"I63", "Cerebral infarction (ischemic stroke)"},
            {"I64", "Stroke, not specified as hemorrhagic or ischemic"},
            {"I70", "Atherosclerosis"},
            {"I71", "Aortic aneurysm and dissection"},
            {"I72", "Other aneurysms"},
            {"I73", "Other peripheral vascular diseases"},
            {"I74", "Arterial embolism and thrombosis"},
            {"I78", "Diseases of capillaries"},
            {"I79", "Disorders of arteries, arterioles, and capillaries"},
            {"I80", "Phlebitis and thrombophlebitis"},
            {"I81", "Portal vein thrombosis"},
            {"I82", "Other venous embolism and thrombosis"},
            {"I83", "Varicose veins of lower extremities"},
            {"I85", "Esophageal varices"},
            {"I87", "Other disorders of veins"},
            {"I89", "Other noninfective disorders of lymphatic vessels and lymph nodes"},
            {"I95", "Hypotension"},
            {"I96", "Gangrene, not elsewhere classified"},
            {"I99", "Other and unspecified circulatory disorders"}
        };

        for (String[] row : circulatoryDiseases) {
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
