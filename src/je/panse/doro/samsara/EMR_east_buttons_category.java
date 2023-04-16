package je.panse.doro.samsara;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EMR_east_buttons_category extends JFrame {

    private ArrayList<JButton> buttons = new ArrayList<>();

    public EMR_east_buttons_category(String position, String title) {
        setSize(new Dimension(800, 600));
        setLayout(new GridLayout(10, 1));
        setBackground(new Color(240, 240, 240));
        setTitle(title);

        String[] buttonNames = { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>","A>", "P>", "Comment>" };

        // Create buttons and add to array list
        for (String buttonName : buttonNames) {
            JButton button = new JButton(buttonName);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            buttons.add(button);
            add(button);
        }

        // Change background color of buttons gradually
//            Color color = new Color(240 - i * 12, 240 - i * 12, 240 - i * 12);
//            Color color = new Color(255, 165, 0); // Create an orange color object

        for (int i = 0; i < buttons.size(); i++) {
          Color color = new Color(240 - i * 12, 240 - i * 12, 240 - i * 12);
            buttons.get(i).setBackground(color);
        }
        
        
        setLocationRelativeTo(null);
        int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int x = screenWidth - frameWidth;
        int y = 0;
        setLocation(x, y+200);
 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new EMR_east_buttons_category("east", "EMR East Buttons");
    }
}
