package je.panse.doro.samsara.EMR_PE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import je.panse.doro.GDSEMR_frame;

public class PhysicalExaminationAbdomen {

    public static void main(String[] args) {

        // Create the frame with a title
        JFrame frame = new JFrame("Physical Examination for Abdomen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel to hold other panels
        JPanel mainPanel = new JPanel(new GridLayout(2, 5)); // 2 rows, 5 columns

        // Create a JTextArea for output
        JTextArea outputArea = new JTextArea(10, 20);
        outputArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.getContentPane().add(scrollPane, BorderLayout.NORTH);

        // Create 10 sub-panels
        String[][] options = {
            {"Sudden Onset", "Gradual Onset", "Intermittent", "Constant"},
            {"Specific Location", "Quadrants", "Epigastric Region", "Umbilical Region",
            "Hypogastric Region (Suprapubic)", "Periumbilical", "Diffuse Pain",
            "Migration of Pain", "Radiation"},
            {"<1 Hour", "1-6 Hours", "6-24 Hours", "1-3 Days", ">3 Days"},
            {"Sharp", "Dull", "Cramping", "Burning", "Other"},
            {"Yes", "No","Neck", "Back", "flank", "Anterior chest","inguinal area"},
            {"Nausea", "Vomiting", "Changes in Bowel Movements", "Fever", "Other"},
            {"Rest", "Certain Positions", "Medications", "Other" ,"Movement", "Eating", "Stress", "Other"},
            {"Neck...", "Back", "flank", "Anterior chest","inguinal area..."},
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
            {"Constant", "Intermittent", "Pattern"}
        };

        String[] labels = {"Onset", "Location", "Duration", "Character", "Radiation",
                           "Associated Symptoms", "Alleviating Factors", "Etc.",
                           "Severity", "Timing"};

        for (int i = 0; i < 10; i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout

            JLabel label = new JLabel(labels[i]);
            panel.add(label);

            ArrayList<String> selectedCheckboxes = new ArrayList<>();

            for (String option : options[i]) {
                JCheckBox checkbox = new JCheckBox(option);

                checkbox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (checkbox.isSelected()) {
                            selectedCheckboxes.add(checkbox.getText());
                        } else {
                            selectedCheckboxes.remove(checkbox.getText());
                        }

                        StringBuilder output = new StringBuilder(label.getText() + " [");
                        for (int j = 0; j < selectedCheckboxes.size(); j++) {
                            output.append(selectedCheckboxes.get(j));
                            if (j < selectedCheckboxes.size() - 1) {
                                output.append(", ");
                            }
                        }
                        output.append("]");

                        outputArea.append(output.toString()+"\n");
                        outputArea.setText(keepLastLines(outputArea.getText()) + "\n");
                    }
                });

                panel.add(checkbox);
            }

            mainPanel.add(panel);
        }

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();

        // Create buttons
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
            }
        });

        JButton saveButton = new JButton("Save");
        
        saveButton.addActionListener(event -> {
            String selectedItems = outputArea.getText();
            GDSEMR_frame.setTextAreaText(5, selectedItems);
        });
        
//        
//        
//        saveButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String pe = outputArea.getText();
//                GDSEMR_frame.setTextAreaText(6, pe);
//            }
//        });

        JButton quitButton = new JButton("Quit");

        // Add buttons to the panel
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);

        // Add the panel to the frame
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Size the frame to fit the preferred size of its components
        frame.pack();

        // Make the frame visible
        frame.setVisible(true);
    }

    private static String keepLastLines(String jtextAreaOutput) {
        // Split the JTextArea output by newline to get individual lines
        String[] lines = jtextAreaOutput.trim().split("\n");

        // Initialize a HashMap to store the last line for each category
        HashMap<String, String> lastLines = new HashMap<>();

        for (String line : lines) {
            // Extract the category (the first word of the line)
            String category = line.split(" \\[")[0].trim();

            // Store this line as the last line for this category
            lastLines.put(category, line);
        }

        // Combine the last lines for each category
        StringBuilder result = new StringBuilder();
        for (String line : lastLines.values()) {
            result.append(line).append("\n");
        }

        return result.toString().trim();
    }
}
