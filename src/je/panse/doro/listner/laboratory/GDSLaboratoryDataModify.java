package je.panse.doro.listner.laboratory;

import java.util.HashMap;
import java.util.Map;

public class GDSLaboratoryDataModify {

    private static final Map<String, String> MAPPING = new HashMap<>();

    static {
        MAPPING.put("GOT", "\n[ GOT - GPT - ALP - GGT - T-Bil - Alb ]");
        MAPPING.put("T-Chol", "\n[T-Chol - HDL - Triglyceride - LDL ];");
        MAPPING.put("HGB", "\n[ Hb - WBC - Platelet];");
        MAPPING.put("Glucose", "\n[Glucose - HbA1c ];");
        MAPPING.put("free T-4", "\n[ T3 - free-T4 - TSH ];");
        MAPPING.put("Creatinine", "\n [ BUN - Creatinine ];");
        MAPPING.put("eGFR", "\n [ eGFR - A/C ratio ];");
        MAPPING.put("S.G", "\n [urine Protein /  Blood / RBC / WBC ]");
        MAPPING.put("TSH recepter Ab",
        		"\n[TSH recepter Ab \n\t"
        		+ "Thyroglobulin Ab \n\t"
        		+ "Thyroid microsome Ab\t ");
        
        MAPPING.put("Lipoprotein a", "\n [ Lipoprotein a / Apolipoprotein B ]");
        MAPPING.put("D3", "\n [ Vitamin-D3 ]");
        MAPPING.put("Uric", "\n [ Uric Acid ]");
        MAPPING.put("Insulin", "\n [ Insulin ]");
        MAPPING.put("AFP", "\n [ AFP / CEA / CA19-9 / PSA / CA125]");


        
    }

    public static void main(String textFromInputArea) {
        for (Map.Entry<String, String> entry : MAPPING.entrySet()) {
            if (textFromInputArea.contains(entry.getKey())) {
                GDSLaboratoryDataGUI.appendTextAreas(entry.getValue());
            }
        }
    }
}
