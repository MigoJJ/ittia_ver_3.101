package je.panse.doro;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import je.panse.doro.listner.buttons.BlendColors;
import je.panse.doro.samsara.EMR_east_buttons_obj;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_HbA1c;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_Vitalsign_BP;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_Vitalsign_BP;

public class GDSEMR_frame {
	public static JFrame frame = new JFrame("GDS EMR Interface for Physician");
	public static JTextArea[] textAreas = new JTextArea[10];
	public static JTextArea tempOutputArea = new JTextArea();
	public static String[] titles =  { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };

   public GDSEMR_frame() throws Exception {
		      createAndShowGUI();
		   }

   private void createAndShowGUI() throws Exception {
      // Create the JFrame
      frame.setSize(1200, 1200); // Set JFrame size to 800x600
      frame.setLocation(50, 50);

      // Create the center panel and set its layout
      JPanel centerPanel = new JPanel(new GridLayout(5, 2));
      centerPanel.setPreferredSize(new Dimension(900,1000)); // Set West panel width to 300 pixels

      // Create the tempOutputArea and add it to the center panel
      tempOutputArea.setEditable(true); // make the output area read-only

      // Create the west panel and set its layout
      JPanel westPanel = new JPanel(new BorderLayout());
      westPanel.setPreferredSize(new Dimension(500, westPanel.getHeight())); // Set West panel width to 400 pixels
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
			String inputData = (titles[i] + "\t" + " ");
			textAreas[i].setLineWrap(true); // enable line wrapping
			// textAreas[i].append(inputData);
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
		textAreas[i].addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            JTextArea source = (JTextArea) e.getSource();
		            String text = source.getText();
		            System.out.println("Double-clicked on: " + text);
		            try {
						GDSEMR_fourgate.main(text);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		    }
		});
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

	public static void setTextAreaText(int i,String string) {
       textAreas[i].append(string);
   }

	
	public static void main(String[] args) throws Exception {
        new GDSEMR_frame();
	//        EMR_east_buttons_obj.main(null);
        EMR_east_buttons_obj.main(null);
        EMR_Vitalsign_BP.main(null);
        EMR_HbA1c.main(null);
        
   }
}
