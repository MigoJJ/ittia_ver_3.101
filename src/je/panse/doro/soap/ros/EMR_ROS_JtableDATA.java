package je.panse.doro.soap.ros;


public class EMR_ROS_JtableDATA {
	public static String[] columnNames() {
	    return new String[] {
	        "< General >", 
	        "< Vision >", 
	        "< Head and Neck>",
	        "< Pulmonary >", 
	        "< Cardiovascular >", 
	        "< Gastrointestinal >", 
	        "< Genito-Urinary >", 
	        "< Hematology/Oncology >",
	        "< Neurological >", 
	        "< Endocrine >", 
	        "< Mental Health >", 
	        "< Skin and Hair >", 
	    };
	}

	public static String[] General() {
	    return new String[] {
	        "Fever or chills", 
	        "Fatigue or weakness",
	        "Weight changes",
	        "Night sweats",
	        "Headache",
	        "Changes in vision or hearing",
	        "Changes in appetite or thirst",
	        "Chest pain or discomfort",
	        "Shortness of breath or difficulty breathing",
	        "Cough or sputum production",
	        "Abdominal pain or discomfort",
	        "Nausea or vomiting",
	        "Changes in bowel habits",
	        "Joint pain or stiffness",
	        "Muscle pain or weakness",
	        "Skin changes or lesions",
	        "Sleep disturbances",
	    };
	}

    public static String[] Neurological() {
        return new String[] {
            "Headache",
            "Seizures",
            "Dizziness or vertigo",
            "Changes in vision",
            "Numbness or tingling",
            "Muscle weakness",
            "Tremors or involuntary movements",
            "Memory loss or confusion",
            "Sleep disturbances",
            "Speech difficulties"
        };
    }
    
    public static String[] Vision() {
    	return new String[] {    
		    "Changes in visual acuity",
		    "Blurry or hazy vision",
		    "Double vision or diplopia",
		    "Eye pain or discomfort",
		    "Redness or swelling of the eyes",
		    "Excessive tearing or dryness",
		    "Flashing lights or floaters",
		    "Loss of peripheral vision",
		    "Difficulty seeing at night",
		    "Sensitivity to light",
		    "Previous eye surgeries or injuries",
		    "Family history of eye diseases",
		    "Use of corrective lenses or contact lenses"
        };
    }
    
    public static String[] Cardiovascular() {
    	return new String[] {    
			"Chest pain or discomfort",
			"Shortness of breath or dyspnea",
			"Palpitations or irregular heartbeats",
			"Dizziness or lightheadedness",
			"Edema or swelling of the legs or ankles",
			"Fatigue or weakness with exertion",
			"History of high blood pressure or hypertension",
			"History of heart disease or heart attack",
			"Family history of cardiovascular disease",
			"Previous cardiac surgeries or interventions",
			"Use of tobacco or alcohol",
			"Physical inactivity or sedentary lifestyle",
			"High cholesterol or triglycerides",
			"Diabetes or metabolic syndrome"
        };
    }

}