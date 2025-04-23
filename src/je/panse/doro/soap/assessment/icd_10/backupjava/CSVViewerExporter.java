package je.panse.doro.soap.assessment.icd_10.backupjava;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Vector;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import je.panse.doro.entry.EntryDir;

public class CSVViewerExporter extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnExport;

    public CSVViewerExporter() {
        setTitle("CSV to SQLite Exporter");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // 테이블 초기화
        model = new DefaultTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 버튼 패널
        JPanel panel = new JPanel();
        JButton btnLoad = new JButton("Load CSV");
        btnExport = new JButton("Export to SQLite");
        btnExport.setEnabled(false);
        panel.add(btnLoad);
        panel.add(btnExport);
        add(panel, BorderLayout.SOUTH);

        // CSV 로드 이벤트
        btnLoad.addActionListener(e -> loadCSV());

        // SQLite 내보내기 이벤트
        btnExport.addActionListener(e -> exportToSQLite());
    }

    private void loadCSV() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(file);
                 CSVParser parser = CSVFormat.DEFAULT.parse(new InputStreamReader(fis))) {
                
                model.setRowCount(0);
                model.setColumnCount(0);

                boolean isFirstRow = true;
                for (CSVRecord record : parser) {
                    if (isFirstRow) {
                        for (String header : record) {
                            model.addColumn(header);
                        }
                        isFirstRow = false;
                    } else {
                        Vector<String> row = new Vector<>();
                        record.forEach(row::add);
                        model.addRow(row);
                    }
                }
                btnExport.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading CSV: " + ex.getMessage());
            }
        }
    }

    private void exportToSQLite() {
        String url = "jdbc:sqlite:" + EntryDir.homeDir + "/soap/assessment/icd_10/diagnosis.db";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            
            // 테이블 생성 (기존 테이블 삭제)
            String createTable = "DROP TABLE IF EXISTS diagnosis; CREATE TABLE diagnosis (";
            for (int i = 0; i < model.getColumnCount(); i++) {
                createTable += model.getColumnName(i) + " TEXT";
                if (i < model.getColumnCount() - 1) createTable += ", ";
            }
            createTable += ")";
            stmt.executeUpdate(createTable);

            // 데이터 삽입 (Batch 처리)
            String insertSQL = "INSERT INTO diagnosis VALUES (" +
                    "?" + ", ?".repeat(model.getColumnCount() - 1) + ")";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        pstmt.setString(col + 1, model.getValueAt(row, col).toString());
                    }
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
            JOptionPane.showMessageDialog(this, "Exported to SQLite successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CSVViewerExporter().setVisible(true);
        });
    }
}
