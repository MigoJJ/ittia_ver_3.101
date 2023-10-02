package je.panse.doro.listner.laboratory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class GDSbardlab {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GDS EMR Laboratory Summary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);

        JPanel westPanel = createWestPanel();
        JPanel centerPanel = createCenterPanel(westPanel);
        JPanel southPanel = createSouthPanel(westPanel);

        frame.add(westPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JPanel createWestPanel() {
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        JTextArea outputTextArea = new JTextArea(5, 40); // Let's define an initial row count and column count
        outputTextArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        westPanel.add(scrollPane);

        return westPanel;
    }

    private static JPanel createCenterPanel(JPanel westPanel) {
        JPanel centerPanel = new JPanel(new GridLayout(10, 1));

        for (int i = 1; i <= 10; i++) {
            JButton button = new JButton("Select " + i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextArea outputTextArea = (JTextArea)((JScrollPane)westPanel.getComponent(0)).getViewport().getView();
//                    outputTextArea.append("Button " + button.getText() + " clicked.\n");
                    outputTextArea.append(GDSbard_return_message.main(button.getText()));
                }
            });
            centerPanel.add(button);
        }

        return centerPanel;
    }

    private static JPanel createSouthPanel(JPanel westPanel) {
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] buttonLabels = {"Copy", "Save", "Clear","Quit"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextArea outputTextArea = (JTextArea)((JScrollPane)westPanel.getComponent(0)).getViewport().getView();

                    if ("Quit".equals(label)) {
//                        System.exit(0); // Close the application
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
                        frame.dispose();
                    } else if ("Clear".equals(label)) {
                        outputTextArea.setText("");
                    } else if ("Copy".equals(label)) {
                        StringSelection stringSelection = new StringSelection(outputTextArea.getText());
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, null);
                    } else {
                        // other button actions
                    }

                    
                }
            });
            southPanel.add(button);
        }

        return southPanel;
    }
}