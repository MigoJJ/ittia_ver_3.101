package je.panse.doro.fourgate.thyroid;

public class EMR_thyroid_retStr {
    static String[] returnStr(String nameStr) {
	    String[] returnargs;

	    switch (nameStr) {
        case "Thyroid Physical examination":
            returnargs = new String[]{"Polyuria", "Polydipsia", "Polyphagia", "Weight loss", "Fatigue", "Blurred vision"};
            break;
	    case "Hyperthyroidism":
	            returnargs = new String[]{"Polyuria", "Polydipsia", "Polyphagia", "Weight loss", "Fatigue", "Blurred vision"};
	            break;
	        case "Hypothyroidism":
	            returnargs = new String[]{"Weight loss", "Rapid heartbeat", "Nervousness", "Tremors", "Increased appetite", "Heat intolerance"};
	            break;
	        case "Hyperthyroidism with pregnancy":
	            returnargs = new String[]{"Fatigue", "Weight gain", "Depression", "Dry skin", "Cold intolerance", "Constipation"};
	            break;
	        case "Hypothyroidism with pregnancy":
	            returnargs = new String[]{"Cough", "Sore throat", "Nasal congestion", "Runny nose", "Sneezing", "Headache", "Low-grade fever", "Fatigue", "Body aches", "Difficulty breathing"};
	            break;
	        case "Abnormal TFT with pregnancy":
	            returnargs = new String[]{"Frequent urination", "Burning sensation during urination", "Cloudy or bloody urine", "Strong-smelling urine", "Pelvic pain"};
	            break;
	        case "Non thyroidal illness":
	            returnargs = new String[]{"Cramp-like pain", "Bloating", "Nausea", "Vomiting", "Diarrhea", "Constipation","RLQ pain"};
	            break;
	        case "Abnormal TFT on Routine check":
	            returnargs = new String[]{"Sharp or stabbing pain", "Chest tightness", "Shortness of breath", "Dizziness", "Nausea"};
	            break;
	        case "Thyroid nodule":
	            returnargs = new String[]{"Sharp or stabbing pain", "Chest tightness", "Shortness of breath", "Dizziness", "Nausea"};
	            break;
	        default:
	            returnargs = new String[]{};
	            break;
	    }

	    return returnargs;
	}
}
