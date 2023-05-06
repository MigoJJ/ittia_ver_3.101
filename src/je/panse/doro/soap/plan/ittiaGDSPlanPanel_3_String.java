package je.panse.doro.soap.plan;

public class ittiaGDSPlanPanel_3_String {
	public static String[] getCheckboxLabels() {
        return new String[]{"Review of other clinic RC result",
        		"Next Lab F/U with NPO", 
        		"The patient Refused dose-adjustment", 
        		"Ophthalmologist consultation[+]", 
        		"123", 
        		"2", "3", "2", "3", "2", "3", "2", "3", "2", "3", "2", "3", "4"};

	}
    public static String[] getboxs(int i) {
        String[] comboboxesLabels = {};

        switch (i) {
            case 1:
                comboboxesLabels = new String[]{"1", "2", "3", "2", "3", "4"};
                break;
            case 2:
                comboboxesLabels = new String[]{"2", "2", "23", "2", "23", "24"};
                break;
            case 3:
                comboboxesLabels = new String[]{"1", "2", "3", "2", "3", "4"};
                break;
            case 4:
                comboboxesLabels = new String[]{"1", "2", "3", "2", "3", "4"};
                break;    
            case 5:
                comboboxesLabels = new String[]{"Consutation---------------------", 
                		"to refer patients to receive additional health care services.",
                		"NR consult to Univ Hospital", "62", "53", "54"};
                break;

            default:
                System.out.println("ReEnter the Number !!!");
                break;
        }

        return comboboxesLabels;
    }
}

