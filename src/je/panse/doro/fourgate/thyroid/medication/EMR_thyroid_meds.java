package je.panse.doro.fourgate.thyroid.medication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EMR_thyroid_meds extends JFrame {

    public EMR_thyroid_meds() {
        setTitle("Thyroid Medication Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // South panel with buttons
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());

        JButton editButton = new JButton("Edit");
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");

        southPanel.add(editButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);

        add(southPanel, BorderLayout.SOUTH);

        // Main panel with grid layout 2x3
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 3));

        mainPanel.add(createFrameWithTable("Synthyroid", new String[]{
                "Synthyroid [ 25 ] ug 1 tab p.o. q.d.",
                "Synthyroid [ 37.5 ] ug 1 tab p.o. q.d.",
                "Synthyroid [ 50 ] ug 1 tab p.o. q.d.",
                "Synthyroid [ 75 ] ug 1 tab p.o. q.d.",
                "Synthyroid [ 100 ] ug 1 tab p.o. q.d.",
                "Synthyroid [ 112 ] ug 1 tab p.o. q.d.",
                "Synthyroid [ 150 ] ug 1 tab p.o. q.d."
        }));

        mainPanel.add(createFrameWithTable("Synthyroxine", new String[]{
                "Synthyroxine [ 25 ] ug 1 tab p.o. q.d.",
                "Synthyroxine [ 37.5 ] ug 1 tab p.o. q.d.",
                "Synthyroxine [ 50 ] ug 1 tab p.o. q.d.",
                "Synthyroxine [ 75 ] ug 1 tab p.o. q.d.",
                "Synthyroxine [ 100 ] ug 1 tab p.o. q.d.",
                "Synthyroxine [ 150 ] ug 1 tab p.o. q.d."
        }));

        mainPanel.add(createFrameWithTable("Methimazole", new String[]{
                "Methimazole [ 5 ] mg 1 tab p.o. q.d.",
                "Methimazole [ 5 ] mg 1 tab p.o. b.i.d.",
                "Methimazole [ 5 ] mg 2 tab p.o. q.d.",
                "Methimazole [ 5 ] mg 2 tab p.o. b.i.d.",
                "Methimazole [ 5 ] mg 2 tab p.o. t.i.d.",
                "Methimazole [ 5 ] mg 2 tab p.o. t.i.d.\n   Indenol [ 10 ] mg 1 tab p.o. t.i.d.",
                "Methimazole [ 5 ] mg 2 tab p.o. b.i.d.\n   Indenol [ 10 ] mg 1 tab p.o. b.i.d.",
                "Methimazole [ 5 ] mg 2 tab p.o. q.d.\n   Indenol [ 10 ] mg 1 tab p.o. q.d."
        }));

        mainPanel.add(createFrameWithTable("Antiroid", new String[]{
                "Antiroid [ 50 ] mg 1 tab p.o. q.d.",
                "Antiroid [ 50 ] mg 1 tab p.o. b.i.d.",
                "Antiroid [ 50 ] mg 2 tab p.o. q.d.",
                "Antiroid [ 50 ] mg 2 tab p.o. b.i.d.",
                "Antiroid [ 50 ] mg 2 tab p.o. t.i.d.",
                "Antiroid [ 50 ] mg 2 tab p.o. t.i.d.\n   Indenol [ 10 ] mg 1 tab p.o. t.i.d.",
                "Antiroid [ 50 ] mg 2 tab p.o. b.i.d.\n   Indenol [ 10 ] mg 1 tab p.o. b.i.d.",
                "Antiroid [ 50 ] mg 2 tab p.o. q.d.\n   Indenol [ 10 ] mg 1 tab p.o. q.d."
        }));

        mainPanel.add(createFrameWithTable("Camen", new String[]{
                "Camen [ 5 ] mg 1 tab p.o. q.d.",
                "Camen [ 5 ] mg 1 tab p.o. b.i.d.",
                "Camen [ 5 ] mg 2 tab p.o. q.d.",
                "Camen [ 5 ] mg 2 tab p.o. b.i.d.",
                "Camen [ 5 ] mg 2 tab p.o. t.i.d.",
                "Camen [ 10 ] mg 1 tab p.o. q.d.",
                "Camen [ 10 ] mg 1 tab p.o. b.i.d.",
                "Camen [ 10 ] mg 2 tab p.o. q.d.",
                "Camen [ 10 ] mg 2 tab p.o. b.i.d.",
                "Camen [ 10 ] mg 2 tab p.o. t.i.d.",
                "Camen [ 20 ] mg 1 tab p.o. q.d."
        }));

        add(mainPanel, BorderLayout.CENTER);

        // Add action listeners for buttons
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement edit functionality
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement save functionality
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private JScrollPane createFrameWithTable(String title, String[] data) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setLayout(new BorderLayout());

        String[] columnNames = {"Medication"};
        String[][] rowData = new String[data.length][1];
        for (int i = 0; i < data.length; i++) {
            rowData[i][0] = data[i];
        }

        JTable table = new JTable(rowData, columnNames);
        table.setRowHeight(20); // Set row height to 15
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    String cellValue = (String) target.getValueAt(row, column);
                    System.out.println("Selected: " + cellValue);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return scrollPane;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EMR_thyroid_meds().setVisible(true);
            }
        });
    }
}
