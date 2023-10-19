package je.panse.doro.samsara.EMR_CCPIPMH;
import javax.swing.*;

import je.panse.doro.GDSEMR_frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EMRPMH extends JFrame {

    private JTextArea textArea;
    private JTextArea textArea2;
    private List<JCheckBox> checkboxes;
    private String[] checkboxLabels = {
            "DM", "HTN", "Dyslipidemia",
            "Cancer", "Operation", "Thyroid Disease",
            "Asthma", "Tuberculosis", "Pneumonia",
            "Hepatitis", "GERD", "Gout",
            "Arthritis", "Hearing Loss", "...",
            "CVA", "Depression", "Cognitive Disorder", 
            "Angina Pectoris", "AMI", "Arrhythmia",
            "Allergy", "...","...", "Food", "Injection", "Medication"
    };

    public EMRPMH() {
        initializeFrame();
        createCenterPanel();
        createSouthPanel();
    }

    private void initializeFrame() {
        setTitle("GDSEMR PMH");
        setSize(900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        checkboxes = new ArrayList<>();

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // Set the location of the frame to the lower left corner
        setLocation(0, screenHeight - this.getHeight());
    }

    private JCheckBox createCheckBox(String label) {
        JCheckBox checkBox = new JCheckBox(label);
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox source = (JCheckBox) e.getSource();
                if (source.isSelected()) {
                    textArea.append("   ▣   " + source.getText() + "\n\n");
                    textArea2.append("   ▣   " + source.getText() + "\n\n");


                } else {
                    String text = textArea.getText();
                    String labelLine = "   ▣   " + source.getText() + "\n";
                    if (text.contains(labelLine)) {
                        text = text.replace(labelLine, "");
                        textArea.setText(text);
                        textArea2.setText(text);
                    }
                }
            }
        });
        checkboxes.add(checkBox);
        return checkBox;
    }
    
    private void createCenterPanel() {
        // Create a text area for output
        textArea = new JTextArea(20, 40);
        textArea.setEditable(true);
        
        textArea2 = new JTextArea(20, 40);
        textArea2.setEditable(true);
        

        // Create a scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the scroll pane to the CENTER
        add(scrollPane, BorderLayout.CENTER);

        // Create checkboxes panel for the CENTER
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 3)); // 3 columns

        for (String label : checkboxLabels) {
            JCheckBox checkBox = createCheckBox(label);
            centerPanel.add(checkBox);
        }

        // Add the CENTER panel
        add(centerPanel, BorderLayout.WEST);
    }
    private void createSouthPanel() {
        JPanel southPanel = new JPanel();

        String[] buttonLabels = {"COPY", "CLEAR", "SAVE", "QUIT"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(label);
                }
            });
            southPanel.add(button);
        }

        add(southPanel, BorderLayout.SOUTH);
    }

    private void handleButtonClick(String buttonLabel) {
        switch (buttonLabel) {
            case "COPY":
            case "CLEAR":
                textArea.setText(""); // Clear the text area
                textArea2.setText(""); // Clear the text area

                for (JCheckBox checkBox : checkboxes) {
                    checkBox.setSelected(false);
                }                break;
            case "SAVE":
            	EMRPMH_ReplaceString.main(textArea2.getText());
                GDSEMR_frame.setTextAreaText(7, "\n" + textArea.getText());

            	break;
            case "QUIT":
                textArea.setText(""); // Clear the text area
                textArea2.setText(""); // Clear the text area
            	dispose();
                break;
        }
    }

    public static void main(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                EMRPMH frame = new EMRPMH();
                frame.setVisible(true);
            }
        });
    }
}
