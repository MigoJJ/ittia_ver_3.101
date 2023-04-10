package je.panse.doro.listner.buttons;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import je.panse.doro.GDSEMR_frame;

public class ButtonPanel extends JPanel {
    public ButtonPanel(String panelLocation) {
        // Create 11 buttons
        JButton[] buttons = new JButton[11];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Button " + (i+1));
            
            if (i==1) {
//            	"Button " + (i+1) = ("clear");
            	add(buttons[1]);
            }else {
            	add(buttons[i]);

            }
            
            // Add ActionListener to button
            int buttonNumber = i+1;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
						executeMethod(buttonNumber,panelLocation);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });
        }
        
        if (panelLocation.equals("north")) {
            // Add the button panel to the north of the frame
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setBackground(Color.GRAY);
        } else if (panelLocation.equals("south")) {
            // Add the button panel to the south of the frame
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            setBackground(Color.LIGHT_GRAY);
        }
    }
    
    public static void executeMethod(int buttonNumber ,String panelLocation) throws Exception {
        // Add code to execute the desired method here
        GDSEMR_frame.callbutton(buttonNumber, panelLocation);
        
    }
}
