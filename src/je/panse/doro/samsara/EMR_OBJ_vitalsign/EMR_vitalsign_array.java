package je.panse.doro.samsara.EMR_OBJ_vitalsign;

import java.util.ArrayList;

public class EMR_vitalsign_array {

    private static ArrayList<String> inputArrayList = new ArrayList<>();

    public static void printInputArrayList(String input) {
        inputArrayList.add(input);

        // Remove characters from inputArrayList and get modified ArrayList
        ArrayList<String> modifiedArrayList = removeCharactersFromArrayList(inputArrayList, 'h', 'i', 'b', 'g', 'r');
        modifiedArrayList.removeIf(String::isEmpty); // Remove empty strings
        System.out.println("Modified ArrayList: " + modifiedArrayList);

        // Process the modified ArrayList or perform any other operations

        // Print the current contents of the ArrayList
        for (int i = 0; i < modifiedArrayList.size(); i++) {
            String value = modifiedArrayList.get(i);
            String if1 = "BP [" + value + "] mmHg  ";
            String if2 = "PR [" + value + "] per minute\n";
            String if3 = "Body temperature [" + value + "] °C\n";
            String if4 = "Respiration rate [" + value + "] per minute";

            EMR_vitalsign.addTextArea(" ");

            // Add appropriate separator based on the index
            if (i == 0) {
                System.out.print(if1);
                EMR_vitalsign.addTextArea(if1);
            } else if (i == 1) {
                System.out.print(if1);
                EMR_vitalsign.addTextArea("BP [" + value + "] mmHg  ");

            } else if (i == 2) {
                System.out.print("PR [" + value + "] per minute\n");
	        } else if (i == 3) {
	            System.out.print("Body temperature [" + value + "] °C\n");
	        } else if (i == 4) {
	            System.out.print("Respiration rate [" + value + "] per minute");
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

    public static void main(String[] args) {
        // Initialize and test the printInputArrayList method
        printInputArrayList(args[0]);
    }
}
