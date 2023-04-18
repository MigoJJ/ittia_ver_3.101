package je.panse.doro;
<<<<<<< HEAD

import java.awt.BorderLayout;	
=======
import java.awt.BorderLayout;
>>>>>>> refs/remotes/origin/feature
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import je.panse.doro.listner.buttons.BlendColors;
<<<<<<< HEAD
import je.panse.doro.listner.buttons.ButtonPanel;
import je.panse.doro.listner.buttons.EMR_B_1entry;
import je.panse.doro.samsara.EMR_east_buttons_category;
import je.panse.doro.samsara.EMR_east_buttons_obj;
=======
>>>>>>> refs/remotes/origin/feature

<<<<<<< HEAD
public class GDSEMR_frame extends JFrame {
    protected static JTextArea tempOutputArea = new JTextArea();
    protected static JTextArea textArea = new JTextArea();
    protected static JTextArea[] textAreas = new IndentedTextArea[10];
    protected static String[] titles = { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };
    protected static JPanel centerPanel = new JPanel(new GridLayout(5, 2));
    
    public GDSEMR_frame() throws Exception {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle("My Frame");
	    setSize(100, 100);
	    
	    // Create Panel	    
//	    BoxLayout boxLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
//	    centerPanel.setLayout(boxLayout);
	    Dimension maxCenterPanelSize = getContentPane().getSize();
	    centerPanel.setMaximumSize(maxCenterPanelSize);
=======
public class GDSEMR_frame {
	public static JFrame frame = new JFrame("Multiple TextAreas Example");
	public static JTextArea[] textAreas = new JTextArea[10];
	public static JTextArea tempOutputArea = new JTextArea();
	public static String[] titles =  { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };

   public GDSEMR_frame() throws Exception {
		      createAndShowGUI();
		   }
>>>>>>> refs/remotes/origin/feature

<<<<<<< HEAD
	    // Create West panel with tempOutputArea
	    tempOutputArea.setText("");
        tempOutputArea.setPreferredSize(new Dimension(500, 1000));
        add(new JScrollPane(tempOutputArea), BorderLayout.WEST);
=======
   private void createAndShowGUI() throws Exception {
      // Create the JFrame
      frame.setSize(1200, 800); // Set JFrame size to 800x600
      // Create the center panel and set its layout
      JPanel centerPanel = new JPanel(new GridLayout(5, 2));
      centerPanel.setPreferredSize(new Dimension(900,800)); // Set West panel width to 300 pixels
>>>>>>> refs/remotes/origin/feature

<<<<<<< HEAD
        // Create Center panel with 10 textAreas
        for (int i = 0; i < textAreas.length; i++) {
            // Create each JTextArea and wrap it in a JScrollPane
            String inputData = titles[i] + "\t";
            textAreas[i] = new IndentedTextArea(); // Initialize the JTextArea object
            textAreas[i].setPreferredSize(new Dimension(400, 100)); // Set preferred size
            textAreas[i].setText(inputData);
            JScrollPane scrollPane = new JScrollPane(textAreas[i]);
=======
      // Create the tempOutputArea and add it to the center panel
      tempOutputArea.setEditable(true); // make the output area read-only
>>>>>>> refs/remotes/origin/feature

<<<<<<< HEAD
            // Add a document listener and blend colors to each JTextArea
            ListenerTextAreaChange listener = new ListenerTextAreaChange(textAreas[i], tempOutputArea);
            textAreas[i].getDocument().addDocumentListener(listener);
            BlendColors.blendColors(textAreas[i], tempOutputArea, i);

            // Add the scroll pane to the center panel
            centerPanel.add(scrollPane);
        }
        
        // Wrap the center panel in a JScrollPane and add it to the frame
        JScrollPane centerScrollPane = new JScrollPane(centerPanel);
        add(centerScrollPane, BorderLayout.CENTER);
        // Create button panel
        ButtonPanel buttonPanel = new ButtonPanel("north");
        add(buttonPanel, BorderLayout.NORTH);
        // Create second button panel
        ButtonPanel buttonPanel2 = new ButtonPanel("south");
        add(buttonPanel2, BorderLayout.SOUTH);
        
        pack();
        setVisible(true);
    	}
        //	call button number
	public static void callbutton(int noButton, String panelLocation) throws Exception {
		System.out.println("noButton executed for button " + noButton + ">>>" + panelLocation);
			EMR_B_1entry.EMR_B_selection(noButton,panelLocation);
	}
=======
      // Create the west panel and set its layout
      JPanel westPanel = new JPanel(new BorderLayout());
      westPanel.setPreferredSize(new Dimension(400, westPanel.getHeight())); // Set West panel width to 300 pixels
      westPanel.add(tempOutputArea, BorderLayout.WEST); 
      JScrollPane outputScrollPane = new JScrollPane(tempOutputArea);
      outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      westPanel.add(outputScrollPane); // Add the output scrollPane to the west panel
>>>>>>> refs/remotes/origin/feature

   // Create the north panel and add the buttons
      JPanel northPanel = new GDSEMR_ButtonPanel("north");
      JPanel southPanel = new GDSEMR_ButtonPanel("south");
      
      // Create the textAreas and add them to the panel
      for (int i = 0; i < textAreas.length; i++) {
	         textAreas[i] = new JTextArea();
	         String inputData = titles[i] + "\t";
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
	   
	public static void main(String[] args) throws Exception {
        new GDSEMR_frame();
<<<<<<< HEAD
        EMR_east_buttons_obj.main(null);
        EMR_east_buttons_category.main(null);
        
    }
}
=======
	//        EMR_east_buttons_obj.main(null);
   }
}
>>>>>>> refs/remotes/origin/feature
