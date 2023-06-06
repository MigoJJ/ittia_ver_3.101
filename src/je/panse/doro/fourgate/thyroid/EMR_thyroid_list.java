package je.panse.doro.fourgate.thyroid;

import javax.swing.*;

import je.panse.doro.GDSEMR_frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EMR_thyroid_list extends JPanel {
    private JFrame frame;
    private JTextField[] textFieldLabels;
    private JTextField[] textFields;
    private JCheckBox[] checkBoxes;
    private JTextArea textArea = new JTextArea(10, 30);
    private static String[] retString = {};
    private static int retStringlen = 0;

    public EMR_thyroid_list() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        textFieldLabels = new JTextField[retStringlen];
        textFields = new JTextField[retStringlen];
        checkBoxes = new JCheckBox[retStringlen];

        for (int i = 0; i < retStringlen; i++) {
            checkBoxes[i] = new JCheckBox(retString[i]);
            checkBoxes[i].setHorizontalAlignment(JTextField.RIGHT);

            textFieldLabels[i] = new JTextField(50);
            textFieldLabels[i].setHorizontalAlignment(JTextField.RIGHT);

            textFields[i] = new JTextField();
            int textFieldWidth = 260;
            int textFieldHeight = textFields[i].getPreferredSize().height;
            Dimension textFieldSize = new Dimension(textFieldWidth, textFieldHeight);
            textFields[i].setPreferredSize(textFieldSize);
            textFields[i].setMinimumSize(textFieldSize);
            textFields[i].setMaximumSize(textFieldSize);
            textFields[i].setSize(textFieldSize);
            textFields[i].setHorizontalAlignment(JTextField.CENTER);
            textFields[i].setText("□  ");

            final int index = i;
            checkBoxes[i].addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    JCheckBox checkBox = (JCheckBox) e.getSource();
                    if (checkBoxes[index].isSelected()) {
                        textFields[index].setText("▣  ");
                    } else {
                        textFields[index].setText("□  ");
                    }
                }
            });

            JPanel checkBoxPanel = new JPanel();
            checkBoxPanel.setLayout(new BorderLayout());
            checkBoxPanel.add(checkBoxes[i], BorderLayout.WEST);
            checkBoxPanel.add(textFields[i], BorderLayout.EAST);

            add(checkBoxPanel);
        }

        JButton button1 = new JButton("Show Text Field Content");
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < retStringlen; i++) {
                    String symptom = retString[i];
                    String textFieldContent = textFields[i].getText();
                    result.append("    ").append(textFieldContent).append("    ").append(symptom).append("\n");
                }
                textArea.setText("\n  The Patient has suffered from : ▣ \n");
                textArea.append(result.toString());
            }
        });

        JButton button2 = new JButton("Clear");
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        JButton button3 = new JButton("Save");
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                GDSEMR_frame.setTextAreaText(4, text);
                textArea.setText("");            }
        });
        JButton button4 = new JButton("Quit");
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                	JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(EMR_thyroid_list.this);
            		frame.dispose();
            }
        });
        // Create a panel for the buttons and set the layout to horizontal
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        // Add components to the main panel
        add(textArea);
        add(buttonPanel);
    }

    public static void main(String[] argsStr) {
    	retString = argsStr;
    	retStringlen = argsStr.length;
    	
    	JFrame frame = new JFrame("EMR Symptom List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EMR_thyroid_list panel = new EMR_thyroid_list();
        frame.getContentPane().add(panel);
        frame.setSize(700, 800);
//        frame.pack();
        frame.setVisible(true);
    }
}
