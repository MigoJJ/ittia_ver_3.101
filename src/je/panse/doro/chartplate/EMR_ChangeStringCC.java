package je.panse.doro.chartplate;

public class EMR_ChangeStringCC {
    
    public static String EMR_ChangeString_abr(String word) {
            
            if (word.contains(":(")) {
            	if (word.contains("d")) {
					word = word.replace("d", "-day ago)");
					word = word.replace(":(", " (onset ");
            	} else if (word.contains("w")) {
        			word = word.replace("w", "-week ago)");
					word = word.replace(":(", " (onset ");
            	} else if (word.contains("m")) {
        			word = word.replace("m", "-month ago)");
					word = word.replace(":(", " (onset ");
            	} else if (word.contains("y")) {
        			word = word.replace("y", "-year ago)");
					word = word.replace(":(", " (onset ");
            	} else {
            	}
            }
       return word;
        }
    public static String EMR_ChangeString_Px(String word) {
    	String retWord ="";
        if (word.contains(":>")) {
        	if (word.contains("1")) {
        		retWord = word.replace(":>1", " mg 1 tab p.o. q.d.");
        	} else if (word.contains("2")) {
        		retWord = word.replace(":>2", " mg 1 tab p.o. b.i.d");
        	} else if (word.contains("3")) {
        		retWord = word.replace(":>3", " mg 1 tab p.o. t.i.d.");
        	}
        	else {
        	}
        }
   return retWord;
    }

}
