package je.panse.doro.samsara;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EMR_FU_SaveEdit extends JFrame {
  private JTextArea[] textAreas;
  private JButton saveButton;
  private JButton recallButton;
  private JButton exitButton;
  private JFileChooser fileChooser;
  private File savedFile;

  public EMR_FU_SaveEdit() {
    // Set up the window
    setTitle("Text Editor");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set up the text areas
    textAreas = new JTextArea[10];
    JPanel textAreaPanel = new JPanel(new GridLayout(10, 1));
    for (int i = 0; i < 10; i++) {
      textAreas[i] = new JTextArea();
      JScrollPane scrollPane = new JScrollPane(textAreas[i]);
      textAreaPanel.add(scrollPane);
    }
    add(textAreaPanel, BorderLayout.CENTER);

    // Set up the buttons
    saveButton = new JButton("Save");
    recallButton = new JButton("Recall");
    exitButton = new JButton("Exit");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(saveButton);
    buttonPanel.add(recallButton);
    buttonPanel.add(exitButton);
    add(buttonPanel, BorderLayout.SOUTH);

    // Set up the file chooser
    fileChooser = new JFileChooser();

    // Add action listeners to the buttons
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Create a file chooser dialog and display it to the user
        int result = fileChooser.showSaveDialog(EMR_FU_SaveEdit.this);

        // If the user selected a file and clicked "Save", save the text in all JTextAreas to the file
        if (result == JFileChooser.APPROVE_OPTION) {
          try {
				File file = new File("/home/woon/문서/hypertension");
				FileWriter writer = new FileWriter(fileChooser.getSelectedFile());
				for (int i = 0; i < 10; i++) {
					writer.write(textAreas[i].getText() + "\n");
            }
            writer.close();
            savedFile = fileChooser.getSelectedFile();
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(EMR_FU_SaveEdit.this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });

    recallButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // If a file has been saved previously, read the text from the file and set it as the text of all JTextAreas
        if (savedFile != null) {
          try {
				File file = new File("/home/woon/문서/hypertension");
				FileReader reader = new FileReader(savedFile);
				BufferedReader bufferedReader = new BufferedReader(reader);
				for (int i = 0; i < 10; i++) {
              String line = bufferedReader.readLine();
              if (line != null) {
                textAreas[i].setText(line);
              }
            }
            reader.close();
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(EMR_FU_SaveEdit.this, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });

    exitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    // Display the window
    setVisible(true);
  }

  public static void main(String[] args) {
	  EMR_FU_SaveEdit editor = new EMR_FU_SaveEdit();
  }
}
