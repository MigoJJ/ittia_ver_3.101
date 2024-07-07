package je.panse.doro.entry;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EntryDir {
    public static String homeDir;
    public static String backupDir;

    public EntryDir() {
        initializeDirectories();
        printDirectoryPaths();
    }

    private void initializeDirectories() {
        // Fetch the user's current working directory
        String userDir = System.getProperty("user.dir");
        System.out.println("User's current working directory: " + userDir);

        // Initialize the home directory to the user's current working directory, including the specific path
        homeDir = userDir + "/src/je/panse/doro";
        System.out.println("Home Directory Initialized at: " + homeDir);

        // Append a subdirectory path for backups within the project structure
        backupDir = homeDir + "/tripikata/rescue";
        System.out.println("Backup Directory Initialized at: " + backupDir);

        // Ensure directories are created
        createDirectory(Paths.get(homeDir));
        createDirectory(Paths.get(backupDir));
    }

    // Utility method to create a directory if it does not exist using java.nio package
    private void createDirectory(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Created directory: " + path.toString());
            } else {
                System.out.println("Directory already exists: " + path.toString());
            }
        } catch (Exception e) {
            System.err.println("Failed to create directory: " + path.toString() + " due to " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void printDirectoryPaths() {
        System.out.println("Home Directory: " + homeDir);
        System.out.println("Backup Directory: " + backupDir);
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
