package je.panse.doro.soap.assessment.kcd8;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import je.panse.doro.GDSEMR_frame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class KCDViewer extends JFrame {
    private JTable table;
    private DatabaseManager dbManager;
    private JTextField searchField;
    private JTextArea selectedDataArea;

    public KCDViewer() {
        dbManager = new DatabaseManager();
        setTitle("KCD-8DB Viewer");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadData();
    }

    private void initUI() {
        // Center: Table
        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Code", "Korean Name", "English Name"}, 0));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Code
        table.getColumnModel().getColumn(2).setPreferredWidth(350); // Korean Name
        table.getColumnModel().getColumn(3).setPreferredWidth(450); // English Name

        // North: Search Panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton loadAllButton = new JButton("Load All");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(loadAllButton);

        // East: TextArea for selected data
        selectedDataArea = new JTextArea(10, 20);
        selectedDataArea.setEditable(false);
        JScrollPane textAreaScrollPane = new JScrollPane(selectedDataArea);
        textAreaScrollPane.setBorder(BorderFactory.createTitledBorder("Selected Data"));

        // South: Modified Button Panel
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton clearButton = new JButton("Clear");
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");
        JButton othersButton = new JButton("Others");

        southPanel.add(clearButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);
        southPanel.add(othersButton);

        // Layout
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(textAreaScrollPane, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);

        // Event Listeners
        searchButton.addActionListener(e -> searchData());
        loadAllButton.addActionListener(e -> loadData());
        searchField.addActionListener(e -> searchData());

        // Table row selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String code = table.getValueAt(selectedRow, 1).toString();
                    String koreanName = table.getValueAt(selectedRow, 2).toString();
                    String englishName = table.getValueAt(selectedRow, 3).toString();
                    String entry = String.format("Code: %s\n # %s\n   : %s\n\n", code, koreanName, englishName);
                    selectedDataArea.append(entry);
                }
            }
        });

        // Button Listeners
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDataArea.setText(""); // Clear the TextArea
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToSave = selectedDataArea.getText();
                if (textToSave.isEmpty()) {
                    JOptionPane.showMessageDialog(KCDViewer.this, 
                        "No text to save.", 
                        "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    GDSEMR_frame.setTextAreaText(7, textToSave);
                    JOptionPane.showMessageDialog(KCDViewer.this, 
                        "Text saved to GDSEMR_frame (TextArea 7).", 
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(KCDViewer.this, 
                        "Error saving text: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the JFrame
            }
        });

        othersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(KCDViewer.this, 
                    "'Others' functionality not implemented yet.", 
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        ResultSet rs = dbManager.getAllData();
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
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
        }
    }

    private void searchData() {
        String query = searchField.getText().trim();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String sql = "SELECT * FROM kcd8db WHERE CAST(id AS TEXT) LIKE ? OR code LIKE ? OR korean_name LIKE ? OR english_name LIKE ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/migowj/git/ittia_ver_3.100/src/je/panse/doro/soap/assessment/kcd8/kcd8db.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeQuery = "%" + query + "%";
            pstmt.setString(1, likeQuery);
            pstmt.setString(2, likeQuery);
            pstmt.setString(3, likeQuery);
            pstmt.setString(4, likeQuery);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("korean_name"),
                        rs.getString("english_name")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching data: " + e.getMessage(), 
                "Search Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KCDViewer().setVisible(true));
    }
}