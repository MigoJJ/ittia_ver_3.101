package je.panse.doro.fourgate.emrgdsfu;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EMRGDS_FU_ExcelReader {

    private static final String FILE_PATH = "/home/jae/git/ittia_version_2.3/src/je/panse/doro/fourgate/emrgdsfu/emrgdsfudata.xlsx";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                readExcelAndPrint();
            } catch (IOException e) {
                displayError(e);
            }
        });
    }

    private static void readExcelAndPrint() throws IOException {
        try (FileInputStream fis = new FileInputStream(new File(FILE_PATH));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    printRowData(row, rowIndex);
                }
            }
        }
    }

    private static void printRowData(Row row, int rowIndex) {
        for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
            Cell cell = row.getCell(colIndex);
            String cellValue = getDataFromCell(cell);
            System.out.printf("Row: %d, Column: %d, Value: %s%n", rowIndex, colIndex, cellValue);
        }
    }

    private static String getDataFromCell(Cell cell) {
        if (cell == null) return "";
        
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

    private static void displayError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error reading the file!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
