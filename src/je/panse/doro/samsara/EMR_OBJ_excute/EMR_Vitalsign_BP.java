package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import je.panse.doro.GDSEMR_frame;

public class EMR_Vitalsign_BP extends JFrame implements ActionListener, KeyListener {

		private JTextField inputField;
		private JTextArea outputArea;
		private ArrayList<String> inputList;
	    private String bp = "at GDS left seated position Regular";

		public EMR_Vitalsign_BP() {
			super("EMR Interface for BP");
			
			// Create input field and label
			inputField = new JTextField(20);
			inputField.addKeyListener(this);
			JLabel inputLabel = new JLabel("Vital sign input : ");
			inputField.setHorizontalAlignment(JTextField.CENTER);
			inputField.setPreferredSize(new Dimension(20, 30));
			
			// Create output area and label
			outputArea = new JTextArea(10, 29);
			JScrollPane scrollPane = new JScrollPane(outputArea);
			JLabel outputLabel = new JLabel("Vital sign: ");
			
			// Create save and quit buttons
			JButton clearButton = new JButton("Clear");
			clearButton.addActionListener(this);
			JButton saveButton = new JButton("Save");
			saveButton.addActionListener(this);
			JButton quitButton = new JButton("Quit");
			quitButton.addActionListener(this);
			
			// Add components to the frame
			JPanel inputPanel = new JPanel();
			inputPanel.add(inputLabel);
			inputPanel.add(inputField);
			JPanel outputPanel = new JPanel();
			outputPanel.add(outputLabel);
			outputPanel.add(scrollPane);
			JPanel buttonPanel = new JPanel();
			
			buttonPanel.add(clearButton);
			buttonPanel.add(saveButton);
			buttonPanel.add(quitButton);
			
			this.add(inputPanel, BorderLayout.NORTH);
			this.add(outputPanel, BorderLayout.CENTER);
			this.add(buttonPanel, BorderLayout.SOUTH);
			
			// Initialize input list
			inputList = new ArrayList<String>();
		}
		
		// Handle button clicks
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Clear")) {
				inputField.setText("");
				outputArea.setText("");
			} else if (e.getActionCommand().equals("Save")) {
				inputField.setText("");
				dispose();
			} else if (e.getActionCommand().equals("Quit")) {
				dispose();
			}
			GDSEMR_frame.setTextAreaText(5,"\n"+outputArea.getText());
		}
		
		// Handle key presses
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
			String input = inputField.getText();
				if (!input.equals("")) {
					inputList.add(input);
					inputField.setText("");
					outputArea.setText("");
				updateOutputArea();
				} else {
				}
			}
		}
		
		// Update output area with input list
		private void updateOutputArea() {
		    String returnBPdescribe = updateOutputAreaString(inputList); // pass inputList and bp
		    outputArea.setText(returnBPdescribe);
		    inputList.removeIf(s -> s.equals("i") || s.equals("r") || s.equals("h"));

		    String returnBP = changeString(inputList);    
		    outputArea.append("\n"+returnBP);
		}

		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		public static void main(String[] args) {
			EMR_Vitalsign_BP emrInterface = new EMR_Vitalsign_BP();
		    emrInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    emrInterface.pack();
		    emrInterface.setVisible(true);
		}

		private String updateOutputAreaString(ArrayList<String> inputList2) {
		    for (String str : inputList) {
		        if (str.trim().equals("r") && bp.contains(str)) {
		            bp = bp.replace("left", "right");
		        } else if (str.trim().equals("i") && bp.contains(str)) {
		            bp = bp.replace("Regular", "irRegular");
		        } else if (str.trim().equals("h") && bp.contains(str)) {
		            bp = bp.replace(bp, "at home by self");
		        }
		    }
		    System.out.println(bp);
		    return bp;
		}

		private String changeString(ArrayList<String> inputList2) {
		    int length = inputList2.size();
		    ArrayList<String> list = new ArrayList<String>(inputList2);
		    String B = "";

		    // use the index to determine which string to replace
		    switch (length) {
		        case 0:
		            B = ("reinsert Vital signs ~~~");
		            break;
		        case 1:
		            B = ("SBP [ " + list.get(0) + " ] mmHg");
		            break;
		        case 2:
		            B = ("BP [ " + list.get(0) + " / " + list.get(1) + " ] mmHg");
		            break;
		        case 3:
		            B = "BP [ " + list.get(0) + " / " + list.get(1) + " ] mmHg  PR [ " + list.get(2) + " ] /min";
		            break;
		        default:
		            if (length >= 4) {
		                B = "BP [ " + list.get(0) + " / " + list.get(1) + " ] mmHg  PR [ " + list.get(2) + " ] /min" 
		                		+ "\nBody Temperature [ " + list.get(3) + " ] 'C";
		            }
		            if (length >= 5) {
		                B = "BP [ " + list.get(0) + " / " + list.get(1) + " ] mmHg  PR [ " + list.get(2) + " ] /min"
		                		+ "\nBody Temperature [ " + list.get(3) + " ] 'C"
		                		+ "\nRespiration rate [ " + list.get(4) + " ] /min";
		            }
		            break;
		    }
		    return B;
		}


}