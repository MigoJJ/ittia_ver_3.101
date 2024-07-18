package je.panse.doro.entry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import je.panse.doro.GDSEMR_frame;


public class Entryittia extends JFrame implements ActionListener {
    private final ArrayList<JButton> buttons;

    // Constructor
    public Entryittia() {
        super("My JFrame with Buttons");
        buttons = new ArrayList<>();
        initializeFrame();
        createButtons();
        setVisible(true);
    }

    // Initialize JFrame settings
    private void initializeFrame() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));
    }

    // Create and add buttons to the frame
    private void createButtons() {
        String[] buttonNames = {"Log In", "Prologue", "Version Information", "Rescue", "Ittia Start", "Quit"};
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.addActionListener(this);
            button.setBackground(Color.ORANGE); // Set the background color to ORANGE
            buttons.add(button);
            add(button);
        }
    }

    // Handle button press actions
    private void handleButtonPress(String buttonText) throws IOException {
        switch (buttonText) {
            case "Prologue":
                Runtime.getRuntime().exec("gedit " + EntryDir.homeDir + "/tripikata/entry/Prologue");
                break;
            case "Version Information":
                Runtime.getRuntime().exec("gedit " + EntryDir.homeDir + "/tripikata/entry/Version_Information");
                break;
            case "Ittia Start":
                // Placeholder for starting GDSEMR_frame; make sure GDSEMR_frame is correctly implemented
                GDSEMR_frame.main(null);
                break;
            case "Rescue":
                // Code for Rescue action
                break;
            case "Quit":
                dispose();  // Close the frame when "Quit" is pressed
                break;
        }
    }

    // Event handling for button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();
        System.out.println("Button \"" + buttonText + "\" was pressed.");
        try {
            handleButtonPress(buttonText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Main method
    public static void main(String[] args) {
        new Entryittia();
    }
}
