package je.panse.doro;

import java.awt.BorderLayout;			
import java.awt.Dimension;
import java.awt.GridLayout;
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
    protected static IndentedTextArea[] textAreas = new IndentedTextArea[10];
    protected static String[] titles = { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };

    public GDSEMR_frame() throws Exception {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle("My Frame");
	    setSize(1200, 1200/12*9);

        // Create West panel with tempOutputArea
	      // Create the tempOutputArea and add it to the panel
		  JTextArea tempOutputArea = new JTextArea();
				tempOutputArea.setText("");
				tempOutputArea.setEditable(false); // make the output area read-only
				JScrollPane outputScrollPane = new JScrollPane(tempOutputArea);
				outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				tempOutputArea.setPreferredSize(new Dimension(400, 1000));
				add(new JScrollPane(tempOutputArea), BorderLayout.WEST);

        // Create Center panel with 9 textAreas
        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        centerPanel.setPreferredSize(new Dimension(1200, centerPanel.getPreferredSize().height));
        
        
        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new IndentedTextArea();
            String inputData = titles[i] + "\t";
            textAreas[i].setLineWrap(true); // enable line wrapping
//            textAreas[i].setPreferredSize(new Dimension(450, 200)); // set fixed width and height
            textAreas[i].setText(inputData);
            textAreas[i].setCaretPosition(0); // ensure that the JScrollPane knows the preferred size
            
         // Wrap the JTextArea in a JScrollPane
            JScrollPane scrollPane = new JScrollPane(textAreas[i]);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            centerPanel.add(scrollPane); // Add the scrollPane to the panel

            textArea = textAreas[i];
            ListenerTextAreaChange listener = new ListenerTextAreaChange(textArea, tempOutputArea);
            textAreas[i].getDocument().addDocumentListener(listener);
            // Create background colors
            BlendColors.blendColors(textAreas[i], tempOutputArea, i);
        }

        // Create button panel
        ButtonPanel buttonPanel = new ButtonPanel("north");
        add(buttonPanel, BorderLayout.NORTH);
        // Create second button panel
        ButtonPanel buttonPanel2 = new ButtonPanel("south");
        add(buttonPanel2, BorderLayout.SOUTH);
        // Add scroll pane to center panel
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);
        
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