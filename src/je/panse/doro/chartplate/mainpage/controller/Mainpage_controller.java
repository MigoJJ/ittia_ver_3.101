package je.panse.doro.chartplate.mainpage.controller;

import javax.swing.*;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.soap.plan.ittiaGDS_FUplan;

/**
 * Controller class for managing the main page of the chart plate application.
 * Extends GDSEMR_frame to inherit its functionality and UI components.
 */
public class Mainpage_controller extends GDSEMR_frame {
    // Constants
    private static final int PLAN_SECTION_INDEX = 8; // Index for "P>" section

    /**
     * Constructor that initializes the main page controller.
     * Calls the parent GDSEMR_frame constructor.
     */
    public Mainpage_controller() {
        super(); // Initialize GDSEMR_frame
    }

    /**
     * Saves content to a specific text area identified by index.
     * @param index The index of the text area to update
     * @param content The content to set in the text area
     */
    public static void saveTextArea(int index, String content) {
        setTextAreaText(index, content); // Delegate to GDSEMR_frame's static method
    }

    /**
     * Clears all text areas in the application.
     * Checks for null array and individual text areas before clearing.
     */
    public static void clearAllTextAreas() {
        if (textAreas != null) {
            for (JTextArea textArea : textAreas) {
                if (textArea != null) {
                    textArea.setText(""); // Clear each text area
                }
            }
        }
        for (int i = 0; i < textAreas.length; i++) textAreas[i].setText(titles[i] + "\t");
    }

    /**
     * Clears and initializes the plan section (textAreas[8]) with default prefix.
     * Validates array bounds and null check before modification.
     * @param monthno Number of months for follow-up period
     */
    public static void clearPlanSection(int monthno) {
        if (textAreas != null && 
            PLAN_SECTION_INDEX < textAreas.length && 
            textAreas[PLAN_SECTION_INDEX] != null) {
            textAreas[PLAN_SECTION_INDEX].setText("");      // Clear the plan section
            textAreas[PLAN_SECTION_INDEX].setText(" P>\n" + 
                "...follow - up [  " + monthno + "  ] month later[ :cd ] \n");
        }
        ittiaGDS_FUplan.main(null);
    }

    /**
     * Main entry point of the application.
     * Handles command-line arguments to trigger specific actions.
     * @param args Command-line arguments ("f8" for clear all, "f9" for clear plan)
     */
    public static void main(String[] args) {
        // Check for command-line arguments
        if (args != null && args.length > 0) {
            switch (args[0]) {
                case "f8":
                    SwingUtilities.invokeLater(Mainpage_controller::clearAllTextAreas);
                    break;
                case "f9":
                    SwingUtilities.invokeLater(() -> clearPlanSection(1));
                    break;
                case "f10":
                    SwingUtilities.invokeLater(() -> clearPlanSection(2));
                    break;
                case "f11":
                    SwingUtilities.invokeLater(() -> clearPlanSection(6));
                    break;
                default:
                    // No action for unrecognized arguments
                    break;
            }
        }
    }
}