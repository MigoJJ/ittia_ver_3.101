package je.panse.doro.fourgate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.entry.EntryDir;

public class EMR_FU_hypercholestrolemiaEdit extends JFrame {
    private static final int NUM_TEXT_AREAS = 10;
    private JTextArea[] textAreas;

    public EMR_FU_hypercholestrolemiaEdit() {

			for (int i = 0; i < 10; i++) {
				
			    // Read the contents of the file
			    String fileName = EntryDir.homeDir + "/fourgate/hypercholesterolemia/textarea" + (i);
			    String text = "";
			    try (Scanner scanner = new Scanner(new File(fileName))) {
			        while (scanner.hasNextLine()) {
			            text += scanner.nextLine() + "\n";
			        }
			    } catch (FileNotFoundException ex) {
			        System.err.println("Failed to read file " + fileName + ": " + ex.getMessage());
			    }
			    // Set the text of the corresponding JTextArea
			
			    try {
					GDSEMR_frame.call_preform(i,text);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
    }
                    
    public static void main(String[] args) {
        new EMR_FU_hypercholestrolemiaEdit();
    }
}
