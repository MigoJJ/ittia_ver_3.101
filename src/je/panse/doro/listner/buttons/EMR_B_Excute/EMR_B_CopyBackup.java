package je.panse.doro.listner.buttons.EMR_B_Excute;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import je.panse.doro.entry.EntryDir;

public class EMR_B_CopyBackup {

    private static final String DIRECTORY_PATH = EntryDir.homeDir + "/tripikata/rescue/rescuefolder";

    /**
     * Save the provided text to a file.
     *
     * @param text The text to be saved.
     */
    public void saveTextToFile(String text) {
        String fileName = generateFileName();
        writeToFile(DIRECTORY_PATH, fileName, text);
    }

    /**
     * Generate a filename based on the current date and time.
     *
     * @return The generated filename.
     */
    private String generateFileName() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd-HH-mm-ss");
        return currentDateTime.format(formatter) + ".txt";
    }

    /**
     * Write the provided text to the specified directory and filename.
     *
     * @param directoryPath The path of the directory.
     * @param fileName      The name of the file.
     * @param text          The text to be written.
     */
    private void writeToFile(String directoryPath, String fileName, String text) {
        File outputFile = new File(directoryPath, fileName);
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(text);
            System.out.println("Text has been successfully saved to " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while writing to the file.");
        }
    }
}
