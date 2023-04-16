package je.panse.doro.chartplate;

import java.awt.TextArea;	
import java.awt.TextArea;

public class EMR_organize_titles {
	public static String EMR_organize_titles(String text) {
		String[] titles = { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>", "A>", "P>", "Comment>" };
		boolean matchFound = false;
		
		for (int i = 0; i < titles.length; i++) { // Start from index 1
			if (text.trim().equals(titles[i])) {
					text = "";
					
				} else {
					
			}
		}
		return text;
	}
}
