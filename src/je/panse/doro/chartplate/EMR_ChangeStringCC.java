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
        if (word.contains(":>")) {
        	if (word.contains("1")) {
				word = word.replace("1", " 1 tab p.o. q.d.");
				word = word.replace(":>", " mg ");
        	} else if (word.contains("2")) {
				word = word.replace("2", " 1 tab p.o. b.i.d");
				word = word.replace(":>", " mg ");
        	} else if (word.contains("3")) {
				word = word.replace("3", " 1 tab p.o. t.i.d.");
				word = word.replace(":>", " mg ");
        	}
        	else {
        	}
        }
   return word;
    }

}
