package je.panse.doro.listner.buttons.EMR_Backup_Excute;

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
     * @param patientId The Patient ID to be included in the filename.
     */
    public void saveTextToFile(String patientId) {
        String fileName = generateFileName(patientId);
        writeToFile(DIRECTORY_PATH, fileName, patientId);
    }

    /**
     * Generate a filename based on the current date and time and patient ID.
     *
     * @return The generated filename.
     */
    private String generateFileName(String patientId) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" [yyyy-MM-dd-HH-mm-ss]");
        return patientId + " - " + currentDateTime.format(formatter) + ".txt";
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
