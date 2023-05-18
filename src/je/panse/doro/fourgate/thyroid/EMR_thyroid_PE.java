package je.panse.doro.fourgate.thyroid;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import je.panse.doro.GDSEMR_frame;

public class EMR_thyroid_PE extends JFrame {
	private JTextField goiterSizeField;
	private JCheckBox[] goiterRuledCheckBoxes;
	private JCheckBox[] noduleDetectionCheckBoxes;
	private JCheckBox[] consistencyCheckBoxes;
	private JCheckBox[] tendernessCheckBoxes;
	private JCheckBox[] bruitCheckBoxes;
	private JCheckBox[] dtrCheckBoxes;
	private JCheckBox[] tedCheckBoxes = new JCheckBox[17]; // Change the size to accommodate 7 checkboxes
	private JTextArea outputTextArea;

	public EMR_thyroid_PE() {
	    setTitle("Thyroid Physical Exam");
	    setSize(1000, 1000);
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
 
            // Set the checkbox array references based on the panel index
            switch (i) {
                case 0:
                    goiterRuledCheckBoxes = checkBoxes;
                    break;
                case 1:
                    noduleDetectionCheckBoxes = checkBoxes;
                    break;
                case 2:
                    consistencyCheckBoxes = checkBoxes;
                    break;
                case 3:
                    tendernessCheckBoxes = checkBoxes;
                    break;
                case 4:
                    bruitCheckBoxes = checkBoxes;
                    break;
                case 5:
                    dtrCheckBoxes = checkBoxes;
                    break;
                case 6:
                    tedCheckBoxes = checkBoxes;
                    break;
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
        goiterSizeField.setPreferredSize(new Dimension(100, 35)); // Set preferred size for the text field
        goiterSizeField.setHorizontalAlignment(JTextField.CENTER); // Center align the text field
        panel.add(goiterSizeField);
        return panel;
    }
    
    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(true);
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

        JButton saveButton = new JButton("Excute");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOutput();
            }
        });

        JButton quitButton = new JButton("Save and Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		GDSEMR_frame.setTextAreaText(6, outputTextArea.getText());
        		dispose();
            }
        });

        panel.add(clearButton);
        panel.add(saveButton);
        panel.add(quitButton);

        return panel;
    }

    private void saveOutput() {
        String output = "<Thyroid Exam>\n";
        output += "   Goiter size  :\t[ " + goiterSizeField.getText() + "  ] cc\n";
        output += "   Goiter :\t\t" + getSelectedCheckBoxLabels(goiterRuledCheckBoxes) + "\n";
        output += "   Nodules :\t\t" + getSelectedCheckBoxLabels(noduleDetectionCheckBoxes) + "\n";
        output += "   Consistency:\t" + getSelectedCheckBoxLabels(consistencyCheckBoxes) + "\n";
        output += "   Tenderness:\t" + getSelectedCheckBoxLabels(tendernessCheckBoxes) + "\n";
        output += "   Bruit :\t\t" + getSelectedCheckBoxLabels(bruitCheckBoxes) + "\n";
        output += "   DTR :\t\t" + getSelectedCheckBoxLabels(dtrCheckBoxes) + "\n";
        output += "   Werner’s Report :\n\t " + getSelectedCheckBoxLabels(tedCheckBoxes) + "\n";

        outputTextArea.setText(output);
    }

	private String getSelectedCheckBoxLabels(JCheckBox[] checkBoxes) {
	    if (checkBoxes != null) {
	        StringBuilder labels = new StringBuilder();
	        for (JCheckBox checkBox : checkBoxes) {
	            if (checkBox != null && checkBox.isSelected() && checkBox.getText() != null) {
	                if (labels.length() > 0) {
	                    labels.append(", ");
	                }
	                labels.append(checkBox.getText());
	            }
	        }
	        return labels.toString();
	    }
	    return "";
	}
    
    private String getSelectedCheckBoxText(JCheckBox[] checkBoxes) {
        if (checkBoxes != null) {
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox != null && checkBox.isSelected()) {
                    return checkBox.getText();
                }
            }
        }
        return "";
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EMR_thyroid_PE();
            }
        });
    }
}
