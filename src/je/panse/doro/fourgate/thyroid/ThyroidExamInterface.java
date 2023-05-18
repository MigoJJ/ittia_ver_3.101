package je.panse.doro.fourgate.thyroid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThyroidExamInterface extends JFrame {
    private JTextField goiterSizeField;
    private JCheckBox[] goiterRuledCheckBoxes;
    private JCheckBox[] noduleDetectionCheckBoxes;
    private JCheckBox[] consistencyCheckBoxes;
    private JCheckBox[] tendernessCheckBoxes;
    private JCheckBox[] bruitCheckBoxes;
    private JCheckBox[] dtrCheckBoxes;
	private JCheckBox[] tedCheckBoxes = new JCheckBox[17]; // Change the size to accommodate 7 checkboxes
//    private JCheckBox[] tedCheckBoxes;
    private JTextArea outputTextArea;

    public ThyroidExamInterface() {
        setTitle("Thyroid Physical Exam");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);
        add(createOutputPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel[] panels = new JPanel[7];
        String[] panelTitles = {
            "Goiter Ruled",
            "Detect any nodules",
            "Thyroid gland consistency",
            "Evaluate the thyroid gland for tenderness",
            "Systolic or continuous Bruit (y/n)",
            "DTR deep tendon reflex",
            "TED: Thyroid Eye Disease"
        };

        String[][] checkBoxOptions = {
            {"Goiter ruled out", "Goiter ruled in Diffuse Enlargement", "Goiter ruled in Nodular Enlargement",
                "Single Nodular Goiter", "Multiple Nodular Goiter"},
            {"None", "Single nodule", "Multinodular Goiter"},
            {"Soft", "Soft to Firm", "Firm", "Cobble-stone", "Firm to Hard", "Hard"},
            {"Tender", "Non-tender"},
            {"Yes", "No"},
            {"1+ = present but depressed", "2+ = normal / average", "3+ = increased", "4+ = clonus"},
            {"Class 0: No signs or symptoms",
				"Class 1: Only signs (limited to upper lid retraction and stare, with or without lid lag)",
				"Class 2: Soft tissue involvement (oedema of conjunctivae and lids, conjunctival injection, etc.)",
				"Class 3: Proptosis",
				"Class 4: Extraocular muscle involvement (usually with diplopia)",
				"Class 5: Corneal involvement (primarily due to lagophthalmos)",
				"Class 6: Sight loss (due to optic nerve involvement)"}
				};

			        for (int i = 0; i < panels.length; i++) {
			            panels[i] = new JPanel();
			            panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS));
			            panels[i].setBorder(BorderFactory.createTitledBorder(panelTitles[i]));
			
			            JCheckBox[] checkBoxes = new JCheckBox[checkBoxOptions[i].length]; // Initialize checkBoxes array
			            for (int j = 0; j < checkBoxOptions[i].length; j++) {
			                checkBoxes[j] = new JCheckBox(checkBoxOptions[i][j]);
			                panels[i].add(checkBoxes[j]);
			            }
			        }
            JPanel topPanel = new JPanel(new GridLayout(2, 2));
            topPanel.add(createGoiterSizePanel()); // Add the Goiter Size panel to the topPanel
            topPanel.add(panels[0]);
            topPanel.add(panels[1]);
            topPanel.add(panels[2]);
            topPanel.add(panels[3]);
            topPanel.add(panels[4]);
            topPanel.add(panels[5]);
            topPanel.add(panels[6]);
            return topPanel;
        }
    private JPanel createGoiterSizePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Goiter size (mL) "));
        goiterSizeField = new JTextField(10);
        panel.add(goiterSizeField);
        return panel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOutput();
            }
        });

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(clearButton);
        panel.add(saveButton);
        panel.add(quitButton);

        return panel;
    }

    

    private void clearFields() {
        goiterSizeField.setText("");
        clearCheckBoxes(goiterRuledCheckBoxes);
        clearCheckBoxes(noduleDetectionCheckBoxes);
        clearCheckBoxes(consistencyCheckBoxes);
        clearCheckBoxes(tendernessCheckBoxes);
        clearCheckBoxes(bruitCheckBoxes);
        clearCheckBoxes(dtrCheckBoxes);
        clearCheckBoxes(tedCheckBoxes);
        outputTextArea.setText("");
    }

    private void clearCheckBoxes(JCheckBox[] checkBoxes) {
        for (JCheckBox checkBox : checkBoxes) {
            checkBox.setSelected(false);
        }
    }

    private void saveOutput() {
        String output = "<Thyroid Physical Exam>----------\n";
        output += "Goiter size  :  [" + goiterSizeField.getText() + "] cc\n";
        output += "Goiter : ";
        output += getSelectedCheckBoxText(goiterRuledCheckBoxes) + "\n";
        output += "Nodules : ";
        output += getSelectedCheckBoxText(noduleDetectionCheckBoxes) + "\n";
        output += "Consistency : ";
        output += getSelectedCheckBoxText(consistencyCheckBoxes) + "\n";
        output += "Tenderness : ";
        output += getSelectedCheckBoxText(tendernessCheckBoxes) + "\n";
        output += "Bruit : ";
        output += getSelectedCheckBoxText(bruitCheckBoxes) + "\n";
        output += "DTR : ";
        output += getSelectedCheckBoxText(dtrCheckBoxes) + "\n";
        output += "Werner’s Report : ";
        output += getSelectedCheckBoxText(tedCheckBoxes) + "\n";

        outputTextArea.setText(output);
    }

    private String getSelectedCheckBoxText(JCheckBox[] checkBoxes) {
        if (checkBoxes != null) {
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    return checkBox.getText();
                }
            }
        }
        return "";
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ThyroidExamInterface();
            }
        });
    }
}
