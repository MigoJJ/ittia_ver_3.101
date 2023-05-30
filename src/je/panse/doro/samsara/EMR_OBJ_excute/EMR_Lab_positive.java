package je.panse.doro.samsara.EMR_OBJ_excute;

import javax.swing.*;	
import je.panse.doro.GDSEMR_frame;
import java.awt.*;
import java.awt.event.*;

public class EMR_Lab_positive extends JFrame implements ActionListener {
    
    private JLabel label;
    private JCheckBox[] checkboxes;
    private JTextArea resultsTextArea;
    private JButton updateResultsButton;
    
    public EMR_Lab_positive() {
        super("EMR Interface");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the frame on the screen
        setLayout(new BorderLayout());
        
        label = new JLabel("Select test results:");
        JPanel labelPanel = new JPanel();
        labelPanel.add(label);
        add(labelPanel, BorderLayout.NORTH);
        
        JPanel checkboxesPanel = new JPanel();
        checkboxesPanel.setLayout(new GridLayout(7, 2));
        String[] checkboxLabels = {"HAV-Ab IgG:    Negative", "HAV-Ab IgG:    Positive", 
        		"HBsAg:    Negative","HBsAg:    Positive", 
        		"HBsAb:    Negative", "HBsAb:    Positive", 
        		"HCV Ab:    Negative","HCV Ab:    Positive", 
        		"RPR :    Negative","RPR :    Positive", 
        		"Stool Hb:    Negative", "Stool Hb:    Positive",
        		"RF:    Negative", "RF:    Positive"};
        checkboxes = new JCheckBox[checkboxLabels.length];
        for (int i = 0; i < checkboxes.length; i++) {
            checkboxes[i] = new JCheckBox(checkboxLabels[i]);
            checkboxesPanel.add(checkboxes[i]);
        }
        add(checkboxesPanel, BorderLayout.CENTER);
        
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout());
        
        resultsTextArea = new JTextArea(10,30);
        resultsTextArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(resultsTextArea);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
        
        updateResultsButton = new JButton("Update Results");
        updateResultsButton.addActionListener(this);
        resultsPanel.add(updateResultsButton, BorderLayout.SOUTH);
        add(resultsPanel, BorderLayout.SOUTH);
    }
    
    public void actionPerformed(ActionEvent e) {
        String results = "\n";
        for (int i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].isSelected()) {
                results += "\n\t" + checkboxes[i].getText()+"\n";
            }
        }
        resultsTextArea.setText(results);  
        GDSEMR_frame.setTextAreaText(5, results);
        dispose();

    }
    
    public static void main(String[] args) {
        EMR_Lab_positive emrInterface = new EMR_Lab_positive();
        emrInterface.setVisible(true);
    }
}
