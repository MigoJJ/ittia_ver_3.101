package je.panse.doro.fourgate.thyroid;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import je.panse.doro.fourgate.thyroid.prganacy.EMR_Preg_CC;
import je.panse.doro.fourgate.thyroid.prganacy.EMR_Thyroid_Preg_te;
import je.panse.doro.fourgate.thyroid.prganacy.EMR_Thyroid_Preg_to;

public class EMR_thyroid_main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Select category ...");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setLocation(1760, 1350);
        frame.setSize(new Dimension(250, 500)); // Increase the width to 1000 pixels


        String[] buttonNames = {
        		  "Thyroid Physical examination",
                "Hyperthyroidism Symptom",
                "Hypothyroidism Symptom",
                "Hyperthyroidism with pregnancy",
                "Hypothyroidism with pregnancy",
                "Abnormal TFT with pregnancy",
                "Non thyroidal illness",
                "Abnormal TFT on Routine check",
                "Quit"
        };
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.setPreferredSize(new Dimension(300, 50)); // Set a fixed size for the button
            button.setMaximumSize(new Dimension(300, 80)); // Set maximum size to enforce the fixed size
            button.setAlignmentX(Box.CENTER_ALIGNMENT); // Align the button to the center horizontally
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Perform the desired action for each button here
						if (name.contains("Hyperthyroidism Symptom") || name.contains("Hypothyroidism Symptom")) {
						   String[] Esrr = EMR_thyroid_retStr.returnStr(name);
						   EMR_thyroid_list.main(Esrr);
						} else if (name.contains("Thyroid Physical examination")){
							EMR_thyroid_PE.main(null);
						} else if (name.contains("Hyperthyroidism with pregnancy")){
							EMR_Preg_CC.main(null);
//							EMR_Thyroid_Preg_te.main(null);
						} else if (name.contains("Hypothyroidism with pregnancy")){
							EMR_Preg_CC.main(null);
//							EMR_Thyroid_Preg_to.main(null);
						} else if (name.contains("Quit")){
							frame.dispose();
		//					EMR_Thyroid_Preg_to.main(null);
						}
                }
            });

            frame.add(button);
            frame.add(Box.createVerticalStrut(10)); // Add vertical spacing between buttons
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null); // center the frame on the screen
//        frame.pack();
        frame.setVisible(true);
    }
    private String[] returnStr(String name) {
        String[] returnargs = new String[]{};
        // Add your logic to populate the returnargs array
        return returnargs;
    }
}




