package je.panse.doro.support.sqlite3_manager.icd_10.ICD10TextFile;

import javax.swing.*;
import je.panse.doro.entry.EntryDir;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class TextFileConverterUI extends JFrame {
    private final JTextField inputField;
    private final JTextField outputField;
    private final JTextArea statusArea;
    private final FileConverter fileConverter;

    public TextFileConverterUI() {
        setTitle("Text File Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450); // Slightly larger
        setMinimumSize(new Dimension(500, 300)); // Ensure resizable but not too small
        setLocationRelativeTo(null);

        inputField = new JTextField(30);
        outputField = new JTextField(30);
        statusArea = new JTextArea(10, 50);
        statusArea.setEditable(false);

        ICDChapterRegistry chapterRegistry = new ICDChapterRegistry();
        fileConverter = new FileConverter(chapterRegistry, statusArea);

        initializeUI();
        setDefaultPaths();
    }

    private void initializeUI() {
        JButton inputButton = new JButton("Select Input File");
        JButton outputButton = new JButton("Select Output File");
        JButton convertButton = new JButton("Convert");
        JButton clearButton = new JButton("Clear Status");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Input File:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(inputField, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        panel.add(inputButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Output File:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(outputField, gbc);

        gbc.gridx = 3;
        gbc.gridwidth = 1;
        panel.add(outputButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(convertButton, gbc);

        gbc.gridx = 2;
        panel.add(clearButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(new JScrollPane(statusArea), gbc);

        add(panel);

        inputButton.addActionListener(e -> selectInputFile());
        outputButton.addActionListener(e -> selectOutputFile());
        convertButton.addActionListener(e -> convertFile());
        clearButton.addActionListener(e -> statusArea.setText(""));
    }

    private void selectInputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(EntryDir.homeDir)); // Start in EntryDir.homeDir
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            inputField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void selectOutputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(EntryDir.homeDir)); // Start in EntryDir.homeDir
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            outputField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void convertFile() {
        String inputPath = inputField.getText();
        String outputPath = outputField.getText();

        if (inputPath.isEmpty() || outputPath.isEmpty()) {
            statusArea.append("Please select both input and output files.\n");
            return;
        }

        File inputFile = new File(inputPath);
        if (!inputFile.exists() || !inputFile.canRead()) {
            statusArea.append("Input file is invalid or unreadable: " + inputPath + "\n");
            return;
        }

        fileConverter.convertFile(inputPath, outputPath);
    }

    private void setDefaultPaths() {
        inputField.setText(EntryDir.homeDir + "/support/sqlite3_manager/icd_10/ICD10TextFile/ICD10_pre.txt");
        outputField.setText(EntryDir.homeDir + "/support/sqlite3_manager/icd_10/ICD10_post.txt");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextFileConverterUI().setVisible(true));
    }
}