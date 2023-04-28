package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.*;					
import java.awt.event.*;
import javax.swing.*;
import je.panse.doro.GDSEMR_frame;

public class EMR_LpaApoB extends JFrame implements ActionListener, KeyListener {
    private JTextField[] inputFields = {new JTextField(10), new JTextField(10)};
    private int currentInputFieldIndex = 0;
    
    public EMR_LpaApoB() {
        setTitle("EMR Interface for Lp(a) ApoB Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(400, 200);
        
        String[] labelNames = {"Lipoprotein(a)", "ApoproteinB"}; // fixed size of labelNames
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputFields = new JTextField[2]; // removed redundant initialization
        
        for (int i = 0; i < labelNames.length; i++) { // use labelNames.length in loop condition
            inputFields[i] = new JTextField(10);
            Insets insets = new Insets(0, 10, 0, 0);
            inputFields[i].setMargin(insets);
            JLabel label = new JLabel(labelNames[i] + ": ");
            label.setHorizontalAlignment(SwingConstants.RIGHT);
            inputPanel.add(label);
            inputPanel.add(inputFields[i]);
            inputFields[i].addKeyListener(this);
        }
        
        inputFields[0].requestFocus();
        inputFields[0].setCaretPosition(inputFields[0].getText().length());
        
        add(inputPanel, BorderLayout.CENTER);
        
        setVisible(true); // added setVisible call to make frame visible
    }
        
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (currentInputFieldIndex < inputFields.length - 1) { // use inputFields.length - 1 to avoid ArrayIndexOutOfBoundsException
                currentInputFieldIndex++;
                inputFields[currentInputFieldIndex].requestFocus(); // set focus to next input field
                inputFields[currentInputFieldIndex].setCaretPosition(inputFields[currentInputFieldIndex].getText().length()); // set cursor position to end of text
            } else {
            	String result = "\nLiporpotein(a)  " + String.format("[ %3.1f ]", Double.parseDouble(inputFields[0].getText())) + " ≤ 30.0 mg/dL"
            			+  "\nApoLiporpotein(B) " + String.format("[ %3.1f ]", Double.parseDouble(inputFields[1].getText())) + "  M:46-174   F:46-142 mg/dL";
                System.out.println(" result" + result);
                GDSEMR_frame.setTextAreaText(5, result);
                dispose(); // dispose after updating text area
            }
        }
    }

    
    // Other methods of KeyListener are not used, but need to be implemented because of the interface
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        EMR_LpaApoB frame = new EMR_LpaApoB();
        frame.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
