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
        setSize(600, 400);
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

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Input File:"), gbc);
        gbc.gridx = 1;
        panel.add(inputField, gbc);
        gbc.gridx = 2;
        panel.add(inputButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Output File:"), gbc);
        gbc.gridx = 1;
        panel.add(outputField, gbc);
        gbc.gridx = 2;
        panel.add(outputButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        panel.add(convertButton, gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(statusArea), gbc);

        add(panel);

        inputButton.addActionListener(e -> selectInputFile());
        outputButton.addActionListener(e -> selectOutputFile());
        convertButton.addActionListener(e -> fileConverter.convertFile(
            inputField.getText(), outputField.getText()));
    }

    private void selectInputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            inputField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void selectOutputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            outputField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void setDefaultPaths() {
        inputField.setText(EntryDir.homeDir + "/support/sqlite3_manager/code/icd_10/input.txt");
        outputField.setText(EntryDir.homeDir + "/support/sqlite3_manager/code/icd_10/post_Text_Text.txt");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextFileConverterUI().setVisible(true));
    }
}