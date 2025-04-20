package je.panse.doro.support.sqlite3_manager.icd_10.ICD10TextFile;

public class ICDChapter {
    private final String title;
    private final String startCode;
    private final String endCode;

    public ICDChapter(String title, String startCode, String endCode) {
        this.title = title;
        this.startCode = startCode;
        this.endCode = endCode;
    }

    public String getTitle() {
        return title;
    }

    public String getStartCode() {
        return startCode;
    }

    public String getEndCode() {
        return endCode;
    }
}