package je.panse.doro.samsara.EMR_OBJ_vitalsign;

import java.util.ArrayList;	
import javax.swing.JTextArea;

public class EMR_vitalsign_array extends EMR_vitalsign{
	static ArrayList<String> modifiedArrayList = new ArrayList<>();

    public static void printInputArrayList(String input) {
    	
    	modifiedArrayList.add(input);
        System.out.println(" input modifiedArrayList"+  input);
        
        // Remove characters from inputArrayList and get modified ArrayList
        removeCharactersFromArrayList(modifiedArrayList, 'h', 'i', 'b', 'g', 'r','o');
        modifiedArrayList.removeIf(String::isEmpty); // Remove empty strings
        outputTextArea.setText("");
        
        
        // Print the current contents of the ArrayList
        for (int i = 0; i < modifiedArrayList.size(); i++) {
            String value = modifiedArrayList.get(i);
            String if1 = "    BP [" + value + "] mmHg  ";
            String if2 = "PR [" + value + "] per minute\n";
            String if3 = "    Body temperature [" + value + "] °C\n";
            String if4 = "    Respiration rate [" + value + "] per minute";

            // Add appropriate separator based on the index
            if (i == 0) {
                outputTextArea.append(if1);
            } else if (i == 1) {
                outputTextArea.append(if1);
            } else if (i == 2) {
                outputTextArea.append(if2);
            } else if (i == 3) {
                outputTextArea.append(if3);
            } else if (i == 4) {
                outputTextArea.append(if4);
            }
        }
    }

    public static ArrayList<String> removeCharactersFromArrayList(ArrayList<String> arrayList, char... characters) {
        ArrayList<String> modifiedArrayList = new ArrayList<>();

        for (String value : arrayList) {
            StringBuilder sb = new StringBuilder(value);
            for (char ch : characters) {
                int index = sb.indexOf(String.valueOf(ch));
                while (index != -1) {
                    sb.deleteCharAt(index);
                    index = sb.indexOf(String.valueOf(ch));
                }
            }
            modifiedArrayList.add(sb.toString());
        }
        return modifiedArrayList;
    }
    
    public static void clearmodifiedArray() {
    	modifiedArrayList.clear();
    }

}
