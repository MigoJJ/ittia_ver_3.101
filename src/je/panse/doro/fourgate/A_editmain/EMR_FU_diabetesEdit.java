package je.panse.doro.fourgate.A_editmain;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EMR_FU_diabetesEdit extends JFrame {
    private Connection conn;
    private JTable table;
    private DefaultTableModel tableModel;

    private final String[] columnNames = {
            "ID", "History", "Symptoms", "Examination", "Vitals",
            "Lab Results", "Diagnosis", "Treatment", "Medications",
            "Follow-up Plan", "Recommendations"
    };

    /** Constructor: Initializes the EMR Diabetes Follow-up Editor */
    public EMR_FU_diabetesEdit() {
        super("EMR Diabetes Follow-up Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        initializeDatabase();
        createUI();

        setVisible(true);
    }

    /** Initializes the SQLite Database */
    private void initializeDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:diabetes_emr.db");
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS diabetes_records (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "history TEXT, symptoms TEXT, examination TEXT, vitals TEXT, " +
                        "lab_results TEXT, diagnosis TEXT, treatment TEXT, medications TEXT, " +
                        "followup_plan TEXT, recommendations TEXT)");
            }
        } catch (SQLException e) {
            showErrorDialog("Database Error", "Failed to initialize database: " + e.getMessage());
        }
    }

    /** Creates the main UI with JTable and Buttons */
    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Table Configuration
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Table is read-only
            }
        };
        table = new JTable(tableModel);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        String[] buttons = {"ADD", "EDIT", "FIND", "DELETE", "SAVE", "EXIT"};
        for (String label : buttons) {
            JButton button = new JButton(label);
            button.addActionListener(e -> handleButtonAction(label));
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    /** Handles button actions */
    private void handleButtonAction(String action) {
        switch (action) {
            case "ADD" -> new AddFrame().setVisible(true);
            case "EDIT" -> openEditFrame();
            case "FIND" -> findRecord();
            case "DELETE" -> deleteRecord();
            case "SAVE" -> saveRecord();
            case "EXIT" -> exitApplication();
        }
    }

    /** Opens the Edit Frame */
    private void openEditFrame() {
        String id = getInputId("Enter record ID to edit:");
        if (id != null) new JFrame(id).setVisible(true);
    }

    /** Finds and displays a record */
    private void findRecord() {
        String id = getInputId("Enter record ID to find:");
        if (id == null) return;
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM diabetes_records WHERE id = ?")) {
            pstmt.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0);
                if (rs.next()) displayRecord(rs);
                else JOptionPane.showMessageDialog(this, "Record not found");
            }
        } catch (SQLException | NumberFormatException e) {
            showErrorDialog("Find Error", e.getMessage());
        }
    }

    /** Deletes a record */
    private void deleteRecord() {
        String id = getInputId("Enter record ID to delete:");
        if (id == null) return;
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM diabetes_records WHERE id = ?")) {
            pstmt.setInt(1, Integer.parseInt(id));
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted");
                findAllRecords();
            } else {
                JOptionPane.showMessageDialog(this, "Record not found");
            }
        } catch (SQLException | NumberFormatException e) {
            showErrorDialog("Delete Error", e.getMessage());
        }
    }

    private void findAllRecords() {
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM diabetes_records ORDER BY id ASC")) {
            try (ResultSet rs = pstmt.executeQuery()) {
                tableModel.setRowCount(0); // Clear existing table data
                while (rs.next()) {
                    displayRecord(rs); // Add records to the table
                }
            }
        } catch (SQLException e) {
            showErrorDialog("Refresh Error", e.getMessage());
        }
    }

    /** Saves a record (handled through EditFrame/AddFrame) */
    private void saveRecord() {
        JOptionPane.showMessageDialog(this, "Use ADD or EDIT to save new records.");
    }

    /** Exits the application */
    private void exitApplication() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            showErrorDialog("Exit Error", "Failed to close database: " + e.getMessage());
        }
        dispose();
    }

    /** Helper: Get input ID from user */
    private String getInputId(String message) {
        String id = JOptionPane.showInputDialog(this, message);
        return (id == null || id.trim().isEmpty()) ? null : id;
    }

    /** Displays an error message */
    private void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /** Displays a record in the table */
    private void displayRecord(ResultSet rs) throws SQLException {
        Object[] row = new Object[columnNames.length];
        row[0] = rs.getInt("id");
        for (int i = 1; i < columnNames.length; i++) {
            row[i] = rs.getString(columnNames[i].toLowerCase().replace(" ", "_"));
        }
        tableModel.addRow(row);
    }

    /** Inner class for Adding New Records */
    private class AddFrame extends JFrame {
        private final JTextArea[] textAreas = new JTextArea[columnNames.length - 1];

        AddFrame() {
            super("Add New Diabetes Record");
            setSize(500, 600);
            setLocationRelativeTo(EMR_FU_diabetesEdit.this);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            JPanel mainPanel = new JPanel(new GridLayout(columnNames.length, 1, 5, 5));
            for (int i = 1; i < columnNames.length; i++) {
                JPanel row = new JPanel(new BorderLayout());
                row.add(new JLabel(columnNames[i] + ":"), BorderLayout.WEST);
                textAreas[i - 1] = new JTextArea(3, 30);
                row.add(new JScrollPane(textAreas[i - 1]), BorderLayout.CENTER);
                mainPanel.add(row);
            }

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e -> saveNewRecord());
            mainPanel.add(saveButton);

            add(mainPanel);
        }

        private void saveNewRecord() {
            try (PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO diabetes_records (history, symptoms, examination, vitals, " +
                            "lab_results, diagnosis, treatment, medications, followup_plan, recommendations) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                for (int i = 0; i < textAreas.length; i++) {
                    pstmt.setString(i + 1, textAreas[i].getText());
                }
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "New record added!");
                dispose();
                findAllRecords(); // Refresh table
            } catch (SQLException e) {
                showErrorDialog("Save Error", e.getMessage());
            }
        }
    }

    /** Main Method */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EMR_FU_diabetesEdit::new);
    }
}
