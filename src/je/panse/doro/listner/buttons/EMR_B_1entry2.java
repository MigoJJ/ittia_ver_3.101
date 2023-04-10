package je.panse.doro.listner.buttons;

import javax.swing.JTextArea;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.listner.IndentedTextArea;

public class EMR_B_1entry2 extends GDSEMR_frame {
    public EMR_B_1entry2() throws Exception {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void EMR_B_selection(int noButton, String panelLocation, IndentedTextArea[] textAreas,
            JTextArea tempOutputArea) {
        // TODO Auto-generated method stub

        switch (noButton) {
            case 1:
                if (panelLocation.equals("north")) {
                    for (int i = 0; i < textAreas.length; i++) {
                        textAreas[i].setText("");
                    }
                    tempOutputArea.setText("");
                    System.out.println("clear~~~ this button");
                }
                break;
                
            case 2:
                if (panelLocation.equals("north")) {
                    for (int i = 0; i < textAreas.length; i++) {
                        textAreas[i].setText("");
                    }
                    tempOutputArea.setText("123456");

                    System.out.println(" tempOutputArea.setText 123456");
                }
                break;

            default:
                System.out.println("I do not recognize this button");
        }
    }
    
    public static String greet(String setText) {
        String message = "Hello, " + setText + "!";
        return message; 
    }
    
}
