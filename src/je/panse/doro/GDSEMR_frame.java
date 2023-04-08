package je.panse.doro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import je.panse.doro.listner.TextAreaChangeListener;

public class GDSEMR_frame extends JFrame {

    public GDSEMR_frame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("My Frame");
        setSize(1600, 1200);

        // Create West panel with tempOutputArea
        JTextArea tempOutputArea = new JTextArea();
        tempOutputArea.setPreferredSize(new Dimension(400, 200));
        add(new JScrollPane(tempOutputArea), BorderLayout.WEST);

        // Create Center panel with 9 text areas
        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        JTextArea[] textAreas = new JTextArea[10];
        String[] titles = { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam","A>", "P>", "Co>" };
        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new JTextArea();
            String inputData = titles[i];
            textAreas[i].setText(inputData);
            centerPanel.add(textAreas[i]);

            JTextArea textArea = textAreas[i];
            TextAreaChangeListener listener = new TextAreaChangeListener(textArea, tempOutputArea);
            textArea.getDocument().addDocumentListener(listener);

            // Set background color
            float factor = (float) i / centerPanel.getComponentCount();
            Color color = Color.getHSBColor(0.16f, factor, 3.0f);
            textArea.setBackground(color);
        }

        // Add scroll pane to center panel
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new GDSEMR_frame();
    }
}
