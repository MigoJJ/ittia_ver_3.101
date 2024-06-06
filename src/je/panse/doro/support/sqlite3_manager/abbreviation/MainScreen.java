package je.panse.doro.support.sqlite3_manager.abbreviation;

import java.awt.BorderLayout;			
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import je.panse.doro.entry.EntryDir;
import je.panse.doro.support.sqlite3_manager.abbreviation.setfindedit.DatabaseExtractStrings;

public class MainScreen extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private static String dbURL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/abbreviation/AbbFullDis.db";
//    private static String dbURL = "jdbc:sqlite:/home/migowj/git/ittia_ver_3.051/src/je/panse/doro/support/sqlite3_manager/abbreviation/AbbFullDis.db";

    
    public MainScreen() {
        setTitle("Database Interaction Screen");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Abbreviation", "Full Text"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30); // Set the row height to 30

        // Center the frame on the screen
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Set column width ratio 1:2 (Abbreviation: Full Text)
        setColumnWidths(table, 1, 2);

        // Set indentation for table cells
        setColumnIndentation(table, 6);

        // South panel for buttons
        JPanel southPanel = new JPanel();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> showAddDialog());
        southPanel.add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteRecord());
        southPanel.add(deleteButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> editRecord());
        southPanel.add(editButton);

        JButton findButton = new JButton("Find");
        findButton.addActionListener(e -> showFindDialog());
        southPanel.add(findButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> dispose());
        southPanel.add(exitButton);

        JButton extractButton = new JButton("Extract");
        extractButton.addActionListener(e -> {
            try {
                DatabaseExtractStrings.main(null);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        southPanel.add(extractButton);

        add(southPanel, BorderLayout.SOUTH);

        // Load initial data
        loadData();

        // Set default font for center panel components
        setDefaultFont(scrollPane, new Font("Consolas", Font.PLAIN, 16));

        setVisible(true);
    }

    private void setColumnWidths(JTable table, int... widths) {
        int totalWidth = table.getPreferredSize().width;
        for (int i = 0; i < widths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((totalWidth / 3) * widths[i]);
        }
    }

    private void setColumnIndentation(JTable table, int indentSpaces) {
        int indentPixels = indentSpaces * 6;
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    label.setBorder(BorderFactory.createEmptyBorder(0, indentPixels, 0, 0));
                }
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void setDefaultFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setDefaultFont(child, font);
            }
        }
    }

    private void loadData() {
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT abbreviation, full_text FROM Abbreviations")) {

            tableModel.setRowCount(0); // Clear existing data
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString("abbreviation"), rs.getString("full_text")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showFindDialog() {
        JTextField searchText = new JTextField(30);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Search Text:"));
        panel.add(searchText);
        int result = JOptionPane.showConfirmDialog(null, panel, "Find Abbreviation or Full Text", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            findRecords(searchText.getText());
        }
    }

    private void showAddDialog() {
        JTextField abbreviationField = new JTextField(10);
        JTextField fullTextField = new JTextField(30);
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Abbreviation:"));
        panel.add(abbreviationField);
        panel.add(new JLabel("Full Text:"));
        panel.add(fullTextField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Entry", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            insertRecord(abbreviationField.getText(), fullTextField.getText());
        }
    }

    private void findRecords(String searchText) {
        String sql = "SELECT abbreviation, full_text FROM Abbreviations WHERE abbreviation LIKE ? OR full_text LIKE ?";
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchText + "%");
            pstmt.setString(2, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();
            tableModel.setRowCount(0); // Clear existing data
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getString("abbreviation"), rs.getString("full_text")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error finding data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertRecord(String abbreviation, String fullText) {
        String sql = "INSERT INTO Abbreviations (abbreviation, full_text) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, abbreviation);
            pstmt.setString(2, fullText);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                tableModel.addRow(new Object[]{abbreviation, fullText});
                JOptionPane.showMessageDialog(this, "Record added successfully!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error inserting data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String abbreviation = (String) tableModel.getValueAt(selectedRow, 0);
            String sql = "DELETE FROM Abbreviations WHERE abbreviation = ?";
            try (Connection conn = DriverManager.getConnection(dbURL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, abbreviation);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Record deleted successfully!");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }

    private void editRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String abbreviation = (String) tableModel.getValueAt(selectedRow, 0);
            JTextField abbreviationField = new JTextField(abbreviation, 10);
            JTextField fullTextField = new JTextField((String) tableModel.getValueAt(selectedRow, 1), 30);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Abbreviation:"));
            panel.add(abbreviationField);
            panel.add(new JLabel("Full Text:"));
            panel.add(fullTextField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit Entry", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String newAbbreviation = abbreviationField.getText().trim();
                String newFullText = fullTextField.getText().trim();

                if (newAbbreviation.isEmpty() || newFullText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter both abbreviation and full text.");
                    return;
                }

                updateRecord(abbreviation, newAbbreviation, newFullText);
                loadData(); // Reload data to reflect changes
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
        }
    }

    private void updateRecord(String oldAbbreviation, String newAbbreviation, String newFullText) {
        String sql = "UPDATE Abbreviations SET abbreviation = ?, full_text = ? WHERE abbreviation = ?";
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newAbbreviation);
            pstmt.setString(2, newFullText);
            pstmt.setString(3, oldAbbreviation);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(MainScreen::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
