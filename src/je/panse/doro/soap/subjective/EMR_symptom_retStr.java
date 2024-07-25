package je.panse.doro.soap.subjective;

public class EMR_symptom_retStr {
    public static String[] returnStr(String nameStr) {
	    String[] returnargs;

	    switch (nameStr) {
	        case "Diabetes Mellitus":
	            returnargs = new String[]{"Polyuria", "Polydipsia", "Polyphagia", "Weight loss", "Fatigue", "Blurred vision"};
	            break;
	        case "Hyperthyroidism":
	            returnargs = new String[]{"Weight loss", "Rapid heartbeat", "Nervousness", "Tremors", "Increased appetite", "Heat intolerance"};
	            break;
	        case "Hypothyroidism":
	            returnargs = new String[]{"Fatigue", "Weight gain", "Depression", "Dry skin", "Cold intolerance", "Constipation"};
	            break;
	        case "URI":
	            returnargs = new String[]{"Cough", "Sore throat", "Nasal congestion", "Runny nose", "Sneezing", "Headache", "Low-grade fever", "Fatigue", "Body aches", "Difficulty breathing"};
	            break;
	        case "UTI":
	            returnargs = new String[]{"Frequent urination", "Burning sensation during urination", "Cloudy or bloody urine", "Strong-smelling urine", "Pelvic pain"};
	            break;
	        case "Abdominal pain":
	            returnargs = new String[]{"Cramp-like pain", "Bloating", "Nausea", "Vomiting", "Diarrhea", "Constipation","RLQ pain"};
	            break;
	        case "Atypical chest pain":
	            returnargs = new String[]{"Sharp or stabbing pain", "Chest tightness", "Shortness of breath", "Dizziness", "Nausea"};
	            break;
	        case "Quit":
	        	returnargs = new String[]{""};
	            break;
	        default:
	            returnargs = new String[]{};
	            break;
	    }

	    return returnargs;
	}
}
