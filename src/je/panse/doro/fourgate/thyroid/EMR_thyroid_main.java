package je.panse.doro.fourgate.thyroid;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.fourgate.thyroid.prganacy.EMR_Preg_CC;


public class EMR_thyroid_main {
    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("Thyroid disease");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        JPanel panel = new JPanel(new GridLayout(11, 1));

        Map<String, Runnable> actions = new HashMap<>() {{
            put("Thyroid Physical examination", EMR_thyroid_main::thyroidPhysicalExam);
            put("Hyperthyroidism Symptom", EMR_thyroid_main::hyperthyroidismSymptom);
            put("Hypothyroidism Symptom", EMR_thyroid_main::hypothyroidismSymptom);
            put("Hyperthyroidism with pregnancy", EMR_thyroid_main::hyperthyroidismWithPregnancy);
            put("Hypothyroidism with pregnancy", EMR_thyroid_main::hypothyroidismWithPregnancy);
            put("Abnormal TFT with pregnancy", EMR_thyroid_main::abnormalTFTWithPregnancy);
            put("Non thyroidal illness", EMR_thyroid_main::nonThyroidalIllness);
            put("Abnormal TFT on Routine check", EMR_thyroid_main::abnormalTFTOnRoutineCheck);
            put("Thyroidal nodule", EMR_thyroid_main::thyroidalNodule);
            put("Post operation F/U PTC", EMR_thyroid_main::postOperationFUPtc);
            put("Quit", frame::dispose);
        }};

        List<String> buttonOrder = Arrays.asList(
            "Thyroid Physical examination",
            "Hyperthyroidism Symptom",
            "Hypothyroidism Symptom",
            "Non thyroidal illness",
            "Abnormal TFT on Routine check",
            "Thyroidal nodule",
            "Post operation F/U PTC",
            "Hyperthyroidism with pregnancy",
            "Hypothyroidism with pregnancy",
            "Abnormal TFT with pregnancy",
            "Quit"
        );

        for (String label : buttonOrder) {
            JButton button = new JButton(label);
            button.addActionListener(e -> actions.get(label).run());
            button.setBackground(new Color(200, 200, 200));
            panel.add(button);
        }

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    	private static void save(int index, String message) {
        	GDSEMR_frame.setTextAreaText(index, message);
    	}
    
        private static void thyroidPhysicalExam() {
            System.out.println("Performing Thyroid Physical Examination...");
            EMR_thyroid_PE.main(null);
        }

        private static void hyperthyroidismSymptom() {
            System.out.println("Checking Hyperthyroidism Symptoms...");
			   String[] Esrr = EMR_thyroid_retStr.returnStr("Hyperthyroidism Symptom");
			   EMR_thyroid_list.main(Esrr);
		}

        private static void hypothyroidismSymptom() {
            System.out.println("Checking Hypothyroidism Symptoms...");
			   String[] Esrr = EMR_thyroid_retStr.returnStr("Hypothyroidism Symptom");
			   EMR_thyroid_list.main(Esrr);
        }
//-----------------------------------------------Pregnancy
        private static void handlePregnancy(String mainMessage, String followUpMessage) {
            System.out.println(mainMessage);
            save(0, mainMessage + " [   ] weeks");
            save(1, "\n\tDevelopmental delays in the child. [ denied ]");
            save(1, "\n\tPreeclampsia.[ denied ]");
            save(8, followUpMessage + " [   ] weeks");
            EMR_Preg_CC.main(null);
        }

        private static void hyperthyroidismWithPregnancy() {
            handlePregnancy("Checking Hyperthyroidism with Pregnancy", "...F/U Hyperthyroidism with Pregnancy");
        }

        private static void hypothyroidismWithPregnancy() {
            handlePregnancy("Checking Hypothyroidism with Pregnancy", "...F/U Hypothyroidism with Pregnancy");
        }

        private static void abnormalTFTWithPregnancy() {
            handlePregnancy("Checking Abnormal TFT with Pregnancy", "...F/U Abnormal TFT with Pregnancy");
        }
      //-----------------------------------------------
        private static void nonThyroidalIllness() {
            System.out.println("Checking Non-thyroidal Illness...");
            save(0, "Checking NTI ");
            save(8, "\n...F/U NTI [   ] months later");        }

        private static void abnormalTFTOnRoutineCheck() {
            System.out.println("Checking Abnormal TFT on Routine Check...");
            save(0, "Checking Abnormal TFT on Routine Check");
            save(8, "\n...F/U Abnormal TFT [   ] months later");        }

        private static void thyroidalNodule() {
            System.out.println("Checking Thyroidal Nodule...");
            save(0, "Checking Thyroidal Nodule");
            save(8, "\n...F/U Thyroidal Nodule [   ] months later");        }

        private static void postOperationFUPtc() {
            System.out.println("Checking Post Operation F/U PTC...");
            save(0, "Checking Post Operation F/U PTC\n    with hypothyroidism");
            save(8, "\n...F/U Post Operation F/U PTC with hypothyroidism "
            		+ "\n...        [   ] months later");            }
    
}
