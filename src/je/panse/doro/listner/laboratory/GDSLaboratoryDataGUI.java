package je.panse.doro.listner.laboratory;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GDSLaboratoryDataGUI extends JFrame implements ActionListener {

    private static final JTextArea inputTextArea = new JTextArea(55, 35);
    private static final JTextArea outputTextArea = new JTextArea(55, 35);
    private static final String bardorder = """
            make table
            if parameter does not exist -> remove the row;
            Parameter Value Unit 
            using value format 
            """;

    private JButton[] buttons;

    public GDSLaboratoryDataGUI() {
        setupFrame();
        setupTextAreas();
        setupButtons();
        arrangeComponents();
    }

    private void setupFrame() {
        setTitle("GDS Laboratory Data");
        setSize(1200, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupTextAreas() {
        outputTextArea.setEditable(true);
    }

	public static void appendTextAreas(String value) {
		outputTextArea.append(value);		
	}
    
    private void setupButtons() {
        String[] buttonLabels = {"Modify", "Copy to Clipboard", "Clear Input", "Clear Output", "Clear All", "Save and Quit"};
        buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
        }
    }

    private void arrangeComponents() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        JPanel contentPanel = new JPanel(new GridBagLayout());
        addComponent(contentPanel, new JLabel("Input Data:"), 0, 0, GridBagConstraints.NORTH);
        addComponent(contentPanel, new JScrollPane(inputTextArea), 1, 0, GridBagConstraints.BOTH);
        addComponent(contentPanel, new JLabel("Output Data:"), 2, 0, GridBagConstraints.NORTH);
        addComponent(contentPanel, new JScrollPane(outputTextArea), 3, 0, GridBagConstraints.BOTH);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.SOUTH;
        contentPanel.add(buttonPanel, constraints);

        add(contentPanel);
    }

    private void addComponent(JPanel panel, Component comp, int x, int y, int fill) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.fill = fill;
        panel.add(comp, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Modify":
                String textFromInputArea = inputTextArea.getText();
                outputTextArea.append("\nthe blow contents are data --------------------------\n" + textFromInputArea + "\nthe dataset finisfed --------------------------\n");
                outputTextArea.append("\n" + bardorder);
                GDSLaboratoryDataModify.main(textFromInputArea);
                break;
            case "Copy to Clipboard":
                String textToCopy = outputTextArea.getText();
                StringSelection selection = new StringSelection(textToCopy);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
                break;
            case "Clear Input":
                inputTextArea.setText("");
                break;
            case "Clear Output":
                outputTextArea.setText("");
                break;
            case "Clear All":
                inputTextArea.setText("");
                outputTextArea.setText("");
                break;
            case "Save and Quit":
                System.exit(0);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GDSLaboratoryDataGUI gui = new GDSLaboratoryDataGUI();
            gui.setVisible(true);
        });
    }


}
