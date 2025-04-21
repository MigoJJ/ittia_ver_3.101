package je.panse.doro.support.sqlite3_manager.icd_10.ICD10TextFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath, StandardCharsets.UTF_8))) {
            statusArea.append("Reading from: " + inputPath + "\n");

            java.util.List<String> validLines = new java.util.ArrayList<>();
            String line;
            int totalLines = 0;
            int skippedLines = 0;

            while ((line = reader.readLine()) != null) {
                totalLines++;
                if (line.trim().isEmpty()) continue;

                // Split by tab or multiple spaces
                String[] parts = line.trim().split("\\t|\\s{2,}");
                if (parts.length < 4) {
                    statusArea.append("Skipped (not enough parts): " + line + "\n");
                    skippedLines++;
                    continue;
                }

                String chapterTitle = parts[0].trim().replace("\"", "\\\"");
                String code = parts[1].trim();
                String description = parts[2].trim().replace("\"", "\\\"");
                String dot = parts[3].trim();

                if (code.isEmpty() || description.isEmpty()) {
                    statusArea.append("Skipped (empty code or description): " + line + "\n");
                    skippedLines++;
                    continue;
                }

                String outputLine = String.format("{\"%s\", \"%s\", \"%s\", \"%s\" },",
                        chapterTitle, code, description, dot);
                validLines.add(outputLine);
            }

            statusArea.append("Total lines read: " + totalLines + "\n");
            statusArea.append("Valid lines to write: " + validLines.size() + "\n");
            statusArea.append("Skipped lines: " + skippedLines + "\n");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath, StandardCharsets.UTF_8))) {
                for (String outputLine : validLines) {
                    writer.write(outputLine + "\n");
                    statusArea.append("Processed: " + outputLine + "\n");
                }
            }

            statusArea.append("Conversion completed. Output written to: " + outputPath + "\n");
        } catch (IOException e) {
            statusArea.append("Error: " + e.getMessage() + "\n");
        }
    }

}