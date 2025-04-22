package je.panse.doro.support.sqlite3_manager.icd10.editor;

import javax.swing.SwingUtilities;

public class ICD10EditorApp {

    public static void main(String[] args) {
        // Ensure GUI creation runs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new ICD10EditorFrame(); // Create and show the main frame
        });
    }
}