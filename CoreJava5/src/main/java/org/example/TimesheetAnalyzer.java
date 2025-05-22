package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TimesheetAnalyzer {

    static class ShiftInfo {
        String shiftCode;
        int hoursCol;
        int earningsCol;

        ShiftInfo(String shiftCode, int hoursCol, int earningsCol) {
            this.shiftCode = shiftCode;
            this.hoursCol = hoursCol;
            this.earningsCol = earningsCol;
        }
    }

    static class DailyColumn {
        int day;
        String shiftCode;
        int colIndex;

        DailyColumn(int day, String shiftCode, int colIndex) {
            this.day = day;
            this.shiftCode = shiftCode;
            this.colIndex = colIndex;
        }
    }

    public static void main(String[] args) {
        String filePath = "src/main/resources/BangCong.xlsx"; // Replace with your file path
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            analyzeTimesheet(sheet);
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
        }
    }

    private static void analyzeTimesheet(Sheet sheet) {
        Row dayRow = sheet.getRow(0); // Hàng ngày
        Row shiftRow = sheet.getRow(1); // Hàng mã ca

        if (dayRow == null || shiftRow == null) {
            System.err.println("Thiếu hàng tiêu đề.");
            return;
        }

        CellType cellType = CellType.BLANK;

        int totalSalaryCol = 16; // Cột Q - Tổng lương

        // Đọc định nghĩa ca từ cột D đến P
        Map<String, Double> shiftRates = new HashMap<>();
        for (int col = 3; col < totalSalaryCol; col += 2) {
            String shiftCode = getCellString(dayRow, col);
            String rateStr = getCellString(dayRow, col + 1);
            if (shiftCode != null && rateStr != null && rateStr.startsWith("$")) {
                try {
                    double rate = Double.parseDouble(rateStr.substring(1));
                    shiftRates.put(shiftCode, rate);
                } catch (NumberFormatException e) {
                    shiftRates.put(shiftCode, 0.0);
                }
            }
        }

        // Xác định các cột dữ liệu hàng ngày
        List<DailyColumn> dailyColumns = new ArrayList<>();
        for (int col = totalSalaryCol + 1; col < dayRow.getLastCellNum(); col++) {
            String dayStr = getCellString(dayRow, col);
            String shiftCode = getCellString(shiftRow, col);
            if (dayStr != null && shiftCode != null && dayStr.matches("\\d+")) {
                int day = Integer.parseInt(dayStr);
                dailyColumns.add(new DailyColumn(day, shiftCode, col));
            }
        }

        // Xử lý dữ liệu nhân viên
        for (int rowIdx = 2; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) continue;

            String empId = getCellString(row, 1); // Mã NV
            String empName = getCellString(row, 2); // Họ Tên
            if (empId == null || empName == null) continue;

            Map<Integer, Double> dailyEarnings = new TreeMap<>();
            Map<Integer, Double> dailyHours = new TreeMap<>();
            Map<Integer, List<String>> dailyShifts = new TreeMap<>();
            double totalEarnings = 0.0;

            // Nhóm cột theo ngày
            Map<Integer, List<DailyColumn>> dailyByDay = new TreeMap<>();
            for (DailyColumn dc : dailyColumns) {
                dailyByDay.computeIfAbsent(dc.day, k -> new ArrayList<>()).add(dc);
            }

            for (Map.Entry<Integer, List<DailyColumn>> entry : dailyByDay.entrySet()) {
                int day = entry.getKey();
                List<DailyColumn> cols = entry.getValue();
                double dayHours = 0.0;
                double dayEarnings = 0.0;
                List<String> shiftsWorked = new ArrayList<>();

                for (DailyColumn dc : cols) {
                    double hours = getCellNumeric(row, dc.colIndex);
                    if (hours > 0) {
                        double rate = getShiftRate(shiftRates, dc.shiftCode);
                        if (rate == -1) {
                            System.err.println("Ca '" + dc.shiftCode + "' không được định nghĩa.");
                            continue;
                        }
                        dayHours += hours;
                        dayEarnings += hours * rate;
                        shiftsWorked.add(dc.shiftCode + ": " + hours + "h");
                    }
                }

                if (dayHours > 0) {
                    dailyHours.put(day, dayHours);
                    dailyEarnings.put(day, dayEarnings);
                    dailyShifts.put(day, shiftsWorked);
                    totalEarnings += dayEarnings;
                }
            }

            // Lấy tổng lương từ cột Q
            double fileTotalSalary = getCellNumeric(row, totalSalaryCol);

            // Hiển thị kết quả
            System.out.println("Nhân viên: " + empId + " - " + empName);
            for (int day : dailyHours.keySet()) {
                System.out.println("Ngày " + day + ":");
                System.out.println("  Tổng số giờ: " + dailyHours.get(day));
                System.out.println("  Ca làm việc: " + String.join(", ", dailyShifts.get(day)));
                System.out.printf("  Tổng tiền: %.2f%n", dailyEarnings.get(day));
            }
            System.out.printf("Tổng số tiền: %.2f%n", totalEarnings);
            System.out.printf("Tổng lương trong file: %.2f - %s%n", fileTotalSalary,
                    Math.abs(totalEarnings - fileTotalSalary) < 0.01 ? "Khớp" : "Không khớp");
            System.out.println();
        }
    }

    // Hàm riêng để lấy giá trị của ca
    private static double getShiftRate(Map<String, Double> shiftRates, String shiftCode) {
        return shiftRates.getOrDefault(shiftCode, -1.0);
    }

    private static String getCellString(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null || cell.getCellType() == CellType.BLANK) return null;
        return cell.toString().trim();
    }

    private static double getCellNumeric(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null || cell.getCellType() == CellType.BLANK || cell.toString().equals("-")) return 0.0;
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
}