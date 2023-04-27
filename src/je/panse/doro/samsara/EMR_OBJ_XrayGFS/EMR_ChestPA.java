package je.panse.doro.samsara.EMR_OBJ_XrayGFS;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import je.panse.doro.GDSEMR_frame;

public class EMR_ChestPA {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Chest Pain Checklist");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
	// Create the check boxes and labels
		String[] checklistItems = {
			
				"A normal Chest PA X-ray ",
				"Pneumonia",
				"Pulmonary edema",
				"Chronic obstructive pulmonary disease (COPD)",
				"Lung cancer",
				"Pleural effusion",
				"Tuberculosis",
				"Atelectasis",
				"Bronchitis",
				"Interstitial lung disease",
				"Pulmonary embolism."
		
		};
		
		JCheckBox[] checkboxes = new JCheckBox[checklistItems.length];
		for (int i = 0; i < checklistItems.length; i++) {
		checkboxes[i] = new JCheckBox(checklistItems[i]);
		panel.add(checkboxes[i]);
		}
	
		// Create the submit button
		JButton submitButton = new JButton("Submit");
			submitButton.addActionListener(event -> {
			String selectedItems = "\n   Chest PA  : ";
			for (int i = 0; i < checkboxes.length; i++) {
				if (checkboxes[i].isSelected()) {
				selectedItems += "\t" + checkboxes[i].getText() + "\n";
				}
			}
			//		JOptionPane.showMessageDialog(frame, selectedItems);
			GDSEMR_frame.setTextAreaText(5, selectedItems);
			frame.dispose();
		
		});
		panel.add(submitButton);
		// Add the panel to the frame
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}