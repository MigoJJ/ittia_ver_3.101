package je.panse.doro.entry;

import java.net.URL;

public class EntryDir {
    public static void main(String[] args) throws Exception {
        URL homeDirURL = EntryDir.class.getResource("/je/panse/doro");
        if (homeDirURL != null) {
            String homeDir = homeDirURL.getPath();
            String backupDir = homeDir + "/tripikata/rescue";
            System.out.println("homeDir: " + homeDir);
            // Use homeDir and backupDir paths for read-only operations
        } else {
            System.out.println("Directory not found within JAR");
        }
    }
}
