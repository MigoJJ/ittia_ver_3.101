package je.panse.doro.fourgate.emrgdsfu;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EMRGDS_FU_ExcelReader {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                readExcelAndPrint();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error reading the file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static void readExcelAndPrint() throws IOException {
        String filePath = "/home/jae/git/ittia_version_2.3/src/je/panse/doro/fourgate/emrgdsfu/emrgdsfudata.xlsx";
        
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {
             
            Sheet sheet = workbook.getSheetAt(0);  // Assuming you want to read the first sheet

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
                        Cell cell = row.getCell(colIndex);
                        String cellValue = getDataFromCell(cell);
                        System.out.printf("Row: %d, Column: %d, Value: %s%n", rowIndex, colIndex, cellValue);
                    }
                }
            }
        }
    }

    private static String getDataFromCell(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
