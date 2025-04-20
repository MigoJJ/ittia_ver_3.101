package je.panse.doro.support.sqlite3_manager.icd_10;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class convert_Text_Text extends JFrame {
    private JTextField inputField;
    private JTextField outputField;
    private JButton inputButton;
    private JButton outputButton;
    private JButton convertButton;
    private JTextArea statusArea;

    // ICDChapter class first
    private static class ICDChapter {
        String title;
        String startCode;
        String endCode;

        ICDChapter(String title, String startCode, String endCode) {
            this.title = title;
            this.startCode = startCode;
            this.endCode = endCode;
        }
    }

    // Static CHAPTERS list
    private static final List<ICDChapter> CHAPTERS = Arrays.asList(
        new ICDChapter("Certain infectious and parasitic diseases", "A00", "B99"),
        new ICDChapter("Neoplasms", "C00", "D48"),
        new ICDChapter("Diseases of the blood and blood-forming organs and certain disorders involving the immune mechanism", "D50", "D89"),
        new ICDChapter("Endocrine, nutritional and metabolic diseases", "E00", "E90"),
        new ICDChapter("Mental and behavioural disorders", "F00", "F99"),
        new ICDChapter("Diseases of the nervous system", "G00", "G99"),
        new ICDChapter("Diseases of the eye and adnexa", "H00", "H59"),
        new ICDChapter("Diseases of the ear and mastoid process", "H60", "H95"),
        new ICDChapter("Diseases of the circulatory system", "I00", "I99"),
        new ICDChapter("Diseases of the respiratory system", "J00", "J99"),
        new ICDChapter("Diseases of the digestive system", "K00", "K93"),
        new ICDChapter("Diseases of the skin and subcutaneous tissue", "L00", "L99"),
        new ICDChapter("Diseases of the musculoskeletal system and connective tissue", "M00", "M99"),
        new ICDChapter("Diseases of the genitourinary system", "N00", "N99"),
        new ICDChapter("Pregnancy, childbirth and the puerperium", "O00", "O99"),
        new ICDChapter("Certain conditions originating in the perinatal period", "P00", "P96"),
        new ICDChapter("Congenital malformations, deformations and chromosomal abnormalities", "Q00", "Q99"),
        new ICDChapter("Symptoms, signs and abnormal clinical and laboratory findings, not elsewhere classified", "R00", "R99"),
        new ICDChapter("Injury, poisoning and certain other consequences of external causes", "S00", "T98"),
        new ICDChapter("External causes of morbidity and mortality", "V01", "Y98"),
        new ICDChapter("Factors influencing health status and contact with health services", "Z00", "Z99"),
        new ICDChapter("Codes for special purposes", "U00", "U99")
    );

    public convert_Text_Text() {
        setTitle("Text File Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        inputField = new JTextField(30);
        outputField = new JTextField(30);
        inputButton = new JButton("Select Input File");
        outputButton = new JButton("Select Output File");
        convertButton = new JButton("Convert");
        statusArea = new JTextArea(10, 50);
        statusArea.setEditable(false);

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

        inputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectInputFile();
            }
        });

        outputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectOutputFile();
            }
        });

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                convertFile();
            }
        });

        inputField.setText("input.txt");
        outputField.setText("post_Text_Text.java");
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

    private String getChapterTitle(String code) {
        for (ICDChapter chapter : CHAPTERS) {
            if (code.compareTo(chapter.startCode) >= 0 && code.compareTo(chapter.endCode) <= 0) {
                return chapter.title;
            }
        }
        return "Unknown Chapter";
    }

    private void convertFile() {
        String inputPath = inputField.getText();
        String outputPath = outputField.getText();

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
                String chapterTitle = getChapterTitle(code);

                description = description.replace("\"", "\\\"");
                chapterTitle = chapterTitle.replace("\"", "\\\"");

                String outputLine = String.format("{\"%s\", \"%s\", \"%s\", \".\" },%n", chapterTitle, code, description);
                writer.write(outputLine);

                statusArea.append("Processed: " + code + " (" + chapterTitle + ")\n");
                statusArea.setCaretPosition(statusArea.getDocument().getLength());
            }

            statusArea.append("Conversion completed successfully.\n");
        } catch (IOException e) {
            statusArea.append("Error: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new convert_Text_Text().setVisible(true);
            }
        });
    }
}
