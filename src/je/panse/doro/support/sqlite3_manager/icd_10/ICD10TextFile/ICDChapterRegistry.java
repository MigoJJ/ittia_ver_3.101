package je.panse.doro.support.sqlite3_manager.icd_10.ICD10TextFile;

import java.util.Arrays;
import java.util.List;

public class ICDChapterRegistry {
    private static final List<ICDChapter> CHAPTERS = Arrays.asList(
        new ICDChapter("Certain infectious and parasitic diseases", "A00", "B99"),
        new ICDChapter("Neoplasms", "C00", "D48"),
        new ICDChapter("Diseases of the blood and blood-forming organs and certain disorders involving the immune mechanism", "D50", "D89"),
        new ICDChapter("Endocrine, nutritional and metabolic diseases", "E00", "E90"),
        new ICDChapter("Mental and behavioural disorders", "F00", "F99"),
        new ICDChapter("Diseases of the nervous system", "G00", "G99"),
        new ICDChapter("Diseases of the eye and adnexa", "H00", "H59"),
        new ICDChapter("Diseases of the ear and mastoid process", "H60", "H95"),
        new ICDChapter("Diseases of the circulatory system", "I00", "I99"),
        new ICDChapter("Diseases of the respiratory system", "J00", "J99"),
        new ICDChapter("Diseases of the digestive system", "K00", "K93"),
        new ICDChapter("Diseases of the skin and subcutaneous tissue", "L00", "L99"),
        new ICDChapter("Diseases of the musculoskeletal system and connective tissue", "M00", "M99"),
        new ICDChapter("Diseases of the genitourinary system", "N00", "N99"),
        new ICDChapter("Pregnancy, childbirth and the puerperium", "O00", "O99"),
        new ICDChapter("Certain conditions originating in the perinatal period", "P00", "P96"),
        new ICDChapter("Congenital malformations, deformations and chromosomal abnormalities", "Q00", "Q99"),
        new ICDChapter("Symptoms, signs and abnormal clinical and laboratory findings, not elsewhere classified", "R00", "R99"),
        new ICDChapter("Injury, poisoning and certain other consequences of external causes", "S00", "T98"),
        new ICDChapter("External causes of morbidity and mortality", "V01", "Y98"),
        new ICDChapter("Factors influencing health status and contact with health services", "Z00", "Z99"),
        new ICDChapter("Codes for special purposes", "U00", "U99")
    );

    public String getChapterTitle(String code) {
        for (ICDChapter chapter : CHAPTERS) {
            if (code.compareTo(chapter.getStartCode()) >= 0 && code.compareTo(chapter.getEndCode()) <= 0) {
                return chapter.getTitle();
            }
        }
        return "Unknown Chapter";
    }
}