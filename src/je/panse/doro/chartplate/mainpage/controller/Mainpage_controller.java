package je.panse.doro.chartplate.mainpage.controller;

import je.panse.doro.GDSEMR_frame;

// Assuming GDSEMR_frame is a JFrame subclass defined elsewhere
public class Mainpage_controller extends GDSEMR_frame {

    // Method triggered by F8 key press
    private static void F8short() {
        for (int i = 0; i < textAreas.length; i++) {
            textAreas[i].setText(titles[i] + "\t");
        }
    }

    // Main method to run the application
    public static void main(String fshort) {
    	
    	F8short();
   
    }
}