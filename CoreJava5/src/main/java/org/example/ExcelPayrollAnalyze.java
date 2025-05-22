package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

class Employee {
    String id;
    String name;
    List<WorkDay> workDays;
    double totalSalary;
    double recordedTotalSalary;

    private double allowance;

    public Employee(String id, String name, double recordedTotalSalary) {
        this.id = id;
        this.name = name;
        this.workDays = new ArrayList<>();
        this.totalSalary = 0.0;
        this.recordedTotalSalary = recordedTotalSalary;
        this.allowance = 0.0;
    }

    public void addAllowance(double allowance) {
        this.allowance += allowance;
    }

    public void addWorkDay(WorkDay workDay) {
        workDays.add(workDay);
        totalSalary += workDay.totalSalary;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nhân viên: ").append(id).append(" - ").append(name).append("\n");
        sb.append("Danh sách ngày làm việc:\n");
        for (WorkDay workDay : workDays) {
            sb.append(workDay).append("\n");
        }
        sb.append("Tổng tiền lương tính toán: ").append(String.format("%,.2f", totalSalary)).append(" VND\n");
        sb.append("Tổng tiền lương trong file: ").append(String.format("%,.2f", recordedTotalSalary)).append(" VND\n");
        sb.append("Chênh lệch: ").append(String.format("%,.2f", totalSalary - recordedTotalSalary)).append(" VND\n");
        return sb.toString();
    }
}

class WorkDay {
    int day;
    double totalHours;
    Map<String, Double> shifts;
    double totalSalary;

    public WorkDay(int day) {
        this.day = day;
        this.shifts = new LinkedHashMap<>();
        this.totalHours = 0.0;
        this.totalSalary = 0.0;
    }

    public void addShift(String shiftType, double hours, double rate) {
        shifts.put(shiftType, hours);
        totalHours += hours;
        totalSalary += hours * rate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ngày ").append(day).append(": Tổng giờ = ").append(totalHours).append(", Ca: ");
        for (Map.Entry<String, Double> entry : shifts.entrySet()) {
            sb.append(entry.getKey()).append(" = ").append(entry.getValue()).append(" giờ, ");
        }
        sb.append("Tổng tiền = ").append(String.format("%,.2f", totalSalary)).append(" VND");
        return sb.toString();
    }
}

public class ExcelPayrollAnalyze {

    public static void main(String[] args) {
        String filePath = "src/main/resources/BangCong.xlsx";
        try {
            List<Employee> employees = readExcelFile(filePath);
            printResults(employees);
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file Excel: " + e.getMessage());
        }
    }

