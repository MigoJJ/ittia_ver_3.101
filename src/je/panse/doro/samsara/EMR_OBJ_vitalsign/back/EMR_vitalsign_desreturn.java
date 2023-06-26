package je.panse.doro.samsara.EMR_OBJ_vitalsign.back;

public class EMR_vitalsign_desreturn {

	public static String main(String input, String dta) {
        String result = dta;

        if (input.equals("h")) {
            result = "at home by self";
        } else if (input.equals("i")) {
            result = result.replace("Regular","irRegular");
        } else if (input.equals("b")) {
            result = "at GDS, Left seated position, Regular";
            } else if (input.equals("r")) {
            result = result.replace("Left","Right");
        }

        return result;
    }
}