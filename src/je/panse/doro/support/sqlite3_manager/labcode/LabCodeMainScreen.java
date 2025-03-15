package je.panse.doro.support.sqlite3_manager.labcode;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import je.panse.doro.entry.EntryDir;

/**
 * A Swing-based GUI for managing laboratory codes in a SQLite database.
 */
public class LabCodeMainScreen extends JFrame implements ActionListener {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JPanel southPanel;
    private LabDatabaseModel model;

    /**
     * Constructs the LabCodeMainScreen, initializes the database, and sets up the UI.
     */
    public LabCodeMainScreen() {
        model = new LabDatabaseModel();
        model.createDatabaseTable();
        model.createIndexOnItems();

        setupFrame();
        setupTable();
        setupButtons();
        loadData();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Lab Code Database");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    private void setupTable() {
        String[] columnNames = {"Category", "B_code", "Items", "Unit", "Comment"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Consolas", Font.BOLD, 11));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        table.getColumnModel().getColumn(0).setPreferredWidth(200); // Category
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // B_code
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Items
        table.getColumnModel().getColumn(3).setPreferredWidth(150); // Unit
        table.getColumnModel().getColumn(4).setPreferredWidth(300); // Comment
        setColumnAlignment();
    }

    private void setColumnAlignment() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // B_code
    }

    private void setupButtons() {
        southPanel = new JPanel();

        String[] buttonLabels = {"Add", "Delete", "Edit", "Find", "Exit"};
        for (String label : buttonLabels) {
            southPanel.add(createButton(label));
        }

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setFont(new Font("Arial", Font.BOLD, 14));

        searchField.addActionListener(e -> {
            findRecords(searchField.getText());
            searchField.setText("");
        });

        southPanel.add(new JLabel("Search:"));
        southPanel.add(searchField);

        add(southPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        return button;
    }

    private void loadData() {
        try (ResultSet rs = model.getRecordsSortedByItems()) {
            if (rs == null) {
                showError("Failed to fetch records.");
                return;
            }
            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("Category"), rs.getString("B_code"), rs.getString("Items"),
                    rs.getString("unit"), rs.getString("comment")
                });
            }
        } catch (SQLException e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchField) {
            findRecords(searchField.getText());
            searchField.setText("");
        } else {
            String command = e.getActionCommand();
            switch (command) {
                case "Add": showAddDialog(); break;
                case "Delete": deleteRecord(); break;
                case "Edit": editRecord(); break;
                case "Find": findRecords(searchField.getText()); break;
                case "Exit": dispose(); break;
            }
        }
    }

    private void findRecords(String searchText) {
        model.findAndDisplayRecords(searchText, tableModel);
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            model.deleteRecord((String) tableModel.getValueAt(modelRow, 2)); // Use Items column (index 2)
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }

    private void editRecord() {
        JOptionPane.showMessageDialog(this, "Edit functionality is under development.");
    }

    private void showAddDialog() {
        JTextField categoryField = new JTextField(20);
        JTextField bCodeField = new JTextField(10);
        JTextField itemsField = new JTextField(20);
        JTextField unitField = new JTextField(10);
        JTextField commentField = new JTextField(30);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("B_code:"));
        panel.add(bCodeField);
        panel.add(new JLabel("Items:"));
        panel.add(itemsField);
        panel.add(new JLabel("Unit:"));
        panel.add(unitField);
        panel.add(new JLabel("Comment:"));
        panel.add(commentField);

        if (JOptionPane.showConfirmDialog(null, panel, "Add New Entry", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            model.insertRecord(categoryField.getText(), bCodeField.getText(), itemsField.getText(), 
                              unitField.getText(), commentField.getText());
            loadData();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LabCodeMainScreen::new);
    }
}

// ============================ LabDatabaseModel Class ============================

/**
 * Manages database operations for the labcodes table.
 */
class LabDatabaseModel {
    static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/labcode/LabCodeFullDis.db";

    public void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS labcodes (" +
                     "Category TEXT, B_code TEXT, Items TEXT PRIMARY KEY, unit TEXT, comment TEXT)";
        executeSQL(sql);
    }

    public void createIndexOnItems() {
        String sql = "CREATE INDEX IF NOT EXISTS idx_items ON labcodes (Items)";
        executeSQL(sql);
    }

    private void executeSQL(String sql) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public void insertRecord(String category, String bCode, String items, String unit, String comment) {
        String sql = "INSERT INTO labcodes (Category, B_code, Items, unit, comment) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            pstmt.setString(2, bCode);
            pstmt.setString(3, items);
            pstmt.setString(4, unit);
            pstmt.setString(5, comment);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
        }
    }

    public void deleteRecord(String items) {
        String sql = "DELETE FROM labcodes WHERE Items=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, items);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting record: " + e.getMessage());
        }
    }

    public ResultSet getRecordsSortedByItems() {
        String sql = "SELECT Category, B_code, Items, unit, comment FROM labcodes ORDER BY Items ASC";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching records: " + e.getMessage());
            return null;
        }
    }

    public void findAndDisplayRecords(String searchText, DefaultTableModel tableModel) {
        String sql = "SELECT Category, B_code, Items, unit, comment FROM labcodes " +
                     "WHERE Category LIKE ? OR B_code LIKE ? OR Items LIKE ? OR unit LIKE ? OR comment LIKE ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchText + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            pstmt.setString(5, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getString("Category"), rs.getString("B_code"), rs.getString("Items"),
                        rs.getString("unit"), rs.getString("comment")
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding records: " + e.getMessage());
        }
    }
}