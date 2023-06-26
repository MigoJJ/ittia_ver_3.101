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
    private JTextArea outputTextArea;
    private JTextArea desoutputTextArea;
    private ArrayList<String[]> inputArrayList;

    public EMR_vitalsign() {
        setTitle("GDS Vital Signs");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        // Input TextField
        inputTextField = new JTextField();
        inputTextField.setPreferredSize(new Dimension(inputTextField.getPreferredSize().width, 20));
        inputTextField.setHorizontalAlignment(SwingConstants.CENTER);
        inputTextField.addActionListener(e -> {
            String input = inputTextField.getText();
            if (input != null && (input.equals("h") || input.equals("i") || input.equals("b") || input.equals("g") || input.equals("r"))) {
                descriptionOfVitalSigns(input);
            }
            EMR_vitalsign_array.printInputArrayList(input, outputTextArea);
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
        clearButton.addActionListener(e -> clearFieldArea());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> buttonvitalsign());

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> outputTextArea.setText(""));

        // Set the preferred height for the south panel
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        southPanel.setPreferredSize(new Dimension(southPanel.getPreferredSize().width, 20));

        southPanel.add(clearButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);

        // Add the south panel to the main frame
        add(southPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void buttonvitalsign() {
        String outputText = outputTextArea.getText();
        String desoutputText = desoutputTextArea.getText();
        String A = "\n" + desoutputText + "\n" + outputText;
        GDSEMR_frame.setTextAreaText(5, A);
        clearFieldArea();
    }

    public void descriptionOfVitalSigns(String input) {
        String dta = desoutputTextArea.getText();
        String returndta = EMR_vitalsign_desreturn.main(input, dta);
        desoutputTextArea.setText(returndta);
    }

    public void clearFieldArea() {
        inputTextField.setText("");
        outputTextArea.setText("");
        desoutputTextArea.setText("at GDS, Left seated position, Regular");
        inputArrayList.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EMR_vitalsign());
    }
}
