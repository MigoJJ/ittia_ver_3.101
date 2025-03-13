package je.panse.doro.support.sqlite3_manager.code;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import je.panse.doro.entry.EntryDir;

public class CodeMainScreen extends JFrame implements ActionListener {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JPanel southPanel;
    private DatabaseModel model; 

    public CodeMainScreen() {
        model = new DatabaseModel();  
        model.createDatabaseTable();
        model.createIndexOnBCode(); 

        setupFrame();
        setupTable();
        setupButtons();
        loadData();
        setVisible(true);
    }

    private void setupFrame() {
        setTitle("Code/Disease Database");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    private void setupTable() {
        String[] columnNames = {"Category", "B-code", "Name", "Reference"};
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

        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(400);
        setColumnAlignment();
    }

    private void setColumnAlignment() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    }

    private void setupButtons() {
        southPanel = new JPanel();
        
        // Add standard buttons
        String[] buttonLabels = {"Add", "Delete", "Edit", "Find", "Exit"};
        for (String label : buttonLabels) {
            southPanel.add(createButton(label));
        }

        // Add column-specific search buttons
//        JButton findBCodeButton = createButton("Find B-code");
//        JButton findNameButton = createButton("Find Name");
//        JButton findReferenceButton = createButton("Find Reference");

//        southPanel.add(findBCodeButton);
//        southPanel.add(findNameButton);
//        southPanel.add(findReferenceButton);

        // Search Field (Triggers "Find" button when Enter is pressed)
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setFont(new Font("Arial", Font.BOLD, 14));
        
        searchField.addActionListener(e -> { 
            // When Enter is pressed, execute "Find" button action
            findRecords(searchField.getText());
            searchField.setText(""); // Clear the field after search
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
        try (ResultSet rs = model.getRecordsSortedByBCode()) {
            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("Category"), rs.getString("B_code"),
                    rs.getString("name"), rs.getString("reference")
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
        model.findAndDisplayRecords(searchText, "ALL", tableModel);
    }

   
    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            model.deleteRecord((String) tableModel.getValueAt(modelRow, 1));
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }

    private void editRecord() {
        JOptionPane.showMessageDialog(this, "Edit functionality is under development.");
    }
    
    // FIXED showAddDialog() inside CodeMainScreen
    private void showAddDialog() {
        JTextField categoryField = new JTextField(20);
        JTextField bcodeField = new JTextField(10);
        JTextField nameField = new JTextField(30);
        JTextField referenceField = new JTextField(30);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("B-code:"));
        panel.add(bcodeField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Reference:"));
        panel.add(referenceField);

        if (JOptionPane.showConfirmDialog(null, panel, "Add New Entry", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            model.insertRecord(categoryField.getText(), bcodeField.getText(), nameField.getText(), referenceField.getText());
            loadData();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CodeMainScreen::new);
    }
}

// ============================ DatabaseModel Class ============================

class DatabaseModel {
    static final String DB_URL = "jdbc:sqlite:" + EntryDir.homeDir + "/support/sqlite3_manager/code/CodeFullDis.db";

    public void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS codedis (Category TEXT, B_code TEXT PRIMARY KEY, name TEXT, reference TEXT)";
        executeSQL(sql);
    }

    public void findAndDisplayRecords(String searchText, String column, DefaultTableModel tableModel) {
            String sql = "SELECT Category, B_code, name, reference FROM codedis WHERE ";

            switch (column) {
                case "B_code":
                    sql += "B_code LIKE ?";
                    break;
                case "name":
                    sql += "name LIKE ?";
                    break;
                case "reference":
                    sql += "reference LIKE ?";
                    break;
                default: // "ALL" searches all columns
                    sql += "Category LIKE ? OR B_code LIKE ? OR name LIKE ? OR reference LIKE ?";
            }

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                String searchPattern = "%" + searchText + "%";

                if (column.equals("ALL")) {
                    pstmt.setString(1, searchPattern);
                    pstmt.setString(2, searchPattern);
                    pstmt.setString(3, searchPattern);
                    pstmt.setString(4, searchPattern);
                } else {
                    pstmt.setString(1, searchPattern);
                }

                try (ResultSet rs = pstmt.executeQuery()) {
                    tableModel.setRowCount(0); // Clear table before adding new results
                    while (rs.next()) {
                        tableModel.addRow(new Object[]{
                            rs.getString("Category"),
                            rs.getString("B_code"),
                            rs.getString("name"),
                            rs.getString("reference")
                        });
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error finding records: " + e.getMessage());
            }
        }

       

	public void findAndDisplayRecords(String searchText, DefaultTableModel tableModel) {
        String sql = "SELECT Category, B_code, name, reference FROM codedis " +
                     "WHERE Category LIKE ? OR B_code LIKE ? OR name LIKE ? OR reference LIKE ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchText + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0); // Clear table before adding new results
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getString("Category"), 
                        rs.getString("B_code"), 
                        rs.getString("name"), 
                        rs.getString("reference")
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding records: " + e.getMessage());
        }
    }

	public void createIndexOnBCode() {
        String sql = "CREATE INDEX IF NOT EXISTS idx_bcode ON codedis (B_code)";
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

    public void insertRecord(String category, String bcode, String name, String reference) {
        String sql = "INSERT INTO codedis (Category, B_code, name, reference) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            pstmt.setString(2, bcode);
            pstmt.setString(3, name);
            pstmt.setString(4, reference);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
        }
    }

    public void deleteRecord(String bcode) {
        String sql = "DELETE FROM codedis WHERE B_code=?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bcode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting record: " + e.getMessage());
        }
    }

    public ResultSet getRecordsSortedByBCode() {
        String sql = "SELECT Category, B_code, name, reference FROM codedis ORDER BY B_code ASC";
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error fetching records: " + e.getMessage());
            return null;
        }
    }
}
