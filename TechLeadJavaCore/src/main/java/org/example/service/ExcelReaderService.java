package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class ExcelReaderService {
    public Workbook getWorkbook(InputStream fs, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(fs);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(fs);
        } else {
            throw new RuntimeException("The file is not correct?");
        }
        return workbook;
    }

    public Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        Object cellValue = null;
        switch (cellType) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;

            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            default:
                return null;
        }

        return cellValue;
    }

    public boolean isRowEmpty(Row row){
        if (row == null) return true;
        for(int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++){
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK){
                return false;
            }
        }
        return true;
    }
}
