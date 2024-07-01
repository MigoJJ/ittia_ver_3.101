package je.panse.doro.entry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class EntryDir {

    public static String currentDir = System.getProperty("user.dir");

    public static String homeDir = currentDir + "/src/je/panse/doro";
//    public static String homeDir = currentDir + "/je/panse/doro";

    public static String backupDir = homeDir + "/tripikata/rescue";

    public static void main(String[] args) {
        // Get the path to the current user's directory
        System.out.println("Current user's directory: " + currentDir);
        System.out.println("homeDir: " + homeDir);
    }
}
