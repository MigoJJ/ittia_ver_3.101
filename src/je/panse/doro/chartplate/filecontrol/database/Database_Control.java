package je.panse.doro.chartplate.filecontrol.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Database_Control extends JFrame {
    private static final String TARGET_DIR = "/home/dce040b/git/MigoJJ-ittia_ver_3.101/src/je/panse/doro/chartplate/filecontrol/database";
    private static final String[] DB_FILES = {
        "/home/dce040b/git/MigoJJ-ittia_ver_3.101/src/je/panse/doro/fourgate/n_laboratorytest/frequent/javalabtests.db",
        "/home/dce040b/git/MigoJJ-ittia_ver_3.101/src/je/panse/doro/soap/assessment/icd_11/icd11.db",
        "/home/dce040b/git/MigoJJ-ittia_ver_3.101/src/je/panse/doro/soap/assessment/kcd8/kcd8db.db",
        "/home/dce040b/git/MigoJJ-ittia_ver_3.101/src/je/panse/doro/support/sqlite3_manager/abbreviation/AbbFullDis.db",
        "/home/dce040b/git/MigoJJ-ittia_ver_3.101/src/je/panse/doro/support/sqlite3_manager/labcode/LabCodeFullDis.db"
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
    }

    private void handleSave(ActionEvent e) {
        try {
            // Ensure target directory exists
            Path targetDir = Paths.get(TARGET_DIR);
            Files.createDirectories(targetDir);

            // Copy each database file to the target directory
            for (String dbFile : DB_FILES) {
                Path source = Paths.get(dbFile);
                Path target = Paths.get(TARGET_DIR, source.getFileName().toString());
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
            // Copy each database file from the target directory back to its original location
            for (String dbFile : DB_FILES) {
                Path source = Paths.get(TARGET_DIR, Paths.get(dbFile).getFileName().toString());
                Path target = Paths.get(dbFile);
                if (Files.exists(source)) {
                    // Ensure the parent directory exists
                    Files.createDirectories(target.getParent());
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