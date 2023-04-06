package je.panse.doro.chartplate;

	import javax.swing.JPanel;
	import javax.swing.JTextArea;

import je.panse.doro.GDSEMR_frame;

	public class EMR_Button_Excute extends GDSEMR_frame {
	    public static void clearTextAreas(JTextArea[] textAreas) {
	        for (int i = 0; i < textAreas.length; i++) {
	            textAreas[i].setText("");
	        }
	    }
	}