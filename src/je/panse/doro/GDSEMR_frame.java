package je.panse.doro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import je.panse.doro.listner.IndentedTextArea;
import je.panse.doro.listner.ListenerTextAreaChange;
import je.panse.doro.listner.buttons.BlendColors;
import je.panse.doro.listner.buttons.ButtonPanel;
import je.panse.doro.listner.buttons.EMR_B_1entry;
import je.panse.doro.samsara.EMR_east_buttons_obj;

public class GDSEMR_frame extends JFrame {
    protected static JTextArea tempOutputArea = new JTextArea();
    protected static JTextArea textArea = new JTextArea();
    protected static JTextArea[] textAreas = new IndentedTextArea[10];
    protected static String[] titles = { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };
    protected static JPanel centerPanel = new JPanel(new GridLayout(5, 2));
    
    public GDSEMR_frame() throws Exception {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle("My Frame");
	    setSize(1200, 1200/12*9);
	    
	    // Create Panel	    
//	    BoxLayout boxLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
//	    centerPanel.setLayout(boxLayout);
	    Dimension maxCenterPanelSize = getContentPane().getSize();
	    centerPanel.setMaximumSize(maxCenterPanelSize);

	    // Create West panel with tempOutputArea
	    tempOutputArea.setText("");
        tempOutputArea.setPreferredSize(new Dimension(550, 1000));
        add(new JScrollPane(tempOutputArea), BorderLayout.WEST);

        // Create Center panel with 10 textAreas
        for (int i = 0; i < textAreas.length; i++) {
            // Create each JTextArea and wrap it in a JScrollPane
            String inputData = titles[i] + "\t";
            textAreas[i] = new IndentedTextArea(); // Initialize the JTextArea object
            textAreas[i].setText(inputData);
            JScrollPane scrollPane = new JScrollPane(textAreas[i]);

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

	public static void call_preform(int i, String text) {
		textAreas[i].setText(text);	
	}

	public static void main(String[] args) throws Exception {
        new GDSEMR_frame();
        EMR_east_buttons_obj.main(null);;
        
    }
}
