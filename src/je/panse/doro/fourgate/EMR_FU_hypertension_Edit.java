package je.panse.doro.fourgate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EMR_FU_hypertension_Edit extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JButton saveButton;
    private String A1 = "hypertension 001";

    public EMR_FU_hypertension_Edit() {
        // Create components
        textField = new JTextField(A1, 20);
        saveButton = new JButton("Save");

        // Set layout
        setLayout(new FlowLayout());

        // Add components to the JFrame
        add(textField);
        add(saveButton);

        // Set JFrame properties
        setTitle("Edit String A1");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add action listener to the button
        saveButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        // Update the value of A1 when the button is clicked
        A1 = textField.getText();
        JOptionPane.showMessageDialog(this, "String A1 has been saved.");
    }

    public static void main(String[] args) {
        // Create an instance of the JFrame and make it visible
    	EMR_FU_hypertension_Edit frame = new EMR_FU_hypertension_Edit();
        frame.setVisible(true);
    }
}
