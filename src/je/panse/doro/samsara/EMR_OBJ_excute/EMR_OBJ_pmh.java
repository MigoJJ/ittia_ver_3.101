package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
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
        setSize(800, 1200); // Set the JFrame size to 800x600 pixels
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
        add(northPanel); // Add the north panel to the JFrame
        pack(); // Resize the JFrame to fit its contents
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EMR_OBJ_pmh();
    }
}
