package je.panse.doro.fourgate.Influenza;

import java.text.SimpleDateFormat;
import java.util.Date;

import je.panse.doro.GDSEMR_frame;

public class Influenza_Vaccine {
    public static void main() {

    	// Get the current date in yyyy-MM-dd format
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	String currentDate = dateFormat.format(new Date());
    	
    	String CCstring ="for Influenza Vaccination\n";
    	String  PIstring = "  [ ✔ ]  no allergy to eggs, chicken\n\t, or any other component of the vaccine.\n"
    			+ "  [ ✔ ]  no s/p Guillain-Barré syndrome.\n"
    			+ "  [ ✔ ]  no adverse reactions to previous influenza vaccines.\n"
    			+ "  [ ✔ ]  no pregnancy, breastfeeding, and immunosuppression.\n";
    	String Pstring = "\n   ...Vaccination as scheduled";
    	String Influ = CCstring + PIstring;

    GDSEMR_frame.setTextAreaText(0,Influ);
    GDSEMR_frame.setTextAreaText(7,"\n  #" + "Vaxigriptetra pfs inj (4가) [0.5ml]  " + currentDate);
    GDSEMR_frame.setTextAreaText(8,Pstring);
    }
}
