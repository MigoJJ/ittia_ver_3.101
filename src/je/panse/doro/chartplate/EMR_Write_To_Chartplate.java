package je.panse.doro.chartplate;

import java.awt.Toolkit;	
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
		    	if (line.contains(":")) {
			    	line = EMR_ChangeString.code_select(line);
		    	} else {
		    	}
		    	if (!line.trim().isEmpty() && sb.indexOf(line) == -1) {
			    	  sb.append(line).append("\n");
			    }
		    }
		    // Set the text of the JTextArea to the filtered text
		    tempOutputArea.setText(sb.toString());
		    // Copy updated text to clipboard
		    copyToClipboard(tempOutputArea);
		    callsaveTextToFile(tempOutputArea);
		}

	    public static void copyToClipboard(JTextArea textArea) {
	        StringSelection stringSelection = new StringSelection(textArea.getText());
	        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	        clipboard.setContents(stringSelection, null);
	    }
	    
	    public static void callsaveTextToFile(JTextArea textArea) {
	    	String textToSave = textArea.getText();
//	    	String filePath = "/home/migowj/git/ittia_Version_2.1/src/je/panse/doro/tripikata/rescue/backup";
	    	String filePath = "/home/woon/git/ittia_Version_2.1/src/je/panse/doro/tripikata/rescue/backup";
	    	try {
		        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		        writer.write(textToSave);
		        writer.close();
	    	} catch (IOException e) {
	    	    e.printStackTrace();
	    	}
	    }
	}
