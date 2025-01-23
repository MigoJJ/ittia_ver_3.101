package je.panse.doro.samsara.EMR_PE;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClickableJTableExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Clickable JTable Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 1000);
            frame.setLayout(new BorderLayout());

            Object[][] data = initializeData();
            ClickableTableModel tableModel = new ClickableTableModel(data);
            JTable table = new JTable(tableModel);

             // EAST Panel for text areas and buttons
            JPanel eastPanel = new JPanel();
            eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
            eastPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Text Area
            JTextArea notesArea = new JTextArea(20, 30);
            notesArea.setBorder(BorderFactory.createTitledBorder("Notes"));
            JScrollPane notesScrollPane = new JScrollPane(notesArea);

            eastPanel.add(notesScrollPane);

            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton clearButton = new JButton("Clear");
            JButton quitButton = new JButton("Quit");
            JButton saveButton = new JButton("Save");
            JButton dateButton = new JButton("Date");

            clearButton.addActionListener(e -> notesArea.setText(""));
            quitButton.addActionListener(e -> System.exit(0));

            // Save button action
            saveButton.addActionListener(e -> {
                // For demo purposes, just print the notes to the console
                System.out.println("Saved notes:\n" + notesArea.getText());
                JOptionPane.showMessageDialog(frame,"Notes Saved!", "Save Notes", JOptionPane.INFORMATION_MESSAGE );

            });
             // Date button action
            dateButton.addActionListener(e -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(new Date());
                notesArea.append("Date: " + formattedDate + "\n");
            });

            buttonPanel.add(clearButton);
            buttonPanel.add(saveButton);
            buttonPanel.add(dateButton);
            buttonPanel.add(quitButton);

            eastPanel.add(buttonPanel);

            frame.add(eastPanel, BorderLayout.EAST);

             // Add checkbox renderer and editor for "Description" column with listener for notesArea
            table.getColumnModel().getColumn(1).setCellRenderer(new CheckboxListRenderer());
            table.getColumnModel().getColumn(1).setCellEditor(new CheckboxListEditor(notesArea));

            table.setRowHeight(120); // Adjust row height for better readability
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }

   private static Object[][] initializeData() {
        return new Object[][]{
                {"Right Upper Quadrant (RUQ) Pain", new Object[][]{
                        {false, "Gallbladder Disease (Cholecystitis, cholelithiasis, biliary colic)"},
                        {false, "Liver Disease (Hepatitis, liver abscess, liver tumors, hepatic congestion)"},
                        {false, "Duodenal Ulcer"},
                        {false, "Right Kidney Issues (Pyelonephritis, nephrolithiasis)"},
                        {false, "Pneumonia (Right Lower Lobe)"}
                }},
                {"Left Upper Quadrant (LUQ) Pain", new Object[][]{
                        {false, "Gastric Ulcer"},
                        {false, "Gastritis"},
                        {false, "Pancreatitis"},
                        {false, "Splenic Issues (Splenomegaly, splenic infarct, splenic rupture)"},
                        {false, "Left Kidney Issues (Pyelonephritis, nephrolithiasis)"},
                        {false, "Pneumonia (Left Lower Lobe)"}
                }},
                {"Right Lower Quadrant (RLQ) Pain", new Object[][]{
                        {false, "Appendicitis"},
                        {false, "Ovarian Issues (Ovarian cyst, ectopic pregnancy, ovarian torsion)"},
                        {false, "Right Kidney Issues (Pyelonephritis, nephrolithiasis)"},
                        {false, "Inflammatory Bowel Disease (Crohn's disease)"},
                        {false, "Hernia (Inguinal)"},
                        {false, "Ectopic Pregnancy"}
                }},
                {"Left Lower Quadrant (LLQ) Pain", new Object[][]{
                        {false, "Diverticulitis"},
                        {false, "Ovarian Issues (Ovarian cyst, ectopic pregnancy, ovarian torsion)"},
                        {false, "Left Kidney Issues (Pyelonephritis, nephrolithiasis)"},
                        {false, "Inflammatory Bowel Disease (Ulcerative colitis)"},
                        {false, "Hernia (Inguinal)"},
                        {false, "Ectopic Pregnancy"}
                }}
        };
    }

    // Custom Table Model
    static class ClickableTableModel extends AbstractTableModel {
        private final String[] columnNames = {"Select", "Description"};
        private final List<Object[]> rows = new ArrayList<>();

        public ClickableTableModel(Object[][] data) {
            for (Object[] category : data) {
                String categoryName = (String) category[0];
                Object[][] conditions = (Object[][]) category[1];
                rows.add(new Object[]{categoryName, conditions});
            }
        }

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return rows.get(rowIndex)[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1; // Only "Description" column is editable
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

         @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? String.class : Object.class;
        }
    }

   // Custom Checkbox List Renderer
    static class CheckboxListRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Object[][]) {
                JPanel panel = new JPanel(new GridLayout(0, 1));
                Object[][] items = (Object[][]) value;

                for (Object[] item : items) {
                    JCheckBox checkBox = new JCheckBox((String) item[1], (Boolean) item[0]);
                    checkBox.setEnabled(false); // Disabled to ensure it's just a renderer
                    panel.add(checkBox);
                }

                return panel;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Custom Checkbox List Editor
    static class CheckboxListEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel editorPanel;
        private Object[][] currentData;
        private final JTextArea notesArea;

        public CheckboxListEditor(JTextArea notesArea) {
            this.notesArea = notesArea;
        }

         @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value instanceof Object[][]) {
                currentData = (Object[][]) value;
                editorPanel = new JPanel(new GridLayout(0, 1));

                for (Object[] item : currentData) {
                    JCheckBox checkBox = new JCheckBox((String) item[1], (Boolean) item[0]);
                    checkBox.addActionListener(e -> {
                        item[0] = checkBox.isSelected();
                        if ((Boolean) item[0]) {
                            notesArea.append("[R/O] : " + item[1] + "\n");
                        }
                    });
                    editorPanel.add(checkBox);
                }
                return editorPanel;
            }
            return null;
        }

         @Override
        public Object getCellEditorValue() {
            return currentData;
        }
    }
}