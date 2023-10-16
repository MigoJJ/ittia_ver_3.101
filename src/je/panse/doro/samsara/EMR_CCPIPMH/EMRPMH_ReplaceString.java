package je.panse.doro.samsara.EMR_CCPIPMH;

import java.util.regex.Pattern;

import je.panse.doro.GDSEMR_frame;

public class EMRPMH_ReplaceString {

    public static void main(String stringB) {
        String stringA =
            "     ----------------------------------\n" +
            "     □  DM   □ HTN  □  Dyslipidemia\n" +
            "     □  Cancer      □  Operation □  Thyroid Disease\n" +
            "     □  Asthma     □  Pneumonia  □ Tuberculosis\n" +
            "     □  Hepatitis   □  GERD        □  Gout\n" +
            "     □  Arthritis    □  Hearing Loss  □ ...\n" +
            "     □  CVA          □ Depression  □  Cognitive Disorder\n" +
            "     □  Angina Pectoris          □  AMI   □  Arrhythmia\n" +
            "     □  Allergy      □  ...\n" +
            "     □  Food        □  Injection □   Medication\n" +
            "      ------------------------------------\n";

        System.out.println(replaceStringAWithB(stringA, stringB));

    }

    public static String replaceStringAWithB(String stringA, String stringB) {
        // Split String B by lines to get individual diseases
        String[] diseases = stringB.split("\n");

        for (String disease : diseases) {
            disease = disease.trim();
            // Check if disease has enough length for substring operation
            if (disease.length() <= 3) {
                continue; // Skip this iteration if the string is too short
            }

            // Extract the disease name from the current line
            String diseaseName = disease.substring(3).trim();

            // Construct the pattern for the disease in String A with flexible whitespace after the box
            String pattern = "□\\s*+" + Pattern.quote(diseaseName);

            // Replace the occurrence in String A
            stringA = stringA.replaceAll(pattern, "▣  " + diseaseName);
        }
        GDSEMR_frame.setTextAreaText(3, stringA);
        return stringA;
    }


}
