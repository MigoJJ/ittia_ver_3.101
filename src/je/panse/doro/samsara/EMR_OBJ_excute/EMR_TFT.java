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
import je.panse.doro.samsara.comm.String_ArrowChange;

public class EMR_TFT implements ActionListener, KeyListener {
	private JFrame frame = new JFrame("EMR TFT");
	private JTextArea outputArea;
	private JButton clearButton, saveButton, quitButton;
	private String[] labels = {"T3 : ", "free-T4 : ", "TSH : ", "Anti-TSH-R-Ab : ","Anti-mic-Ab : ","Anti-Tg-Ab : "};
	private JTextField[] inputFields = new JTextField[labels.length];
	
    public EMR_TFT() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null); // center the frame on the screen
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
        
        String T3 = inputFields[0].getText(); 
        T3 = String_ArrowChange.compareOriginAndLrange(T3, 0.90, 2.5);
        String free_T4 = inputFields[1].getText(); 
        free_T4 = String_ArrowChange.compareOriginAndLrange(free_T4, 10.6, 19.4);
        String TSH = inputFields[2].getText(); 
        TSH = String_ArrowChange.compareOriginAndLrange(TSH, 0.25, 5.00);
        String Ab_TSH = inputFields[3].getText(); 
        Ab_TSH = String_ArrowChange.compareOriginAndLrange(Ab_TSH, 0.8);
        String Ab_Tg = inputFields[4].getText(); 
        Ab_Tg = String_ArrowChange.compareOriginAndLrange(Ab_Tg, 115);
        String Ab_Mic = inputFields[5].getText(); 
        Ab_Mic = String_ArrowChange.compareOriginAndLrange(Ab_Mic, 34);

        
        String outputText = String.format(
        		"\n   "+
        	    "%-12s\t%-12s\t%-12s\n" +
        	    "------------------------------------------------------------------\n" +
        	    "   %-12s\t%-12s\t\t%-12s\n\n" +
        	    "%-12s\t%-12s\n" +
        	    "%-12s\t%-12s\n" +
        	    "%-12s\t%-12s\n",
        	    "T3 (ug/dL)", "free-T4(ug/dL)", "TSH(mIU/ml)",
        	    T3, free_T4, TSH,
        	    "   [ "+Ab_TSH, " ] Anti-TSH-R-Ab (<1.75 IU/L)",
        	    "   [ "+Ab_Tg, " ] Anti-Thyroglobulin Ab (<115.00 IU/mL)",
        	    "   [ "+Ab_Mic, " ] Anti-microsomal Ab (<9.0 IU/mL)"
        	);
		GDSEMR_frame.setTextAreaText(5, outputText);
    }   

    private void saveResults3() {

        String T3 = inputFields[0].getText(); 
        T3 = String_ArrowChange.compareOriginAndLrange(T3, 1.23, 3.08);
        String free_T4 = inputFields[1].getText(); 
        free_T4 = String_ArrowChange.compareOriginAndLrange(free_T4, 10, 19);
        String TSH = inputFields[2].getText(); 
        TSH = String_ArrowChange.compareOriginAndLrange(TSH, 0.4, 4.0);
    	
        String outputText = String.format(
        		"\n   "+
                	    "%-12s\t%-12s\t%-12s\n" +
                	    "------------------------------------------------------------------\n" +
                	    "   %-12s\t%-12s\t\t%-12s\n\n" +
                	    "%-12s\t%-12s\n" +
                	    "%-12s\t%-12s\n" +
                	    "%-12s\t%-12s\n",
                	    "T3 (ug/dL)", "free-T4(ug/dL)", "TSH(mIU/ml)",
                	    T3, free_T4, TSH
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