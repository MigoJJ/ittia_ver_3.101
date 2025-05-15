package je.panse.doro.chartplate.filecontrol.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import je.panse.doro.entry.EntryDir;

public class Database_Control extends JFrame {
    private static final String TARGET_DIR = "/home/dce040b/문서/ITTIA_EMR_db"; // Backup directory (external)
    private static final String DEST_DIR = EntryDir.homeDir + "/chartplate/filecontrol/database"; // Destination for Rescue
    private static final String[] DB_FILES = {
        EntryDir.homeDir + "/fourgate/n_laboratorytest/frequent/javalabtests.db",
        EntryDir.homeDir + "/soap/assessment/icd_11/icd11.db",
        EntryDir.homeDir + "/soap/assessment/kcd8/kcd8db.db",
        EntryDir.homeDir + "/support/sqlite3_manager/abbreviation/AbbFullDis.db",
        EntryDir.homeDir + "/support/sqlite3_manager/labcode/LabCodeFullDis.db"
    };

    public Database_Control() {
        // Set up the JFrame
        setTitle("Database File Control");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Create buttons
        JButton saveButton = new JButton("Save");
        JButton rescueButton = new JButton("Rescue");
        JButton quitButton = new JButton("Quit");

        // Add action listeners
        saveButton.addActionListener(this::handleSave);
        rescueButton.addActionListener(this::handleRescue);
        quitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the panel
        add(saveButton);
        add(rescueButton);
        add(quitButton);

        // Set background
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Ensure destination directory exists
        EntryDir.createDirectoryIfNotExists(DEST_DIR);
    }

    private void handleSave(ActionEvent e) {
        try {
            // Ensure target directory exists
            Path targetDir = Paths.get(TARGET_DIR);
            Files.createDirectories(targetDir);

            // Copy each database file to the target directory
            for (String dbFile : DB_FILES) {
                Path source = Paths.get(dbFile);
                Path target = Paths.get(TARGET_DIR, Paths.get(dbFile).getFileName().toString());
                if (Files.exists(source)) {
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new IOException("Source file not found: " + dbFile);
                }
            }
            JOptionPane.showMessageDialog(this, "Database files saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error during save: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void handleRescue(ActionEvent e) {
        try {
            // Ensure destination directory exists
            Path destDir = Paths.get(DEST_DIR);
            Files.createDirectories(destDir);

            // Copy each database file from TARGET_DIR to DEST_DIR
            for (String dbFile : DB_FILES) {
                Path source = Paths.get(TARGET_DIR, Paths.get(dbFile).getFileName().toString());
                Path target = Paths.get(DEST_DIR, Paths.get(dbFile).getFileName().toString());
                if (Files.exists(source)) {
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new IOException("Backup file not found: " + source);
                }
            }
            JOptionPane.showMessageDialog(this, "Database files rescued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error during rescue: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Database_Control().setVisible(true));
    }
}