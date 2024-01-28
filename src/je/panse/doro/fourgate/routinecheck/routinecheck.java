package je.panse.doro.fourgate.routinecheck;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.samsara.comm.datetime.Date_current;

public class routinecheck {
    
    public static void GDSRC(String[] args) {
    	String cdate = Date_current.main("d");
            GDSEMR_frame.setTextAreaText(0, "The patient was seen at the GDS clinic \n\tfor a routine checkup on ." + cdate);
            GDSEMR_frame.setTextAreaText(7, "\n  #  GDS clinic Routine check  [ " + cdate + " ]");
    }
    public static void HCRC(String[] args) {
    	String cdate = Date_current.main("d");
            GDSEMR_frame.setTextAreaText(0, "The patient was seen at the GDS clinic \n\tfor a 공단검진 on " + cdate);
            GDSEMR_frame.setTextAreaText(7, "\n  #  공단검진 at GDS clinic  [ " + cdate + " ]");
    }
}
