package je.panse.doro.soap.pe;

import java.awt.event.ActionEvent;	
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class EMR_PE_createButtonPanel extends EMR_PE_general {

    public static JPanel createButtonPanel(final JTextArea outputTextArea, final JCheckBox clearCheckbox) {
        JPanel buttonPanel = new JPanel();

        String[] buttonNames = {"Clear", "Save", "Save and Quit"};
        

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String buttonText = button.getText();
                    if (buttonText.equals("Clear")) {
                        outputTextArea.setText(""); // Set the outputTextArea to an empty string
                        clearCheckbox.setSelected(false); // Set the clearCheckbox to false
                    } else {
                        System.out.println(buttonText + " button clicked");
                    }
                }
            });
            buttonPanel.add(button);
        }

        return buttonPanel;
    }
}
