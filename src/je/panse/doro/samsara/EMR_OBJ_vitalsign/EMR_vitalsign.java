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

import je.panse.doro.GDSEMR_frame;

public class EMR_vitalsign extends JFrame {

    private JTextField inputTextField;
    private static JTextArea outputTextArea;
    private static JTextArea desoutputTextArea;
    private static ArrayList<String[]> inputArrayList = new ArrayList<>();

    public EMR_vitalsign() {
        setTitle("GDS Vital Signs");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1)); // Use GridLayout with 4 rows and 1 column

        // Input TextField
        inputTextField = new JTextField();
        inputTextField.setPreferredSize(new Dimension(inputTextField.getPreferredSize().width, 20));
        inputTextField.setHorizontalAlignment(SwingConstants.CENTER);
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextField.getText();

                if (input != null && (input.equals("h") || input.equals("i") || input.equals("b") || input.equals("g") || input.equals("r"))) {
                    descriptionOfVitalSigns(input);
                } else {
                    // JOptionPane.showMessageDialog(null, "String does not match the specified values.");
                }

                EMR_vitalsign_array.printInputArrayList(input, outputTextArea);
                inputTextField.setText("");
            }
        });
        add(inputTextField);

        // Output TextArea
        outputTextArea = new JTextArea();
        outputTextArea.setRows(3);
        outputTextArea.setColumns(30);
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane);

        desoutputTextArea = new JTextArea();
        desoutputTextArea.setRows(3);
        desoutputTextArea.setColumns(30);
        desoutputTextArea.setText("at GDS, Left seated position, Regular");
        desoutputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane1 = new JScrollPane(desoutputTextArea);
        add(scrollPane1);

        // Buttons
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputTextArea.setText("");
                desoutputTextArea.setText("");
                inputTextField.setText("");
            }
        });
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonvitalsign();
                outputTextArea.setText("");
                desoutputTextArea.setText("");
                inputTextField.setText("");
            }
        });
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                buttonvitalsign();
                dispose();
            }
        });

        // Set the preferred height for the south panel
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        Dimension panelSize = new Dimension(southPanel.getPreferredSize().width, 20);
        southPanel.setPreferredSize(panelSize);

        southPanel.add(clearButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);

        // Add the south panel to the main frame
        add(southPanel, BorderLayout.SOUTH);
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
        String[] vitalSigns = input.split(" "); // Split the input string by space
        StringBuilder sb = new StringBuilder();
        ArrayList<String> formattedVitalSigns = new ArrayList<>(); // Create a new ArrayList to store formatted vital signs
        for (String vitalSign : vitalSigns) {
            String[] parts = vitalSign.split("\\[|\\]"); // Split each vital sign by "[" or "]"
            if (parts.length == 3) {
                String name = parts[0];
                String value = parts[1];
                String unit = parts[2];
                String formattedVitalSign = name + " [ " + value + " ] " + unit;
                formattedVitalSigns.add(formattedVitalSign); // Add the formatted vital sign to the ArrayList
            }
        }

        // Append the formatted vital signs to the StringBuilder
        for (int i = 0; i < formattedVitalSigns.size(); i++) {
            sb.append(formattedVitalSigns.get(i));
            if (i != formattedVitalSigns.size() - 1) {
                sb.append("  ");
            }
        }

        // Append the formatted vital signs to the outputTextArea
        outputTextArea.setText(""); // Clear the outputTextArea before adding new content
        outputTextArea.append(sb.toString() + "\n");
    }

    public static void buttonvitalsign() {
        String outputText = outputTextArea.getText();
        String desoutputText = desoutputTextArea.getText();
        String A = "\n" + desoutputText + "\n" + outputText;
        System.out.println(A);
        GDSEMR_frame.setTextAreaText(5, A);
    }

    public static void descriptionOfVitalSigns(String input) {
        String dta = desoutputTextArea.getText();
        String returndta = EMR_vitalsign_desreturn.main(input, dta);
        desoutputTextArea.setText(returndta);
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
