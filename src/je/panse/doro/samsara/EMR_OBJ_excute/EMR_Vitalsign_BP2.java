package je.panse.doro.samsara.EMR_OBJ_excute;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EMR_Vitalsign_BP2 extends JFrame {

    private JTextField inputTextField;
    private JTextArea outputTextArea;

    public EMR_Vitalsign_BP2() {
        setTitle("GDS Vital Signs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input TextField
        inputTextField = new JTextField();
        inputTextField.setPreferredSize(new Dimension(inputTextField.getPreferredSize().width, 40));
        inputTextField.setHorizontalAlignment(SwingConstants.CENTER);
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextField.getText();
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
                System.exit(0);
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
                new EMR_Vitalsign_BP2();
            }
        });
    }
}
