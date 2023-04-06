package je.panse.doro;

import javax.swing.*;	
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import je.panse.doro.chartplate.EMR_Button_Excute;
import je.panse.doro.chartplate.EMR_Write_To_Chartplate;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GDSEMR_frame extends JFrame {

    public GDSEMR_frame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("My Frame");
        setSize(1000, 800);

        // Create South North panel with 7 buttons
        JPanel southPanel = new JPanel(new GridLayout(1, 7));
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton("Button " + i);
            southPanel.add(button);
        }
        add(southPanel, BorderLayout.SOUTH);
        
        JPanel northPanel = new JPanel(new GridLayout(1, 7));
        add(northPanel, BorderLayout.NORTH);
        
        
        // Create West panel with tempOutputArea
        JTextArea tempOutputArea = new JTextArea();
        
        tempOutputArea.setPreferredSize(new Dimension(300, 200));
        add(tempOutputArea, BorderLayout.WEST);
        add(new JScrollPane(tempOutputArea), BorderLayout.WEST);
     // Create Center panel with 9 text areas
        JPanel centerPanel = new JPanel(new GridLayout(9, 1));
        
        JTextArea[] textAreas = new JTextArea[9];
        String[] titles = {"CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "A>", "P>", "Co>"};
        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new JTextArea();
            String inputData = titles[i];
            textAreas[i].setText(inputData);
            centerPanel.add(textAreas[i]);
            textAreas[i].getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateTempOutputArea();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateTempOutputArea();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateTempOutputArea();
                }

                private void updateTempOutputArea() {
                    StringBuilder appendedTextBuilder = new StringBuilder();
                    for (int j = 0; j < textAreas.length; j++) {
                        String text = textAreas[j].getText();
                        appendedTextBuilder.append(" ").append(text).append("\n");
                    }
                    tempOutputArea.setText(appendedTextBuilder.toString());
                    EMR_Write_To_Chartplate.textAreaAppend(tempOutputArea);

                }
            });

                JButton button = new JButton("Button " + i);
                northPanel.add(button);

                button.setActionCommand(Integer.toString(i)); // Set the action command to the button number
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String command = e.getActionCommand(); // Get the action command of the clicked button
                        int buttonNumber = Integer.parseInt(command); // Convert the action command to an integer
                        System.out.println("Clicked button " + buttonNumber);
                        EMR_Button_Excute.clearTextAreas(textAreas);
                        dispose();
                        GDSEMR_frame.main(null);
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
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GDSEMR_frame();
    }
}
