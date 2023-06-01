package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.*;				
import java.awt.event.*;
import javax.swing.*;
import je.panse.doro.GDSEMR_frame;

public class EMR_LDL extends JFrame implements ActionListener, KeyListener {
    private JTextField[] inputFields = {new JTextField(10), new JTextField(10), new JTextField(10)};
    private int currentInputFieldIndex = 0;
    
    public EMR_LDL() {
        setTitle("EMR Interface for Lipid Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the frame on the screen
        setLayout(new BorderLayout());
        setSize(400, 200);
        
        String[] labelNames = {"Total Cholesterol", "HDL-C", "Triglyceride", "LDL-C"}; // create an array of label names
        
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputFields = new JTextField[4]; // initialize inputFields array with size 4
        
        for (int i = 0; i < 4; i++) {
            inputFields[i] = new JTextField(10); // create corresponding input field
	            Insets insets = new Insets(0, 10, 0, 0); // 10 pixels left margin
	            inputFields[i].setMargin(insets);
	            JLabel label = new JLabel(labelNames[i] + ": "); // create label with appropriate text
	            label.setHorizontalAlignment(SwingConstants.RIGHT);
            inputPanel.add(label); // add label to inputPanel
	            inputPanel.add(inputFields[i]); // add input field to inputPanel
	            inputFields[i].addKeyListener(this); // add key listener to input field
        }
        
        inputFields[0].requestFocus(); // set focus to the first input field
        inputFields[0].setCaretPosition(inputFields[0].getText().length()); // set cursor position to end of text
        
        add(inputPanel, BorderLayout.CENTER);
    }
        
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (currentInputFieldIndex < 3) {
                currentInputFieldIndex++;
                inputFields[currentInputFieldIndex].requestFocus(); // set focus to next input field
                inputFields[currentInputFieldIndex].setCaretPosition(inputFields[currentInputFieldIndex].getText().length()); // set cursor position to end of text
            } else {
            	String result = ("TC-HDL-Tg-LDL [ " 
                	    + inputFields[0].getText() + " - " 
                	    + inputFields[1].getText() + " - " 
                	    + inputFields[2].getText() + " - " 
                	    + inputFields[3].getText()+ " ] mg/dL");
		        System.out.println(" result" + result);
		        GDSEMR_frame.setTextAreaText(5,"\n" + result);
				GDSEMR_frame.setTextAreaText(9, "\n#  " + result);
				dispose();
            }
        }
    }
    
    // Other methods of KeyListener are not used, but need to be implemented because of the interface
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        EMR_LDL frame = new EMR_LDL();
        frame.setVisible(true);
    }


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
