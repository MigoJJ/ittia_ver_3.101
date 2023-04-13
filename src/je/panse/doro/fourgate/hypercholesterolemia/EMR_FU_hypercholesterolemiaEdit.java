package je.panse.doro.fourgate.hypercholesterolemia;

import java.awt.BorderLayout;	
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.entry.EntryDir;

public class EMR_FU_hypercholesterolemiaEdit extends JFrame {
    private static final int NUM_TEXT_AREAS = 10;
    private JTextArea[] textAreas;

    public EMR_FU_hypercholesterolemiaEdit() {
        // Set up the frame
        setTitle("My App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the text areas
        JPanel textAreaPanel = new JPanel(new GridLayout(NUM_TEXT_AREAS, 1));
        textAreas = new JTextArea[NUM_TEXT_AREAS];
        for (int i = 0; i < NUM_TEXT_AREAS; i++) {
            textAreas[i] = new JTextArea();
              textAreas[i].setText("123456 + i\n");

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
                    String fileName = EntryDir.homeDir +"/fourgate/hypertension/textarea" + (i + 1);

                    try (PrintWriter out = new PrintWriter(fileName)) {
                        out.print(text);
                    } catch (IOException ex) {
                        System.err.println("Failed to save file " + fileName + ": " + ex.getMessage());
                    }
                }
            }
        });
        
        recallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < textAreas.length; i++) {
                    // Read the contents of the file
                    String fileName = EntryDir.homeDir + "/fourgate/hypertension/textarea" + (i + 1);
                    String text = "";
                    try (Scanner scanner = new Scanner(new File(fileName))) {
                        while (scanner.hasNextLine()) {
                            text += scanner.nextLine() + "\n";
                        }
                    } catch (FileNotFoundException ex) {
                        System.err.println("Failed to read file " + fileName + ": " + ex.getMessage());
                    }
                    // Set the text of the corresponding JTextArea

                    try {
						GDSEMR_frame.call_preform(i,text);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                    
                    //                    textAreas[i].setText(text);
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the frame
                dispose();
            }
        });

        // Set the frame size and make it visible
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EMR_FU_hypercholesterolemiaEdit();
    }
}
