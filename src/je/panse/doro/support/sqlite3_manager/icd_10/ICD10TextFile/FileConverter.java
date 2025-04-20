package je.panse.doro.support.sqlite3_manager.icd_10.ICD10TextFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;

public class FileConverter {
    private final ICDChapterRegistry chapterRegistry;
    private final JTextArea statusArea;

    public FileConverter(ICDChapterRegistry chapterRegistry, JTextArea statusArea) {
        this.chapterRegistry = chapterRegistry;
        this.statusArea = statusArea;
    }

    public void convertFile(String inputPath, String outputPath) {
        if (inputPath.isEmpty() || outputPath.isEmpty()) {
            statusArea.append("Please select both input and output files.\n");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\t");
                if (parts.length < 2) {
                    statusArea.append("Invalid line format: " + line + "\n");
                    continue;
                }

                String code = parts[1].trim();
                String description = parts.length > 2 ? parts[2].trim() : "";
                String chapterTitle = chapterRegistry.getChapterTitle(code);

                description = description.replace("\"", "\\\"");
                chapterTitle = chapterTitle.replace("\"", "\\\"");

                String outputLine = String.format("{\"%s\", \"%s\", \"%s\", \".\" },%n", 
                    chapterTitle, code, description);
                writer.write(outputLine);

                statusArea.append("Processed: " + code + " (" + chapterTitle + ")\n");
                statusArea.setCaretPosition(statusArea.getDocument().getLength());
            }

            statusArea.append("Conversion completed successfully.\n");
        } catch (IOException e) {
            statusArea.append("Error: " + e.getMessage() + "\n");
        }
    }
}