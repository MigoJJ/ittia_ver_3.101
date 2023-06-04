package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.samsara.comm.datetime.Date_current;

public class EMR_Vitalsign_BP extends JFrame implements ActionListener, KeyListener {

		private JTextField inputField;
		private JTextArea outputArea;
		private ArrayList<String> inputList;
	    private String bp = "at GDS left seated position Regular";

	    public EMR_Vitalsign_BP() {
	        super("EMR Interface for BP");
	        setLocation(1460, 220);
//	        setSize(new Dimension(1000, 600));

	        // Create input field and label
	        inputField = new JTextField(10);
	        inputField.addKeyListener(this);
	        JLabel inputLabel = new JLabel("Vital Sign: ");
	        inputField.setHorizontalAlignment(JTextField.CENTER);
	        inputField.setPreferredSize(new Dimension(10, 30));

	        // Create output area and label
	        outputArea = new JTextArea(5, 25);
	        JScrollPane scrollPane = new JScrollPane(outputArea);
	        JLabel outputLabel = new JLabel("Vital sign: ");
	        
	        // Create save and quit buttons
	        String[] buttonLabels = { "Clear", "Save", "Quit" };
	        JButton[] buttons = new JButton[buttonLabels.length];

	        for (int i = 0; i < buttons.length; i++) {
	            buttons[i] = new JButton(buttonLabels[i]);
	            buttons[i].addActionListener(this);
	        }

	        // Add components to the frame
	        JPanel inputPanel = new JPanel();
	        inputPanel.add(inputLabel);
	        inputPanel.add(inputField);
	        JPanel outputPanel = new JPanel();
	        outputPanel.add(outputLabel);
	        outputPanel.add(scrollPane);
	        JPanel buttonPanel = new JPanel();

	        for (JButton button : buttons) {
	            buttonPanel.add(button);
	        }

	        this.add(inputPanel, BorderLayout.NORTH);
	        this.add(outputPanel, BorderLayout.CENTER);
	        this.add(buttonPanel, BorderLayout.SOUTH);

	        // Initialize input list
	        inputList = new ArrayList<String>();

	    }

		public static void main(String[] args) {
			EMR_Vitalsign_BP emrInterface = new EMR_Vitalsign_BP();
		    emrInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    emrInterface.setSize(200, 100);
		    emrInterface.pack();
		    emrInterface.setVisible(true);
		}
	    
	    // Handle button clicks
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Clear")) {
				inputField.setText("");
				outputArea.setText("");
		        inputList = new ArrayList<String>();
			} else if (e.getActionCommand().equals("Save")) {
				inputField.setText("");
//				outputArea.setText("");
//				dispose();
			} else if (e.getActionCommand().equals("Quit")) {
				outputArea.setText("");
				dispose();
			}
			GDSEMR_frame.setTextAreaText(5,"\n"+outputArea.getText());

            if (inputList.size() >= 2) {
                int SBP = Integer.parseInt(inputList.get(0));
                int DBP = Integer.parseInt(inputList.get(1));
                getHypertensionControlStatus(SBP, DBP);
            }
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
		    inputList.removeAll(Arrays.asList("i", "r", "h", "s", "b"));
		    String returnBP = changeString(inputList);    
		    String returnTime = Date_current.defineTime("h");
		    outputArea.append("\n"+ returnTime + "\n" + returnBP);
		}

		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		private String updateOutputAreaString(ArrayList<String> inputList) {
		    String[] replacements = { 
		    		"right", 
		    		"irRegular", 
		    		"at home by self", 
		    		"at GDS by self BP machine", 
		    		"at GDS left seated position Regular" };

		    for (String str : inputList) {
		        str = str.trim();
		        int index = bp.indexOf(str);
		        if (index >= 0) {
		            if (str.equals("r")) {
		                bp = bp.replace("left", replacements[0]);
		            } else if (str.equals("i")) {
		                bp = bp.replace("Regular", replacements[1]);
		            } else if (str.equals("h")) {
		                bp = replacements[2];
		            } else if (str.equals("s")) {
		                bp = replacements[3];
		            } else if (str.equals("b")) {
		                bp = replacements[4];
		            }
		        }
		    }
		    System.out.println(bp);
		    return bp;
		}

		private String changeString(ArrayList<String> inputList) {
		    int length = inputList.size();
		    StringBuilder sb = new StringBuilder("    ");
		    String[] labels = { "SBP", "DBP", "PR", "\n    Body Temperature", "\n    Respiration rate" };

		    for (int i = 0; i < length; i++) {
		        String label = labels[i];
		        String value = inputList.get(i);
		        sb.append(label).append(" [ ").append(value).append(" ]");
					        
		        if (i < length - 1) {
		            sb.append("  ");
		        }

		        if (i == 1) {
		            sb.append(" mmHg");
		        } else if (i == 2) {
		            sb.append("/min");
		        } else if (i == 3) {
		            sb.append("'C");
		        } else if (i == 4) {
		            sb.append("/min");
		        }
		        sb.append("");
		    }

		    return sb.toString();
		}
		public static void getHypertensionControlStatus(int SBP, int DBP) {
		    String status;
		    
		    if (SBP < 120 && DBP < 80) {
		        status = "Well-controlled";
		    } else if (SBP >= 120 && SBP <= 129 && DBP < 80) {
		        status = "Borderline controlled";
		    } else if ((SBP >= 130 && SBP <= 139) || (DBP >= 80 && DBP <= 89)) {
		        status = "Partially controlled";
		    } else if (SBP >= 140 || DBP >= 90) {
		        status = "Poorly controlled";
		    } else if (SBP > 180 || DBP > 120) {
		        status = "Hypertensive crisis";
		    } else {
		        status = "Unknown status ";
		    }

		    String message = String.format("\n...now [ %s ] HTN with current medication", status);
		    GDSEMR_frame.setTextAreaText(8, message);
		}

		
}