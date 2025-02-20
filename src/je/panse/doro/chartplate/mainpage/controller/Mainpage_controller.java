package je.panse.doro.chartplate.mainpage.controller;

import javax.swing.*;
import je.panse.doro.GDSEMR_frame;

public class Mainpage_controller extends GDSEMR_frame {
    private static final int PLAN_SECTION_INDEX = 8; // Index for "P>" section

    public Mainpage_controller() {
        super(); // Calls GDSEMR_frame constructor
    }

    public static void saveTextArea(int index, String content) {
        setTextAreaText(index, content); // Delegates to GDSEMR_frame's static method
    }

    public static void clearAllTextAreas() {
        if (textAreas != null) {
            for (JTextArea textArea : textAreas) {
                if (textArea != null) {
                    textArea.setText(""); // Clear each text area
                }
            }
        }
    }

    public static void clearPlanSection() {
        if (textAreas != null && PLAN_SECTION_INDEX < textAreas.length && textAreas[PLAN_SECTION_INDEX] != null) {
            textAreas[PLAN_SECTION_INDEX].setText(""); // Clear only textAreas[8]
            textAreas[PLAN_SECTION_INDEX].setText(" P>\n"); // Clear only textAreas[8]
        }
    }

    public static void main(String[] args) {
        if (args != null && args.length > 0 && "f8".equals(args[0])) {
            SwingUtilities.invokeLater(Mainpage_controller::clearAllTextAreas);
        }
    }
}