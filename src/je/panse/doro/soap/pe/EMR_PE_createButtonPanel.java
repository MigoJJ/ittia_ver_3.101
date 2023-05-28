package je.panse.doro.soap.pe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EMR_PE_createButtonPanel {

    public static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        for (int i = 1; i <= 12; i++) {
            JButton button = new JButton("Button " + i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Button action
                    String buttonText = button.getText();
                    System.out.println(buttonText + " north clicked");
                }
            });
            buttonPanel.add(button);
        }

        return buttonPanel;
    }
}
