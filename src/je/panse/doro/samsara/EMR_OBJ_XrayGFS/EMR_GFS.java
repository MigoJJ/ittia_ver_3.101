package je.panse.doro.samsara.EMR_OBJ_XrayGFS;

import java.awt.BorderLayout;	
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import je.panse.doro.GDSEMR_frame;

public class EMR_GFS {
	public static void main(String[] args) {
	    JFrame frame = new JFrame("GFS CFS Checklist");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new GridLayout(1, 3)); // set layout to one row, two columns

	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    JPanel panel1 = new JPanel();
	    panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
	    JPanel panel2 = new JPanel();
	    panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
	    
	    // Create the label and set its properties
	    JLabel label = new JLabel("Esophus : ");
	    label.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(label);

        // Create the text area
        JTextArea textArea = new JTextArea(" < GFS : CFS : > \n");
        textArea.setEditable(true);

        // Create the buttons and add them to a panel
        JButton clearButton = new JButton("Clear");
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);
        
	    // Create the check boxes and labels
	    String[] checklistItems = {
	    		"Gastroesophageal reflux disease (GERD)",
	    		"Esophageal spasm",
	    		"Achalasia",
	    		"Hiatal hernia",
	    		"Esophageal strictures",
	    		"Barrett's esophagus",
	    		"Eosinophilic esophagitis",
	    		"Infectious esophagitis",
	    		"Esophageal varices",
	    		"Plummer-Vinson syndrome",
	    		"Reflux esophagitis",
	    		"Zenker's diverticulum",
	    };
	    
	    JCheckBox[] checkboxes = new JCheckBox[checklistItems.length];
	    for (int i = 0; i < checklistItems.length; i++) {
	        checkboxes[i] = new JCheckBox(checklistItems[i]);
	        panel.add(checkboxes[i]);
	    }

	    // Create the label and set its properties
	    JLabel label1 = new JLabel("Stomach and Duodenum :");
	    label1.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel1.add(label1);

	    // Create the check boxes and labels
	    String[] checklistItems1 = {
	    	"Gastritis",
	    	"      Acute gastritisc",
	    	"      Chronic superficial gastritis",
	    	"      Chronic erosive gastritis",
	    	"      Chronic atrophicl gastritis",
	    	"Peptic ulcer disease",
	    	"Gastroesophageal reflux disease (GERD)",
	    	"Zollinger-Ellison syndrome",
	    	"Helicobacter pylori infection",
	    	"Gastric volvulus",
	    	"Ménétrier's disease",
	    	"Acute gastric dilatation",
	    	"Gastrointestinal stromal tumor (GIST)",
	    	"Gastric cancer",
	    	"      Linitis plastica",
	    	"Gastric lymphoma",
	    	"Eosinophilic gastroenteritis",
	    	"Duodenitis",
	    	"Duodenal ulcer",

	    };
	    
	    JCheckBox[] checkboxes1 = new JCheckBox[checklistItems1.length];
	    for (int i = 0; i < checklistItems1.length; i++) {
	        checkboxes1[i] = new JCheckBox(checklistItems1[i]);
		    panel1.add(checkboxes1[i]);
	    }
	    // Create the label and set its properties
	    JLabel label2 = new JLabel(" Colon:");

	    label2.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel2.add(label2);

	    // Create the check boxes and labels
	    String[] checklistItems2 = {
	    		"The colon mucosa appeared normal and No polyps ",
	    		"Colonic polyps",
	    		"    polyp #1",
	    		"    polyp #2-5",
	    		"    polyp #5 or more",
	    		
	    		"Inflammatory bowel disease (IBD)",
	    		"Ulcerative colitis",
	    		"Crohn's disease",
	    		"Diverticulitis",
	    		"Diverticulosis",
	    		"Colorectal cancer",

	    		"Irritable bowel syndrome (IBS)",
	    		"     Ischemic colitis",
	    		"     Pseudomembranous colitis",
	    		"     Infectious colitis",
	    		"     Lymphocytic colitis",
	    		"     Collagenous colitis",
	    		"     Microscopic colitis",
	    		"Familial adenomatous polyposis (FAP)"
	    };
	    
	    JCheckBox[] checkboxes2 = new JCheckBox[checklistItems2.length];
	    for (int i = 0; i < checklistItems2.length; i++) {
	        checkboxes2[i] = new JCheckBox(checklistItems2[i]);
	        panel2.add(checkboxes2[i]);
	    }

        // Create a new panel for the buttons and text area
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(textArea, BorderLayout.CENTER);

        // Add the bottom panel to the bottom of the main frame
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Add an action listener to the checkboxes to update the text area
        for (JCheckBox[] checkboxeslist : new JCheckBox[][] {checkboxes, checkboxes1, checkboxes2}) {
            for (JCheckBox checkbox : checkboxeslist) {
                checkbox.addChangeListener(e -> {
                    if (!checkbox.isSelected()) {
                        String checkboxText = checkbox.getText() + "\n";
                        int index = textArea.getText().indexOf(checkboxText);
                        if (index >= 0) {
                            textArea.replaceRange("", index, index + checkboxText.length());
                        }
                    } else {
                        textArea.append("\t" + checkbox.getText() + "\n");
                        GDSEMR_frame.setTextAreaText(5, checkbox.getText() + "\n");
                    }
                });
            }
        }
        
        saveButton.addActionListener(event -> {
        String selectedItems = textArea.getText();
		GDSEMR_frame.setTextAreaText(5, selectedItems);
		frame.dispose();
        });
        quitButton.addActionListener(event -> {
//        String selectedItems = textArea.getText();
//		GDSEMR_frame.setTextAreaText(5, selectedItems);
		frame.dispose();
        });
        clearButton.addActionListener(event -> {
          textArea.setText(" < GFS : CFS : > \n");
//  		GDSEMR_frame.setTextAreaText(5, selectedItems);
//  		frame.dispose();
          });
        
	    frame.add(panel);
	    frame.add(panel1);
	    frame.add(panel2);
	    frame.setSize(400, 400);
	    frame.pack(); // resize frame to fit its contents
	    frame.setLocationRelativeTo(null); // center the frame on the screen
	    frame.setVisible(true);
	}
}
