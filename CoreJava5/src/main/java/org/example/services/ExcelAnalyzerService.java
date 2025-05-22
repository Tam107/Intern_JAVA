//package org.example.services;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.example.model.Employee;
//import org.example.model.WorkDay;
//import org.example.utils.Constants;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.*;
//
//public class ExcelAnalyzerService {
//    private final ExcelDataChecker excelDataChecker;
//
//    public ExcelAnalyzerService(ExcelDataChecker excelDataChecker) {
//        this.excelDataChecker = excelDataChecker;
//    }
//
//    public Map<String, List<Employee>> analyzeExcel(String filePath) throws IOException {
//        Map<String, List<Employee>> result = new HashMap<>();
//
//        try (FileInputStream fis = new FileInputStream(filePath);
//             Workbook workbook = new XSSFWorkbook(fis)) {
//
//            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
//                Sheet sheet = workbook.getSheetAt(sheetIndex);
//                System.out.println("Processing sheet: " + sheet.getSheetName());
//
//                Iterator<Row> rowIterator = sheet.iterator();
//                Row headerRow = null;
//
//                // Find the header row
//                while (rowIterator.hasNext()) {
//                    Row row = rowIterator.next();
//                    if (row.getCell(0) != null && row.getCell(0).toString().equals("STT")) {
//                        headerRow = row;
//                        break;
//                    }
//                }
//
//                if (headerRow == null) {
//                    System.out.println("Header row not found in sheet: " + sheet.getSheetName());
//                    continue;
//                }
//
//                // Identify columns
//                Map<String, Integer> totalHoursColumns = new HashMap<>();
//                Map<String, Integer> totalMoneyColumns = new HashMap<>();
//                int totalSalaryCol = (sheetIndex == 0) ? 15 : 151;
//
//                for (int col = 0; col < headerRow.getLastCellNum(); col++) {
//                    Cell cell = headerRow.getCell(col);
//                    if (cell != null) {
//                        String cellValue = cell.toString().trim();
//                        if (cellValue.startsWith("Tổng ")) {
//                            if (cellValue.equals("Tổng GC")) totalHoursColumns.put("GC", col);
//                            else if (cellValue.equals("Tổng TC")) totalHoursColumns.put("TC", col);
//                            else if (cellValue.equals("Tổng GC1")) totalHoursColumns.put("GC1", col);
//                            else if (cellValue.equals("Tổng TC1")) totalHoursColumns.put("TC1", col);
//                            else if (cellValue.equals("Tổng WK-D & WK-N")) totalHoursColumns.put("WK-D", col);
//                        } else if (cellValue.equals("$")) {
//                            String prevCellValue = headerRow.getCell(col - 1).toString().trim();
//                            if (prevCellValue.equals("Tổng GC")) totalMoneyColumns.put("GC", col);
//                            else if (prevCellValue.equals("Tổng TC")) totalMoneyColumns.put("TC", col);
//                            else if (prevCellValue.equals("Tổng GC1")) totalMoneyColumns.put("GC1", col);
//                            else if (prevCellValue.equals("Tổng TC1")) totalMoneyColumns.put("TC1", col);
//                            else if (prevCellValue.equals("Tổng WK-D & WK-N")) totalMoneyColumns.put("WK-D", col);
//                        }
//                    }
//                }
//
//                // Identify day and shift columns
//                int dayStartCol = 14;
//                Map<Integer, Integer> dayColumns = new TreeMap<>();
//                Map<Integer, String> shiftColumns = new HashMap<>();
//                Map<Integer, String> allowanceColumns = new HashMap<>();
//                List<Integer> dayColIndices = new ArrayList<>();
//
//                for (int col = dayStartCol; col < headerRow.getLastCellNum(); col++) {
//                    Cell cell = headerRow.getCell(col);
//                    if (cell != null) {
//                        String cellValue = cell.toString().trim();
//                        try {
//                            int day = (int) Double.parseDouble(cellValue);
//                            if (day >= 1 && day <= 31) {
//                                dayColumns.put(col, day);
//                                dayColIndices.add(col);
//                            }
//                        } catch (NumberFormatException e) {
//                            for (String shiftType : Constants.SHIFT_TYPES) {
//                                if (cellValue.equals(shiftType)) {
//                                    shiftColumns.put(col, shiftType);
//                                    break;
//                                }
//                            }
//                            if (cellValue.equals("PCĐS") || cellValue.equals("PCCC") ||
//                                    cellValue.equals("PC HNS") || cellValue.equals("Tiền cơm")) {
//                                allowanceColumns.put(col, cellValue);
//                            }
//                        }
//                    }
//                }
//
//                // Process employee data
//                List<Employee> employees = new ArrayList<>();
//                int currentRowIndex = 0;
//                int startRow = (sheetIndex == 0) ? 3 : 8;
//
//                while (rowIterator.hasNext()) {
//                    Row row = rowIterator.next();
//                    currentRowIndex++;
//
//                    if (currentRowIndex < startRow) {
//                        continue;
//                    }
//
//                    Cell idCell = row.getCell(1);
//                    Cell nameCell = row.getCell(2);
//                    if (idCell == null || nameCell == null) {
//                        continue;
//                    }
//
//                    String id = idCell.toString().trim();
//                    String name = nameCell.toString().trim();
//                    if (id.isEmpty() || name.isEmpty()) {
//                        continue;
//                    }
//
//                    Employee employee = new Employee(id, name);
//
//                    // Calculate hourly rate
//                    for (String shiftType : Constants.SHIFT_TYPES) {
//                        Integer hoursCol = totalHoursColumns.get(shiftType);
//                        Integer moneyCol = totalMoneyColumns.get(shiftType);
//                        if (hoursCol != null && moneyCol != null) {
//                            Cell hoursCell = row.getCell(hoursCol);
//                            Cell moneyCell = row.getCell(moneyCol);
//                            double hours = 0.0;
//                            double money = 0.0;
//
//                            if (hoursCell != null && hoursCell.getCellType() == CellType.NUMERIC) {
//                                hours = hoursCell.getNumericCellValue();
//                            }
//                            if (moneyCell != null && moneyCell.getCellType() == CellType.NUMERIC) {
//                                money = moneyCell.getNumericCellValue();
//                            }
//                            if (hours > 0) {
//                                double rate = money / hours;
//                                employee.setRate(shiftType, rate);
//                            }
//                        }
//                    }
//
//                    // Read total salary
//                    Cell totalMoneyCell = row.getCell(totalSalaryCol);
//                    double recordedTotalMoney = 0.0;
//                    if (totalMoneyCell != null && totalMoneyCell.getCellType() == CellType.NUMERIC) {
//                        recordedTotalMoney = totalMoneyCell.getNumericCellValue();
//                    }
//                    employee.setRecordedTotalMoney(recordedTotalMoney);
//
//                    // Log data
//                    excelDataChecker.checkAndLogData(employee, row, totalMoneyColumns, totalSalaryCol, dayColumns,
//                            shiftColumns, dayColIndices, allowanceColumns);
//
//                    // Read daily attendance
//                    for (int i = 0; i < dayColIndices.size(); i++) {
//                        int colIndex = dayColIndices.get(i);
//                        int day = dayColumns.get(colIndex);
//
//                        WorkDay workDay = new WorkDay(day);
//                        double dailyTotalMoney = 0.0;
//                        boolean hasWork = false;
//
//                        int nextDayCol = (i + 1 < dayColIndices.size()) ? dayColIndices.get(i + 1) : headerRow.getLastCellNum();
//                        for (int subCol = colIndex; subCol < nextDayCol; subCol++) {
//                            String shiftType = shiftColumns.get(subCol);
//                            if (shiftType != null) {
//                                Cell shiftCell = row.getCell(subCol);
//                                double hours = 0.0;
//                                if (shiftCell != null && shiftCell.getCellType() == CellType.NUMERIC) {
//                                    hours = shiftCell.getNumericCellValue();
//                                }
//
//                                if (hours > 0) {
//                                    workDay.addShift(shiftType, hours);
//                                    double rate = employee.getRate(shiftType);
//                                    dailyTotalMoney += hours * rate;
//                                    hasWork = true;
//                                }
//                            }
//                        }
//
//                        if (hasWork) {
//                            workDay.setTotalMoney(dailyTotalMoney);
//                            employee.addWorkDay(workDay);
//                        }
//                    }
//
//                    // Read allowances
//                    double allowances = 0.0;
//                    for (Map.Entry<Integer, String> allowanceEntry : allowanceColumns.entrySet()) {
//                        int allowanceCol = allowanceEntry.getKey();
//                        Cell allowanceCell = row.getCell(allowanceCol);
//                        if (allowanceCell != null && allowanceCell.getCellType() == CellType.NUMERIC) {
//                            allowances += allowanceCell.getNumericCellValue();
//                        }
//                    }
//                    if (allowances > 0) {
//                        WorkDay allowanceDay = new WorkDay(0);
//                        allowanceDay.setTotalMoney(allowances);
//                        employee.addWorkDay(allowanceDay);
//                    }
//
//                    employees.add(employee);
//                }
//
//                result.put("Sheet " + (sheetIndex + 1), employees);
//            }
//        }
//
//        return result;
//    }
//}
