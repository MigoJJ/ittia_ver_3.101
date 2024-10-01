package je.panse.doro.fourgate.influenza;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InjectionCheck extends JFrame {
    private final String[] vaccines;
    private final String[] controls;
    private final String[] columnNames;
    private final Object[][] tableData;
    private JTable reactionTable;
    private JTextArea outputTextArea;

    public InjectionCheck() {
        super("Injection Check");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize data
        vaccines = new String[]{
                "Sanofi's VaxigripTetra® Vaccine(4가) [유독]",
                "Kovax Influ 4ga PF® vaccine [nip]",
                "Tdap (Tetanus, Diphtheria, Pertussis)",
                "Td (Tetanus, Diphtheria)",
                "Shingles Vaccine (Shingrix) #1/2",
                "HAV vaccination #1/2",
                "HBV vaccination #1/3",
                "Prevena 13 (pneumococcal vaccine (PCV13))",
                "Etc."
        };
        controls = new String[]{"All denied", "Delay", "Clear","Save", "Quit"};
        columnNames = new String[]{"Reaction Type", "Reaction"};
        tableData = new Object[][]{
                {"Injection Site", "Pain"},
                {"Injection Site", "Redness"},
                {"Injection Site", "Tenderness or soreness"},
                {"Injection Site", "Swelling"},
                {"Injection Site", "Hardened lump"},
                {"Systemic", "Fever or chilling"},
                {"Systemic", "Headache"},
                {"Systemic", "Muscle Pain"},
                {"Systemic", "Fatigue or tiredness"},
                {"Systemic", "Joint pain"},
                {"Systemic", "Swollen lymph nodes"},
                {"Systemic", "Nausea"}
        };

        // Create UI components
        createVaccineButtonsPanel();
        createControlButtonsPanel();
        createJTableAndTextArea();

        // Set frame properties
        setSize(800, 500);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    // EAST Panel: Vaccine Buttons
    private void createVaccineButtonsPanel() {
        JPanel eastPanel = new JPanel(new GridLayout(vaccines.length, 1, 5, 5));
        for (String vaccine : vaccines) {
            JButton button = new JButton(vaccine);
            // Add ActionListener to append button text to TextArea
            button.addActionListener(e -> outputTextArea.append(vaccine + "\n"));
            eastPanel.add(button);
        }
        add(eastPanel, BorderLayout.EAST);
    }

    // SOUTH Panel: Control Buttons
    private void createControlButtonsPanel() {
        JPanel southPanel = new JPanel(new FlowLayout());
        for (String control : controls) {
            JButton button = new JButton(control);
            button.addActionListener(e -> {
                String command = e.getActionCommand();
                switch (command) {
                    case "All denied":
                        outputTextArea.setText("All injections denied.");
                        break;
                    case "Delay":
                        outputTextArea.setText("Injections delayed.");
                        break;
                    case "Clear":
                        outputTextArea.setText("");
                        break;
                    case "Save":
                        saveSelections();
                        break;
                    case "Quit":
                        System.exit(0);
                        break;
                }
            });
            southPanel.add(button);
        }
        add(southPanel, BorderLayout.SOUTH);
    }

    // CENTER Panel: JTable and Output TextArea
    private void createJTableAndTextArea() {
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Creating the JTable
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the table cells non-editable
                return false;
            }
        };
        reactionTable = new JTable(tableModel);
        reactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Adding MouseListener to JTable to handle clicks
        reactionTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = reactionTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the reaction value from the clicked row
                    String reaction = (String) reactionTable.getValueAt(selectedRow, 1);
                    // Display the reaction in the output text area
                    outputTextArea.append(reaction +"\n");
                }
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(reactionTable);

        // Creating the Output TextArea
        outputTextArea = new JTextArea(8, 20);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        JScrollPane textAreaScrollPane = new JScrollPane(outputTextArea);

        // Adding components to center panel
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        centerPanel.add(textAreaScrollPane, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    // Save the selected row's reaction into the outputTextArea
    private void saveSelections() {
        int selectedRow = reactionTable.getSelectedRow();
        if (selectedRow != -1) {
            String reactionType = (String) reactionTable.getValueAt(selectedRow, 0);
            String reaction = (String) reactionTable.getValueAt(selectedRow, 1);
            // Append the new selection to the outputTextArea with a new line
            outputTextArea.append("Saved Selection: " + reactionType + ": " + reaction + "\n");
        } else {
            outputTextArea.append("No selection made.\n");
        }
    }


    // Main method to launch the UI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(InjectionCheck::new);
    }
}
