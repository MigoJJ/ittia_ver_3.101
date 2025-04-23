package je.panse.doro.soap.assessment.icd10;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ICD10DataModel {

    public record Record(String diseaseCode, String koreanName, String englishName, String completeCodeDivision) {
    }

    public void loadAllRecords(DefaultTableModel tableModel, List<Record> records) {
        tableModel.setRowCount(0);
        for (Record record : records) {
            tableModel.addRow(new Object[]{
                    record.diseaseCode(),
                    record.koreanName(),
                    record.englishName(),
                    record.completeCodeDivision()
            });
        }
    }

    public void loadSearchResults(DefaultTableModel tableModel, List<Record> records) {
        tableModel.setRowCount(0);
        for (Record record : records) {
            tableModel.addRow(new Object[]{
                    record.diseaseCode(),
                    record.koreanName(),
                    record.englishName(),
                    record.completeCodeDivision()
            });
        }
    }
}