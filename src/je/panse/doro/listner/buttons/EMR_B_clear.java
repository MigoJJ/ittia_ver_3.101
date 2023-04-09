package je.panse.doro.listner.buttons;

import javax.swing.JTextArea;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.listner.IndentedTextArea;

	public class EMR_B_clear extends GDSEMR_frame {
	    public EMR_B_clear() throws Exception {
			super();
			// TODO Auto-generated constructor stub
		}

		public static void EMR_B_clear(IndentedTextArea[] textAreas, JTextArea tempOutputArea) {
	        for (int i = 0; i < textAreas.length; i++) {
	            textAreas[i].setText("");
	        }
	       tempOutputArea.setText("");
	    }
	}