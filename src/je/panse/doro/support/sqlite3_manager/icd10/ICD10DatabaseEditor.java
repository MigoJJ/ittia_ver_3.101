package je.panse.doro.support.sqlite3_manager.icd10;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.LineBorder;
import je.panse.doro.entry.EntryDir;

public class ICD10DatabaseEditor extends JFrame {

    private JTextField categoryField, codeField, titleField, commentField;
    private JTextArea findTextArea, statusArea;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private Connection conn;
    private int selectedRow = -1; // To track the currently selected row for edit/delete

    private final String dbPath = EntryDir.homeDir + "/support/sqlite3_manager/icd10/icd10_data.db";

    public ICD10DatabaseEditor() {
        setTitle("ICD10 SQLite DB Editor");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // North Panel: Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.add(createRightAlignedLabel("Category:"));
        categoryField = new JTextField();
        inputPanel.add(categoryField);
        inputPanel.add(createRightAlignedLabel("Code:"));
        codeField = new JTextField();
        inputPanel.add(codeField);
        inputPanel.add(createRightAlignedLabel("Title:"));
        titleField = new JTextField();
        titleField.setBackground(new Color(255, 255, 204)); // Light Yellow
        inputPanel.add(titleField);
        inputPanel.add(createRightAlignedLabel("Comment:"));
        commentField = new JTextField();
        inputPanel.add(commentField);
        inputPanel.add(createRightAlignedLabel("Find Title:")); // Changed label
        findTextArea = new JTextArea(1, 15); // Initialize JTextArea with rows and columns
        JScrollPane findScrollPane = new JScrollPane(findTextArea);
        inputPanel.add(findScrollPane);
        inputPanel.setBorder(new EmptyBorder(2, 5, 2, 5)); // Add some padding

        // Attempt to reduce North panel height (may not be exact 50% due to layout)
        Dimension currentPreferredSize = inputPanel.getPreferredSize();
        inputPanel.setMaximumSize(new Dimension(currentPreferredSize.width, currentPreferredSize.height / 2));

        // East Panel: Vertical Buttons with Same Size and Rounded Gradient Background
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JButton addButton = createRoundedGradientButton("Add New", new Color(100, 149, 237), new Color(65, 105, 225), this::addData); // Cornflower Blue
        JButton saveButton = createRoundedGradientButton("Save", new Color(144, 238, 144), new Color(34, 139, 34), this::saveData);   // Light Green to Forest Green
        JButton editButton = createRoundedGradientButton("Edit", new Color(255, 255, 0), new Color(255, 215, 0), this::editData);     // Yellow to Gold
        JButton deleteButton = createRoundedGradientButton("Delete", new Color(255, 99, 71), new Color(178, 34, 34), this::deleteData); // Tomato to Firebrick
        JButton findButton = createRoundedGradientButton("Find", new Color(255, 165, 0), new Color(255, 69, 0), this::findData);       // Orange to Red-Orange
        JButton refreshButton = createRoundedGradientButton("Refresh", new Color(238, 130, 238), new Color(139, 0, 139), this::loadData); // Violet to Dark Magenta
        JButton quitButton = createRoundedGradientButton("Quit", new Color(255, 192, 203), new Color(255, 0, 0), this::quitData);       // Pink to Red

        // Determine maximum button dimensions
        Dimension maxSize = new Dimension(0, 0);
        JButton[] buttons = {addButton, saveButton, editButton, deleteButton, findButton, refreshButton, quitButton};
        for (JButton button : buttons) {
            Dimension prefSize = button.getPreferredSize();
            maxSize.width = Math.max(maxSize.width, prefSize.width);
            maxSize.height = Math.max(maxSize.height, prefSize.height);
        }

        // Set same size for all buttons
        for (JButton button : buttons) {
            button.setPreferredSize(maxSize);
            button.setMaximumSize(maxSize);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(5));
        }

