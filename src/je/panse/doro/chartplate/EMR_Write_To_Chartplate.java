package je.panse.doro.chartplate;

import java.awt.Toolkit;	
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import javax.swing.JTextArea;
import je.panse.doro.GDSEMR_frame;

public class EMR_Write_To_Chartplate extends GDSEMR_frame {
		public EMR_Write_To_Chartplate() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

		public static void textAreaAppend(JTextArea tempOutputArea) throws IOException {

			tempOutputArea.setText(tempOutputArea.getText());
			
		    // Remove empty and duplicate lines
		    String[] lines = tempOutputArea.getText().split("\n");
		    StringBuilder sb = new StringBuilder();
		    for (String line : lines) {
//	    		 line = EMR_ChangeString.code_select(line);
		    	
				line.replace(":d", "Diabetes mellitus");
				line.replace(":dr","DM without Retinopathy");
				line.replace(":dn","DM with Nephropathy");
				line.replace(":dp","Diabetes mellitus");
		    	
	    		 if (!line.trim().isEmpty() && sb.indexOf(line) == -1) {
			    	  sb.append(line).append("\n");
			      }
		    }
		    // Set the text of the JTextArea to the filtered text
		    tempOutputArea.setText(sb.toString());
		    
		    // Copy updated text to clipboard
		    copyToClipboard(tempOutputArea);

		}

	    public static void copyToClipboard(JTextArea textArea) {
	        StringSelection stringSelection = new StringSelection(textArea.getText());
	        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	        clipboard.setContents(stringSelection, null);
	    }
			
	}
