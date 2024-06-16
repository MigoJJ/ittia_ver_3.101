package je.panse.doro.listner.buttons.EMR_Backup_Excute;


import java.io.BufferedWriter;	
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import je.panse.doro.GDSEMR_frame;

public class EMR_B_CopyBackup {
    public void saveTextToFile(String patientId) {
        try {
            String content = GDSEMR_frame.tempOutputArea.getText();
            String directoryPath = "/home/migowj/git/ittia_ver_3.502/src/je/panse/doro/tripikata/rescue/rescuefolder/";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();  // Create the directory if it does not exist
            }
            File file = new File(directoryPath + patientId + ".txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
//            JOptionPane.showMessageDialog(null, "Backup saved successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error saving backup: " + ex.getMessage());
        }
    }
}
	