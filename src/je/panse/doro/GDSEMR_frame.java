package je.panse.doro;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import je.panse.doro.chartplate.EMR_ChangeString;
import je.panse.doro.chartplate.EMR_Write_To_Chartplate;
import je.panse.doro.chartplate.EMR_organize_titles;

public class GDSEMR_frame {
   public GDSEMR_frame() {
		      createAndShowGUI();
		   }

   private void createAndShowGUI() {
      // Create the JFrame
      JFrame frame = new JFrame("Multiple TextAreas Example");
      frame.setSize(1200, 800); // Set JFrame size to 800x600

      // Create the center panel and set its layout
      JPanel centerPanel = new JPanel(new GridLayout(5, 2));
      centerPanel.setPreferredSize(new Dimension(900,800)); // Set West panel width to 300 pixels

      // Create the tempOutputArea and add it to the center panel
      JTextArea tempOutputArea = new JTextArea();
      tempOutputArea.setEditable(true); // make the output area read-only

      // Create the west panel and set its layout
      JPanel westPanel = new JPanel(new BorderLayout());
      westPanel.setPreferredSize(new Dimension(400, westPanel.getHeight())); // Set West panel width to 300 pixels
      westPanel.add(tempOutputArea, BorderLayout.WEST); 
      JScrollPane outputScrollPane = new JScrollPane(tempOutputArea);
      outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      westPanel.add(outputScrollPane); // Add the output scrollPane to the west panel

      // Create the textAreas and add them to the panel
      JTextArea[] textAreas = new JTextArea[10];
      String[] titles =  { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };
      for (int i = 0; i < textAreas.length; i++) {
         textAreas[i] = new JTextArea();
         String inputData = titles[i] + "\t";
         textAreas[i].setLineWrap(true); // enable line wrapping
         textAreas[i].setText(inputData);
         textAreas[i].setCaretPosition(0); // ensure that the JScrollPane knows the preferred size
         
         // Wrap the JTextArea in a JScrollPane
         JScrollPane scrollPane = new JScrollPane(textAreas[i]);
         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         centerPanel.add(scrollPane); // Add the scrollPane to the panel
         
         // Add a DocumentListener to each JTextArea that updates the tempOutputArea
         textAreas[i].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
               updateOutputArea();
            }
            public void removeUpdate(DocumentEvent e) {
               updateOutputArea();
            }
            public void insertUpdate(DocumentEvent e) {
               updateOutputArea();
            }
         
         public void updateOutputArea() {
        	   // Iterate through the textAreas and append their text to the tempOutputArea
        	   String outputData = "";
        	   for (int i = 0; i < textAreas.length; i++) {
        	      String text = textAreas[i].getText();
        	      text = EMR_ChangeString.EMR_ChangeString(text); // Apply EMR_ChangeString method
        	      text = EMR_organize_titles.EMR_organize_titles(text); // Apply EMR_organize_titles method
        	      outputData += ("\n" + text);
        	   }
        	   tempOutputArea.setText(outputData);
	        	   try {
					EMR_Write_To_Chartplate.textAreaAppend(tempOutputArea);
	        	   } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	        	   }
        		}
         });
      // Add the center and west panels to the main JFrame
      frame.add(centerPanel);
      frame.add(westPanel, BorderLayout.WEST);
      // Set the JFrame properties
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
      	}
   }
         
	public static void main(String[] args) throws Exception {
        new GDSEMR_frame();
//        EMR_east_buttons_obj.main(null);
   }
}