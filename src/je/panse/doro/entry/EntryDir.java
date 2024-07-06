package je.panse.doro.entry;

import java.io.File;

public class EntryDir {
    public static String homeDir;
    public static String backupDir;

    public EntryDir() {
        // Initialize the home directory to the user's current working directory, including the src folder
        homeDir = System.getProperty("user.dir") + "/src/je/panse/doro";

        // Append a subdirectory path for backups within the project structure
        backupDir = homeDir + "/tripikata/rescue";

        // Ensuring directory paths are created and printed correctly
        createDirectory(homeDir);
        createDirectory(backupDir);

        System.out.println("Home Directory: " + homeDir);
        System.out.println("Backup Directory: " + backupDir);
    }

    // Utility method to create a directory if it does not exist
    private void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + path);
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        }
    }

    public static void main(String[] args) {
        try {
            new EntryDir();
        } catch (Exception e) {
            System.err.println("Error initializing EntryDir: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
