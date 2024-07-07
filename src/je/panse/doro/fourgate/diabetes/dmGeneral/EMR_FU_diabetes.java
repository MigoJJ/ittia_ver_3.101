package je.panse.doro.fourgate.diabetes.dmGeneral;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import je.panse.doro.entry.EntryDir;

public class EMR_FU_diabetes extends JFrame implements ActionListener {
    private ArrayList<JTextArea> textAreas;
    private JButton saveButton, exitButton;

    public EMR_FU_diabetes() {
        setTitle("Diabetes Mellitus Preform");
        setSize(400, 1000);
        setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize JTextAreas
        textAreas = new ArrayList<>();
        String[] defaultTexts = {"CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>", "A>", "P>", "Comment>"};

        JPanel textAreaPanel = new JPanel(new GridLayout(defaultTexts.length, 1));
        for (String text : defaultTexts) {
            JTextArea textArea = new JTextArea(text);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textAreaPanel.add(scrollPane);
            textAreas.add(textArea);
        }

        // Buttons
        saveButton = new JButton("Save");
        exitButton = new JButton("Exit");
        saveButton.addActionListener(this);
        exitButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);

        // Add components to JFrame
        getContentPane().add(textAreaPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String getSavedText(int index) {
        String filename = EntryDir.homeDir + "/fourgate/diabetes/dmGeneral/textarea" + index; // Ensure EntryDir.homeDir is initialized
        if (filename.contains("null")) {
            System.err.println("Error: Path contains 'null'. Path was not initialized correctly.");
            return "";
        }

        File file = new File(filename);
        if (!file.exists()) return ""; // If file doesn't exist, return empty string

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return ""; // Return empty string if there was an error
        }
        return sb.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            for (int i = 0; i < textAreas.size(); i++) {
                JTextArea textArea = textAreas.get(i);
                String filename = EntryDir.homeDir + "/fourgate/diabetes/dmGeneral/textarea" + i;
                if (filename.contains("null")) {
                    JOptionPane.showMessageDialog(this, "File path is not initialized correctly.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                    writer.write(textArea.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(this, "Text saved to files.");
        } else if (e.getSource() == exitButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EMR_FU_diabetes::new);
    }
}
