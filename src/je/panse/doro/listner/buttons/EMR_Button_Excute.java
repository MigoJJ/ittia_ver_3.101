package je.panse.doro.listner.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextArea;
import je.panse.doro.GDSEMR_frame;

public class EMR_Button_Excute extends GDSEMR_frame {
	private static JTextArea textArea;
		
		public EMR_Button_Excute() throws Exception {
			super();
			// TODO Auto-generated constructor stub
		}
		
		public static void clearButton() {
		    // Initialize your text area and clear button
		    textArea = new JTextArea();
		    JButton clearButton = new JButton("Clear");
		    clearButton.addActionListener((ActionListener) new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            clearTextAreas();
		        }
		    });
		}
		private static void clearTextAreas() {
		    // Call the static method from the EMR_B_clear class to clear the text area
		    JTextArea[] textAreas = {textArea};
		    JTextArea tempOutputArea = null; // Replace null with the temporary output area object
		    EMR_B_clear.clearTextAreas(textAreas, tempOutputArea);
		}
		
	}