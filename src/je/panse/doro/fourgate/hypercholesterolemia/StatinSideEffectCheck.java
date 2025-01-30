package je.panse.doro.fourgate.hypercholesterolemia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StatinSideEffectCheck extends JFrame {
    private JCheckBox[] sideEffectBoxes;
    private JCheckBox[] emergencyBoxes;
    private JTextArea resultArea;
    private final Color BROWN = new Color(139, 69, 19); // Define brown color
    private final Font TITLE_FONT = new Font("Arial", Font.BOLD, 11);

    public StatinSideEffectCheck() {
        setTitle("Statin Side Effect Checker");
        setLayout(new BorderLayout());

        // North Panel with Checkboxes
        JPanel northPanel = new JPanel(new GridLayout(0, 1));
        northPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5)); // Add left padding
        
        String[] sideEffects = {
            "All other side effects have been denied.",
            "Muscle Pain & Weakness",
            "Rhabdomyolysis",
            "Liver Problems",
            "Cognitive Issues",
            "Diabetes Risk",
            "Digestive Issues",
            "Nerve Problems",
            "All other side effects have been denied.",
        };

        String[] emergencySymptoms = {
        	"All other emergency side effects have been denied.",
        	"Severe muscle pain or weakness",
            "Dark urine",
            "Yellowing of skin/eyes",
            "Unexplained fever",
            "Severe stomach pain",
            "Allergic reactions",
            "All emergency side effects have been denied.",
        };

        sideEffectBoxes = new JCheckBox[sideEffects.length];
        emergencyBoxes = new JCheckBox[emergencySymptoms.length];

        // Common Side Effects Label
        JLabel commonLabel = new JLabel("Common Side Effects: Statin/Ezetimibe");
        commonLabel.setFont(TITLE_FONT);
        commonLabel.setForeground(BROWN);
        northPanel.add(commonLabel);

        // Add side effect checkboxes with indentation
        for (int i = 0; i < sideEffects.length; i++) {
            sideEffectBoxes[i] = new JCheckBox("    " + sideEffects[i]); // Add spaces for indentation
            northPanel.add(sideEffectBoxes[i]);
            sideEffectBoxes[i].addActionListener(e -> updateTextArea());
        }

        // Emergency Symptoms Label
        JLabel emergencyLabel = new JLabel("Emergency Symptoms: Statin/Ezetimibe");
        emergencyLabel.setFont(TITLE_FONT);
        emergencyLabel.setForeground(BROWN);
        northPanel.add(emergencyLabel);

        // Add emergency checkboxes with indentation
        for (int i = 0; i < emergencySymptoms.length; i++) {
            emergencyBoxes[i] = new JCheckBox("    " + emergencySymptoms[i]); // Add spaces for indentation
            northPanel.add(emergencyBoxes[i]);
            emergencyBoxes[i].addActionListener(e -> updateTextArea());
        }

        // South Panel with TextArea and Buttons
        JPanel southPanel = new JPanel(new BorderLayout());
        
        // Text Area
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        southPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton clearButton = new JButton("Clear");
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");

        // Button Actions
        clearButton.addActionListener(e -> {
            for (JCheckBox box : sideEffectBoxes) box.setSelected(false);
            for (JCheckBox box : emergencyBoxes) box.setSelected(false);
            resultArea.setText("");
        });

        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Results Saved!");
        });

        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panels to frame
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTextArea() {
        StringBuilder text = new StringBuilder("***Selected Side Effects:  Statin/Ezetimibe***\n");
        for (JCheckBox box : sideEffectBoxes) {
            if (box.isSelected()) {
                text.append("   - ").append(box.getText().trim()).append("\n"); // trim() removes leading spaces
            }
        }
        text.append("\n***Selected Emergency Symptoms:  Statin/Ezetimibe***\n");
        for (JCheckBox box : emergencyBoxes) {
            if (box.isSelected()) {
                text.append("   - ").append(box.getText().trim()).append("\n"); // trim() removes leading spaces
            }
        }
        resultArea.setText(text.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StatinSideEffectCheck());
    }
}
