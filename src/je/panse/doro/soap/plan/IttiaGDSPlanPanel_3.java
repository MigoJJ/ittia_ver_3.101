package je.panse.doro.soap.plan;

import javax.swing.*;
import java.awt.*;

public class IttiaGDSPlanPanel_3 extends JPanel {

	public IttiaGDSPlanPanel_3(IttiaGDSPlan frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (int i = 1; i <= 5; i++) {
            add(new JCheckBox("Check Box " + i));
            add(new JRadioButton("Radio Button " + i));
            add(new JComboBox(new String[]{"Option 1", "Option 2", "Option 3"}));
        }
	}
}

