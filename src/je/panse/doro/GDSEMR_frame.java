package je.panse.doro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import je.panse.doro.fourgate.thyroid.EMR_thyroid_main;
import je.panse.doro.listner.buttons.BlendColors;
import je.panse.doro.samsara.EMR_east_buttons_obj;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_HbA1c;
import je.panse.doro.samsara.EMR_OBJ_vitalsign.EMR_vitalsign;
import je.panse.doro.soap.subjective.EMR_symptom_main;

public class GDSEMR_frame {
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 1200;

    public static JFrame frame;
    public static JTextArea[] textAreas;
    public static JTextArea tempOutputArea;
    public static String[] titles;

    public GDSEMR_frame() {
        frame = new JFrame("GDS EMR Interface for Physician");
        textAreas = new JTextArea[10];
        tempOutputArea = new JTextArea();
        titles = new String[] { "CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>", "A>", "P>", "Comment>" };
    }

    public void createAndShowGUI() throws Exception {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocation(50, 50);

        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        centerPanel.setPreferredSize(new Dimension(900, 1000));

        tempOutputArea.setEditable(true);

        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.setPreferredSize(new Dimension(500, westPanel.getHeight()));
        westPanel.add(tempOutputArea, BorderLayout.WEST);
        JScrollPane outputScrollPane = new JScrollPane(tempOutputArea);
        outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        westPanel.add(outputScrollPane);

        JPanel northPanel = new GDSEMR_ButtonPanel("north");
        JPanel southPanel = new GDSEMR_ButtonPanel("south");

        // Create the textAreas and add them to the panel
        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new JTextArea();
            String inputData = (titles[i] + "\t" + " ");
            textAreas[i].setLineWrap(true); // enable line wrapping
            textAreas[i].setText(inputData);
            textAreas[i].setCaretPosition(0); // ensure that the JScrollPane knows the preferred size
            // Create background colors
            BlendColors.blendColors(textAreas[i], tempOutputArea, i);

            // Wrap the JTextArea in a JScrollPane
            JScrollPane scrollPane = new JScrollPane(textAreas[i]);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            centerPanel.add(scrollPane); // Add the scrollPane to the panel

            textAreas[i].getDocument().addDocumentListener(new GDSEMR_DocumentListner(textAreas, tempOutputArea));

            textAreas[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        JTextArea source = (JTextArea) e.getSource();
                        String text = source.getText();
                        System.out.println("Double-clicked on: " + text);
                        try {
                            GDSEMR_fourgate.main(text);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
        }

        frame.add(centerPanel);
        frame.add(westPanel, BorderLayout.WEST);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(southPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void setTextAreaText(int i, String string) {
        textAreas[i].append(string);
    }

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    GDSEMR_frame emrFrame = new GDSEMR_frame();
                    emrFrame.createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            EMR_east_buttons_obj.main(null);
            EMR_vitalsign.main(args);
            EMR_HbA1c.main(null);
            EMR_symptom_main.main(null);
            EMR_thyroid_main.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
