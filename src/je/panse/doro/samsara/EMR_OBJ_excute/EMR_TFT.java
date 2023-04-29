package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.Dimension;	
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import je.panse.doro.GDSEMR_frame;

public class EMR_TFT implements ActionListener, KeyListener {
	private JFrame frame = new JFrame("EMR TFT");
	private JTextArea outputArea;
	private JButton clearButton, saveButton, quitButton;
	private String[] labels = {"T3 : ", "free-T4 : ", "TSH : ", "Anti-TSH-R-Ab : ","Anti-mic-Ab : ","Anti-Tg-Ab : "};
	private JTextField[] inputFields = new JTextField[labels.length];
	
    public EMR_TFT() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame. setSize(400, 300);

        JPanel inputPanel = new JPanel(new GridLayout(6, 1));
        for (int i = 0; i < labels.length; ++i) {
            inputFields[i] = new JTextField();
            inputFields[i].setPreferredSize(new Dimension(120, 50)); // Set the width and height here
        }

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            inputPanel.add(label);
            inputFields[i] = new JTextField();
            inputFields[i].setHorizontalAlignment(JTextField.CENTER);
            inputFields[i].addActionListener(this);
            inputFields[i].addKeyListener(this);
            inputFields[i].setPreferredSize(new Dimension(50, 30));
            inputPanel.add(inputFields[i]);
        }

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);

        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(scrollPane);

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton) {
            // clear button was pressed
            for (JTextField inputField : inputFields) {
                inputField.setText("");
            }
            outputArea.setText("");
        } else if (e.getSource() == saveButton) {
            // save button was pressed
            // implement save functionality here
        } else if (e.getSource() == quitButton) {
            // quit button was pressed
            frame.dispose();
        } else {
            // one of the input fields was edited
            // implement corresponding functionality here
        }
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        Object source = e.getSource();

        // Check if Enter key is pressed and the source is an input field
        if (keyCode == KeyEvent.VK_ENTER && Arrays.asList(inputFields).contains(source)) {
			JTextField inputField = (JTextField) source;
			int inputIndex = Arrays.asList(inputFields).indexOf(inputField);
				// Check if the text field is not empty
			if (!inputField.getText().isEmpty()) {
			    int nextIndex = inputIndex + 1;
			    if (nextIndex < inputFields.length) {
			        inputFields[nextIndex].requestFocusInWindow();
			    } 
			}
			if (inputIndex == 3 && inputField.getText().isEmpty()) {
			    // do something if input is empty for the 4th field
			    saveResults3();
			} else {
			    saveResults6();
			    // do something else for other input fields
			}
    	}
    }

    private void saveResults6() {
        for (JTextField field : inputFields) {
            if (field.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        String outputText = String.format(
        	    "\nT3 :\t%.2f (ug/dL)\n" +
        	    "free-T4 :\t%.2f (ug/dL)\n" +
        	    "TSH :\t%.2f (mIU/ml)\n" +
        	    "\tAnti-TSH-R-Ab: %.2f (IU/L)\n" +
        	    "\tAnti-Thyrogobulin Ab: %.1f (ng/mL)\n"+
        	    "\tAnti-microsomal Ab: %.1f ( IU/mL)\n",
        	    Float.parseFloat(inputFields[0].getText()),
        	    Float.parseFloat(inputFields[1].getText()),
        	    Float.parseFloat(inputFields[2].getText()),
        	    Float.parseFloat(inputFields[3].getText()),
        	    Float.parseFloat(inputFields[4].getText()),
        	    Float.parseFloat(inputFields[5].getText())
        	    // add one more floating point number for the missing specifier
        	);
				outputArea.setText(outputText);
				GDSEMR_frame.setTextAreaText(5, outputText);
    }   

    private void saveResults3() {
        String outputText = String.format(
        		"\nT3 :\t%.2f (ug/dL)\n" +
              "free-T4 :\t%.2f (ug/dL)\n" +
              "TSH :\t%.2f (mIU/ml)\n" +
        	    Float.parseFloat(inputFields[0].getText()),
        	    Float.parseFloat(inputFields[1].getText()),
        	    Float.parseFloat(inputFields[2].getText())
        	    // add one more floating point number for the missing specifier
        	);
				outputArea.setText(outputText);
				GDSEMR_frame.setTextAreaText(5, outputText);
				frame.dispose();
            }
    
    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new EMR_TFT();
    }
}