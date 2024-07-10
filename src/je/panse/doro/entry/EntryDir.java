package je.panse.doro.entry;

import java.io.File;

public class EntryDir {
    public static String currentDir = System.getProperty("user.dir");
    public static String homeDir = currentDir + File.separator + "src" + File.separator + "je" + File.separator + "panse" + File.separator + "doro";
    public static String backupDir = homeDir + File.separator + "tripikata" + File.separator + "rescue";

    public static void main(String[] args) {
        // Ensure that the home directory exists
        createDirectoryIfNotExists(homeDir);
        // Ensure that the backup directory exists
        createDirectoryIfNotExists(backupDir);

        // Get the path to the current user's directory
        System.out.println("Current user's directory: " + currentDir);
        System.out.println("homeDir: " + homeDir);
        System.out.println("backupDir: " + backupDir);
    }

    // Utility method to create a directory if it does not exist
    private static void createDirectoryIfNotExists(String directoryPath) {
        File dir = new File(directoryPath);
        System.out.println("Checking directory: " + directoryPath);  // Debugging line
        if (!dir.exists()) {
            System.out.println("Directory does not exist, attempting to create it.");  // More detailed debug
            boolean wasSuccessful = dir.mkdirs();
            if (wasSuccessful) {
                System.out.println("Created directory: " + directoryPath);
            } else {
                System.out.println("Failed to create directory: " + directoryPath);
            }
        } else {
            System.out.println("Directory already exists: " + directoryPath);  // Notify if the directory exists
        }
    }
}
