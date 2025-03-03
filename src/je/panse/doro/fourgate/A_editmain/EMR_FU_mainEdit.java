package je.panse.doro.fourgate.A_editmain;

import javax.swing.JFrame;				

import je.panse.doro.fourgate.diabetes.dmGeneral.EMR_FU_diabetes;
import je.panse.doro.fourgate.hypercholesterolemia.cholGeneral.EMR_FU_hypercholesterolemia;
import je.panse.doro.fourgate.hypertension.htnGeneral.EMR_FU_hypertension;
import je.panse.doro.fourgate.uri.EMR_FU_uri;

public class EMR_FU_mainEdit extends JFrame {

	public EMR_FU_mainEdit() {
		EMR_FU_uri.main(null);
		EMR_FU_hypercholesterolemia.main(null);
		EMR_FU_hypertension.main(null);
		EMR_FU_diabetes.main(null);
	}
	
	public static void main(String[] args) {
	    new EMR_FU_mainEdit();
	}
}