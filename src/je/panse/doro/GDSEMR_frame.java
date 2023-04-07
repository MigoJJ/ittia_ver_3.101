package je.panse.doro;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import je.panse.doro.chartplate.EMR_Write_To_Chartplate;

import java.awt.*;

public class GDSEMR_frame extends JFrame {

    public GDSEMR_frame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("My Frame");
        setSize(1000, 800);

        // Create South panel with 7 buttons
        JPanel southPanel = new JPanel(new GridLayout(1, 7));
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton("Button " + i);
            southPanel.add(button);
        }
        add(southPanel, BorderLayout.SOUTH);
        
        // Create North panel with 7 buttons
        JPanel northPanel = new JPanel(new GridLayout(1, 7));
        for (int i = 0; i < 7; i++) {
            JButton button = new JButton("Button " + i);
            northPanel.add(button);
        }
        add(northPanel, BorderLayout.NORTH);

        // Create West panel with tempOutputArea
        JTextArea tempOutputArea = new JTextArea();
        tempOutputArea.setPreferredSize(new Dimension(300, 200));
        add(new JScrollPane(tempOutputArea), BorderLayout.WEST);

        // Create Center panel with 9 text areas
        JPanel centerPanel = new JPanel(new GridLayout(9, 1));
                
        JTextArea[] textAreas = new JTextArea[9];
        
        for (int i = 0; i < centerPanel.getComponentCount(); i++) {
            JTextArea textArea = (JTextArea) centerPanel.getComponent(i);
            float factor = (float) i / centerPanel.getComponentCount();
            int red = (int) (255 * factor);
            int green = 255;
            int blue = 0;
            Color color = new Color(red, green, blue);
            textArea.setBackground(color);
        }
        
        String[] titles = {"CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "A>", "P>", "Co>"};
        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new JTextArea();
            String inputData = titles[i];
            textAreas[i].setText(inputData);
            centerPanel.add(textAreas[i]);

            JTextArea textArea = textAreas[i];
            textArea.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateTempOutputArea(textArea);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateTempOutputArea(textArea);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateTempOutputArea(textArea);
                }

                private void updateTempOutputArea(JTextArea textArea) {
                    String areastring = textArea.getText();
                    String[] inputWords = areastring.split("\\s+");
                    for (int i = 0; i < inputWords.length; i++) {
                        if (inputWords[i].equals(":c")) {
                            inputWords[i] = "Hypercholesterolemia";
                        }
                    }
                    // Join the input word array into a single string with spaces
                    String outputText = String.join(" ", inputWords);
                    // Display the output text in the output text area
                    tempOutputArea.setText(outputText);

                    StringBuilder appendedTextBuilder = new StringBuilder();
                    for (int j = 0; j < textAreas.length; j++) {
                        String text = textAreas[j].getText();
                        appendedTextBuilder.append(" ").append(text).append("\n");
                    }
                    tempOutputArea.setText(appendedTextBuilder.toString());
                    EMR_Write_To_Chartplate.textAreaAppend(tempOutputArea);
                }
            });
        }
        // Add scroll pane to center panel
        for (int i = 0; i < centerPanel.getComponentCount(); i++) {
            JTextArea textArea = (JTextArea) centerPanel.getComponent(i);
            float factor = (float) i / centerPanel.getComponentCount();
            Color color = Color.getHSBColor(0.16f, factor, 1.0f);
            textArea.setBackground(color);
        }

        add(centerPanel, BorderLayout.CENTER);
        // Add scroll pane to center panel
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GDSEMR_frame();
    }
}
