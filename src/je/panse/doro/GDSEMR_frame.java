package je.panse.doro;
import java.awt.BorderLayout;	
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import je.panse.doro.listner.buttons.BlendColors;

public class GDSEMR_frame {
	public static JFrame frame = new JFrame("Multiple TextAreas Example");
	public static JTextArea[] textAreas = new JTextArea[10];
	public static JTextArea tempOutputArea = new JTextArea();
	public static String[] titles =  { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };

   public GDSEMR_frame() throws Exception {
		      createAndShowGUI();
		   }

   private void createAndShowGUI() throws Exception {
      // Create the JFrame
      frame.setSize(1200, 800); // Set JFrame size to 800x600
      // Create the center panel and set its layout
      JPanel centerPanel = new JPanel(new GridLayout(5, 2));
      centerPanel.setPreferredSize(new Dimension(900,800)); // Set West panel width to 300 pixels

      // Create the tempOutputArea and add it to the center panel
      tempOutputArea.setEditable(true); // make the output area read-only

      // Create the west panel and set its layout
      JPanel westPanel = new JPanel(new BorderLayout());
      westPanel.setPreferredSize(new Dimension(400, westPanel.getHeight())); // Set West panel width to 300 pixels
      westPanel.add(tempOutputArea, BorderLayout.WEST); 
      JScrollPane outputScrollPane = new JScrollPane(tempOutputArea);
      outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      westPanel.add(outputScrollPane); // Add the output scrollPane to the west panel

   // Create the north panel and add the buttons
      JPanel northPanel = new GDSEMR_ButtonPanel("north");
      JPanel southPanel = new GDSEMR_ButtonPanel("south");
      
      // Create the textAreas and add them to the panel
      for (int i = 0; i < textAreas.length; i++) {
	         textAreas[i] = new JTextArea();
	         String inputData = titles[i] + "\t" + " ";
	   	         textAreas[i].setLineWrap(true); // enable line wrapping
	   	         textAreas[i].setText(inputData);
	   	         textAreas[i].setCaretPosition(0); // ensure that the JScrollPane knows the preferred size
	   	         // Create background colors
	   	         BlendColors.blendColors(textAreas[i],tempOutputArea, i);

	         // Wrap the JTextArea in a JScrollPane
	         JScrollPane scrollPane = new JScrollPane(textAreas[i]);
	         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	         centerPanel.add(scrollPane); // Add the scrollPane to the panel

	         GDSEMR_DocumentListner documentListener = new GDSEMR_DocumentListner(textAreas, tempOutputArea);
					for (int j = 0; j < textAreas.length; j++) {
					     textAreas[i].getDocument().addDocumentListener(documentListener);
					}

			  // Add the center and west panels to the main JFrame
			  frame.add(centerPanel);
			  frame.add(westPanel, BorderLayout.WEST);
			  frame.add(northPanel, BorderLayout.NORTH);
			  frame.add(southPanel, BorderLayout.SOUTH);

			  // Set the JFrame properties
			  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			  frame.pack();
			  frame.setVisible(true);
      }
   }

   public static void saveEachTextAreas(int j, String eText) throws Exception {
			System.out.println(" saveEachTextAreas  eText >>> " + eText);
			System.out.println("textAreas[j]: " + textAreas[j].getText());

			textAreas[j].append(textAreas[j].getText() +"\n");
	}

	public static void main(String[] args) throws Exception {
        new GDSEMR_frame();
	//        EMR_east_buttons_obj.main(null);
   }
}