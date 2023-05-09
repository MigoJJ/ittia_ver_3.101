package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import je.panse.doro.GDSEMR_frame;

public class EMR_Lab_enterresult extends JFrame {

    private JTextField[] textBoxes;
    private int numFields = 6;
    private String[] nameFields = {"Vitamin-D  (20-50 ng/mL)", "CRP  ", "ESR  ","URic Acid  ", "CA-19-9","CEA"};

    public EMR_Lab_enterresult() {
        super("Name Prompt Program");

        // Create the labels and text fields
        JLabel[] labels = new JLabel[numFields];
        textBoxes = new JTextField[numFields];
        
        for (int i = 0; i < numFields; i++) {
            labels[i] = new JLabel(nameFields[i]);
//      1,000 IU vitamin D equals 0.025 mg
            
            labels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            textBoxes[i] = new JTextField(21);
            textBoxes[i].setHorizontalAlignment(JTextField.CENTER);
            textBoxes[i].setPreferredSize(new Dimension(10, 30)); // Set the preferred size to 100x30
        }
                // Create the panel for the labels and text fields
                JPanel panelTop = new JPanel(new GridLayout(8, 1));
                for (int i = 0; i < labels.length; i++) {
                    panelTop.add(labels[i]);
                    panelTop.add(textBoxes[i]);
                }

                // Create the buttons and their action listeners
                JButton clearButton = new JButton("Clear");
                clearButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < textBoxes.length; i++) {
                            textBoxes[i].setText("");
                        }
                    }
                });
                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Save button clicked");
                        for (int i = 0; i < textBoxes.length; i++) {
                        	  	String outputText = textBoxes[i].getText();
                        	  	String labelText = labels[i].getText();
                            if (outputText != null && !outputText.isEmpty()) {
                                GDSEMR_frame.setTextAreaText(5, "\n  " + outputText + "\t[ " +  labelText+ " ]\n");
                                dispose();
                            	}
                        	}
                       }
            	});
                JButton quitButton = new JButton("Quit");
                quitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                
             // Add key listener to each text field
                for (int i = 0; i < textBoxes.length; i++) {
                    textBoxes[i].setPreferredSize(new Dimension(200, 30)); // set text field height
                    labels[i].setHorizontalAlignment(SwingConstants.RIGHT); // align label text to the right
                    textBoxes[i].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                int index = Arrays.asList(textBoxes).indexOf(e.getSource());
                                if (index < textBoxes.length - 1) {
                                    textBoxes[index + 1].requestFocus();
                                } else {
                                    // Replace the following line with your own code to save the text field values
                                   System.out.println("Save button clicked");
//                                   String outputText = textBoxes[index].getText();
//                        	        	GDSEMR_frame.setTextAreaText(5, outputText);
                                }
                            }
                        }
                    });
                }

                // Create the panel for the buttons
                JPanel panelBottom = new JPanel();
                panelBottom.add(clearButton);
                panelBottom.add(saveButton);
                panelBottom.add(quitButton);

                // Add the panels to the frame
                add(panelTop, BorderLayout.NORTH);
                add(panelBottom, BorderLayout.SOUTH);

                // Set up the frame
                setTitle("EMR Laboratory Enter Result.");
                pack();
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }

            public static void main(String[] args) {
                EMR_Lab_enterresult promptForName = new EMR_Lab_enterresult();
                promptForName.setVisible(true);
            }
        }
