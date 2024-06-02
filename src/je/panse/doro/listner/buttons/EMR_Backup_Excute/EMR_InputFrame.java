package je.panse.doro.listner.buttons.EMR_Backup_Excute;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import je.panse.doro.GDSEMR_frame;

public class EMR_InputFrame extends JFrame {

    private JTextField patientIdField;
    private EMR_B_CopyBackup backupHandler;

    public EMR_InputFrame() {
        // Set up the frame
        setTitle("Enter Patient ID");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Initialize the backup handler
        backupHandler = new EMR_B_CopyBackup();

        // Create the label and text field
        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdField = new JTextField(10);
        patientIdField.setHorizontalAlignment(JTextField.CENTER);
        patientIdField.setBackground(Color.CYAN);

        // Create the button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientId = patientIdField.getText();
                if (!patientId.isEmpty()) {
                    backupHandler.saveTextToFile(patientId);
                    GDSEMR_frame.updateTempOutputArea(GDSEMR_frame.tempOutputArea.getText());
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Patient ID cannot be empty.");
                }
            }
        });

        // Add components to the frame
        add(patientIdLabel);
        add(patientIdField);
        add(saveButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EMR_InputFrame().setVisible(true);
            }
        });
    }
}
