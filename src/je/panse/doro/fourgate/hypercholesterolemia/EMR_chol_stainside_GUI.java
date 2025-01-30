package je.panse.doro.fourgate.hypercholesterolemia;

import javax.swing.*;	
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EMR_chol_stainside_GUI extends JFrame {
    private StatinSideEffects sideEffects = new StatinSideEffects();

    private JCheckBox musclePainCheckBox = new JCheckBox("Muscle Pain & Weakness");
    private JCheckBox rhabdomyolysisCheckBox = new JCheckBox("Rhabdomyolysis");
    private JCheckBox liverProblemsCheckBox = new JCheckBox("Liver Problems");
    private JCheckBox cognitiveIssuesCheckBox = new JCheckBox("Cognitive Issues");
    private JCheckBox digestiveIssuesCheckBox = new JCheckBox("Digestive Issues");
    private JCheckBox neuropathyCheckBox = new JCheckBox("Nerve Problems");
    private JCheckBox fatigueCheckBox = new JCheckBox("Fatigue");
    private JCheckBox headacheCheckBox = new JCheckBox("Headache");

    private JButton displayButton = new JButton("Display Side Effects Status");
    private JButton emergencyButton = new JButton("Check Emergency Symptoms");
    private JButton monitoringButton = new JButton("Show Monitoring Schedule");

    public EMR_chol_stainside_GUI() {
        setTitle("Statin Side Effects Monitoring");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 1));

        add(musclePainCheckBox);
        add(rhabdomyolysisCheckBox);
        add(liverProblemsCheckBox);
        add(cognitiveIssuesCheckBox);
        add(digestiveIssuesCheckBox);
        add(neuropathyCheckBox);
        add(fatigueCheckBox);
        add(headacheCheckBox);
        add(displayButton);
        add(emergencyButton);
        add(monitoringButton);

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sideEffects.setMusclePain(musclePainCheckBox.isSelected());
                sideEffects.setRhabdomyolysis(rhabdomyolysisCheckBox.isSelected());
                sideEffects.setLiverProblems(liverProblemsCheckBox.isSelected());
                sideEffects.setCognitiveIssues(cognitiveIssuesCheckBox.isSelected());
                sideEffects.setDigestiveIssues(digestiveIssuesCheckBox.isSelected());
                sideEffects.setNeuropathy(neuropathyCheckBox.isSelected());
                sideEffects.setFatigue(fatigueCheckBox.isSelected());
                sideEffects.setHeadache(headacheCheckBox.isSelected());

                sideEffects.displaySideEffectsStatus();
            }
        });

        emergencyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sideEffects.checkEmergencySymptoms();
            }
        });

        monitoringButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sideEffects.displayMonitoringSchedule();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EMR_chol_stainside_GUI().setVisible(true);
            }
        });
    }

    public static class StatinSideEffects {
        private boolean musclePain = false;
        private boolean digestiveIssues = false;
        private boolean fatigue = false;
        private boolean headache = false;
        private boolean rhabdomyolysis = false;
        private boolean liverProblems = false;
        private boolean cognitiveIssues = false;
        private boolean neuropathy = false;
        private double creatineKinase = 0.0;
        private boolean elevatedLiverEnzymes = false;

        public void setMusclePain(boolean musclePain) {
            this.musclePain = musclePain;
        }

        public void setDigestiveIssues(boolean digestiveIssues) {
            this.digestiveIssues = digestiveIssues;
        }

        public void setFatigue(boolean fatigue) {
            this.fatigue = fatigue;
        }

        public void setHeadache(boolean headache) {
            this.headache = headache;
        }

        public void setRhabdomyolysis(boolean rhabdomyolysis) {
            this.rhabdomyolysis = rhabdomyolysis;
        }

        public void setLiverProblems(boolean liverProblems) {
            this.liverProblems = liverProblems;
        }

        public void setCognitiveIssues(boolean cognitiveIssues) {
            this.cognitiveIssues = cognitiveIssues;
        }

        public void setNeuropathy(boolean neuropathy) {
            this.neuropathy = neuropathy;
        }

        public void displaySideEffectsStatus() {
            System.out.println("< Statin Side Effect Check > all denied------------");
            System.out.println("• Muscle Pain & Weakness: " + (musclePain ? "Present" : "Denied"));
            System.out.println("• Rhabdomyolysis: " + (rhabdomyolysis ? "Present" : "Denied"));
            System.out.println("• Liver Problems: " + (liverProblems ? "Present" : "Denied"));
            System.out.println("• Cognitive Issues: " + (cognitiveIssues ? "Present" : "Denied"));
            System.out.println("• Digestive Issues: " + (digestiveIssues ? "Present" : "Denied"));
            System.out.println("• Nerve Problems: " + (neuropathy ? "Present" : "Denied"));
            System.out.println("• Fatigue: " + (fatigue ? "Present" : "Denied"));
            System.out.println("• Headache: " + (headache ? "Present" : "Denied"));
        }

        public void checkEmergencySymptoms() {
            System.out.println("\n< Emergency Symptoms to Monitor >");
            String[] emergencySymptoms = {
                "Severe muscle pain or weakness",
                "Dark urine",
                "Yellowing of skin/eyes",
                "Unexplained fever",
                "Severe stomach pain",
                "Allergic reactions"
            };

            for (String symptom : emergencySymptoms) {
                System.out.println("• " + symptom + ": Denied");
            }
        }

        public void displayMonitoringSchedule() {
            System.out.println("\n< Monitoring Schedule >");
            String[] schedule = {
                "Baseline liver function",
                "CK levels if muscle symptoms",
                "Follow-up at 4-12 weeks",
                "Regular monitoring every 3-12 months"
            };

            for (String item : schedule) {
                System.out.println("• " + item);
            }
        }
    }
}