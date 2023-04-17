package je.panse.doro;


import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MultipleTextAreas {
    protected static String[] titles = { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };

   public static void main(String[] args) {

	// Create the JFrame and JPanel
	   JFrame frame = new JFrame("Multiple TextAreas Example");
	   JPanel centerPanel = new JPanel(new GridLayout(5, 2));
	   centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	   frame.add(centerPanel, BorderLayout.CENTER); // use BorderLayout and add to CENTER position

	   // Create the tempOutputArea and add it to the panel
	   JTextArea tempOutputArea = new JTextArea();
	   tempOutputArea.setEditable(false); // make the output area read-only
	   JScrollPane outputScrollPane = new JScrollPane(tempOutputArea);
	   outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	   frame.add(outputScrollPane, BorderLayout.WEST); // add to WEST position

	   // Create the textAreas and add them to the panel
	   JTextArea[] textAreas = new JTextArea[10];
	   
      
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
                  outputData += textAreas[i].getText();
               }
               tempOutputArea.setText(outputData);
            }
         });
      }


      
      // Set the JFrame properties
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);
   }
}