    private static List<Employee> readExcelFile(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            // Xác định các cột cố định
            int idCol = 1; // Cột Mã NV (cột B)
            int nameCol = 2; // Cột Họ Tên (cột C)
            int totalSalaryCol = 16; // Cột Q - Tổng lương (giữ nguyên)

            // Xác định cột ngày và ca từ dòng tiêu đề
            Row headerRow = sheet.getRow(0); // Dòng tiêu đề (ngày)
            Row shiftRow = sheet.getRow(1); // Dòng mã ca
            Row headerRow2 = sheet.getRow(5); // Dòng tiêu đề ca (Row 5)

            if (headerRow == null || shiftRow == null || headerRow2 == null) {
                throw new IOException("Thiếu dòng tiêu đề.");
            }

            // Xác định các cột tổng giờ và tổng tiền cho từng loại ca (D đến P)
            Map<String, Integer> shiftHoursCols = new HashMap<>();
            Map<String, Integer> shiftEarningsCols = new HashMap<>();
            for (int col = 3; col < totalSalaryCol; col += 2) {
                String shiftHeader = getCellStringValue(headerRow.getCell(col));
                if (shiftHeader != null && shiftHeader.startsWith("Tổng ")) {
                    String shiftCode = shiftHeader.replace("Tổng ", "").split(" ")[0];
                    shiftHoursCols.put(shiftCode, col);
                    shiftEarningsCols.put(shiftCode, col + 1);
                }
            }

            // Xác định cột ngày động
            int firstDayCol = -1;
            Map<Integer, String> shiftTypes = new HashMap<>();
            for (int col = totalSalaryCol + 1; col <= headerRow2.getLastCellNum(); col++) {
                String cellValue = getCellStringValue(headerRow2.getCell(col));
                if (cellValue != null && (cellValue.equals("CN") || cellValue.equals("GC") || cellValue.equals("TC") ||
                        cellValue.equals("GC1") || cellValue.equals("TC1") || cellValue.equals("WK-D") || cellValue.equals("WK-N"))) {
                    shiftTypes.put(col, cellValue);
                    if (firstDayCol == -1) {
                        firstDayCol = col; // Cột đầu tiên của ngày 1
                    }
                }
            }

            if (firstDayCol == -1) {
                throw new IOException("Không tìm thấy cột ngày trong file Excel.");
            }

            // Duyệt qua từng dòng nhân viên (bắt đầu từ dòng 6)
            for (int rowIdx = 6; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                if (row == null || row.getCell(idCol) == null) continue;

                // Lấy thông tin nhân viên
                String id = getCellStringValue(row.getCell(idCol));
                String name = getCellStringValue(row.getCell(nameCol));
                double recordedTotalSalary = getCellNumericValue(row.getCell(totalSalaryCol));
                if (id.isEmpty() || name.isEmpty()) continue;

                Employee employee = new Employee(id, name, recordedTotalSalary);

                // Tính salary_rate cho từng loại ca dựa trên dữ liệu của nhân viên
                Map<String, Double> salaryRates = new HashMap<>();
                for (String shiftCode : shiftHoursCols.keySet()) {
                    int hoursCol = shiftHoursCols.get(shiftCode);
                    int earningsCol = shiftEarningsCols.get(shiftCode);
                    double totalHours = getCellNumericValue(row.getCell(hoursCol));
                    double totalEarnings = getCellNumericValue(row.getCell(earningsCol));
                    if (totalHours > 0) {
                        double rate = totalEarnings / totalHours;
                        salaryRates.put(shiftCode, rate);
                    } else {
                        salaryRates.put(shiftCode, 0.0);
                    }
                }

                // Duyệt qua các cột ngày (từ firstDayCol trở đi, mỗi ngày có 2 cột: Ca ngày, Ca đêm)
                int maxDays = 31;
                for (int day = 1; day <= maxDays; day++) {
                    int dayCol = firstDayCol + (day - 1) * 2; // Cột Ca ngày
                    int nightCol = dayCol + 1; // Cột Ca đêm

                    if (dayCol >= headerRow2.getLastCellNum() || nightCol >= headerRow2.getLastCellNum()) break;

                    double dayHours = getCellNumericValue(row.getCell(dayCol));
                    double nightHours = getCellNumericValue(row.getCell(nightCol));

                    if (dayHours > 0 || nightHours > 0) {
                        WorkDay workDay = new WorkDay(day);
                        if (dayHours > 0) {
                            String shiftType = shiftTypes.getOrDefault(dayCol, "CN");
                            double rate = salaryRates.getOrDefault(shiftType, 0.0);
                            workDay.addShift(shiftType, dayHours, rate);
                        }
                        if (nightHours > 0) {
                            String shiftType = shiftTypes.getOrDefault(nightCol, "WK-N");
                            double rate = salaryRates.getOrDefault(shiftType, 0.0);
                            workDay.addShift(shiftType, nightHours, rate);
                        }
                        employee.addWorkDay(workDay);
                    }
                }

                employees.add(employee);
            }
        }
        return employees;
    }

    private static String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    private static double getCellNumericValue(Cell cell) {
        if (cell == null) return 0.0;
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }

    private static void printResults(List<Employee> employees) {
        for (Employee employee : employees) {
            System.out.println(employee);
            System.out.println("----------------------------------------");
        }
    }
}