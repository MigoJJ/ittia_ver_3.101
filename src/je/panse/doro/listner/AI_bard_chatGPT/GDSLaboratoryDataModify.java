package je.panse.doro.listner.AI_bard_chatGPT;

import java.util.HashMap;		
import java.util.Map;

public class GDSLaboratoryDataModify {

    private static final Map<String, String> MAPPING = new HashMap<>();

    static {
        MAPPING.put("GOT", "\n[ GOT - GPT - ALP - GGT - T-Bil - Alb ]");
        MAPPING.put("T-Chol", "\n[T-Chol - HDL - Triglyceride - LDL ];");
        MAPPING.put("Total Cholesterol", "\n[T-Chol - HDL - Triglyceride - LDL ];");
        MAPPING.put("HGB", "\n[ Hb - WBC - Platelet];");
        MAPPING.put("Glucose", "\n[Glucose - HbA1c ];");
        MAPPING.put("free T-4", "\n[ T3 - free-T4 - TSH ];");
        MAPPING.put("Creatinine", "\n [ BUN - Creatinine ];");
        MAPPING.put("creatinine(urine)", "\n;");

        MAPPING.put("eGFR", "\n [ eGFR - A/C ratio ];");
        MAPPING.put("S.G", "\n [urine Protein /  Blood / RBC / WBC ]");
        MAPPING.put("TSH receptor Ab","Anti TSH-R-Ab - " + "Anti-Tg Ab -" + "Anti mic Ab ]\n ");
        
        MAPPING.put("Lipoprotein a", "\n [ Lipoprotein a / Apolipoprotein B ]");
        MAPPING.put("D3", "\n [ Vitamin-D3 ]");
        MAPPING.put("Uric", "\n [ Uric Acid ]");
        MAPPING.put("Insulin", "\n [ Insulin ]");
        MAPPING.put("AFP", "\n [ AFP / CEA / CA19-9 / PSA / CA125 ]");
        MAPPING.put("Sodium(Na)", "\n [ Sodium(Na) - Potassium(K) - Chloride(Cl) ]");
        MAPPING.put("Calcium(Ca)", "\n [ Calcium - Phosphrus ]");
        MAPPING.put("APTT", "\n [ PT - APTT ]");
        MAPPING.put("CRP", "\n [ CRP - ESR ]");


    }

    public static void main(String textFromInputArea) {
        for (Map.Entry<String, String> entry : MAPPING.entrySet()) {
            if (textFromInputArea.contains(entry.getKey())) {
                GDSLaboratoryGUI.appendTextAreas(entry.getValue());
            }
        }
    }
}
