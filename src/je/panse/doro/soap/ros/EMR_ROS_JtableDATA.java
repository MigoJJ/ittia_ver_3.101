package je.panse.doro.soap.ros;

public class EMR_ROS_JtableDATA {
	public static String[] columnNames() {
	    return new String[] {
	        "< General >", 
	        "< Vision >", 
	        "< Head_and_Neck >",
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
    public static String[] Head_and_Neck() {
    	return new String[] {
    	    "Headache",
    	    "Neck pain",
    	    "Earache",
    	    "Sinus congestion",
    	    "Sore throat",
    	    "Difficulty swallowing",
    	    "Hoarseness",
    	    "Jaw pain",
    	    "Facial swelling",
    	    "Nasal congestion",
    	    "Runny nose",
    	    "Dizziness",
    	    "Tinnitus (ringing in the ears)",
    	    "Vision changes",
    	    "Mouth sores",
    	    "Gum bleeding",
    	    "Enlarged lymph nodes",
    	    "Thyroid problems",
    	    "Temporomandibular joint (TMJ) issues",
    	    "Snoring",
    	    "Sleep apnea"
    	};
	}
    public static String[] Pulmonary() {
    	return new String[] {
		    "Cough",
		    "Shortness of breath",
		    "Wheezing",
		    "Chest pain",
		    "Chest tightness",
		    "Sputum production",
		    "Hemoptysis (coughing up blood)",
		    "Difficulty breathing",
		    "Frequent respiratory infections",
		    "Fatigue",
		    "Cyanosis (bluish discoloration of lips or skin)",
		    "Clubbing of fingers",
		    "Orthopnea (difficulty breathing while lying flat)",
		    "Paroxysmal nocturnal dyspnea (sudden difficulty breathing at night)",
		    "Snoring",
		    "Sleep apnea",
		    "Allergies",
		    "Asthma",
		    "Chronic obstructive pulmonary disease (COPD)",
		    "Pneumonia",
		    "Pulmonary embolism",
		    "Tuberculosis",
		    "Lung cancer"
    	};
	}
    public static String[] Gastrointestinal() {
    	return new String[] {
		    "Abdominal pain",
		    "Nausea",
		    "Vomiting",
		    "Heartburn",
		    "Indigestion",
		    "Loss of appetite",
		    "Weight loss",
		    "Difficulty swallowing",
		    "Abdominal bloating",
		    "Change in bowel habits",
		    "Constipation",
		    "Diarrhea",
		    "Blood in stool",
		    "Black, tarry stools",
		    "Jaundice (yellowing of skin or eyes)",
		    "Liver problems",
		    "Gallbladder problems",
		    "Pancreatic problems",
		    "Hepatitis",
		    "Gastroesophageal reflux disease (GERD)",
		    "Peptic ulcer disease",
		    "Inflammatory bowel disease (IBD)",
		    "Irritable bowel syndrome (IBS)",
		    "Gastrointestinal bleeding",
		    "Colorectal cancer"
    	};
	}
    public static String[] GenitoUrinary() {
    	return new String[] {
    		 "Frequent urination",
		    "Urgency to urinate",
		    "Burning sensation during urination",
		    "Blood in urine",
		    "Cloudy or foul-smelling urine",
		    "Difficulty starting or maintaining urination",
		    "Urinary incontinence",
		    "Nocturia (frequent urination at night)",
		    "Urinary retention",
		    "Kidney pain",
		    "Flank pain",
		    "Lower abdominal pain",
		    "Testicular pain",
		    "Pelvic pain",
		    "Prostate problems",
		    "Urinary tract infection (UTI)",
		    "Kidney stones",
		    "Bladder issues",
		    "Interstitial cystitis",
		    "Urinary incontinence",
		    "Erectile dysfunction",
		    "Sexually transmitted infections (STIs)",
		    "Prostate cancer",
		    "Bladder cancer",
		    "Renal cancer"
    	};
	}
    public static String[] HematologyOncology() {
    	return new String[] {
		    "Easy bruising",
		    "Excessive bleeding",
		    "Anemia",
		    "Fatigue",
		    "Enlarged lymph nodes",
		    "Night sweats",
		    "Unexplained weight loss",
		    "Bone pain",
		    "Joint pain",
		    "Abdominal pain",
		    "Swollen spleen",
		    "Swollen liver",
		    "Fever",
		    "Chills",
		    "Recurrent infections",
		    "Chest pain",
		    "Shortness of breath",
		    "Cough",
		    "Skin changes",
		    "Lumps or masses",
		    "Abnormal bleeding or clotting",
		    "Leukemia",
		    "Lymphoma",
		    "Multiple myeloma",
		    "Hemophilia",
		    "Thrombocytopenia",
		    "Hemolytic anemia",
		    "Sickle cell disease",
		    "Coagulation disorders",
		    "Solid tumors (e.g., breast, lung, colon, etc.)"
    	};
	}
    public static String[] Endocrine() {
    	return new String[] {
		    "Fatigue",
		    "Weight changes",
		    "Heat or cold intolerance",
		    "Excessive sweating",
		    "Increased thirst",
		    "Frequent urination",
		    "Hunger",
		    "Tremors",
		    "Irregular menstrual periods",
		    "Infertility",
		    "Hair loss",
		    "Dry skin",
		    "Mood changes",
		    "Depression",
		    "Anxiety",
		    "Sleep disturbances",
		    "Enlarged thyroid (goiter)",
		    "Thyroid nodules",
		    "Thyroid cancer",
		    "Hypothyroidism",
		    "Hyperthyroidism",
		    "Diabetes mellitus",
		    "Adrenal gland disorders",
		    "Pituitary gland disorders",
		    "Parathyroid gland disorders",
		    "Hypoglycemia",
		    "Cushing's syndrome",
		    "Addison's disease"
    	};
	}
    public static String[] MentalHealth() {
    	return new String[] {
			"Depressed mood",
		    "Anxiety",
		    "Panic attacks",
		    "Mood swings",
		    "Sleep disturbances",
		    "Appetite changes",
		    "Loss of interest or pleasure",
		    "Poor concentration",
		    "Memory problems",
		    "Feelings of guilt or worthlessness",
		    "Suicidal thoughts",
		    "Hallucinations",
		    "Delusions",
		    "Impulsivity",
		    "Agitation",
		    "Social withdrawal",
		    "Substance abuse",
		    "Eating disorders",
		    "Obsessions or compulsions",
		    "Personality disorders",
		    "Post-traumatic stress disorder (PTSD)",
		    "Generalized anxiety disorder (GAD)",
		    "Major depressive disorder (MDD)",
		    "Bipolar disorder",
		    "Schizophrenia",
		    "Attention deficit hyperactivity disorder (ADHD)",
		    "Borderline personality disorder (BPD)",
    		    "Substance use disorder (SUD)"
    	};
	}
    public static String[] SkinAndHair() {
    	return new String[] {
		    "Rash",
		    "Itching",
		    "Redness",
		    "Swelling",
		    "Dry skin",
		    "Oily skin",
		    "Acne",
		    "Eczema",
		    "Psoriasis",
		    "Hives",
		    "Skin infections",
		    "Changes in moles",
		    "Skin discoloration",
		    "Hair loss",
		    "Excessive hair growth",
		    "Nail abnormalities",
		    "Warts",
		    "Fungal infections",
		    "Sunburn",
		    "Skin cancer",
		    "Allergies",
		    "Burns",
		    "Scars",
		    "Rosacea",
		    "Dandruff",
		    "Alopecia areata",
		    "Lice infestation",
		    "Folliculitis",
		    "Cellulitis"
		};
	}
}