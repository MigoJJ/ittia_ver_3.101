package je.panse.doro.soap.plan;

import javax.swing.*;		
import java.awt.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IttiaGDSPlanPanel_21 extends JPanel implements ActionListener {

    private JTextArea textArea;

    public IttiaGDSPlanPanel_21(IttiaGDSPlan frame) {

        // Other code
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (int i = 1; i <= 10; i++) {
        	
        	
        	
            add(new JLabel("Combo Box " + i));
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
            comboBox.addActionListener(this); // Add action listener to the combo box
            add(comboBox);
        }
        textArea = new JTextArea();
        add(textArea);
        textArea.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String selectedItem = (String) comboBox.getSelectedItem();
        textArea.append(selectedItem + "\n"); // Append selected item to the target JTextArea
        IttiaGDSPlanPanel_1.appendTextArea(textArea.getText());
    }

}
