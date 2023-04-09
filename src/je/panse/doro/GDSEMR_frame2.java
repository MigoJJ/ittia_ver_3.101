package je.panse.doro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import je.panse.doro.listner.IndentedTextArea;
import je.panse.doro.listner.ListenerTextAreaChange;
import je.panse.doro.listner.buttons.BlendColors;
import je.panse.doro.listner.buttons.ButtonPanel;
import je.panse.doro.listner.buttons.EMR_B_clear;
import je.panse.doro.listner.buttons.EMR_Button_Excute;
	
public class GDSEMR_frame2 extends JFrame {
    public GDSEMR_frame2() throws Exception {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("My Frame");
        setSize(1500, 1500/12*9);

        // Create West panel with tempOutputArea
        JTextArea tempOutputArea = new JTextArea();
        tempOutputArea.setPreferredSize(new Dimension(500, 400));
        add(new JScrollPane(tempOutputArea), BorderLayout.WEST);

        // Create Center panel with 9 textAreas
        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        IndentedTextArea[] textAreas = new IndentedTextArea[10];
//        JTextArea[] textAreas = new JTextArea[10];
        String[] titles = { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };
		        for (int i = 0; i < textAreas.length; i++) {
		        	textAreas[i] = new IndentedTextArea();
//					textAreas[i] = new JTextArea();
					String inputData = titles[i] + "\t";
					textAreas[i].setText(inputData);
					centerPanel.add(textAreas[i]);
		
					JTextArea textArea = textAreas[i];
					ListenerTextAreaChange listener = new ListenerTextAreaChange(textArea, tempOutputArea);
					textArea.getDocument().addDocumentListener(listener);
					// Create background colors
					BlendColors.blendColors(textArea,tempOutputArea, i);
		        }
			// Create button panel
			ButtonPanel buttonPanel = new ButtonPanel();
			add(buttonPanel, BorderLayout.NORTH);
			
			ButtonPanel buttonPanel2 = new ButtonPanel();
			add(buttonPanel2, BorderLayout.SOUTH);
		        
		   // Add scroll pane to center panel
			JScrollPane scrollPane = new JScrollPane(centerPanel);
			add(scrollPane, BorderLayout.CENTER);
			setVisible(true);
    }
    
    public static void main(String[] args) throws Exception {
        new GDSEMR_frame2();
    }
}
