package je.panse.doro.soap.assessment.icd10;

import java.io.BufferedReader;
import java.io.FileReader;

public class ICD10CSVImporter {

    private static final String CSV_PATH = "/home/dce040b/git/ittia_ver_3.076/src/je/panse/doro/soap/assessment/icd10/ICD10.csv";
    private final ICD10DatabaseManager dbManager;

    public ICD10CSVImporter(ICD10DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public boolean importCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split(",", -1);
                if (data.length >= 4) {
                    ICD10DataModel.Record record = new ICD10DataModel.Record(
                            data[0].trim(),
                            data[1].trim().isEmpty() ? null : data[1].trim(),
                            data[2].trim().isEmpty() ? null : data[2].trim(),
                            data[3].trim().isEmpty() ? null : data[3].trim()
                    );
                    if (!dbManager.addRecord(record)) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error importing CSV: " + e.getMessage());
            return false;
        }
    }
}