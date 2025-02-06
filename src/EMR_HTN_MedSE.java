import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class EMR_HTN_MedSE extends JFrame {
    private JTable sideEffectsTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public EMR_HTN_MedSE() {
        setTitle("Blood Pressure Medication Side Effects");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLayout(new BorderLayout());

        // Table Columns
        String[] columns = {"Select", "Side Effect", "Medication Type"};

        // Table Data
        Object[][] data = {
                {false, "Dizziness/Lightheadedness", "Common"},
                {false, "Urination Changes", "Common"},
                {false, "Fatigue/Weakness", "Common"},
                {false, "Gastrointestinal Issues", "Common"},
                {false, "Dry Cough", "ACE Inhibitors"},
                {false, "Increased Heart Rate", "Diuretics"},
                {false, "Low Potassium", "Diuretics"},
                {false, "Cold Hands/Feet", "Beta-Blockers"},
                {false, "Sleep Disturbances", "Beta-Blockers"},
                {false, "Flushing", "Calcium Channel Blockers"},
                {false, "Ankle Swelling", "Calcium Channel Blockers"},
        };

        // Initialize Table Model
        tableModel = new DefaultTableModel(data, columns) {
            @Override
            public Class<?> getColumnClass(int column) {
                return (column == 0) ? Boolean.class : String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only "Select" column is editable
            }
        };

        // Create JTable
        sideEffectsTable = new JTable(tableModel);
        sideEffectsTable.setRowHeight(25);

        // Enable sorting
        sorter = new TableRowSorter<>(tableModel);
        sideEffectsTable.setRowSorter(sorter);

        // Scroll Pane for better visibility
        JScrollPane scrollPane = new JScrollPane(sideEffectsTable);

        // Submit Button
        JButton submitButton = new JButton("Submit Selected");
        submitButton.addActionListener(e -> showSelectedSideEffects());

        // Add Components
        add(scrollPane, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }

    // Method to Display Selected Side Effects
    private void showSelectedSideEffects() {
        StringBuilder selectedEffects = new StringBuilder("Selected Side Effects:\n");
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0)) {
                selectedEffects.append("- ").append(tableModel.getValueAt(i, 1)).append(" (").append(tableModel.getValueAt(i, 2)).append(")\n");
            }
        }
        JOptionPane.showMessageDialog(this, selectedEffects.length() > 25 ? selectedEffects.toString() : "No side effects selected.", "Selection Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EMR_HTN_MedSE().setVisible(true));
    }
}
