package je.panse.doro.listner.buttons;

import je.panse.doro.GDSEMR_frame;	
import je.panse.doro.chartplate.EMR_Write_To_Chartplate;
import je.panse.doro.fourgate.EMR_FU_Edit;
import je.panse.doro.fourgate.EMR_FU_diabetesEdit;
import je.panse.doro.fourgate.EMR_FU_hypercholesterolemiaEdit;
import je.panse.doro.fourgate.EMR_FU_hypertensionEdit;
import je.panse.doro.samsara.comm.FileGeditToCilpboard;

public class EMR_B_1entry extends GDSEMR_frame {
    
    public EMR_B_1entry() throws Exception {
        super();
    }
    
    public static void EMR_B_1entryentry(String noButton, String panelLocation) throws Exception{
        if (panelLocation.equals("north")) {
              switch (noButton) {
                    case "Rescue":
                    	EMR_Write_To_Chartplate.copyToClipboard(tempOutputArea);
                    	FileGeditToCilpboard.FileGeditToCilpboard();
                        break;
                    case "Copy":
                        break;
                    case "Clear":
	    					for (int i = 0; i < textAreas.length; i++) {
								 textAreas[i].setText("");
								 String inputData = titles[i] + "\t";
								 textAreas[i].setText(inputData);
	    					}
                        break;
                    case "Exit":
                    		frame.dispose();// Add code block for Button 4 here
                        break;
                    case "Button 5":
                        break;
                    case "Button 6":
                        break;
                    case "Button 7":
                        break;
                    case "Button 8":
                        break;
                    case "Button 9":
                        break;
                    case "Button 10":
                        break;
                    case "Button 11":
                        break;
                    default:
                        System.out.println("Invalid Button title");
                        break;
              		}
        		}

    if (panelLocation.equals("south")) {                    
            switch (noButton) {
                case "F/U DM":
                    EMR_FU_diabetesEdit.main(null);
                    break;
                    
                case "F/U HTN":
                    EMR_FU_hypertensionEdit.main(null);
                    break;
                
                case "F/U Chol":
                    EMR_FU_hypercholesterolemiaEdit.main(null);
                    break;
                    
                case "F/U Thyroid":
                    EMR_FU_Edit.main(null);
                    break;
                    
                case "F/U Edit":
                    System.out.println("southsouthsouth 1 1 1 ~~!!");
                    EMR_FU_Edit.main(null);
                    break;
                default:
                    System.out.println("I do not recognize this button");
            }
        }
    }
}
