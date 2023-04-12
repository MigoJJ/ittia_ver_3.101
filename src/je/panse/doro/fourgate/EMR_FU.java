package je.panse.doro.fourgate;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class EMR_FU {

	public static void main(String[] args) {
		// Create the first JFrame with JTextAreas
		JFrame frame1 = new JFrame("Frame 1");
		frame1.setLocationRelativeTo(null);
		
		JPanel panel1 = new JPanel(new GridLayout(5, 2, 10, 10)); // 5 rows, 2 columns
		panel1.setPreferredSize(new Dimension(600, 500)); // set the preferred size of panel1
		   
		    for (int i = 1; i <= 10; i++) {
		        JTextArea textArea1 = new JTextArea();
		        textArea1.setEditable(true);
		        
				        String changedtextArea = "";
				        changedtextArea = EMR_FU_hypertension.getString(i);
				        textArea1.setText(changedtextArea);
		        
		        
		        panel1.add(textArea1);
		    }
		frame1.getContentPane().add(panel1);
		
		// Create the second JFrame with JTextAreas
		JFrame frame2 = new JFrame("Frame 2");
		JPanel panel2 = new JPanel(new GridLayout(5, 2, 10, 10)); // 5 rows, 2 columns
		panel2.setPreferredSize(new Dimension(600, 500)); // set the preferred size of panel2
		
		for (int i = 1; i <= 10; i++) {
		    JTextArea textArea2 = new JTextArea("TextArea " + i);
		    textArea2.setEditable(true);
		    panel2.add(textArea2);
		}
		frame2.getContentPane().add(panel2);
		
		// Create a button to copy the text from JTextAreas in frame1 to JTextAreas in frame2
		JButton copyButton = new JButton("Copy");
		copyButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        for (int i = 0; i < panel1.getComponentCount(); i++) {
		            JTextArea textArea1 = (JTextArea) panel1.getComponent(i);
		            JTextArea textArea2 = (JTextArea) panel2.getComponent(i);
		            textArea2.setText(textArea1.getText());
		        }
		    }
		});
		frame1.getContentPane().add(copyButton, "South");
		
		    frame1.pack();
		    frame2.pack();
		    frame1.setVisible(true);
		    frame2.setVisible(true);
		}
}

