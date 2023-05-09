package je.panse.doro;

import java.io.IOException;

import je.panse.doro.samsara.EMR_east_buttons_obj;
import je.panse.doro.samsara.EMR_CCPIPMH.EMRPMH;
import je.panse.doro.soap.plan.IttiaGDSPlan;
import je.panse.doro.soap.ros.EMR_ROS;

public class GDSEMR_fourgate extends GDSEMR_frame {

	public GDSEMR_fourgate() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String text) throws IOException {
		System.out.println("Double-clicked on: text >>> " + text);
		if (text.contains("PMH>")) {
			EMRPMH.main(text);
		} else if (text.contains("ROS>")) {
			EMR_ROS.main(null);
		} else if (text.contains("O>")) {
			EMR_east_buttons_obj.main(text);
		} else if (text.contains("P>")) {
			IttiaGDSPlan.main(null);
		}
	}
}
