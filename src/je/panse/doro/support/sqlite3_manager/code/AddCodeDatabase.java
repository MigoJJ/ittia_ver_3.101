package je.panse.doro.support.sqlite3_manager.code;

import java.sql.*;

import je.panse.doro.entry.EntryDir;

public class AddCodeDatabase {

    private static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";

    public static void addData() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)")) {

            // Congenital Iodine-Deficiency Syndrome (E00)
            addRow(pstmt, "Endocrine", "E00", "Congenital iodine-deficiency syndrome", "ICD-10");
            addRow(pstmt, "Endocrine", "E00.0", "Neurological form", "ICD-10");
            addRow(pstmt, "Endocrine", "E00.1", "Myxedematous form", "ICD-10");
            addRow(pstmt, "Endocrine", "E00.2", "Mixed form", "ICD-10");
            addRow(pstmt, "Endocrine", "E00.9", "Unspecified congenital iodine-deficiency syndrome", "ICD-10");

            // Iodine-Deficiency-Related Thyroid Disorders (E01)
            addRow(pstmt, "Endocrine", "E01", "Iodine-deficiency-related thyroid disorders and goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E01.0", "Iodine-deficiency-related diffuse (endemic) goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E01.1", "Iodine-deficiency-related multinodular (endemic) goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E01.2", "Iodine-deficiency-related goiter, unspecified", "ICD-10");

            // Subclinical Iodine-Deficiency Hypothyroidism (E02)
            addRow(pstmt, "Endocrine", "E02", "Subclinical iodine-deficiency hypothyroidism", "ICD-10");

            // Other Hypothyroidism (E03)
            addRow(pstmt, "Endocrine", "E03", "Other hypothyroidism", "ICD-10");
            addRow(pstmt, "Endocrine", "E03.0", "Congenital hypothyroidism with diffuse goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E03.1", "Congenital hypothyroidism without goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E03.2", "Hypothyroidism due to medicaments and other exogenous substances", "ICD-10");
            addRow(pstmt, "Endocrine", "E03.3", "Postinfectious hypothyroidism", "ICD-10");
            addRow(pstmt, "Endocrine", "E03.4", "Atrophy of thyroid (Primary hypothyroidism)", "ICD-10");
            addRow(pstmt, "Endocrine", "E03.5", "Myxedema coma", "ICD-10");
            addRow(pstmt, "Endocrine", "E03.8", "Other specified hypothyroidism", "ICD-10");
            addRow(pstmt, "Endocrine", "E03.9", "Hypothyroidism, unspecified", "ICD-10");

            // Other Non-Toxic Goiter (E04)
            addRow(pstmt, "Endocrine", "E04", "Other non-toxic goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E04.0", "Nontoxic diffuse goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E04.1", "Nontoxic single thyroid nodule", "ICD-10");
            addRow(pstmt, "Endocrine", "E04.2", "Nontoxic multinodular goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E04.8", "Other specified nontoxic goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E04.9", "Nontoxic goiter, unspecified", "ICD-10");

            // Thyrotoxicosis (Hyperthyroidism) (E05)
            addRow(pstmt, "Endocrine", "E05", "Thyrotoxicosis (Hyperthyroidism)", "ICD-10");
            addRow(pstmt, "Endocrine", "E05.0", "Thyrotoxicosis with diffuse goiter (Graves’ disease)", "ICD-10");
            addRow(pstmt, "Endocrine", "E05.1", "Thyrotoxicosis with toxic single thyroid nodule", "ICD-10");
            addRow(pstmt, "Endocrine", "E05.2", "Thyrotoxicosis with toxic multinodular goiter", "ICD-10");
            addRow(pstmt, "Endocrine", "E05.3", "Thyrotoxicosis from ectopic thyroid tissue", "ICD-10");
            addRow(pstmt, "Endocrine", "E05.4", "Thyrotoxicosis factitia (Induced by excessive thyroid hormone intake)", "ICD-10");
            addRow(pstmt, "Endocrine", "E05.8", "Other thyrotoxicosis", "ICD-10");
            addRow(pstmt, "Endocrine", "E05.9", "Thyrotoxicosis, unspecified", "ICD-10");

            // Thyroiditis (Inflammation of the Thyroid) (E06)
            addRow(pstmt, "Endocrine", "E06", "Thyroiditis (Inflammation of the Thyroid)", "ICD-10");
            addRow(pstmt, "Endocrine", "E06.0", "Acute thyroiditis", "ICD-10");
            addRow(pstmt, "Endocrine", "E06.1", "Subacute thyroiditis (De Quervain's thyroiditis)", "ICD-10");
            addRow(pstmt, "Endocrine", "E06.2", "Chronic thyroiditis with transient thyrotoxicosis", "ICD-10");
            addRow(pstmt, "Endocrine", "E06.3", "Autoimmune thyroiditis (Hashimoto’s thyroiditis)", "ICD-10");
            addRow(pstmt, "Endocrine", "E06.4", "Drug-induced thyroiditis", "ICD-10");
            addRow(pstmt, "Endocrine", "E06.5", "Other chronic thyroiditis", "ICD-10");
            addRow(pstmt, "Endocrine", "E06.9", "Thyroiditis, unspecified", "ICD-10");

            // Other Thyroid Disorders (E07)
            addRow(pstmt, "Endocrine", "E07", "Other thyroid disorders", "ICD-10");
            addRow(pstmt, "Endocrine", "E07.0", "Hypersecretion of calcitonin", "ICD-10");
            addRow(pstmt, "Endocrine", "E07.1", "Dyshormonogenic goiter (Inherited thyroid hormone biosynthesis defect)", "ICD-10");
            addRow(pstmt, "Endocrine", "E07.8", "Other specified thyroid disorders", "ICD-10");
            addRow(pstmt, "Endocrine", "E07.9", "Thyroid disorder, unspecified", "ICD-10");

            System.out.println("Data added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding data: " + e.getMessage());
        }
    }

    private static void addRow(PreparedStatement pstmt, String category, String bCode, String name, String reference) throws SQLException {
        pstmt.setString(1, category);
        pstmt.setString(2, bCode);
        pstmt.setString(3, name);
        pstmt.setString(4, reference);
        pstmt.executeUpdate();
    }

    public static void main(String[] args) {
        addData();
    }
}
