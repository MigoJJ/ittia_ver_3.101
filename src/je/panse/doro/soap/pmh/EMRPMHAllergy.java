package je.panse.doro.soap.pmh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import je.panse.doro.GDSEMR_frame;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class EMRPMHAllergy extends JFrame {
    private static JTable table;
    private static DefaultTableModel tableModel;
    private static JTextArea textArea;
    private static JTable eastTable;  // New Table for East Panel
    private static DefaultTableModel eastTableModel;
    private static EMRPMHAllergy instance;

    public EMRPMHAllergy() {
        initializeFrame();
        createNorthPanel();
        createTable();
        createEastPanel();      // New East Panel
        createButtonPanel();
        setVisible(true);
    }

    /**
     * Initialize Main Frame
     */
    private void initializeFrame() {
        setTitle("Allergy Data Input");
        setSize(850, 715);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    /**
     * Create North Panel with Text Area
     */
    private void createNorthPanel() {
        textArea = new JTextArea(10, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.NORTH);
    }

    /**
     * Create Main Table
     */
    private void createTable() {
        String[] columnNames = {"Category", "Selected", "Symptom"};
        Object[][] data = createTableData();

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 1 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Only checkbox column is editable
            }
        };

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 1) {
                    int row = e.getFirstRow();
                    boolean isSelected = (boolean) tableModel.getValueAt(row, 1);
                    updateTextArea(row, isSelected);
                }
            }
        });

        table = new JTable(tableModel);
        configureTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Create East Panel with Mini-Table (from row 12 "Other symptoms")
     */
    private void createEastPanel() {
        String[] columnNames = {"Aller Cause"};
        String[] otherSymptoms = {
                "Pain relievers: NSAIDs ",
                "sulfa drugs (sulfamethoxazole, trimethoprim-sulfamethoxazole).",
                "Anesthesia: anesthetic medications such as propofol, ",
                
                "Dust Mite",
                
                "Antibiotics : penicillin and its derivatives ",
                "Antibiotics : Cephalosporins",
                
                "Food : 우유 (uyu): Milk",
                "Food : 계란 (gyeran): Eggs",
                "Food : 땅콩 (ttangkong): Peanuts",
                "Food : 견과류 (gyeongwaryu): Tree nuts (e.g., 호두 - walnuts, 아몬드 - almonds, 캐슈넛 - cashews)",
                "Food : 콩 (kong): Soybeans",
                "Food : 밀 (mil): Wheat",
                "Food : 생선 (saengseon): Fish (e.g., 고등어 - mackerel, 연어 - salmon, 참치 - tuna)",
                "Food : 갑각류 (gabgagryu): Shellfish (e.g., 새우 - shrimp, 게 - crab, 가리비 - scallops)",
                "Food : 복숭아 (boksuong-a): Peach (and sometimes other stone fruits like apricots and plums due to cross-reactivity)",
                "Food : 메밀 (memil): Buckwheat"
        };

        eastTableModel = new DefaultTableModel(new Object[][]{}, columnNames);
        
        for (String symptom : otherSymptoms) {
            eastTableModel.addRow(new Object[]{symptom});
        }

        eastTable = new JTable(eastTableModel);
        eastTable.setRowHeight(22);
        eastTable.setFont(new Font("Consolas", Font.BOLD, 12));

        // Click Listener
        eastTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = eastTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedSymptom = (String) eastTableModel.getValueAt(selectedRow, 0);
                    textArea.append("*** Allergy Cause : " + selectedSymptom + "\n");
                }
            }
        });

        JScrollPane eastScrollPane = new JScrollPane(eastTable);
        eastScrollPane.setPreferredSize(new Dimension(200, 300));

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(new JLabel("Other Symptoms (Click to Add):", JLabel.CENTER), BorderLayout.NORTH);
        eastPanel.add(eastScrollPane, BorderLayout.CENTER);

        add(eastPanel, BorderLayout.EAST);
    }

    /**
     * Create Button Panel at Bottom
     */
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton allDeniedButton = new JButton("All denied");
        JButton anaphylaxisDeniedButton = new JButton("Anaphylaxis denied");
        JButton clearButton = new JButton("Clear");
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");

        allDeniedButton.addActionListener(e -> setAllSymptoms(false));
        anaphylaxisDeniedButton.addActionListener(e -> setAnaphylaxisSymptoms(false));
        saveButton.addActionListener(e -> saveSelectedSymptoms());
        clearButton.addActionListener(e -> clearSymptoms());
        quitButton.addActionListener(e -> dispose());

        buttonPanel.add(allDeniedButton);
        buttonPanel.add(anaphylaxisDeniedButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Configure Main JTable
     */
    private void configureTable() {
        table.setRowHeight(21);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
    }

    /**
     * Collect Table Data
     */
    private Object[][] createTableData() {
        return new Object[][] {
            {"Skin reactions", false, "Rash"},
            {"Skin reactions", false, "Hives (raised, itchy spots)"},
            {"Skin reactions", false, "Itching"},
            {"Swelling", false, "Swelling of the mouth, face, lip, tongue and throat"},
            {"Swelling", false, "Angioedema (tissue swelling under the skin)"},
            {"Respiratory symptoms", false, "Wheezing"},
            {"Respiratory symptoms", false, "Coughing"},
            {"Respiratory symptoms", false, "Shortness of breath or trouble breathing"},
            {"Gastrointestinal symptoms", false, "Nausea"},
            {"Gastrointestinal symptoms", false, "Vomiting"},
            {"Gastrointestinal symptoms", false, "Stomach cramps"},
            {"Other symptoms", false, "Fever"},
            {"Other symptoms", false, "Dizziness or lightheadedness"},
            {"Other symptoms", false, "Runny nose"},
            {"Other symptoms", false, "Itchy, watery eyes"},
            {"Anaphylaxis", false, "Difficulty swallowing"},
            {"Anaphylaxis", false, "Tightening of the airways"},
            {"Anaphylaxis", false, "Drop in blood pressure"},
            {"Anaphylaxis", false, "Weak, fast pulse"},
            {"Anaphylaxis", false, "Loss of consciousness"}
        };
    }

    /**
     * Append or Remove Symptoms from TextArea
     */
    private void updateTextArea(int row, boolean isSelected) {
        SwingUtilities.invokeLater(() -> {
            String symptom = (String) table.getValueAt(row, 2);
            String category = (String) table.getValueAt(row, 0);
            String lineToAdd = "* " + category + ": " + symptom + "\n";

            if (isSelected) {
                textArea.append(lineToAdd);
            } else {
                String content = textArea.getText();
                textArea.setText(content.replace(lineToAdd, ""));
            }
        });
    }

    /**
     * Mark all Non-Anaphylaxis Symptoms as Denied
     */
    public static void setAllSymptoms(boolean value) {
        textArea.append("\n[ All Denied Non-Anaphylaxis Symptoms ]\n");
        StringBuilder generalSymptoms = new StringBuilder();

        for (int i = 0; i < table.getRowCount(); i++) {
            if (!"Anaphylaxis".equals(table.getValueAt(i, 0))) {
                table.setValueAt(value, i, 1);
                String symptom = (String) table.getValueAt(i, 2);
                generalSymptoms.append("- ").append(symptom).append("\n");
            }
        }
        textArea.append(generalSymptoms.toString());
        GDSEMR_frame.setTextAreaText(7, "\n### All Non-Anaphylaxis Allergies Denied ###\n" + textArea.getText());
    }

    /**
     * Mark all Anaphylaxis Symptoms as Denied
     */
    private void setAnaphylaxisSymptoms(boolean value) {
        textArea.append("\n[ All Denied Anaphylaxis Symptoms ]\n");
        StringBuilder anaphylaxisSymptoms = new StringBuilder();

        for (int i = 0; i < table.getRowCount(); i++) {
            if ("Anaphylaxis".equals(table.getValueAt(i, 0))) {
                table.setValueAt(value, i, 1);
                String symptom = (String) table.getValueAt(i, 2);
                anaphylaxisSymptoms.append("- ").append(symptom).append("\n");
            }
        }
        textArea.append(anaphylaxisSymptoms.toString());
        GDSEMR_frame.setTextAreaText(7, "\n### Anaphylaxis Allergies Denied ###\n" + textArea.getText());
    }

    /**
     * Clear All Symptoms from TextArea
     */
    public static void clearSymptoms() {
        textArea.setText("");
    }

    /**
     * Save All Selected Symptoms
     */
    private void saveSelectedSymptoms() {
        StringBuilder selectedSymptoms = new StringBuilder("Selected Symptoms:\n");

        for (int i = 0; i < table.getRowCount(); i++) {
            if ((boolean) table.getValueAt(i, 1)) {
                selectedSymptoms.append("- ").append(table.getValueAt(i, 2)).append("\n");
            }
        }

        if (!textArea.getText().isEmpty()) {
            selectedSymptoms.append("\nAdditional Notes:\n").append(textArea.getText());
        }

        GDSEMR_frame.setTextAreaText(7, "\n### Final Allergic Reactions ###\n" + selectedSymptoms);
        JOptionPane.showMessageDialog(this, selectedSymptoms.toString(), "Saved Symptoms", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Singleton Pattern for Instance Management
     */
    public static EMRPMHAllergy getInstance() {
        if (instance == null) {
            instance = new EMRPMHAllergy();
        }
        return instance;
    }

    /**
     * Application Entry Point
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EMRPMHAllergy::new);
    }
}
