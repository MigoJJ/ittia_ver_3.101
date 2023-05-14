package je.panse.doro.chartplate;

public class EMR_ChangeStringCC {
		
	public static String EMR_ChangeString_abr(String strCC) {
        String duration;
        if (strCC.contains("d")) {
            duration = strCC.replace("d", "-day");
            duration = "(onset " + duration + " ago)";
        } else if (strCC.contains("w")) {
            duration = strCC.replace("w", "-week");
            duration = "(onset " + duration + " ago)";
        } else if (strCC.contains("m")) {
            duration = strCC.replace("m", "-month");
            duration = "(onset " + duration + " ago)";
        } else if (strCC.contains("y")) {
            duration = strCC.replace("y", "-year");
            duration = "(onset " + duration + " ago)";
        } else {
            duration = "";
        }
        duration = duration.replace(":(","");
        String strippedStrCC = strCC.replaceAll("[dwmuy:( )]", "");
        
        System.out.println("Duration >>> " + duration);
		return duration;
	}
}
