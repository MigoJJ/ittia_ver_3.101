package je.panse.doro.soap.plan;

import javax.swing.*;	
import java.awt.*;

public class IttiaGDSPlanPanel_2 extends JPanel {

	public IttiaGDSPlanPanel_2(IttiaGDSPlan frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (int i = 1; i <= 10; i++) {
            add(new JLabel("Combo Box " + i));
            add(new JComboBox(new String[]{"Option 1", "Option 2", "Option 3"}));
        }
	}
}

