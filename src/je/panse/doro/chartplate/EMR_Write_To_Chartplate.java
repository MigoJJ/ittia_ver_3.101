package je.panse.doro.chartplate;

import java.awt.Toolkit;		
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTextArea;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.entry.EntryDir;
import je.panse.doro.samsara.comm.File_copy;

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
			    	line = EMR_ChangeString.EMR_ChangeString(line);
		    	} else {
		    	}
		    	if (!line.trim().isEmpty() && sb.indexOf(line) == -1) {
			    	  sb.append(line).append("\n");
			    }
		    }
		    // Set the text of the JTextArea to the filtered text
		    tempOutputArea.setText(sb.toString());
		    System.out.println("sb.toString()" +sb.toString());

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
	    	String filePath = EntryDir.homeDir + "/tripikata/rescue/backup";
			String newFilePath = EntryDir.homeDir + "/tripikata/rescue/backuptemp";

	    	try {
		        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		        writer.write(textToSave);
		        writer.close();
	    	} catch (IOException e) {
	    	    e.printStackTrace();
	    	}
          File_copy.main(filePath, newFilePath);
	    }
	}
