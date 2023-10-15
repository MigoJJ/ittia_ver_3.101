package je.panse.doro.samsara.EMR_CCPIPMH;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import je.panse.doro.GDSEMR_frame;

public class EMRPMH extends JFrame implements ActionListener {

    private JTextArea pmhxTextArea, commentpmh;
    private ArrayList<JCheckBox> checkBoxList;
    private String dsquare = "◙";

    public EMRPMH() {
        super("Medical History");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 700));
        setLayout(new BorderLayout());

        // Create a panel to hold the checkboxes
        Font font = new Font("DejaVu Sans", Font.PLAIN, 12);
        JPanel pmhxPanel = new JPanel();
        pmhxPanel.setLayout(new GridLayout(0, 3));

        // Create the checkboxes and add them to the panel
        checkBoxList = new ArrayList<JCheckBox>();
        String[] checkboxLabels = { "Diabetes Mellitus", "HTN", "Dyslipidemia\n", "Cancer", "Operation",
                "Thyroid Disease\n", "Asthma", "Tuberculosis", "Pneumonia\n", "Chronic/Acute Hepatitis", "GERD",
                "Gout\n", "Arthritis", "Hearing Loss", "CVA\n", "Depression", "Cognitive Disorder\n",
                "...","Angina Pectoris", "AMI", "Arrhythmia\n", "Allergy\n", "...", "Food", "Injection",
                "Medication\n" };

        JTextArea[] textAreas = createTextAreas(font);

        for (String label : checkboxLabels) {
            JCheckBox checkbox = createCheckBox(label, font);
            checkBoxList.add(checkbox);
            pmhxPanel.add(checkbox);
            addActionListenerToCheckBox(checkbox, label);
        }

        JScrollPane pmhxScrollPane = new JScrollPane(textAreas[0]);
        JScrollPane commentpmhPane = new JScrollPane(textAreas[1]);

        JPanel buttonPanel = createButtonPanel();

        add(pmhxPanel, BorderLayout.NORTH);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pmhxScrollPane, commentpmhPane);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextArea[] createTextAreas(Font font) {
        JTextArea[] textAreas = new JTextArea[2];

        pmhxTextArea = new JTextArea();
        pmhxTextArea.setPreferredSize(new Dimension(600, 200));
        pmhxTextArea.setText(" -\n");
        pmhxTextArea.setEditable(true);
        pmhxTextArea.setFont(font);

        commentpmh = new JTextArea();
        commentpmh.setPreferredSize(new Dimension(600, 200));
        commentpmh.setEditable(true);
        commentpmh.setFont(font);

        textAreas[0] = pmhxTextArea;
        textAreas[1] = commentpmh;

        return textAreas;
    }

    private JCheckBox createCheckBox(String label, Font font) {
        JCheckBox checkbox = new JCheckBox(label);
        checkbox.setFont(font);
        return checkbox;
    }

    private void addActionListenerToCheckBox(JCheckBox checkbox, String label) {
        checkbox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    pmhxTextArea.append("         " + dsquare + " " + label + "\n");
                } else {
                    String text = pmhxTextArea.getText().replace(label + "\n", "");
                    pmhxTextArea.setText(text);
                }
            }
        });
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton saveShowButton = new JButton("Save and Show");
        JButton clearButton = new JButton("Clear and Restart");
        JButton saveQuitButton = new JButton("Save and Quit");
        buttonPanel.add(saveShowButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveQuitButton);

        saveShowButton.addActionListener(this);
        clearButton.addActionListener(this);
        saveQuitButton.addActionListener(this);

        return buttonPanel;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("Save and Show".equals(command)) {
            handleSaveAndShow();
        } else if ("Clear and Restart".equals(command)) {
            handleClearAndRestart();
        } else if ("Save and Quit".equals(command)) {
            handleSaveAndQuit();
        } else {
            // Handle any other commands here
        }
    }

    private void handleSaveAndShow() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------\n    ");

        for (JCheckBox checkbox : checkBoxList) {
            sb.append(checkbox.isSelected() ? "◙  " : "☐ ");
            sb.append(checkbox.getText());
            sb.append("    ");
        }

        sb.append("--------------------------------------\n");

        String content = pmhxTextArea.getText();
        content = content.replace("☐...", "");

        GDSEMR_frame.setTextAreaText(7, content);
        pmhxTextArea.append(sb.toString());
        GDSEMR_frame.setTextAreaText(3, sb.toString());
        GDSEMR_frame.setTextAreaText(3, commentpmh.getText());
        dispose();
    }

    private void handleClearAndRestart() {
        for (JCheckBox checkbox : checkBoxList) {
            checkbox.setSelected(false);
        }
    }

    private void handleSaveAndQuit() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit without saving?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public static void main(String text) throws IOException {
        EMRPMH gui = new EMRPMH();
    }
}
