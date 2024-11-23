package je.panse.doro.support;

import java.awt.Desktop;	
import java.io.File;
import java.io.IOException;
import je.panse.doro.entry.EntryDir;

public class EMR_ittia_support {
    public static void main(String[] args) {
        String directoryPath = EntryDir.homeDir + "/support/EMR_support_Folder";

        try {
            String dirPath = directoryPath + File.separator;

            File directory = new File(dirPath);
            
            if (directory.exists()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(directory);
//                System.out.println("Directory opened successfully.");
            } else {
                System.err.println("Directory does not exist.");
            }
        } catch (IOException e) {
            System.err.println("Error opening directory: " + e.getMessage());
        }
    }
}
