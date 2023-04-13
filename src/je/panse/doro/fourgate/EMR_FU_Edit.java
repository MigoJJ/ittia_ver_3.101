package je.panse.doro.fourgate;

import javax.swing.JFrame;

import je.panse.doro.fourgate.diabetes.EMR_FU_diabetes;
import je.panse.doro.fourgate.hypercholesterolemia.EMR_FU_hypercholesterolemia;

public class EMR_FU_Edit extends JFrame {

	public EMR_FU_Edit() {
		EMR_FU_diabetes.main(null);
		EMR_FU_hypercholesterolemia.main(null);
		EMR_FU_hypertensionEdit.main(null);
	}
	
	public static void main(String[] args) {
	    new EMR_FU_Edit();
	}
}