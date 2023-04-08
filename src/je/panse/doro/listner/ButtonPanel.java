package je.panse.doro.listner;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
    public ButtonPanel() {
        // Create 9 buttons
        JButton[] buttons = new JButton[11];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("Button " + (i+1));
            add(buttons[i]);
        }
    }
}
