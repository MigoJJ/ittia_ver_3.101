package je.panse.doro.entry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class EntryDir {
    public static String currentDir = System.getProperty("user.dir");
    public static String homeDir = currentDir + "/src/je/panse/doro";
//     public static String homeDir = currentDir + "/je/panse/doro";
    public static String backupDir = homeDir + "/tripikata/rescue";

    public static void main(String[] args) {
        // Get the path to the current user's directory
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current user's directory: " + currentDir);
        System.out.println("homeDir: " + homeDir);

        // Load a text file from resources
        try (InputStream inputStream = EntryDir.class.getClassLoader().getResourceAsStream("resources/example.txt")) {
            if (inputStream == null) {
                System.out.println("Resource not found: resources/example.txt");
                return;
            }
            String content = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            System.out.println("Content of example.txt: " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
