package je.panse.doro.soap.pmh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import je.panse.doro.GDSEMR_frame;

public class EMRPMH extends JFrame {

    private JTextArea textArea;
    private JTextArea textArea2;
    private List<JCheckBox> checkboxes;
    private String[] checkboxLabels = {
            "Diabetes Mellitus", "Hypertension", "Dyslipidemia", "Cancer", "Operation", "Thyroid Disease",
            "Asthma", "Tuberculosis", "Pneumonia", "Chrnic Hepatitis B", "GERD", "Gout",
            "Arthritis", "Hearing Loss", "Parkinson's disease", "CVA", "Depression", 
            "Cognitive Disorder", "Angina Pectoris", "AMI", "Arrhythmia", "Allergy", 
            "...", "...", "Food", "Injection", "Medication"
    };

    public EMRPMH() {
        setTitle("GDSEMR PMH");
        setSize(1200, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 40);
        textArea2 = new JTextArea(20, 40);
        checkboxes = new ArrayList<>();

        JScrollPane scrollPane1 = new JScrollPane(textArea);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        
        JPanel textPanel = new JPanel(new GridLayout(1, 2));
        textPanel.add(scrollPane1);
        textPanel.add(scrollPane2);
        add(textPanel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(0, 3));
        for (String label : checkboxLabels) {
            JCheckBox checkBox = new JCheckBox(label);
            checkBox.addActionListener(this::handleCheckBoxAction);
            checkboxes.add(checkBox);
            centerPanel.add(checkBox);
        }
        add(centerPanel, BorderLayout.WEST);

        JPanel southPanel = new JPanel();
        String[] buttonLabels = {"COPY", "CLEAR", "SAVE", "QUIT"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(e -> handleButtonClick(label));
            southPanel.add(button);
        }
        add(southPanel, BorderLayout.SOUTH);
    }

    private void handleCheckBoxAction(ActionEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();
        String labelLine = "□ " + source.getText();
        String checkedLabelLine = "▣ " + source.getText();

        if (source.isSelected()) {
            textArea.setText(textArea.getText().replace(labelLine, checkedLabelLine));
            textArea2.append("   " + checkedLabelLine + " \n\n");
        } else {
            textArea.setText(textArea.getText().replace(checkedLabelLine, labelLine));
            textArea2.setText(textArea2.getText().replace(" " + checkedLabelLine + " \n", ""));
        }
        
    }

    private void handleButtonClick(String buttonLabel) {
        switch (buttonLabel) {
            case "COPY":
            case "CLEAR":
                textArea.setText(initialText());
                textArea2.setText("");
                checkboxes.forEach(cb -> cb.setSelected(false));
                break;
            case "SAVE":
                String processedText = processCheckedItems();
                textArea.setText((processedText));
                GDSEMR_frame.setTextAreaText(3, "\n" + textArea.getText());
                
                String textArea2String = textArea2.getText();
                textArea2.setText((getCheckedItemsText()));
                GDSEMR_frame.setTextAreaText(7, "\n" + textArea2String);
                
                break;
            case "QUIT":
                dispose();
                break;
        }
    }

    private String initialText() {
        return "\n     ----------------------------------\n" +
               "     □ Diabetes Mellitus □ Hypertension □ Dyslipidemia \n" +
               "     □ Cancer           □ Operation        □ Thyroid Disease\n" +
               "     □ Asthma           □ Pneumonia        □ Tuberculosis\n" +
               "     □ Chrnic Hepatitis B        □ GERD             □ Gout\n" +
               "     □ Arthritis        □ Hearing Loss     □ Parkinson's Disease\n" +
               "     □ CVA              □ Depression       □ Cognitive Disorder\n" +
               "     □ Angina Pectoris  □ AMI              □ Arrhythmia\n" +
               "     □ Allergy          □ ...              \n" +
               "     □ Food             □ Injection        □ Medication\n" +
               "     ------------------------------------\n";
    }

    private String processCheckedItems() {
        String template = initialText();
        for (JCheckBox checkBox : checkboxes) {
            String labelLine = "□ " + checkBox.getText();
            String checkedLabelLine = "▣ " + checkBox.getText();
            if (checkBox.isSelected()) {
                template = template.replace(labelLine, checkedLabelLine);
            }
        }
        return template;
    }

    private String getCheckedItemsText() {
        StringBuilder checkedItems = new StringBuilder();
        for (JCheckBox checkBox : checkboxes) {
            if (checkBox.isSelected()) {
                checkedItems.append("▣ ").append(checkBox.getText()).append("\n\n");
            }
        }
        return checkedItems.toString();
    }

    private String addSpaceToEachLine(String text) {
        String[] lines = text.split("\n");
        StringBuilder spacedText = new StringBuilder();
        for (String line : lines) {
            spacedText.append(" ").append(line).append("\n");
        }
        return spacedText.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EMRPMH frame = new EMRPMH();
            frame.setVisible(true);
            frame.textArea.setText(frame.initialText());
            frame.textArea2.setText("");
        });
    }
}
