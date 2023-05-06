package je.panse.doro.soap.plan;

import javax.swing.*;	
import java.awt.*;

public class IttiaGDSPlanPanel_3 extends JPanel {

	public IttiaGDSPlanPanel_3(IttiaGDSPlan frame) {
	    setLayout(new GridLayout(12, 2)); // Set layout to a 2x2 grid
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	
	    // Create 10 checkboxes
	    for (int i = 1; i <= 10; i++) {
	        add(new JCheckBox("Check Box " + i));
	    }
	
	    // Create 5 radio buttons
	    ButtonGroup radioGroup = new ButtonGroup(); // Create a group for the radio buttons
	    for (int i = 1; i <= 6; i++) {
	        JRadioButton radioButton = new JRadioButton("Radio Button " + i);
	        radioGroup.add(radioButton); // Add the radio button to the group
	        add(radioButton);
	    }
	
	    // Create 5 combo boxes
	    for (int i = 1; i <= 8; i++) {
	        add(new JComboBox(new String[]{"Option 1", "Option 2", "Option 3"}));
	    }
	}
}
