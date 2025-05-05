package je.panse.doro.fourgate.n_laboratorytest;

import javax.swing.*;		
import je.panse.doro.GDSEMR_frame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class n_Laboratoytest extends JFrame {

    public n_Laboratoytest() {
        setTitle("Thyroid Medication Management");
        setSize(850, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the frame on the monitor

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

        // Main panel with grid layout 3x2
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2));

        mainPanel.add(createFrameWithTable("TFT", new String[]{
                "T3, free-T4, TSH ",
                "T3, free-T4, TSH + Auto Abs'",
                "T3, free-T4, TSH + Auto Abs' + TUS",
                "T3, free-T4, TSH + Auto Abs' + TUS + thyroglobulin",
        }));

        mainPanel.add(createFrameWithTable("Diabetes Mellitus", new String[]{
                "FBS, HbA1c", 
                "FBS, HbA1c + A/C, eGFR",
                "FBS, HbA1c, Insulin + A/C + Cystatin-C + eGFR = DM Lab",
                "DM Lab + CUS + EKG",
        }));

        mainPanel.add(createFrameWithTable("Hypercholesterolemia", new String[]{
                "Lipid Battery",
                "Lipid Battery + ApoB",
        }));

        mainPanel.add(createFrameWithTable("Hypertension", new String[]{
                "EKG + CUS",
                "EKG + CUS + Na⁺ K⁺ Cl⁻ [Ca²⁺]",
        }));

        mainPanel.add(createFrameWithTable("Etc", new String[]{
       }));
        
        mainPanel.add(createFrameWithTable("Followup", new String[]{
        	    "[ → ] advised the patient to continue with current medication\n",
	           "[ ↘ ] decreased the dose of current medication\n",
	           "[ ↗ ] increased the dose of current medication\n",
	           "[ ⭯ ] changed the dose of current medication\n",
	           " |→   Starting new medication\n",
	           "  →|  discontinue current medication\n",
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
                dispose();
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
            rowData[i][0] = "   " + data[i]; // Add three spaces as a prefix
        }

        JTable table = new JTable(rowData, columnNames);
        table.setRowHeight(23); // Set row height to 23
        table.setFont(new Font("Consolas", Font.PLAIN, 12)); // Set font to Consolas
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    String cellValue = (String) target.getValueAt(row, column);
                    System.out.println("Current meds  :cd \n ..." + cellValue);
                    
                    try {
                        GDSEMR_frame.setTextAreaText(8, "\n...Current meds [ :cd ]\n ..." + cellValue);
                    } catch (Exception ex) {
                        System.out.println("Error updating text area: " + ex.getMessage());
                    }
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
                new n_Laboratoytest().setVisible(true);
            }
        });
    }
}
