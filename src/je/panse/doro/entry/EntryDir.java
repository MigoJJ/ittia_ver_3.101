package je.panse.doro.entry;


public class EntryDir {
	public static String currentDir = System.getProperty("user.dir");

	// ----- "/home/woon/git/ittia_Version_2.1"
	public static String homeDir = currentDir + "/src/je/panse/doro";
//	public static String homeDir = currentDir + "/je/panse/doro";

	public static String backupDir = homeDir + "/tripikata/rescue";
	public static void main(String[] args) {
	    // Get the path to the current user's directory
	    String currentDir = System.getProperty("user.dir");
	    System.out.println("Current user's directory: " + currentDir);
	}
}

