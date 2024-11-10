package je.panse.doro.soap.pmh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import je.panse.doro.GDSEMR_frame;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.time.LocalDate;

public class EMRPMHAllergy extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextArea textArea;

    public EMRPMHAllergy() {
        initializeFrame();
        createNorthPanel();
        createTable();
        createButtonPanel();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Allergy Data Input");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void createNorthPanel() {
        textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.NORTH);
    }

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
                return column == 1;
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

    private void configureTable() {
        table.setRowHeight(21);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);
    }

    private void updateTextArea(int row, boolean isSelected) {
        SwingUtilities.invokeLater(() -> {
            String symptom = (String) table.getValueAt(row, 2);
            String category = (String) table.getValueAt(row, 0);
            String lineToAdd = category + ": " + symptom + "  [+] \n";

            if (isSelected) {
                textArea.append(lineToAdd);
            } else {
                String content = textArea.getText();
                textArea.setText(content.replace(lineToAdd, ""));
            }
            
            textArea.revalidate();
            textArea.repaint();
        });
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton allDeniedButton = new JButton("All denied");
        JButton anaphylaxisDeniedButton = new JButton("Anaphylaxis denied");
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");

        allDeniedButton.addActionListener(e -> setAllSymptoms(false));
        anaphylaxisDeniedButton.addActionListener(e -> setAnaphylaxisSymptoms(false));
        saveButton.addActionListener(e -> saveSelectedSymptoms());
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(allDeniedButton);
        buttonPanel.add(anaphylaxisDeniedButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setAllSymptoms(boolean value) {
        for (int i = 0; i < table.getRowCount(); i++) {
            table.setValueAt(value, i, 1);
        }
        
        // Clear the text area
        textArea.setText("");
        
        // Get the current date
        String currentDate = LocalDate.now().toString();
        
        // Prepare the no allergies text
        String noAllergiesText = String.format("▣ Allergy\n" +
                                               "During the medical check-up, the patient had no known allergies\n" +
                                               "to food, injections, and medications as of: %s", currentDate);
        
        // Set the text in the North Panel's JTextArea
        textArea.setText(noAllergiesText);
        
        // Update the GDSEMR_frame
        GDSEMR_frame.setTextAreaText(1, "\n###  Allergic Reactions  ###\n" + noAllergiesText);
    }

    private void setAnaphylaxisSymptoms(boolean value) {
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 0).equals("Anaphylaxis")) {
                table.setValueAt(value, i, 1);
            }
        }
    }

    private void saveSelectedSymptoms() {
        StringBuilder selectedSymptoms = new StringBuilder("Selected Symptoms:\n");
        for (int i = 0; i < table.getRowCount(); i++) {
            if ((boolean) table.getValueAt(i, 1)) {
                selectedSymptoms.append(table.getValueAt(i, 2)).append("\n");
            }
        }
        String textAreaContent = textArea.getText();
        if (!textAreaContent.isEmpty()) {
            selectedSymptoms.append("\nAdditional Notes:\n").append(textAreaContent);
            
            GDSEMR_frame.setTextAreaText(1, "\n###  Allergic Reactions  ###\n" + textAreaContent);
        }
        JOptionPane.showMessageDialog(this, selectedSymptoms.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EMRPMHAllergy::new);
    }
}
