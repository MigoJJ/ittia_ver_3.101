package je.panse.doro.fourgate.thyroid;

import javax.swing.*;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.fourgate.thyroid.prganacy.EMR_Preg_CC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EMR_thyroid_main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Thyroid disease");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        JPanel panel = new JPanel(new GridLayout(11, 1));

        String[] buttonLabels = {
                "Thyroid Physical examination",
                "Hyperthyroidism Symptom",
                "Hypothyroidism Symptom",
                ">  Hyperthyroidism with pregnancy",
                ">  Hypothyroidism with pregnancy",
                ">  Abnormal TFT with pregnancy",
                "Non thyroidal illness",
                "Abnormal TFT on Routine check",
                "Thyroidal nodule",
                "Post operation F/U PTC",
                "Quit"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ThyroidButtonListener(frame));
            button.setBackground(new Color(200, 200, 200));
            panel.add(button);
        }

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static class ThyroidButtonListener implements ActionListener {
        private JFrame frame;

        ThyroidButtonListener(JFrame frame) {
            this.frame = frame;
        }

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "Thyroid Physical examination":
                    thyroidPhysicalExam();
                    break;
                case "Hyperthyroidism Symptom":
                    hyperthyroidismSymptom();
                    break;
                case "Hypothyroidism Symptom":
                    hypothyroidismSymptom();
                    break;
                case ">  Hyperthyroidism with pregnancy":
                    hyperthyroidismWithPregnancy();
                    break;
                case ">  Hypothyroidism with pregnancy":
                    hypothyroidismWithPregnancy();
                    break;
                case ">  Abnormal TFT with pregnancy":
                    abnormalTFTWithPregnancy();
                    break;
                case "Non thyroidal illness":
                    nonThyroidalIllness();
                    break;
                case "Abnormal TFT on Routine check":
                    abnormalTFTOnRoutineCheck();
                    break;
                case "Thyroidal nodule":
                    thyroidalNodule();
                    break;
                case "Post operation F/U PTC":
                    postOperationFUPtc();
                    break;
                case "Quit":
                    frame.dispose();
                    break;
                default:
                    System.out.println("Unknown command: " + command);
            }
        }

        private void thyroidPhysicalExam() {
            System.out.println("Performing Thyroid Physical Examination...");
            EMR_thyroid_PE.main(null);
        }

        private void hyperthyroidismSymptom() {
            System.out.println("Checking Hyperthyroidism Symptoms...");
			   String[] Esrr = EMR_thyroid_retStr.returnStr("Hyperthyroidism Symptom");
			   EMR_thyroid_list.main(Esrr);
		}

        private void hypothyroidismSymptom() {
            System.out.println("Checking Hypothyroidism Symptoms...");
			   String[] Esrr = EMR_thyroid_retStr.returnStr("Hypothyroidism Symptom");
			   EMR_thyroid_list.main(Esrr);
        }

        private void hyperthyroidismWithPregnancy() {
            System.out.println("Checking Hyperthyroidism with Pregnancy...");
            GDSEMR_frame.setTextAreaText(0, "Checking Hyperthyroidism with Pregnancy [   ] weeks");
            GDSEMR_frame.setTextAreaText(8, "\n...F/U Hyperthyroidism with Pregnancy [   ] weeks");
			EMR_Preg_CC.main(null);
        }

        private void hypothyroidismWithPregnancy() {
            System.out.println("Checking Hypothyroidism with Pregnancy...");
            GDSEMR_frame.setTextAreaText(0, "Checking Hypothyroidism with Pregnancy [   ] weeks");
            GDSEMR_frame.setTextAreaText(8, "\n...F/U Hypothyroidism with Pregnancy [   ] weeks");
			EMR_Preg_CC.main(null);
        }

        private void abnormalTFTWithPregnancy() {
            System.out.println("Checking Abnormal TFT with Pregnancy...");
            GDSEMR_frame.setTextAreaText(0, "Checking Abnormal TFT with Pregnancy [   ] weeks");
            GDSEMR_frame.setTextAreaText(8, "\n...F/U Abnormal TFT with Pregnancy [   ] weeks");
			EMR_Preg_CC.main(null);        
		}

        private void nonThyroidalIllness() {
            System.out.println("Checking Non-thyroidal Illness...");
            GDSEMR_frame.setTextAreaText(0, "Checking NTI ");
            GDSEMR_frame.setTextAreaText(8, "\n...F/U NTI [   ] months later");        }

        private void abnormalTFTOnRoutineCheck() {
            System.out.println("Checking Abnormal TFT on Routine Check...");
            GDSEMR_frame.setTextAreaText(0, "Checking Abnormal TFT on Routine Check");
            GDSEMR_frame.setTextAreaText(8, "\n...F/U Abnormal TFT [   ] months later");        }

        private void thyroidalNodule() {
            System.out.println("Checking Thyroidal Nodule...");
            GDSEMR_frame.setTextAreaText(0, "Checking Thyroidal Nodule");
            GDSEMR_frame.setTextAreaText(8, "\n...F/U Thyroidal Nodule [   ] months later");        }

        private void postOperationFUPtc() {
            System.out.println("Checking Post Operation F/U PTC...");
            GDSEMR_frame.setTextAreaText(0, "Checking Post Operation F/U PTC\n    with hypothyroidism");
            GDSEMR_frame.setTextAreaText(8, "\n...F/U Post Operation F/U PTC with hypothyroidism "
            		+ "\n...        [   ] months later");            }
    }
}
