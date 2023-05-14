package je.panse.doro.soap.plan;

public class ittiaGDSPlanPanel_3_String {
	public static String[] getCheckboxLabels() {
        return new String[]{"Review of other clinic RC result",
			"Next Lab F/U with NPO", 
			"The patient Refused dose-adjustment", 
			"Ophthalmologist consultation[+]", 
			"History of surgeries or hospitalizations",
			"Family medical history",
			"Current medications or supplements",
			"2", "3", "2", "3", "2", "3", "2", "3", "2", "3", "4"};

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
                		"Neurology consult to Univ Hospital",
                		"Ohthalmology consult to Univ Hospital",
                		"Nephrology consult to Univ Hospital",
                		"Endocrinology and metabolism consult to Univ Hospital",
                		"Orthopedics consult to Univ Hospital",
                		"Cardiology consult to Univ Hospital",
                		
                		"transfer to Severence Univ. Hospital",
                		"transfer to AMC Hospital",
                		"transfer to SMC Univ. Hospital",
                		"transfer to SNUH Seoul Hospital",
                		"transfer to SNUH BUNDang Hospital",
                		"transfer to catholic Univ. Hospital",
                		"consult to Emergency Room"
                		};
                break;

            default:
                System.out.println("ReEnter the Number !!!");
                break;
        }

        return comboboxesLabels;
    }
}

