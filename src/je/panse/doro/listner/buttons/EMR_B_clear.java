package je.panse.doro.listner.buttons;

import javax.swing.JTextArea;
import je.panse.doro.GDSEMR_frame;

	public class EMR_B_clear extends GDSEMR_frame {
	    public EMR_B_clear() throws Exception {
			super();
			// TODO Auto-generated constructor stub
		}

		public static void clearTextAreas(JTextArea[] textAreas, JTextArea tempOutputArea) {
	        for (int i = 0; i < textAreas.length; i++) {
	            textAreas[i].setText("");
	        }
	        tempOutputArea.setText("");
	    }
	}