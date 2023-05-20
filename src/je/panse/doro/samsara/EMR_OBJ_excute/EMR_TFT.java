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
	    frame.setSize(400, 300);

	    JPanel inputPanel = new JPanel(new GridLayout(labels.length, 2));
	    inputFields = new JTextField[labels.length];

	    for (int i = 0; i < labels.length; i++) {
	        JLabel label = new JLabel(labels[i]);
	        label.setHorizontalAlignment(SwingConstants.RIGHT);
	        inputPanel.add(label);

	        inputFields[i] = new JTextField();
	        inputFields[i].setHorizontalAlignment(JTextField.CENTER);
	        inputFields[i].addActionListener(this);
	        inputFields[i].addKeyListener(this);
	        inputFields[i].setPreferredSize(new Dimension(120, 50));
	        inputPanel.add(inputFields[i]);
	    }

	    JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

	    JButton[] buttons = { new JButton("Clear"), new JButton("Save"), new JButton("Quit") };
	    String[] buttonActions = { "Clear", "Save", "Quit" };

	    for (int i = 0; i < buttons.length; i++) {
	        buttons[i].addActionListener(this);
	        buttonPanel.add(buttons[i]);
	        buttons[i].setActionCommand(buttonActions[i]);
	    }

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
            for (JTextField inputField : inputFields) {
                inputField.setText("");
            }
            outputArea.setText("");
        } else if (e.getSource() == saveButton) {
        } else if (e.getSource() == quitButton) {
            frame.dispose();
        } else {
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
        String[] labels = { "T3 (ug/dL)", "free-T4 (ug/dL)", "TSH (mIU/ml)", "Ab_TSH", "Ab_Mic", "Ab_Tg" };
        double[] ranges = { 0.90, 10.6, 0.25, 0.8, 34, 115 };
        double[] upperLimits = { 2.5, 19.4, 5.00, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE };
        String[] units = { "", "", "", "IU/L", "IU/mL", "IU/mL" };

        StringBuilder outputText = new StringBuilder("\n   T3 (ug/dL)  free-T4(ug/dL)   TSH(mIU/ml)\n");
        outputText.append("---------------------------------------\n");

        for (int i = 0; i < inputFields.length; i++) {
            JTextField field = inputFields[i];
            String value = field.getText();

            if (value.isEmpty()) {
                // JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            value = String_ArrowChange.compareOriginAndLrange(value, ranges[i], upperLimits[i]);
            outputText.append(String.format("   %s\t", value));
            outputText.append(String.format("[  %s  ]\t%s\n", value, labels[i]));
        }

        GDSEMR_frame.setTextAreaText(5, outputText.toString());
    }


    private void saveResults3() {
        String[] labels = { "T3 (ug/dL)", "free-T4 (ug/dL)", "TSH (mIU/ml)" };
        double[] ranges = { 0.90, 10.6, 0.25 };
        double[] upperLimits = { 2.5, 19.4, 5.00 };

        StringBuilder outputText = new StringBuilder("\n   T3 (ug/dL)  free-T4(ug/dL)   TSH(mIU/ml)\n");
        outputText.append("---------------------------------------\n");

        for (int i = 0; i < inputFields.length; i++) {
            JTextField field = inputFields[i];
            String value = field.getText();

            value = String_ArrowChange.compareOriginAndLrange(value, ranges[i], upperLimits[i]);
            outputText.append(String.format("   %s\t", value));
        }

        outputText.append("\n");

        outputArea.setText(outputText.toString());
        GDSEMR_frame.setTextAreaText(5, outputText.toString());
        frame.dispose();
    }

    
    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new EMR_TFT();
    }
}