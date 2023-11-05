package je.panse.doro.fourgate.emrgdsfujtable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

public class EMRGDS_FU_Jtable {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JButton saveButton, loadButton;

    public EMRGDS_FU_Jtable() {
        initializeFrame();
        initializeTable();
        initializeButtons();
        layoutComponents();
        frame.setVisible(true);
    }

    private void initializeFrame() {
        frame = new JFrame("EMRGDS_FU_Jtable");
        frame.setSize(1600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeTable() {
        model = new DefaultTableModel(10, 10);
        table = new JTable(model);
        table.setDefaultEditor(Object.class, new MultiLineCellEditor());
        setRowAndColumnDimensions();
    }

    private void setRowAndColumnDimensions() {
        // Example: Set heights for the first three rows
        table.setRowHeight(0, 150);  
        table.setRowHeight(1, 30);
        table.setRowHeight(2, 40);

        // Example: Set widths for the first three columns
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(75);
    }

    private void initializeButtons() {
        saveButton = new JButton("Save Data");
        saveButton.addActionListener(e -> saveData());
        loadButton = new JButton("Load Data");
        loadButton.addActionListener(e -> loadData());
    }

    private void layoutComponents() {
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.add(saveButton);
        panel.add(loadButton);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
    }

    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tabledata.ser"))) {
            out.writeObject(model.getDataVector());
            JOptionPane.showMessageDialog(frame, "Data Saved Successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("tabledata.ser"))) {
            Vector<? extends Vector> data = (Vector<? extends Vector>) in.readObject();
            Vector<String> columnNames = new Vector<>();
            for (int i = 0; i < model.getColumnCount(); i++) {
                columnNames.add(model.getColumnName(i));
            }
            model.setDataVector(data, columnNames);
            JOptionPane.showMessageDialog(frame, "Data Loaded Successfully!");

            // Reapply the row and column dimensions after loading the data
            setRowAndColumnDimensions();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EMRGDS_FU_Jtable());
    }
}

// MultiLineCellEditor class moved outside the main class
class MultiLineCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JScrollPane scrollPane;
    private JTextArea textArea;

    public MultiLineCellEditor() {
        textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        scrollPane = new JScrollPane(textArea);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textArea.setText((value != null) ? value.toString() : "");
        return scrollPane;
    }

    @Override
    public Object getCellEditorValue() {
        return textArea.getText();
    }
}
