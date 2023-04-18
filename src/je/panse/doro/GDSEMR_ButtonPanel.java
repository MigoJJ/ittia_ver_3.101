package je.panse.doro;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GDSEMR_ButtonPanel extends JPanel {
    private JButton[] buttons = new JButton[11];
    
    public GDSEMR_ButtonPanel(String locations) {
        super();
        
        // Create the buttons
        for (int i = 0; i < 11; i++) {
            buttons[i] = new JButton(locations + (i + 1));
            
            // Add a listener to each button
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Handle button click
                    System.out.println("Button " + e.getActionCommand() + " clicked");
                }
            });
            
            // Add the button to the panel
            add(buttons[i]);
        }
		if (locations.equals("north")) {
			 buttons[0].setText("Clear");
			 buttons[1].setText("Copy");
			 buttons[2].setText("Rescue");
			 buttons[3].setText("Exit");
			 // Add the button panel to the north of the frame
			 setLayout(new FlowLayout(FlowLayout.LEFT));
			 setBackground(Color.GRAY);
	   } else if (locations.equals("south")) {
			 buttons[0].setText("F/U DM");
			 buttons[1].setText("F/U HTN");
			 buttons[2].setText("F/U Chol");
			 buttons[3].setText("F/U Thyroid");
			 buttons[10].setText("F/U Edit");
			 // Add the button panel to the south of the frame
			 setLayout(new FlowLayout(FlowLayout.RIGHT));
			 setBackground(Color.LIGHT_GRAY);
		}
	}
}
