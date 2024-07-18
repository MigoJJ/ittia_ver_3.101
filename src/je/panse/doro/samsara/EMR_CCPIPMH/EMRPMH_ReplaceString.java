package je.panse.doro.samsara.EMR_CCPIPMH;

import java.util.regex.Pattern;	
import je.panse.doro.GDSEMR_frame; // Ensure this import statement correctly points to your GDSEMR_frame class

public class EMRPMH_ReplaceString {

    public static void main(String args) {
        // Prepare the input string (usually this would come from user input or another source)
        String stringB ="";


        // Process the string and update the central TextArea in GDSEMR_frame
        String resultString = replaceStringAWithB(stringB);
        GDSEMR_frame.setTextAreaText(3, "\n" + resultString);
    }

    public static String replaceStringAWithB(String stringB) {
        String stringA =
            "\n     ----------------------------------\n" +
            "     □ DM               □ HTN              □ Dyslipidemia\n" +
            "     □ Cancer           □ Operation        □ Thyroid Disease\n" +
            "     □ Asthma           □ Pneumonia        □ Tuberculosis\n" +
            "     □ Hepatitis        □ GERD             □ Gout\n" +
            "     □ Arthritis        □ Hearing Loss     □ Parkinson's Disease\n" +
            "     □ CVA              □ Depression       □ Cognitive Disorder\n" +
            "     □ Angina Pectoris  □ AMI              □ Arrhythmia\n" +
            "     □ Allergy          □ ...              \n" +
            "     □ Food             □ Injection        □ Medication\n" +
            "     ------------------------------------\n";

        String[] diseases = stringB.split("\n");

        for (String disease : diseases) {
            disease = disease.trim();
            if (disease.length() <= 3) {
                continue; // Skip this iteration if the string is too short
            }

            String diseaseName = disease.trim();
            String pattern = "□\\s*+" + Pattern.quote(diseaseName);
            stringA = stringA.replaceAll(pattern, "▣  " + diseaseName);
        }

        return stringA;  // Return the modified string
    }
}
