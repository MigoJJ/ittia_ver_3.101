package je.panse.doro.samsara.EMR_CCPIPMH;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class EMRPMH3 extends JFrame {

    private JTextArea textArea;
    private List<JCheckBox> checkboxes; // Store checkboxes

    public EMRPMH3() {
        initializeFrame();
        createNorthPanel();
        createCenterPanel();
        createSouthPanel();
    }

    private void initializeFrame() {
        // Set frame properties
        setTitle("GDSEMR PMH");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the list of checkboxes
        checkboxes = new ArrayList<>();
    }

    private void createNorthPanel() {
    	// Create a panel for the NORTH
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 1)); // 2 rows, 1 column

        // Create two panels for checkboxes in the NORTH
        JPanel northPanel1 = new JPanel();
        JPanel northPanel2 = new JPanel();

        // Add north panels to the NORTH panel
        northPanel.add(northPanel1);
        northPanel.add(northPanel2);

        // Create checkboxes panel for the NORTH (northPanel1)
        northPanel1.setLayout(new GridLayout(0, 3)); // 3 columns

        String[] checkboxLabels = {
                "Diabetes Mellitus", "HTN", "Dyslipidemia",
                "Cancer", "Operation", "Thyroid Disease",
                "Asthma", "Tuberculosis", "Pneumonia",
                "Chronic/Acute Hepatitis", "GERD", "Gout",
                "Arthritis", "Hearing Loss", "CVA",
                "Depression", "Cognitive Disorder", "...",
                "Angina Pectoris", "AMI", "Arrhythmia",
                "Allergy", "...", "Food", "Injection", "Medication"
        };

        for (String label : checkboxLabels) {
            northPanel1.add(new JCheckBox(label));
        }

        // Add the NORTH panel to the frame
        add(northPanel, BorderLayout.NORTH);
    }

    private void createCenterPanel() {
        // Create checkboxes panel for the CENTER
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 3)); // 3 columns

        String[] checkboxLabels = {
            // Add your checkbox labels here
        };

        for (String label : checkboxLabels) {
            JCheckBox checkBox = new JCheckBox(label);
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle checkbox action here
                }
            });
            checkboxes.add(checkBox); // Add checkbox to the list
            centerPanel.add(checkBox);
        }

        // Create a text area for output
        textArea = new JTextArea(20, 40);
        textArea.setEditable(false); // Make it non-editable

        // Create a scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the scroll pane to the CENTER
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createSouthPanel() {
        // Create buttons panel for the SOUTH
        JPanel southPanel = new JPanel();

        // Create buttons
        JButton copyButton = new JButton("COPY");
        JButton clearButton = new JButton("CLEAR");
        JButton saveButton = new JButton("SAVE");
        JButton quitButton = new JButton("QUIT");

        // Add action listeners to the buttons
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add copy functionality here
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(""); // Clear the text area
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add save functionality here
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Quit the application
            }
        });

        // Add buttons to the SOUTH panel
        southPanel.add(copyButton);
        southPanel.add(clearButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);

        // Add the SOUTH panel to the frame
        add(southPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                EMRPMH3 frame = new EMRPMH3();
                frame.setVisible(true);
            }
        });
    }
}
