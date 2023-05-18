package je.panse.doro.fourgate.thyroid;

public class EMR_thyroid_retStr {
    static String[] returnStr(String nameStr) {
	    String[] returnargs;
	    switch (nameStr) {
		    case "Hyperthyroidism Symptom":
		           returnargs = new String[] {
	    			    "Weight loss or increased appetite",
	    			    "Rapid or irregular heartbeat",
	    			    "Nervousness, anxiety, or irritability",
	    			    "Fatigue or muscle weakness",
	    			    "Tremors in the hands or fingers",
	    			    "Increased sensitivity to heat",
	    			    "Changes in menstrual patterns",
	    			    "Difficulty sleeping",
	    			    "Frequent bowel movements or diarrhea",
	    			    "Enlarged thyroid gland (goiter)",
	    			    "Thin or brittle hair",
	    			    "Bulging eyes (exophthalmos)",
	    			    "Skin thinning or increased perspiration",
	    			    "Emotional instability or mood swings"
	    			};
		            break;
		        case "Hypothyroidism Symptom":
		            returnargs = new String[]{
		            	 "Fatigue or tiredness",
	        		    "Weight gain or difficulty losing weight",
	        		    "Cold intolerance (feeling excessively cold)",
	        		    "Constipation",
	        		    "Dry skin and hair",
	        		    "Muscle aches and weakness",
	        		    "Depression or mood swings",
	        		    "Memory problems or difficulty concentrating",
	        		    "Decreased libido",
	        		    "Irregular or heavy menstrual periods",
	        		    "Hoarse voice",
	        		    "Swelling of the face, hands, or legs",
	        		    "Brittle nails",
	        		    "Elevated cholesterol levels",
	        		    "Puffy face or puffiness around the eyes"
		            };
		            break;

	        default:
	            returnargs = new String[]{};
	            break;
	    }
	    return returnargs;
	}
}
