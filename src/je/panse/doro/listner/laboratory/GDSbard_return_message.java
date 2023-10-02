package je.panse.doro.listner.laboratory;

public class GDSbard_return_message {
    public static String getMultilineMessage() {
        // Use a StringBuilder to construct a multiline string
        StringBuilder message = new StringBuilder();
        message.append("------------ make summary table \n"
        		+ "\n"
        		+ "Parameter Value Unit \n"
        		+ "using value format [   -   -   -   -   ];\n"
        		+ "if all of the value data is not null, do not remove the row;\n"
        		+ "if data is \"null\" or \"...ing\", Remove the row;\n"
        		+ "remove reference range;\n"
        		+ "\n"
        		+ "T-Chol - HDL - TG - LDL ;\n"
        		+ "GOT - GPT - ALP - GGT - T-Bil - Alb ;\n"
        		+ "Glucose (mg/dL) - HbA1c (%) ;\n"
        		+ "Hb - WBC - Platelet without unit;\n"
        		+ "BUN - Creatitnie - eGFR - A/C atio;\n"
        		+ "T3 - free-T4 - TSH;\n"
        		+ "Vitamin-D /;\n"
        		+ "Insulin - Lp(a) - ApoB ;\n"
        		+ "AFP - CEA - CA19-9 - CA125 - PSA ;\n");

        
        
        
        //        message.append("Line 2 of the message.\n");
//        message.append("Line 3 of the message.\n");
//        message.append("And so on...");

        // Return the constructed multiline message as a string
        return message.toString();
    }

    public static String main(String string) {
        // You can call the getMultilineMessage method like this
        String multilineMessage = getMultilineMessage();

        // Print the multiline message
        System.out.println(multilineMessage);
		return multilineMessage;
    }
}
