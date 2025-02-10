package je.panse.doro;

import java.awt.*;		
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

import je.panse.doro.chartplate.keybutton.EMR_east_buttons_obj;
import je.panse.doro.chartplate.keybutton.GDSEMR_ButtonNorthSouth;
import je.panse.doro.chartplate.keybutton.GDSEMR_FunctionKey;
import je.panse.doro.chartplate.mainpage.EMR_BlendColors;
import je.panse.doro.chartplate.mainpage.GDSEMR_DocumentListner;
import je.panse.doro.chartplate.mainpage.GDSEMR_fourgate;
import je.panse.doro.fourgate.n_vaccinations.InjectionApp;
import je.panse.doro.samsara.EMR_OBJ_Vitalsign.Vitalsign;
import je.panse.doro.samsara.EMR_OBJ_excute.*;
import je.panse.doro.soap.subjective.EMR_symptom_main;

public class GDSEMR_frame {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 1020;

    public static JFrame frame;
    public static JTextArea[] textAreas;
    public static JTextArea tempOutputArea;
    public static String[] titles = {
        "CC>", "PI>", "ROS>", "PMH>", "S>", 
        "O>", "Physical Exam>", "A>", "P>", "Comment>"
    };

    public GDSEMR_frame() {
        frame = new JFrame("GDS EMR Interface for Physician");
        textAreas = new JTextArea[titles.length];
        tempOutputArea = new JTextArea();
    }

    /**
     * Initializes and displays the GUI.
     */
    public void createAndShowGUI() throws Exception {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocation(360, 60);
        frame.setUndecorated(true);

        // Add panels
        frame.add(createCenterPanel(), BorderLayout.CENTER);
        frame.add(createWestPanel(), BorderLayout.WEST);
        frame.add(new GDSEMR_ButtonNorthSouth("north"), BorderLayout.NORTH);
        frame.add(new GDSEMR_ButtonNorthSouth("south"), BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Creates the center panel containing text areas.
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(5, 2));
        centerPanel.setPreferredSize(new Dimension(900, 1000));

        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i] = new JTextArea(titles[i] + "\t");
            textAreas[i].setLineWrap(true);
            textAreas[i].setCaretPosition(0);

            // Apply custom blending colors
            try {
                EMR_BlendColors.blendColors(textAreas[i], tempOutputArea, i);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add scroll pane and listeners
            JScrollPane scrollPane = new JScrollPane(textAreas[i]);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            centerPanel.add(scrollPane);

            textAreas[i].getDocument().addDocumentListener(new GDSEMR_DocumentListner(textAreas, tempOutputArea));
            textAreas[i].addMouseListener(new DoubleClickMouseListener());
            textAreas[i].addKeyListener(new FunctionKeyPress());
        }
        return centerPanel;
    }

    /**
     * Creates the west panel containing the output area.
     */
    private JPanel createWestPanel() {
        // Custom JPanel with gradient background
        JPanel westPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                // Define the gradient from white to light green
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(240, 255, 240), // Very light green
                    0, height, new Color(220, 245, 220) // Slightly darker green
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, width, height);
            }
        };

        westPanel.setPreferredSize(new Dimension(500, FRAME_HEIGHT));

        // ScrollPane for tempOutputArea
        JScrollPane outputScrollPane = new JScrollPane(tempOutputArea);
        outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setOpaque(false);
        outputScrollPane.getViewport().setOpaque(false);

        // Ensure tempOutputArea itself is transparent to show the gradient
        tempOutputArea.setOpaque(false);
        tempOutputArea.setBorder(null);

        westPanel.add(outputScrollPane, BorderLayout.CENTER);

        return westPanel;
    }


    /**
     * Updates the text of a specific text area.
     */
    public static void setTextAreaText(int index, String text) {
        if (textAreas != null && index >= 0 && index < textAreas.length) {
            textAreas[index].append(text);
        } else {
            System.err.println("Invalid text area index or text areas not initialized.");
        }
    }

    /**
     * Updates the temporary output area.
     */
    public static void updateTempOutputArea(String text) {
        tempOutputArea.setText(text);
    }

    /**
     * Handles function key press events.
     */
    private static class FunctionKeyPress extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F12) {
                String functionKeyMessage = "F" + (keyCode - KeyEvent.VK_F1 + 1) + " key pressed - Action executed.";
                GDSEMR_FunctionKey.handleFunctionKeyAction(1, functionKeyMessage, keyCode);
            }
        }
    }

    /**
     * Handles double-click events on text areas.
     */
    private static class DoubleClickMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                JTextArea source = (JTextArea) e.getSource();
                String text = source.getText();
                try {
                    GDSEMR_fourgate.main(text);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GDSEMR_frame emrFrame = new GDSEMR_frame();
            try {
                emrFrame.createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Additional feature executions
        EMR_east_buttons_obj.main(null);
        Vitalsign.main(args);
        EMR_HbA1c.main(null);
        EMR_symptom_main.main(null);
        EMR_BMI_calculator.main(null);
        EMR_TFT.main(null);
        InjectionApp.main(null);
    }
}
