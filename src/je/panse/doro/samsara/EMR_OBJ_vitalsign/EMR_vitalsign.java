package je.panse.doro.samsara.EMR_OBJ_vitalsign;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class EMR_vitalsign extends JFrame {

    private JTextField inputTextField;
    private static JTextArea outputTextArea;
    private static JTextArea additionalOutputTextArea; // New JTextArea for additional output
    private static ArrayList<String[]> inputArrayList = new ArrayList<>();

    public EMR_vitalsign() {
        setTitle("GDS Vital Signs");
        setSize(300,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1)); // Use GridLayout with 2 rows and 1 column

        // Create an ArrayList<String[]> to store the input values
        ArrayList<String[]> inputArrayList = new ArrayList<>();

        // Input TextField
        inputTextField = new JTextField();
        inputTextField.setPreferredSize(new Dimension(inputTextField.getPreferredSize().width, 20));
        inputTextField.setHorizontalAlignment(SwingConstants.CENTER);
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextField.getText();
                EMR_vitalsign_array.printInputArrayList(input);

                inputTextField.setText("");
            }
        });
        add(inputTextField);

        // Additional Output TextArea
        additionalOutputTextArea = new JTextArea();
        additionalOutputTextArea.setRows(2);
        additionalOutputTextArea.setColumns(30);
        additionalOutputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane additionalScrollPane = new JScrollPane(additionalOutputTextArea);
        add(additionalScrollPane);
        
        // Output TextArea
        outputTextArea = new JTextArea();
        outputTextArea.setRows(4);
        outputTextArea.setColumns(30);
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane);

        // Buttons
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputTextArea.setText("");
                additionalOutputTextArea.setText("");
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement your save functionality here
            }
        });

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Set the preferred height for the south panel
        JPanel southPanel = new JPanel(new GridLayout(1, 3));
//        Dimension preferredSize = southPanel.getPreferredSize();
//        preferredSize.height = preferredSize.height / 2;
//        southPanel.setPreferredSize(preferredSize);
        southPanel.add(clearButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);

        // Add the south panel to the main frame
        add(southPanel, BorderLayout.SOUTH);

//        pack();
        setVisible(true);
    }

    private static String addIndentation(String text, int indentationLevel) {
        StringBuilder indentedText = new StringBuilder();
        for (int i = 0; i < indentationLevel; i++) {
            indentedText.append(" ");
        }
        indentedText.append(text);
        return indentedText.toString();
    }

    public static void addTextArea(String input) {
        outputTextArea.append(addIndentation(input, 4)); // Prepend 4 spaces
//        additionalOutputTextArea.append(addIndentation("Additional: " + input, 4) + "\n"); // Prepend 4 spaces for additional output
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EMR_vitalsign();
            }
        });
    }
}
