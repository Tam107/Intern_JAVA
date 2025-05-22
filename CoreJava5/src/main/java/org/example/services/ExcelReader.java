package org.example.services;




import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.exception.TimesheetException;
import org.example.model.Employee;
import org.example.model.Shift;
import org.example.model.WorkDay;
import org.example.model.WorkSummary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public WorkSummary readTimesheet(String filePath) throws TimesheetException {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Tìm sheet chứa dữ liệu chấm công
            Sheet sheet = findTimesheetSheet(workbook);
            if (sheet == null) {
                throw new TimesheetException("Không tìm thấy bảng chấm công trong file Excel");
            }

            // Đọc định nghĩa ca làm việc
            Map<String, Shift> shiftDefinitions = readShiftDefinitions(sheet);

            // Đọc dữ liệu nhân viên và ngày công
            List<Employee> employees = readEmployeeData(sheet, shiftDefinitions);

            return new WorkSummary(employees, shiftDefinitions);

        } catch (IOException e) {
            throw new TimesheetException("Lỗi khi đọc file Excel: " + e.getMessage());
        }
    }

    private Sheet findTimesheetSheet(Workbook workbook) {
        // Tìm sheet chứa bảng chấm công dựa trên tiêu đề hoặc cấu trúc
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            Row headerRow = sheet.getRow(2); // Dòng chứa tiêu đề cột

            if (headerRow != null) {
                Cell cell = headerRow.getCell(0);
                if (cell != null && cell.getStringCellValue().equals("STT")) {
                    return sheet;
                }
            }
        }
        return null;
    }

    private Map<String, Shift> readShiftDefinitions(Sheet sheet) {
        Map<String, Shift> shifts = new HashMap<>();

        // Đọc dòng định nghĩa ca làm việc (dòng 3)
        Row shiftRow = sheet.getRow(3);

        // Đọc từng cột từ D đến P để tìm định nghĩa ca
        for (int col = 3; col < 16; col++) {
            Cell codeCell = shiftRow.getCell(col);
            Cell rateCell = shiftRow.getCell(col + 1);

            if (codeCell != null && !codeCell.getStringCellValue().isEmpty()) {
                String code = codeCell.getStringCellValue();
                double rate = 0.0;

                if (rateCell != null) {
                    rate = getNumericCellValue(rateCell);
                }

                shifts.put(code, new Shift(code, rate));
                col++; // Bỏ qua cột giá tiền
            }
        }

        return shifts;
    }

    private List<Employee> readEmployeeData(Sheet sheet, Map<String, Shift> shiftDefinitions) throws TimesheetException {
        List<Employee> employees = new ArrayList<>();

        // Tìm dòng bắt đầu dữ liệu nhân viên
        int startRow = -1;
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getCell(0) != null && isNumeric(row.getCell(0))) {
                startRow = i;
                break;
            }
        }

        if (startRow == -1) {
            throw new TimesheetException("Không tìm thấy dữ liệu nhân viên trong bảng");
        }

        // Đọc dữ liệu từng nhân viên
        for (int rowNum = startRow; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) continue;

            // Kiểm tra nếu là dòng nhân viên hợp lệ
            if (row.getCell(0) != null && isNumeric(row.getCell(0)) &&
                    row.getCell(1) != null && !row.getCell(1).getStringCellValue().isEmpty() &&
                    row.getCell(2) != null && !row.getCell(2).getStringCellValue().isEmpty()) {

                String id = row.getCell(1).getStringCellValue();
                String name = row.getCell(2).getStringCellValue();

                Employee employee = new Employee(id, name);

                // Đọc tổng lương từ cột Q
                if (row.getCell(16) != null) {
                    employee.setReportedTotalSalary(getNumericCellValue(row.getCell(16)));
                }

                // Đọc dữ liệu chấm công theo ngày
                readEmployeeWorkDays(sheet, row, employee, shiftDefinitions);

                employees.add(employee);
            }
        }

        return employees;
    }

    private void readEmployeeWorkDays(Sheet sheet, Row employeeRow, Employee employee, Map<String, Shift> shiftDefinitions) throws TimesheetException {
        // Đọc thông tin ngày và ca làm việc
        Row dateHeaderRow = sheet.getRow(1);
        Row shiftTypeRow = sheet.getRow(2);

        // Bắt đầu từ cột 17 (sau cột tổng lương)
        for (int col = 17; col < employeeRow.getLastCellNum(); col++) {
            Cell dateCell = dateHeaderRow.getCell(col);
            Cell shiftTypeCell = shiftTypeRow.getCell(col);
            Cell hoursCell = employeeRow.getCell(col);

            if (dateCell != null && shiftTypeCell != null && hoursCell != null) {
                String dateStr = getStringCellValue(dateCell);
                String shiftCode = getStringCellValue(shiftTypeCell);
                double hours = getNumericCellValue(hoursCell);

                if (!dateStr.isEmpty() && !shiftCode.isEmpty() && hours > 0) {
                    // Kiểm tra ca làm việc có được định nghĩa không
                    if (!shiftDefinitions.containsKey(shiftCode)) {
                        throw new TimesheetException("Ca làm việc '" + shiftCode + "' chưa được định nghĩa");
                    }

                    // Tạo hoặc cập nhật ngày làm việc
                    WorkDay workDay = employee.getWorkDays().getOrDefault(dateStr, new WorkDay(dateStr));

                    // Tạo bản sao của định nghĩa ca làm việc và đặt số giờ
                    Shift shift = new Shift(shiftCode, shiftDefinitions.get(shiftCode).getRate());
                    shift.setHours(hours);

                    workDay.addShift(shift);
                    employee.addWorkDay(dateStr, workDay);
                }
            }
        }
    }

    private boolean isNumeric(Cell cell) {
        return cell.getCellType() == CellType.NUMERIC ||
                (cell.getCellType() == CellType.STRING && cell.getStringCellValue().matches("\\d+(\\.\\d+)?"));
    }

    private double getNumericCellValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().matches("\\d+(\\.\\d+)?")) {
            return Double.parseDouble(cell.getStringCellValue());
        } else {
            return 0.0;
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return "";
        }
    }
}

