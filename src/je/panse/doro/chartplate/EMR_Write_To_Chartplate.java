package je.panse.doro.chartplate;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.JTextArea;

import je.panse.doro.GDSEMR_frame;

public class EMR_Write_To_Chartplate extends GDSEMR_frame {
		public static void textAreaAppend(JTextArea tempOutputArea) {

			tempOutputArea.setText(tempOutputArea.getText());
			
		    // Remove empty and duplicate lines
		    String[] lines = tempOutputArea.getText().split("\n");
		    StringBuilder sb = new StringBuilder();
		    for (String line : lines) {
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
