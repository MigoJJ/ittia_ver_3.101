package je.panse.doro.listner.buttons;

import java.io.IOException;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.entry.EntryDir;
import je.panse.doro.entry.IttiaEntry;
import je.panse.doro.listner.IndentedTextArea;

public class EMR_B_1entry extends GDSEMR_frame {
    public EMR_B_1entry() throws Exception {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void EMR_B_selection(int noButton, String panelLocation) throws Exception{
        // TODO Auto-generated method stub

        switch (noButton) {
            case 1:
                if (panelLocation.equals("north")) {
                	
                	
                	

                    // When the clear button is clicked, clear all the input text areas
                    for (int i = 0; i < textAreas.length; i++) {
                    	textAreas[i].setText("");
        				String inputData = titles[i] + "\t";
        				textAreas[i].setText(inputData);
                    }
                    tempOutputArea.setText("");
                   System.out.println("clear~~~ this button");
                }
                break;
                
            case 2:
                break;

            case 3:
            	IttiaEntry.main(null);
//        			System.exit(0);
                break;
            case 4:
                if (panelLocation.equals("north")) {
                    // dispose the previous JFrame
                    GDSEMR_frame frame = new GDSEMR_frame();
                    // display the new JFrame
                    frame.setVisible(true);

                }
                break;
       	
            default:
                System.out.println("I do not recognize this button");
        }
	}
}
