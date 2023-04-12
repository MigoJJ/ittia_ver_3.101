package je.panse.doro.samsara;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MyFrame extends JFrame {
    private static final int NUM_TEXT_AREAS = 10;
    private JTextArea[] textAreas;

    public MyFrame() {
        // Set up the frame
        setTitle("My App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the text areas
        JPanel textAreaPanel = new JPanel(new GridLayout(NUM_TEXT_AREAS, 1));
        textAreas = new JTextArea[NUM_TEXT_AREAS];
        for (int i = 0; i < NUM_TEXT_AREAS; i++) {
            textAreas[i] = new JTextArea();
              textAreas[i].setText("123456 + i\n"
              		+ "        JButton saveButton = new JButton(\"Save\");\n"
              		+ "        JButton recallButton = new JButton(\"Recall\");");
            textAreaPanel.add(textAreas[i]);
        }

        // Create the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("Edit");
        JButton saveButton = new JButton("Save");
        JButton recallButton = new JButton("Recall");
        JButton exitButton = new JButton("Exit");
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(recallButton);
        buttonPanel.add(exitButton);

        // Add the components to the frame
        add(textAreaPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i <= 9; i++) {
                    // Get the text from the JTextArea
                    String text = textAreas[i].getText();

                    // Save the text to a file
                    String fileName = "textarea" + (i + 1);

                    try (PrintWriter out = new PrintWriter(fileName)) {
                        out.print(text);
                    } catch (IOException ex) {
                        System.err.println("Failed to save file " + fileName + ": " + ex.getMessage());
                    }
                }
            }
        });



        // Set the frame size and make it visible
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
