package je.panse.doro.samsara.EMR_CCPIPMH;

import java.awt.BorderLayout;	
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import je.panse.doro.GDSEMR_frame;

public class EMRPMH<pmhxTextArea> extends JFrame implements ActionListener {

    private JTextArea pmhxTextArea;
    private ArrayList<JCheckBox> checkBoxList;
    private char dsquare = '\u2B1B';


    public EMRPMH() {
        super("Medical History");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel to hold the checkboxes
        Font font = new Font("DejaVu Sans", Font.PLAIN, 12);
//        Font font = new Font("Arial", Font.PLAIN, 12);
        JPanel pmhxPanel = new JPanel();
        pmhxPanel.setLayout(new GridLayout(0, 3)); // Set the layout to a grid with 3 columns

        // Create the checkboxes and add them to the panel
        checkBoxList = new ArrayList<JCheckBox>();
        String[] checkboxLabels = { "Diabetes Mellitus", "HTN", "Dyslipidemia\n", "Cancer", "Operation",
                "Thyroid Disease\n", "Asthma", "Tuberculosis", "Pneumonia\n", "Chronic/Acute Hepatitis", "GERD",
                "Gout\n", "Arthritis", "Hearing Loss", "CVA\n", "Depression", "Cognitive Disorder\n", "Allergy\n",
                "Food", "Injection", "Medication" };

		// Create a scrollable text area to display the medical history
		pmhxTextArea = new JTextArea(20, 40);
		pmhxTextArea.setText(" ---------------------\n");
		pmhxTextArea.setEditable(true);
		pmhxTextArea.setFont(font);

		for (String label : checkboxLabels) {
			JCheckBox checkbox = new JCheckBox(label);
		    checkbox.setFont(font);
		    checkBoxList.add(checkbox);
		    pmhxPanel.add(checkbox);

		    checkbox.addItemListener(new ItemListener() {
		        public void itemStateChanged(ItemEvent e) {
		            if (e.getStateChange() == ItemEvent.SELECTED) {
		                // Checkbox is checked, add text to pmhxTextArea
		                pmhxTextArea.append("         " +dsquare+ " " + label + "\n");
		            } else {
		                // Checkbox is unchecked, remove text from pmhxTextArea
		                String text = pmhxTextArea.getText().replace(label + "\n", "");
		                pmhxTextArea.setText(text);
		            }
		        }
		    });
		}
		
        JScrollPane pmhxScrollPane = new JScrollPane(pmhxTextArea);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton saveShowButton = new JButton("Save and Show");
        JButton clearButton = new JButton("Clear and Restart");
        JButton saveQuitButton = new JButton("Save and Quit");
        buttonPanel.add(saveShowButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(saveQuitButton);

        // Add everything to the main frame
        add(pmhxPanel, BorderLayout.NORTH);
        add(pmhxScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Register action listeners for the buttons
        saveShowButton.addActionListener(this);
        clearButton.addActionListener(this);
        saveQuitButton.addActionListener(this);
        
        // Display the frame
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){			
    	if (e.getActionCommand().equals("Save and Show")) {
//		    pmhxTextArea.setText("");
			StringBuilder sb = new StringBuilder();
//			sb.append("Past Medical History:>\n    ");
			sb.append("\n    ");
           for (JCheckBox checkbox : checkBoxList) {
                if (checkbox.isSelected()) {
                    sb.append(dsquare + " ");
                } else {
                    sb.append("☐ ");
                  }
                sb.append(checkbox.getText());
                sb.append("    ");
            }
            sb.append("\n--------------------------------------\n");
            pmhxTextArea.append(sb.toString());
            GDSEMR_frame.setTextAreaText(3,sb.toString());
            dispose();

		} else if (e.getActionCommand().equals("Clear and Restart")) {
		    for (JCheckBox checkbox : checkBoxList) {
		        checkbox.setSelected(false);
		    }
		    
		} else if (e.getActionCommand().equals("Save and Quit")) {
			// TODO: Implement save and quit functionality
		    JOptionPane.showMessageDialog(this, "Save and Quit not yet implemented.");
			dispose();
		}
	}

    public static void main(String text) throws IOException {
        EMRPMH gui = new EMRPMH();
//        gui.actionPerformed(new ActionEvent(gui, 0, "Save and Show"));
    }
}