package je.panse.doro.fourgate.A_editmain;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.entry.EntryDir;

public class EMR_FU_diabetesEdit extends JFrame {
    private static final int NUM_TEXT_AREAS = 10;
    private static final String DB_PATH = EntryDir.homeDir + "/fourgate/diabetes/diabetes_manager.db";
    private JTextArea[] localTextAreas;
    private JComboBox<Integer> sectionSelector;

    public EMR_FU_diabetesEdit() {
        super("Diabetes Follow-up Manager");
        initializeUI();
        initializeDatabase();
        loadContent();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(5, 5));
        
        // Center panel for text areas
        JPanel centerPanel = new JPanel(new GridLayout(NUM_TEXT_AREAS, 1, 5, 5));
        localTextAreas = new JTextArea[NUM_TEXT_AREAS];
        
        for (int i = 0; i < NUM_TEXT_AREAS; i++) {
            localTextAreas[i] = new JTextArea(5, 40);
            localTextAreas[i].setLineWrap(true);
            localTextAreas[i].setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(localTextAreas[i]);
            centerPanel.add(scrollPane);
        }
        
        // South panel for buttons
        JPanel southPanel = new JPanel(new FlowLayout());
        sectionSelector = new JComboBox<>();
        for (int i = 0; i < NUM_TEXT_AREAS; i++) {
            sectionSelector.addItem(i);
        }
        
        JButton editButton = new JButton("EDIT");
        JButton findButton = new JButton("FIND");
        JButton deleteButton = new JButton("DELETE");
        JButton saveButton = new JButton("SAVE");
        JButton exitButton = new JButton("EXIT");

        southPanel.add(new JLabel("Section:"));
        southPanel.add(sectionSelector);
        southPanel.add(editButton);
        southPanel.add(findButton);
        southPanel.add(deleteButton);
        southPanel.add(saveButton);
        southPanel.add(exitButton);

        // Button actions
        editButton.addActionListener(e -> enableEditing());
        findButton.addActionListener(e -> findContent());
        deleteButton.addActionListener(e -> deleteContent());
        saveButton.addActionListener(e -> saveContent());
        exitButton.addActionListener(e -> dispose());

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
             Statement stmt = conn.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS diabetes_records " +
                                  "(id INTEGER PRIMARY KEY, section_text TEXT, section_name TEXT)";
            stmt.execute(createTableSQL);

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM diabetes_records");
            if (rs.next() && rs.getInt(1) == 0) {
                String insertSQL = "INSERT INTO diabetes_records (id, section_text, section_name) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    String[] defaultNames = {"History", "Symptoms", "Examination", "Vitals", 
                                          "Lab Results", "Diagnosis", "Treatment", "Medications", 
                                          "Follow-up Plan", "Recommendations"};
                    for (int i = 0; i < NUM_TEXT_AREAS; i++) {
                        pstmt.setInt(1, i);
                        pstmt.setString(2, "Enter " + defaultNames[i] + " here...");
                        pstmt.setString(3, defaultNames[i]);
                        pstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }

    private void loadContent() {
        for (int i = 0; i < NUM_TEXT_AREAS; i++) {
            new ContentLoader(i).execute();
        }
    }

    private void enableEditing() {
        int index = (Integer) sectionSelector.getSelectedItem();
        localTextAreas[index].setEditable(true);
    }

    private void findContent() {
        int index = (Integer) sectionSelector.getSelectedItem();
        new ContentLoader(index).execute();
    }

    private void deleteContent() {
        int index = (Integer) sectionSelector.getSelectedItem();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
             PreparedStatement pstmt = conn.prepareStatement(
                 "UPDATE diabetes_records SET section_text = '' WHERE id = ?")) {
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
            localTextAreas[index].setText("");
            JOptionPane.showMessageDialog(this, "Section " + index + " cleared");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting content: " + ex.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveContent() {
        int index = (Integer) sectionSelector.getSelectedItem();
        String content = localTextAreas[index].getText();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
             PreparedStatement pstmt = conn.prepareStatement(
                 "UPDATE diabetes_records SET section_text = ? WHERE id = ?")) {
            pstmt.setString(1, content);
            pstmt.setInt(2, index);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Section " + index + " saved successfully");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving content: " + ex.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ContentLoader extends SwingWorker<String, Void> {
        private final int index;

        public ContentLoader(int index) {
            this.index = index;
        }

        @Override
        protected String doInBackground() throws Exception {
            String content = "";
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
                 PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT section_text FROM diabetes_records WHERE id = ?")) {
                pstmt.setInt(1, index);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    content = rs.getString("section_text");
                }
            } catch (SQLException ex) {
                System.err.println("Database read failed for index " + index + ": " + ex.getMessage());
            }
            return content;
        }

        @Override
        protected void done() {
            try {
                String text = get();
                if (localTextAreas[index] != null) {
                    localTextAreas[index].setText(text);
                    localTextAreas[index].setEditable(false); // Default to non-editable
                }
            } catch (Exception ex) {
                System.err.println("Failed to display content for index " + index + ": " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EMR_FU_diabetesEdit();
        });
    }
}