package org.example.service;

import org.apache.poi.ss.usermodel.*;
import org.example.model.Employee;
import org.example.model.Shift;
import org.example.model.WorkingDay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeProcessingService {

    public static final int COLUMN_INDEX_ID = 1;
    public static final int COLUMN_INDEX_NAME = 2;

    public List<Employee> processEmployee(Sheet sheet, ShiftMappingService shiftMappingService) {

        List<Employee> employees = new ArrayList<>();

        // Identify "Tổng lương" column
        int totalSalaryColumn = 0;
        for (int i = 0; i < sheet.getRow(3).getLastCellNum(); i++) {
            Cell cell = sheet.getRow(3).getCell(i);
            if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equalsIgnoreCase("Tổng lương")) {
                totalSalaryColumn = cell.getColumnIndex();
            }
        }

        // Get Map of Day Range
        Map<Integer, List<Integer>> colDayRange = shiftMappingService.getDayColumnRanges(sheet.getRow(3), totalSalaryColumn);
//        colDayRange.forEach((k, v) -> System.out.println(k + ": " + v));

        // Get Employee data
        Row shiftCodeRow = sheet.getRow(5);
        for (int i = 6; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || isRowEmpty(row)) continue;

            String employeeId = getCellValue(row.getCell(COLUMN_INDEX_ID)).toString();
            String employeeName = getCellValue(row.getCell(COLUMN_INDEX_NAME)).toString();

            // Get shifts rate
            Map<String, Double> shiftsRate = shiftMappingService.getShiftsRate(row, shiftCodeRow, totalSalaryColumn);

            // Get working days
            List<WorkingDay> workingDays = new ArrayList<>();
            for (Map.Entry<Integer, List<Integer>> entry : colDayRange.entrySet()) {
                WorkingDay workingDay = new WorkingDay(String.valueOf(entry.getKey()));

                for (int j = 0; j < entry.getValue().size(); j++) {
                    int colIndex = entry.getValue().get(j);
                    String shiftName = shiftCodeRow.getCell(colIndex).getStringCellValue();

                    if (shiftName != null && !shiftName.equalsIgnoreCase("$")) {
                        double hours = 0.0;
                        Cell shiftCell = row.getCell(colIndex);
                        if (shiftCell != null && shiftCell.getCellType() == CellType.NUMERIC) {
                            hours = shiftCell.getNumericCellValue();
                        }
                        if (!shiftsRate.containsKey(shiftName)) {
//                            System.err.println("The shift '" + shiftName + "' has no rate for employees: " + employeeName);
                            continue;
                        }
                        double rate = shiftsRate.getOrDefault(shiftName, 0.0);
                        double amount = rate * hours;
                        Shift shift = new Shift(shiftName, hours, amount);
                        workingDay.addShift(shift, hours, amount);
                    }
                }
                workingDays.add(workingDay);
            }

            Employee employee = new Employee(employeeId, employeeName, row.getCell(totalSalaryColumn).getNumericCellValue());
            employee.setShiftsRate(shiftsRate);
            for (WorkingDay workingDay : workingDays) {
                employee.addWorkingDay(workingDay);
            }

            employees.add(employee);
        }

        return employees;
    }

    private Object getCellValue(Cell cell){
        CellType cellType = cell.getCellType();
        Object cellValue = null;

        switch (cellType){
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

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}
