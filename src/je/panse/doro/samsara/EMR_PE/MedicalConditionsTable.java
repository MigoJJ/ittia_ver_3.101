package je.panse.doro.samsara.EMR_PE;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalConditionsTable extends JFrame {
    private final JTable table;
    private final JTextArea notesArea;

    public MedicalConditionsTable() {
        setTitle("Medical Conditions Checklist");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        notesArea = new JTextArea(5, 40);
        notesArea.setEditable(true);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);

        table = createTable();
        table.setRowHeight(130); // Adjust row height for readability
        table.setPreferredScrollableViewportSize(new Dimension(1000, 700)); // Preferred table size

        // Layout setup
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(table),
            new JScrollPane(notesArea)
        );
        splitPane.setDividerLocation(660);
        splitPane.setResizeWeight(0.7); // Allocate space (70% for table, 30% for notes)

        add(splitPane, BorderLayout.CENTER);

        pack(); // Compute optimal size
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize frame
        setLocationRelativeTo(null); // Center frame on screen
    }


    private JTable createTable() {
        ClickableTableModel model = new ClickableTableModel();
        JTable table = new JTable(model);
        
        // Configure table properties
        table.setRowHeight(100);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(400);
        
        // Set custom cell renderer and editor
        table.getColumnModel().getColumn(1).setCellRenderer(new CheckboxListRenderer());
        table.getColumnModel().getColumn(1).setCellEditor(new CheckboxListEditor(notesArea));

        return table;
    }

    static class MedicalCondition {
        String category;
        List<ConditionItem> items;

        MedicalCondition(String category) {
            this.category = category;
            this.items = new ArrayList<>();
        }

        static class ConditionItem {
            boolean selected;
            String description;

            ConditionItem(boolean selected, String description) {
                this.selected = selected;
                this.description = description;
            }
        }
    }

    static class ClickableTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Category", "Conditions"};
        private final List<Object[]> data;

        public ClickableTableModel() {
            data = new ArrayList<>();
            initializeData();
        }

        private void initializeData() {
            // Right Upper Quadrant Pain
            Object[][] ruqConditions = {
                {false, "Gallbladder Disease (Cholecystitis, cholelithiasis, biliary colic)"},
                {false, "Liver Disease (Hepatitis, liver abscess, liver tumors, hepatic congestion)"},
                {false, "Duodenal Ulcer"},
                {false, "Right Kidney Issues (Pyelonephritis, nephrolithiasis)"},
                {false, "Pneumonia (Right Lower Lobe)"}
            };
            data.add(new Object[]{"Right Upper Quadrant Pain", ruqConditions});

            // Left Lower Quadrant Pain
            Object[][] llqConditions = {
                    {false, "Diverticulitis"},
                    {false, "Ovarian Issues (Ovarian cyst, ectopic pregnancy, ovarian torsion)"},
                    {false, "Left Kidney Issues (Pyelonephritis, nephrolithiasis)"},
                    {false, "Inflammatory Bowel Disease (Ulcerative colitis)"},
                    {false, "Hernia (Inguinal)"},
                    {false, "Ectopic Pregnancy"}
            };
            data.add(new Object[]{"Left Lower Quadrant (LLQ) Pain", llqConditions});

            // Epigastric Pain (Upper Middle Abdomen)
            Object[][] epigastricConditions = {
                    {false, "Peptic Ulcer Disease (Gastric or duodenal ulcers)"},
                    {false, "Gastroesophageal Reflux Disease (GERD)"},
                    {false, "Gastritis"},
                    {false, "Pancreatitis"},
                    {false, "Non-Ulcer Dyspepsia"},
                    {false, "Myocardial Infarction (MI)"}
            };
            data.add(new Object[]{"Epigastric Pain (Upper Middle Abdomen)", epigastricConditions});

            // Periumbilical Pain (Around the Navel)
            Object[][] periumbilicalConditions = {
                    {false, "Early Appendicitis"},
                    {false, "Small Bowel Issues (Small bowel obstruction, gastroenteritis)"},
                    {false, "Aortic Aneurysm"}
            };
            data.add(new Object[]{"Periumbilical Pain (Around the Navel)", periumbilicalConditions});

            // Generalized Abdominal Pain (Diffuse Pain)
            Object[][] generalizedConditions = {
                    {false, "Gastroenteritis"},
                    {false, "Irritable Bowel Syndrome (IBS)"},
                    {false, "Peritonitis"},
                    {false, "Bowel Obstruction"},
                    {false, "Mesenteric Ischemia"},
                    {false, "Metabolic Disorders (Diabetic ketoacidosis (DKA))"}
            };
            data.add(new Object[]{"Generalized Abdominal Pain (Diffuse Pain)", generalizedConditions});

            // Add more categories as needed
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? String.class : Object[][].class;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;
        }

        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            data.get(rowIndex)[columnIndex] = value;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    static class CheckboxListRenderer extends DefaultTableCellRenderer {
        private final JPanel panel;

        public CheckboxListRenderer() {
            panel = new JPanel(new GridLayout(0, 1));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            panel.removeAll();
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());

            if (value instanceof Object[][]) {
                Object[][] items = (Object[][]) value;
                for (Object[] item : items) {
                    JCheckBox checkBox = new JCheckBox((String) item[1], (Boolean) item[0]);
                    checkBox.setBackground(panel.getBackground());
                    panel.add(checkBox);
                }
            }
            return panel;
        }
    }

    static class CheckboxListEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final List<JCheckBox> checkboxes;
        private final JTextArea notesArea;
        private Object[][] currentValue;

        public CheckboxListEditor(JTextArea notesArea) {
            this.panel = new JPanel(new GridLayout(0, 1));
            this.checkboxes = new ArrayList<>();
            this.notesArea = notesArea;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            panel.removeAll();
            checkboxes.clear();
            panel.setBackground(table.getSelectionBackground());

            if (value instanceof Object[][]) {
                currentValue = (Object[][]) value;
                for (Object[] item : currentValue) {
                    JCheckBox checkBox = new JCheckBox((String) item[1], (Boolean) item[0]);
                    checkBox.setBackground(panel.getBackground());
                    checkBox.addActionListener(e -> {
                        item[0] = checkBox.isSelected();
                        if (checkBox.isSelected()) {
                            notesArea.append("[R/O]: " + item[1] + "\n");
                        }
                    });
                    checkboxes.add(checkBox);
                    panel.add(checkBox);
                }
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return currentValue;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MedicalConditionsTable().setVisible(true);
        });
    }
}
