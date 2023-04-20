package je.panse.doro.entry;

import javax.swing.*;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.samsara.EMR_categoryButtons;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class IttiaEntry  extends JFrame implements ActionListener {
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    public IttiaEntry() {
	    super("My JFrame with Buttons");
	    setSize(300, 300);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLayout(new GridLayout(6, 1));
	
	    String[] buttonNames = {"Log In", "Prologue", "Version Information", "Rescue", "Ittia Start", "Quit"};
	
	    for (int i = 0; i < buttonNames.length; i++) {
	        JButton button = new JButton(buttonNames[i]);
	        button.addActionListener(this);
	        button.setBackground(Color.ORANGE); // Set the background color to ORANGE
	        buttons.add(button);
	        add(button);
	    }
	    setVisible(true);
    }
    private void ButtonPress(String buttonText) throws Exception {
		try {
			if (buttonText.equals("Prologue")) {
			    Runtime.getRuntime().exec("gedit " + EntryDir.homeDir + "/tripikata/entry/Prologue");
				} else if (buttonText.equals("Version Information")) {
				    Runtime.getRuntime().exec("gedit " + EntryDir.homeDir + "/tripikata/entry/Version_Information");
				} else if (buttonText.equals("Ittia Start")) {
					GDSEMR_frame.main(null);
					EMR_categoryButtons.main(null);
				} else if (buttonText.equals("Rescue")) {
//				    Runtime.getRuntime().exec("java -cp "+ EntryDir.currentUsersDir +"/bin je.panse.doro.ittia2.FPF");
				} 
				else {
				dispose();
				}	
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
    }
    public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String buttonText = button.getText();
		System.out.println("Button \"" + buttonText + "\" was pressed.");
		
		try {
			ButtonPress(buttonText);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dispose();
    }
    
    public static void main(String[] args) {
    	IttiaEntry myFrame = new IttiaEntry();
    }

}