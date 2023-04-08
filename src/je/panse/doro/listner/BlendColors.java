package je.panse.doro.listner;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import je.panse.doro.GDSEMR_frame;

public class BlendColors extends GDSEMR_frame {
	public BlendColors() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void blendColors(JTextArea textAreas, int i) throws Exception {
		  int r = 255; // Red value remains constant at 255 for orange color
		  int g = 255 - i * 10; // Green value decreases gradually from 165 to 65
		  int b = 0; // Blue value remains constant at 0 for orange color
		  Color color = new Color(r, g, b);
		  textAreas.setBackground(color);
		  
	  	  Font font = new Font("Arial mono", Font.PLAIN, 13);
		  textAreas.setFont(font);
	}
}
