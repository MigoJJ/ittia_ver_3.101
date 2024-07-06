package je.panse.doro.entry.utils;

import java.net.URL;
import java.nio.file.Paths;

public class ResourceManager {
    public static String getHomeDirectory() throws Exception {
        URL homeDirURL = ResourceManager.class.getResource("/je/panse/doro");
        if (homeDirURL != null) {
            return Paths.get(homeDirURL.toURI()).toString();
        } else {
            throw new Exception("Directory not found within JAR");
        }
    }
}

