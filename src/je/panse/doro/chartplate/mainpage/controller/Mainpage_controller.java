package je.panse.doro.chartplate.mainpage.controller;

import javax.swing.*;
import je.panse.doro.GDSEMR_frame;

public class Mainpage_controller extends GDSEMR_frame {
    private static final String FOLLOW_UP = "Follow up interval: [ 3 ] months";
    private static final String PRESCRIPTION = "Prescription : [ → ] advised the patient to continue with current medication";
    private static final int PLAN_SECTION_INDEX = 8;

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

    public static void main(String[] args) {
        if (args != null && args.length > 0 && "f8".equals(args[0])) {
            SwingUtilities.invokeLater(Mainpage_controller::clearAllTextAreas);
        }
    }
}