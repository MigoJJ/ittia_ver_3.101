package je.panse.doro.soap.assessment.kcd8;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class KCDViewer extends JFrame {
    private JTable table;
    private DatabaseManager dbManager;

    public KCDViewer() {
        dbManager = new DatabaseManager();
        setTitle("KCD-8DB Viewer");
        setSize(900, 600); // Increased size to accommodate additional columns
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadData();
    }

    private void initUI() {
        // Updated column names to match new schema
        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Code", "Korean Name", "English Name"}, 0));
        JScrollPane scrollPane = new JScrollPane(table);

        // Set column widths for better readability
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Code
        table.getColumnModel().getColumn(2).setPreferredWidth(300); // Korean Name
        table.getColumnModel().getColumn(3).setPreferredWidth(400); // English Name

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        ResultSet rs = dbManager.getAllData();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        try {
            if (rs != null) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("code"),
                            rs.getString("korean_name"),
                            rs.getString("english_name")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close ResultSet to prevent resource leaks
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KCDViewer().setVisible(true));
    }
}