        // Center Panel: Table with Adjustable Column Widths
        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(dataTable);
        dataTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && dataTable.getSelectedRow() != -1) {
                selectedRow = dataTable.getSelectedRow();
                populateFields(selectedRow);
            }
        });

        // Set preferred column widths AFTER the table model has columns
        SwingUtilities.invokeLater(() -> {
            TableColumnModel columnModel = dataTable.getColumnModel();
            System.out.println("Column Count: " + columnModel.getColumnCount()); // Debugging
            if (columnModel.getColumnCount() == 5) { // Ensure there are 5 columns
                dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                int baseUnit = 70; // Adjust this value to scale the widths

                columnModel.getColumn(0).setPreferredWidth(1 * baseUnit);   // ID (1 part)
                columnModel.getColumn(1).setPreferredWidth(3 * baseUnit);   // Category (3 parts)
                columnModel.getColumn(2).setPreferredWidth(1 * baseUnit);   // Code (1 part)
                columnModel.getColumn(3).setPreferredWidth(5 * baseUnit);   // Title (5 parts)
                columnModel.getColumn(4).setPreferredWidth(2 * baseUnit);   // Comment (2 parts)
            }
        });

        // South Panel: Status Area
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        JScrollPane statusScrollPane = new JScrollPane(statusArea);
        statusScrollPane.setPreferredSize(new Dimension(getWidth(), 100));
        statusScrollPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add some padding

        // Main Layout
        setLayout(new BorderLayout(5, 5)); // Add some gap between components
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.EAST); // Buttons now in the EAST
        add(tableScrollPane, BorderLayout.CENTER); // Table now in the CENTER
        add(statusScrollPane, BorderLayout.SOUTH);

        // Initialize Database Connection and Load Data
        connectDatabase();
        loadData(null);
    }

    private JLabel createRightAlignedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    // Custom JButton with Rounded Gradient Background
    private JButton createRoundedGradientButton(String text, Color color1, Color color2, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15)); // Padding for text

        JPanel roundedPanel = new JPanel() {
            private final int arcWidth = 30; // Increased for more rounded corners
            private final int arcHeight = 30;
            private final int margin = 8; // Slightly larger margin for better spacing

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                RoundRectangle2D roundedRect = new RoundRectangle2D.Double(
                    margin, margin, 
                    width - 1 - 2 * margin, 
                    height - 1 - 2 * margin, 
                    arcWidth, arcHeight
                );

                // Paint the gradient (top-left to bottom-right for better effect)
                GradientPaint gp = new GradientPaint(
                    margin, margin, color1.brighter(), // Brighter start color
                    width - margin, height - margin, color2.darker() // Darker end color
                );
                g2d.setPaint(gp);
                g2d.fill(roundedRect);

                // Paint a thicker, more visible border
                g2d.setColor(color1.darker().darker());
                g2d.setStroke(new BasicStroke(2.0f)); // Thicker border
                g2d.draw(roundedRect);

                g2d.dispose();
                super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension buttonSize = button.getPreferredSize();
                return new Dimension(buttonSize.width + 2 * margin, buttonSize.height + 2 * margin);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        roundedPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundedPanel.setBorder(new EmptyBorder(8, 0, 8, 0)); // Increased vertical spacing
        roundedPanel.add(button);

        button.addActionListener(actionListener);

        return button;
    }

    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statusArea.append("Database connected: " + dbPath + "\n");
        } catch (SQLException e) {
            statusArea.append("Error connecting to database: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void closeDatabase() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                statusArea.append("Database connection closed.\n");
            }
        } catch (SQLException e) {
            statusArea.append("Error closing database connection: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void loadData(ActionEvent event) {
        statusArea.setText("");
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        selectedRow = -1;
        clearInputFields();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, category, code, title, comment FROM icd10_data")) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            tableModel.addColumn("ID"); // Changed from "RowID"
            for (int i = 1; i < columnCount; i++) { // Corrected loop condition
                tableModel.addColumn(metaData.getColumnName(i + 1)); // Adjusted column index
            }
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                row[0] = rs.getInt("id"); // Get ID
                for (int i = 1; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1); // Adjusted column index
                }
                tableModel.addRow(row);
            }
            statusArea.append("Data loaded successfully.\n");

        } catch (SQLException e) {
            statusArea.append("Error loading data: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void addData(ActionEvent event) {
        String category = categoryField.getText().trim();
        String code = codeField.getText().trim();
        String title = titleField.getText().trim();
        String comment = commentField.getText().trim();

        if (category.isEmpty() || code.isEmpty() || title.isEmpty()) {
            statusArea.append("Category, Code, and Title cannot be empty.\n");
            return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO icd10_data (category, code, title, comment) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, category);
            pstmt.setString(2, code);
            pstmt.setString(3, title);
            pstmt.setString(4, comment);
            pstmt.executeUpdate();
            statusArea.append("New record added.\n");
            loadData(null); // Refresh the table
            clearInputFields();
        } catch (SQLException e) {
            statusArea.append("Error adding data: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void saveData(ActionEvent event) {
        if (selectedRow == -1) {
            statusArea.append("Please select a row to edit first.\n");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0); // Get ID
        String category = categoryField.getText().trim();
        String code = codeField.getText().trim();
        String title = titleField.getText().trim();
        String comment = commentField.getText().trim();

        if (category.isEmpty() || code.isEmpty() || title.isEmpty()) {
            statusArea.append("Category, Code, and Title cannot be empty.\n");
            return;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE icd10_data SET category=?, code=?, title=?, comment=? WHERE id=?")) {
            pstmt.setString(1, category);
            pstmt.setString(2, code);
            pstmt.setString(3, title);
            pstmt.setString(4, comment);
            pstmt.setInt(5, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                statusArea.append("Record updated successfully.\n");
                loadData(null); // Refresh the table
                clearInputFields();
                selectedRow = -1;
            } else {
                statusArea.append("Failed to update record.\n");
            }
        } catch (SQLException e) {
            statusArea.append("Error updating data: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void editData(ActionEvent event) {
        if (selectedRow == -1) {
            statusArea.append("Please select a row to edit.\n");
            return;
        }
        // The fields are already populated when a row is selected.
        // The "Save" button will perform the actual update.
        statusArea.append("Ready to edit the selected row. Click 'Save' to apply changes.\n");
    }

    private void deleteData(ActionEvent event) {
        if (selectedRow == -1) {
            statusArea.append("Please select a row to delete.\n");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0); // Get ID

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM icd10_data WHERE id=?")) {
                pstmt.setInt(1, id);
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    statusArea.append("Record deleted successfully.\n");
                    loadData(null); // Refresh the table
                    clearInputFields();
                    selectedRow = -1;
                } else {
                    statusArea.append("Failed to delete record.\n");
                }
            } catch (SQLException e) {
                statusArea.append("Error deleting data: " + e.getMessage() + "\n");
                e.printStackTrace();
            }
        }
    }

    private void findData(ActionEvent event) {
        String findTitle = findTextArea.getText().trim(); // Get text from JTextArea
        if (findTitle.isEmpty()) {
            statusArea.append("Please enter a title to find.\n");
            return;
        }

        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        tableModel.addColumn("ID");
        tableModel.addColumn("Category");
        tableModel.addColumn("Code");
        tableModel.addColumn("Title");
        tableModel.addColumn("Comment");

        try (PreparedStatement pstmt = conn.prepareStatement(
                "SELECT id, category, code, title, comment FROM icd10_data WHERE title LIKE ?")) {
            pstmt.setString(1, "%" + findTitle + "%"); // Search based on title
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getString("comment")
                };
                tableModel.addRow(row);
            }
            statusArea.append("Found " + tableModel.getRowCount() + " records matching title '" + findTitle + "'.\n");

        } catch (SQLException e) {
            statusArea.append("Error finding data: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void quitData(ActionEvent event) {
        dispose();
    }

    private void populateFields(int row) {
        if (row != -1) {
            categoryField.setText(tableModel.getValueAt(row, 1).toString());
            codeField.setText(tableModel.getValueAt(row, 2).toString());
            titleField.setText(tableModel.getValueAt(row, 3).toString());
            commentField.setText(tableModel.getValueAt(row, 4).toString());
        }
    }

    private void clearInputFields() {
        categoryField.setText("");
        codeField.setText("");
        titleField.setText("");
        commentField.setText("");
        findTextArea.setText(""); // Clear the JTextArea
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ICD10DatabaseEditor().setVisible(true));
    }

    @Override
    protected void finalize() throws Throwable {
        closeDatabase();
        super.finalize();
    }
}
