package je.panse.doro.samsara.EMR_OBJ_XrayGFS;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.samsara.comm.datetime.Date_current;

public class EMR_EKG {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Chest Pain Checklist");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // center the frame on the screen
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
	// Create the check boxes and labels
		String[] checklistItems = {
				"A normal EKG (electrocardiogram)",
				"Sinus tachycardia - [   ] /min",
				"Sinus bradycardia - [   ] /min",
				"NonSpecific ST-T change",
				"RBBB : Right bundle branch block ",
				"iRBBB : Incomplete Right bundle branch block ",
				"LBBB : Left bundle branch block ",
				"iLBBB : Incomplete Left bundle branch block ",
				"Atrial fibrillation - ",
				"Atrial flutter ",
				"PVC's : Premature ventricular contractions",
				"APC's : Premature atrial contractions",
				"Supraventricular tachycardia (SVT) ",
				"(AV) block : First-degree atrioventricular",
				"(AV) block : Second-degree",
				"(AV) block : Third-degree",
				
				"ST-segment elevation myocardial infarction (STEMI) ",
				"Non-ST-segment elevation myocardial infarction (NSTEMI)",
				"Ventricular tachycardia ",
				"Ventricular fibrillation - "
		};
		
		JCheckBox[] checkboxes = new JCheckBox[checklistItems.length];
		for (int i = 0; i < checklistItems.length; i++) {
		checkboxes[i] = new JCheckBox(checklistItems[i]);
		panel.add(checkboxes[i]);
		}
	
		// Create the submit button
		JButton submitButton = new JButton("Submit");
			submitButton.addActionListener(event -> {
			String selectedItems = "\n< EKG >";
			for (int i = 0; i < checkboxes.length; i++) {
				if (checkboxes[i].isSelected()) {
				selectedItems += "\n\t" + checkboxes[i].getText();
				}
			}
			//		JOptionPane.showMessageDialog(frame, selectedItems);
			String cdate = Date_current.defineTime("d");
			GDSEMR_frame.setTextAreaText(5,selectedItems);
			GDSEMR_frame.setTextAreaText(9,"Ⓔ " + selectedItems + "  " +cdate);
			frame.dispose();
		});
		panel.add(submitButton);
		// Add the panel to the frame
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}