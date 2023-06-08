package je.panse.doro.samsara.EMR_OBJ_vitalsign;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
    private JTextArea outputTextArea;
    private static ArrayList<String[]> inputArrayList = new ArrayList<>();

    public EMR_vitalsign() {
        setTitle("GDS Vital Signs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create an ArrayList<String[]> to store the input values
        ArrayList<String[]> inputArrayList = new ArrayList<>();

        // Input TextField
        inputTextField = new JTextField();
        inputTextField.setPreferredSize(new Dimension(inputTextField.getPreferredSize().width, 40));
        inputTextField.setHorizontalAlignment(SwingConstants.CENTER);
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextField.getText();
                EMR_vitalsign_array.printInputArrayList(input);
                outputTextArea.append(addIndentation(input, 4) + "\n"); // Prepend 4 spaces
                inputTextField.setText("");
            }
        });
        add(inputTextField, BorderLayout.NORTH);

        // Output TextArea
        outputTextArea = new JTextArea();
        outputTextArea.setRows(10);
        outputTextArea.setColumns(40);
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputTextArea.setText("");
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

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(clearButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(quitButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private String addIndentation(String text, int indentationLevel) {
        StringBuilder indentedText = new StringBuilder();
        for (int i = 0; i < indentationLevel; i++) {
            indentedText.append(" ");
        }
        indentedText.append(text);
        return indentedText.toString();
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
