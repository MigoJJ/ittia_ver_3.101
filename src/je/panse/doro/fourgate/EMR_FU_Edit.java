package je.panse.doro.fourgate;

import javax.swing.JFrame;

import je.panse.doro.fourgate.diabetes.dmGeneral.EMR_FU_diabetes;
import je.panse.doro.fourgate.hypercholesterolemia.EMR_FU_hypercholesterolemia;
import je.panse.doro.fourgate.hypertension.EMR_FU_hypertension;
import je.panse.doro.fourgate.uri.EMR_FU_uri;

public class EMR_FU_Edit extends JFrame {

	public EMR_FU_Edit() {
		EMR_FU_diabetes.main(null);
		EMR_FU_hypercholesterolemia.main(null);
		EMR_FU_hypertension.main(null);
		EMR_FU_uri.main(null);
	}
	
	public static void main(String[] args) {
	    new EMR_FU_Edit();
	}
}