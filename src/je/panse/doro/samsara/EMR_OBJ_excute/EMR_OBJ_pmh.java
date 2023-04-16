package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class EMR_OBJ_pmh extends JFrame {

    private static final long serialVersionUID = 1L;
    private ArrayList<JCheckBox> checkBoxList;
    private JTextArea textArea;

    public EMR_OBJ_pmh() {
        // Set up the JFrame
        super("My JFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Set the JFrame size to 800x600 pixels
        setLayout(new BorderLayout());

        // Set up the north panel
        JPanel northPanel = new JPanel();
        checkBoxList = new ArrayList<JCheckBox>();
        String[] checkboxLabels = { "Diabetes Mellitus", "HTN", "Dyslipidemia\n", "Cancer", "Operation",
                "Thyroid Disease\n", "Asthma", "Tuberculosis", "Pneumonia\n", "Chronic/Acute Hepatitis", "GERD",
                "Gout\n", "Arthritis", "Hearing Loss", "CVA\n", "Depression", "Cognitive Disorder\n", "Allergy\n",
                "Food", "Injection", "Medication" };

        textArea = new JTextArea();
        textArea.setText("");
        textArea.setEditable(true);
        textArea.setPreferredSize(new Dimension(600, 300));

        for (String label : checkboxLabels) {
            label = "\t☐ " + label;
            textArea.append(label);
        }

        northPanel.add(textArea, BorderLayout.NORTH);
        add(northPanel, BorderLayout.NORTH);

        // Set up the center panel
        JPanel centerPanel = new JPanel(new GridLayout(0, 2));

        String[] comboOptions = { "Option 1", "Option 2", "Option 3" };
        JComboBox<String> comboBox = new JComboBox<String>(comboOptions);
        centerPanel.add(new JLabel("Select an option: "));
        centerPanel.add(comboBox);

        for (String label : checkboxLabels) {
            JCheckBox checkBox = new JCheckBox(label);
            checkBoxList.add(checkBox);
            centerPanel.add(checkBox);
        }

        add(centerPanel, BorderLayout.CENTER);

     // Add ItemListener to each checkbox in the list
        for (JCheckBox checkBox : checkBoxList) {
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    // Check if checkbox was selected or deselected
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        // Replace "☐ " with "▸ " in the label
                        String label = "▸ " + checkBox.getText();
                        // Replace the old label with the new label in the textArea
                        String text = textArea.getText().replace("☐ " + checkBox.getText(), label);
                        textArea.setText(text);
                    } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                        // Replace "▸ " with "☐ " in the label
                        String label = "☐ " + checkBox.getText();
                        // Replace the old label with the new label in the textArea
                        String text = textArea.getText().replace("▸ " + checkBox.getText(), label);
                        textArea.setText(text);
                    }
                }
            });
        }

        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EMR_OBJ_pmh();
    }
}
