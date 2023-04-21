package je.panse.doro.samsara;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import je.panse.doro.samsara.EMR_CCPIPMH.EMRPMH;

public class EMR_categoryButtons extends JFrame implements ActionListener {
	    private static final long serialVersionUID = 1L;
	    public static String[] titles =  { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };
	    private JPanel buttonPanel;

	    public EMR_categoryButtons() {
	        super("EMR_categorybuttons");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new GridLayout(0, 1)); // Set the layout to a grid with 1 column
	        setLocation(1400, 100);

	        // Create the panel to hold the buttons
	        Font font = new Font("Arial", Font.PLAIN, 16);
	        Color color = new Color(255, 255, 0);
	        buttonPanel = new JPanel();
	        buttonPanel.setLayout(new GridLayout(0, 1)); // Set the layout to a grid with 1 column
	        buttonPanel.setLocation(300, 100);

	        // Create the buttons and add them to the panel
	        JButton[] buttons = new JButton[titles.length];
	        for (int i = 0; i < titles.length; i++) {
	            buttons[i] = new JButton(titles[i]);
	            buttons[i].setFont(font);
	            buttons[i].setBackground(color);
	            buttons[i].addActionListener(this);
	            buttonPanel.add(buttons[i]);
//	            color = color.darker().brighter();
//	            color = color.brighter();
	        }

	        // Add the button panel to the frame
	        add(buttonPanel);

	        // Display the frame
	        pack();
//	        setLocationRelativeTo(null);
	        setVisible(true);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        // Handle button clicks
	        System.out.println("Button clicked: " + e.getActionCommand());
	        if (e.getActionCommand().equals("PMH>")) {
	            try {
					EMRPMH.main(null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }

	    }

	    public static void main(String[] args) {
	        new EMR_categoryButtons();
	    }
	}
