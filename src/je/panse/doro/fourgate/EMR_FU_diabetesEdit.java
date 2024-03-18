package je.panse.doro.fourgate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane; // Import for user messages
import javax.swing.SwingWorker;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.entry.EntryDir;

public class EMR_FU_diabetesEdit extends JFrame {

    private static final int NUM_TEXT_AREAS = 10;

    public EMR_FU_diabetesEdit() {
        for (int i = 0; i < NUM_TEXT_AREAS; i++) {
            if (GDSEMR_frame.textAreas[i] != null) {
                GDSEMR_frame.textAreas[i].setText("");
            }
            new FileLoader(getFilepath(i)).execute(); // Use getFilepath for dynamic path
        }
    }

    private String getFilepath(int index) {
        return EntryDir.homeDir + "/fourgate/diabetes/dmGeneral/textarea" + index;
    }

    private static class FileLoader extends SwingWorker<String, Void> {

        private final String fileName;

        public FileLoader(String fileName) {
            this.fileName = fileName;
        }

        @Override
        protected String doInBackground() throws Exception {
            String text = "";
            try (Scanner scanner = new Scanner(new File(fileName))) {
                while (scanner.hasNextLine()) {
                    text += scanner.nextLine() + "\n";
                }
            } catch (FileNotFoundException ex) {
                // Inform user about file not found
                JOptionPane.showMessageDialog(null, "Failed to read file: " + fileName, "Error", JOptionPane.ERROR_MESSAGE);
                return null; // Indicate failure
            }
            return text;
        }

        @Override
        protected void done() {
            try {
                String text = get();
                if (text != null && GDSEMR_frame.textAreas[getIndex()] != null) {
                    GDSEMR_frame.textAreas[getIndex()].setText(text);
                    EMR_Comments.main("DM");
                }
            } catch (Exception ex) {
                System.err.println("Failed to load file " + fileName + ": " + ex.getMessage());
            }
        }

        private int getIndex() {
            // Assuming FileLoader is created with the correct index
            return this.fileName.lastIndexOf("/") + 1; // Extract index from filename
        }
    }

    public static void main(String[] args) {
        new EMR_FU_diabetesEdit();
    }
}
