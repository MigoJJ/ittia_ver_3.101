package je.panse.doro.support.sqlite3abbreviation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import je.panse.doro.entry.EntryDir;

public class DatabaseExtractStrings {
    private static String dbURL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3abbreviation/AbbFullDis.db";

    public static void main(String[] args) {
        // Extract data from the database
        String[][] data = extractData();

        // Print the extracted data
        for (String[] entry : data) {
            System.out.println("outputText = outputText.replace( \"" + entry[0] + " \" , \""  + entry[1] + " \");");
        }
    }

    private static String[][] extractData() {
        List<String[]> dataList = new ArrayList<>();
        String sql = "SELECT abbreviation, full_text FROM abbreviations";

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String abbreviation = rs.getString("abbreviation");
                String fullText = rs.getString("full_text");
                dataList.add(new String[]{abbreviation, fullText});
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Convert List to String[][]
        String[][] dataArray = new String[dataList.size()][2];
        return dataList.toArray(dataArray);
    }
}
