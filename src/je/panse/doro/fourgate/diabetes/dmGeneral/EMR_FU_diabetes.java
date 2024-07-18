package je.panse.doro.fourgate.diabetes.dmGeneral;

import javax.swing.*;	
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL; // Import java.net.URL
import java.util.ArrayList;

import je.panse.doro.entry.EntryDir;

public class EMR_FU_diabetes extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;  // Add this line
    private static ArrayList<JTextArea> textAreas;
    private static JButton saveButton, exitButton;

    public EMR_FU_diabetes() {
        setTitle("Diabetes Mellitus Preform");
        setSize(400, 1000);
        setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize JTextAreas with default text and make them scrollable
        textAreas = new ArrayList<>();
        String[] defaultTexts = {"CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>", "A>", "P>", "Comment>"};

        // Create panel for JTextAreas
        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new GridLayout(defaultTexts.length, 1));

        for (int i = 0; i < defaultTexts.length; i++) {
            JTextArea textArea = new JTextArea();
            textArea.setText(getSavedText(i)); // Load saved text from file
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textAreaPanel.add(scrollPane);
            textAreas.add(textArea);
        }

        // Create buttons
        saveButton = new JButton("Save");
        exitButton = new JButton("Exit");

        // Attach action listeners to buttons
        saveButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);

        // Set background color using orange
        Color lightGray = Color.LIGHT_GRAY; // Choose your preferred method (predefined or custom)
        for (JTextArea textArea : textAreas) {
            textArea.setBackground(lightGray);
        }

        // Add components to JFrame
        add(textAreaPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveTextToFile();
        } else if (e.getSource() == exitButton) {
            dispose();
        }
    }

    private static String getSavedText(int index) {
        String filename = EntryDir.homeDir + "/fourgate/diabetes/dmGeneral/textarea" + index;
        File file = new File(filename);
        if (file.exists()) {
            return readFile(file);
        } else {
            // Try to read from resources within the JAR
            String resourcePath = EntryDir.homeDir + "/fourgate/diabetes/dmGeneral/textarea" + index;
            URL resource = EMR_FU_diabetes.class.getResource(resourcePath);
            if (resource != null) {
                return readResource(resource);
            }
        }
        return ""; // Return empty string if file doesn't exist
    }

    private static String readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            return sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private static String readResource(URL resource) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            return sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private void saveTextToFile() {
        for (int i = 0; i < textAreas.size(); i++) {
            JTextArea textArea = textAreas.get(i);
            try {
                String filename = EntryDir.homeDir + "/fourgate/diabetes/dmGeneral/textarea" + i;
                File file = new File(filename);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(textArea.getText());
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(null, "Text saved to files.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EMR_FU_diabetes();
            }
        });
    }
}
