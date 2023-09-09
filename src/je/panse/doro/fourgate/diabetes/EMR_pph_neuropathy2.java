package je.panse.doro.fourgate.diabetes;

import java.awt.BorderLayout;	
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import je.panse.doro.GDSEMR_frame;

public class EMR_pph_neuropathy2 extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final String FRAME_TITLE = "EMR Physician's Interface";
    private static final String DEFAULT_TEXT = "< Diabetes Mellitus : peripheral neuropathy >\n";

    private static JFrame frame = new JFrame(FRAME_TITLE);
    private static JTextArea textArea = new JTextArea(9, 50);
    private static final Map<JCheckBox, String> checkboxTexts = new HashMap<>();
    private static final String[] buttonLabels = {"Negative pph_Neuropathy","Clear", "Save", "Quit"};
    private static final String[] labels = {
            "Symptoms of neuropathy:  ",
            "Foot abnormalities:  ",
            "Medication:  ",
            "Symptoms of neuropathy:  ",
            "Foot abnormalities:  ",
            "Sensory examination:  ",
            "Motor examination:  ",
            "Reflex testing:  ",
            "Coordination and balance tests:  "
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EMR_pph_neuropathy2::createAndShowGUI);
    }

    private static void createAndShowGUI() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 600);

        textArea.setText("< Diabetes Mellitus : peripheral neuropathy >\n");
        frame.add(new JScrollPane(textArea), BorderLayout.NORTH);

        JPanel inputPanel = createInputPanel();
        frame.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel(inputPanel);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 3));

        for (int i = 0; i < labels.length; i++) {
            inputPanel.add(createLabel(labels[i]));
            inputPanel.add(createCheckBoxPanel(i));
        }

        return inputPanel;
    }

    private static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setFont(new Font("Consolas", Font.PLAIN, 14));
        return label;
    }

    private static JPanel createCheckBoxPanel(int index) {
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        String[][] options = {
                {"numbness[-]", "tingling[-]", "pain[-]", "weakness in limbs[-]", "muscle atrophy or loss[-]"},
                {"ulcers[-]", "sores[-]", "infections[-]: redness, swelling, or warmth","hammertoes[-]","claw toes[-]"},
                {"a", "b", "c", "d", "e"},
                {"numbness[+]", "tingling[+]", "pain[+]", "weakness in limbs[+]", "muscle atrophy or loss[+]"},
                {"ulcers[+]", "sores[+]", "infections[+]: redness, swelling, or warmth","hammertoes[+]","claw toes[+]"},
                {"10-g monofilament test for light touch sense in the feet[ - ]", "tuning fork test for vibration sense in the feet [ - ]", "c", "d", "e"},
                {"muscle atrophy, which is a loss of muscle mass [-]","muscle weakness [-]","fasciculations, which are involuntary muscle twitches [-]","muscle tenderness[-]",""},
                {"a", "b", "c", "d", "e"},
                {"a", "b", "c", "d", "e"},
                {"Romberg's Test: [-]", "Tandem Walking: [-]", "Finger-Nose Test: [-]", "Heel-to-Shin Test: [-]", "Gait Assessment: [-]"}
        };
        for (int i = 0; i < 5; i++) {
            JCheckBox checkBox = new JCheckBox(options[index][i]);
            checkboxTexts.put(checkBox, "    " + checkBox.getText() + "\n");
            checkBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateTextArea();
                } else {
                    updateTextArea();
                }
            });
            checkBoxPanel.add(checkBox);
        }

        return checkBoxPanel;
    }

    private static void updateTextArea() {
        StringBuilder text = new StringBuilder("< Diabetes Mellitus : peripheral neuropathy >\n");
        for (Map.Entry<JCheckBox, String> entry : checkboxTexts.entrySet()) {
            if (entry.getKey().isSelected()) {
                text.append(entry.getValue());
            }
        }
        textArea.setText(text.toString());
    }

    private static JPanel createButtonPanel(JPanel inputPanel) {
        JPanel buttonPanel = new JPanel();

        for (String buttonLabel : buttonLabels) {
            JButton button = new JButton(buttonLabel);
            button.addActionListener(e -> {
                switch (buttonLabel) {
	                case "Negative pph_Neuropathy":
	                    GDSEMR_frame.setTextAreaText(4, ""
	                    		+ "< Diabetes Mellitus : peripheral neuropathy >\n"
	                    		+ "\n   numbness[-], tingling[-], pain[-], weakness in limbs[-] \n"
	                    		+ "   ulcers[ - ], sores[ - ], infections[ - ]\n"
	                    		+ "   10-g monofilament test for light touch sense in the feet[ - ]\n,"
	                    		+ "   tuning fork test for vibration sense in the feet [ - ]");

	                	clearInput(inputPanel);
                        frame.dispose();

	                    break;                
	                case "Clear":
                        clearInput(inputPanel);
                        break;
                    case "Save":
                        GDSEMR_frame.setTextAreaText(1, textArea.getText());
                        clearInput(inputPanel);
                        frame.dispose();
                        // Implement save logic here
                        break;
                    case "Quit":
                        clearInput(inputPanel);
                        frame.dispose();
                        break;
                }
            });
            buttonPanel.add(button);
        }

        return buttonPanel;
    }

    private static void clearInput(JPanel inputPanel) {
        for (Component comp : inputPanel.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component innerComp : ((JPanel) comp).getComponents()) {
                    if (innerComp instanceof JCheckBox) {
                        ((JCheckBox) innerComp).setSelected(false);
                    }
                }
            }
        }
        textArea.setText("< Diabetes Mellitus : peripheral neuropathy >\n");
    }
}
