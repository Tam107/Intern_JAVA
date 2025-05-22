package com.example.employeemanagement.services;



import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.model.WorkDay;
import com.example.employeemanagement.utils.Constants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelAnalyzerService {
    public Map<String, List<Employee>> analyzeExcel(String filePath) throws IOException {
        Map<String, List<Employee>> result = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                Iterator<Row> rowIterator = sheet.iterator();

                Row headerRow = null;
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getCell(0) != null && row.getCell(0).toString().equals("STT")) {
                        headerRow = row;
                        break;
                    }
                }

                if (headerRow == null) {
                    continue;
                }

                Map<Integer, Integer> dayColumns = new HashMap<>();
                Map<Integer, String> shiftColumns = new HashMap<>();
                Map<Integer, String> allowanceColumns = new HashMap<>();
                Map<Integer, String> moneyColumns = new HashMap<>();
                for (int col = 6; col < headerRow.getLastCellNum(); col++) {
                    Cell cell = headerRow.getCell(col);
                    if (cell != null) {
                        String cellValue = cell.toString().trim();
                        try {
                            int day = (int) Double.parseDouble(cellValue);
                            if (day >= 1 && day <= 31) {
                                dayColumns.put(col, day);
                                for (int subCol = col; subCol < headerRow.getLastCellNum(); subCol++) {
                                    Cell subCell = headerRow.getCell(subCol);
                                    if (subCell != null) {
                                        String subCellValue = subCell.toString().trim();
                                        for (String shiftType : Constants.SHIFT_TYPES) {
                                            if (subCellValue.equals(shiftType)) {
                                                shiftColumns.put(subCol, shiftType);
                                                break;
                                            }
                                        }
                                        if (subCellValue.equals("PCĐS") || subCellValue.equals("PCCC") ||
                                                subCellValue.equals("PC HNS") || subCellValue.equals("Tiền cơm")) {
                                            allowanceColumns.put(subCol, subCellValue);
                                        }
                                        if (subCellValue.equals("$")) {
                                            moneyColumns.put(subCol, subCellValue);
                                        }
                                    }
                                }
                            }
                        } catch (NumberFormatException e) {
                            // Không phải cột ngày, bỏ qua
                        }
                    }
                }

                List<Employee> employees = new ArrayList<>();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Cell idCell = row.getCell(1);
                    Cell nameCell = row.getCell(2);
                    if (idCell == null || nameCell == null) {
                        continue;
                    }

                    String id = idCell.toString().trim();
                    String name = nameCell.toString().trim();
                    if (id.isEmpty() || name.isEmpty()) {
                        continue;
                    }

                    Employee employee = new Employee(id, name);

                    Cell totalMoneyCell = row.getCell(16);
                    double recordedTotalMoney = 0.0;
                    if (totalMoneyCell != null && totalMoneyCell.getCellType() == CellType.NUMERIC) {
                        recordedTotalMoney = totalMoneyCell.getNumericCellValue();
                    }
                    employee.setRecordedTotalMoney(recordedTotalMoney);

                    for (Map.Entry<Integer, Integer> entry : dayColumns.entrySet()) {
                        int colIndex = entry.getKey();
                        int day = entry.getValue();

                        WorkDay workDay = new WorkDay(day);
                        double dailyTotalMoney = 0.0;
                        boolean hasWork = false;

                        for (Map.Entry<Integer, String> shiftEntry : shiftColumns.entrySet()) {
                            int shiftCol = shiftEntry.getKey();
                            String shiftType = shiftEntry.getValue();

                            Cell shiftCell = row.getCell(shiftCol);
                            double hours = 0.0;
                            if (shiftCell != null && shiftCell.getCellType() == CellType.NUMERIC) {
                                hours = shiftCell.getNumericCellValue();
                            }

                            if (hours > 0) {
                                workDay.addShift(shiftType, hours);
                                hasWork = true;
                            }
                        }

                        // Đọc tổng tiền từ cột "$" tương ứng
                        for (Map.Entry<Integer, String> moneyEntry : moneyColumns.entrySet()) {
                            int moneyCol = moneyEntry.getKey();
                            if (moneyCol > colIndex && moneyCol < (colIndex + 10)) { // Đảm bảo cột tiền nằm gần cột ngày
                                Cell moneyCell = row.getCell(moneyCol);
                                if (moneyCell != null && moneyCell.getCellType() == CellType.NUMERIC) {
                                    dailyTotalMoney = moneyCell.getNumericCellValue();
                                    break;
                                }
                            }
                        }

                        if (hasWork) {
                            workDay.setTotalMoney(dailyTotalMoney);
                            employee.addWorkDay(workDay);
                        }
                    }

                    double allowances = 0.0;
                    for (Map.Entry<Integer, String> allowanceEntry : allowanceColumns.entrySet()) {
                        int allowanceCol = allowanceEntry.getKey();
                        Cell allowanceCell = row.getCell(allowanceCol);
                        if (allowanceCell != null && allowanceCell.getCellType() == CellType.NUMERIC) {
                            allowances += allowanceCell.getNumericCellValue();
                        }
                    }
                    if (allowances > 0) {
                        WorkDay allowanceDay = new WorkDay(0);
                        allowanceDay.setTotalMoney(allowances);
                        employee.addWorkDay(allowanceDay);
                    }

                    employees.add(employee);
                }

                result.put("Sheet " + (sheetIndex + 1), employees);
            }
        }

        return result;
    }
}