package je.panse.doro.support.sqlite3_manager.icd10.editor10;

import javax.swing.SwingUtilities;

import je.panse.doro.support.sqlite3_manager.icd10.editor.ICD10EditorFrame;

public class ICD10EditorApp {

    public static void main(String[] args) {
        // Ensure GUI creation runs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            FontUtil.applyKoreanFont(); // 👈 Apply Korean font here
            new ICD10EditorFrame(); // Create and show the main frame
        });
    }
}