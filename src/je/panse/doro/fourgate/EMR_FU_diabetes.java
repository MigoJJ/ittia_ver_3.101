package je.panse.doro.fourgate;

import java.awt.BorderLayout;	
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.entry.EntryDir;

public class EMR_FU_diabetes extends JFrame {
    private static final int NUM_TEXT_AREAS = 10;
    private JTextArea[] textAreas;

    public EMR_FU_diabetes() {

			for (int i = 0; i < 10; i++) {
				
			    // Read the contents of the file
			    String fileName = EntryDir.homeDir + "/fourgate/diabetes/textarea" + (i + 1);
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
        new EMR_FU_diabetes();
    }
}
