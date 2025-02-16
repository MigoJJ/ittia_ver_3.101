package je.panse.doro.fourgate.thyroid.pregnancy;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.entry.EntryDir;

public class EMR_thyroid_Pregnancyentry {
    // Button labels for different thyroid conditions
    private static final String[] BUTTONS = {
        "New Patient for Pregnancy with Thyroid disease",
        "F/U Pregnancy with Normal Thyroid Function (TAb+)",
        "Infertility and Thyroid Function Evaluation",
        "F/U Pregnancy with Hyperthyroidism",
        "F/U Pregnancy with TSH low (Hyperthyroidism/GTT)",
        "F/U Pregnancy with Hypothyroidism",
        "F/U Pregnancy with TSH elevation (Subclinical Hypothyroidism)",
        "Postpartum Thyroiditis",
        "Quit"
    };

    // Frame dimensions
    private static final int FRAME_WIDTH = 410;
    private static final int FRAME_HEIGHT = 400;
    private static final int AUTO_CLOSE_DELAY = 300000; // 5 minutes

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EMR_thyroid_Pregnancyentry::createAndShowGUI);
    }

    /**
     * Creates and displays the GUI frame.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Thyroid Pregnancy Management");
        setupFrame(frame);
        addButtons(frame);
        positionFrame(frame);
        setupAutoClose(frame);
        frame.setVisible(true);
    }

    /**
     * Configures the frame properties.
     * 
     * @param frame The JFrame to configure
     */
    private static void setupFrame(JFrame frame) {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(BUTTONS.length, 1));
    }

    /**
     * Adds buttons for thyroid condition management.
     * 
     * @param frame The JFrame to which buttons are added
     */
    private static void addButtons(JFrame frame) {
        for (String buttonText : BUTTONS) {
            frame.add(createStyledButton(buttonText, e -> handleButtonClick(frame, buttonText)));
        }
    }

    /**
     * Creates a styled JButton with gradient background.
     * 
     * @param text     Button label
     * @param listener Action listener for the button
     * @return A styled JButton
     */
    private static JButton createStyledButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2d) {
                    int h = getHeight();
                    GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(210, 180, 140),
                        0, h, new Color(180, 150, 110)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), h);
                }
                super.paintComponent(g);
            }
        };
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(listener);
        return button;
    }

    /**
     * Positions the frame on the screen (bottom-right corner).
     * 
     * @param frame The JFrame to position
     */
    private static void positionFrame(JFrame frame) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
            screen.width - FRAME_WIDTH,
            screen.height - FRAME_HEIGHT
        );
    }

    /**
     * Sets up automatic frame closure after a delay.
     * 
     * @param frame The JFrame to automatically close
     */
    private static void setupAutoClose(JFrame frame) {
        new Timer(AUTO_CLOSE_DELAY, e -> frame.dispose()).start();
    }

    /**
     * Handles button click events.
     * 
     * @param frame      The parent JFrame
     * @param buttonText The label of the clicked button
     */
    private static void handleButtonClick(JFrame frame, String buttonText) {
        if ("Quit".equals(buttonText)) {
            frame.dispose();
            return;
        }

        if ("New Patient for Pregnancy with Thyroid disease".equals(buttonText)) {
            // Assuming EMR_Preg_CC.main() triggers patient entry
            EMR_Preg_CC.main(null);
            return;
        }

        // Construct the file path for the selected follow-up type
        String fileName = buttonText.replaceAll("[^a-zA-Z0-9\\s]", "").replace(" ", "_") + ".odt";
        String filePath = EntryDir.getThyroidFilePath(fileName);

        // Attempt to open the corresponding ODT document
//        openFile(filePath, frame);

        // Update the EMR text area with the selected condition
        updateEMRFrameText(buttonText);
    }

    /**
     * Opens the specified file using the default desktop application.
     * 
     * @param filePath The path to the file
     * @param frame    The parent JFrame for error dialogs
     */
    private static void openFile(String filePath, JFrame frame) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(frame, 
                    "File not found: " + filePath, 
                    "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "Desktop operations are not supported on this platform.", 
                    "System Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, 
                "Error opening file: " + ex.getMessage(), 
                "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the EMR frame text area with patient condition details.
     * 
     * @param condition The thyroid condition selected
     */
    private static void updateEMRFrameText(String condition) {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String baseCondition = condition.replace("F/U ", "");

        GDSEMR_frame.setTextAreaText(0, 
            String.format("F/U [   ] weeks    %s%n\t%s", currentDate, baseCondition));
        GDSEMR_frame.setTextAreaText(7, 
            String.format("%n  #  %s  [%s]", condition, currentDate));
        GDSEMR_frame.setTextAreaText(8, 
            String.format("...Plan F/U [   ] weeks%n\t %s", baseCondition));
    }
}
