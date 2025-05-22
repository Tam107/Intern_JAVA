package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelAttendanceAnalyzer {
    private static class Shift {
        String name;
        double rate;
        Shift(String name, double rate) {
            this.name = name;
            this.rate = rate;
        }
    }

    private static class Employee {
        String id;
        String name;
        List<DailyRecord> dailyRecords = new ArrayList<>();
        double totalSalary;

        Employee(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private static class DailyRecord {
        int day;
        Map<String, Double> shiftHours = new HashMap<>();
        double dailyTotal;

        DailyRecord(int day) {
            this.day = day;
        }
    }

    public static void analyzeExcel(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Read shift definitions (columns D to P)
        List<Shift> shifts = new ArrayList<>();
        Row headerRow = sheet.getRow(0);
        for (int col = 3; col <= 15; col += 2) {
            Cell shiftNameCell = headerRow.getCell(col);
            if (shiftNameCell == null || shiftNameCell.getStringCellValue().isEmpty()) break;
            String shiftName = shiftNameCell.getStringCellValue();
            double rate = getCellValue(headerRow.getCell(col + 1));
            if (rate == 0) {
                throw new IllegalArgumentException("Shift " + shiftName + " has no defined rate");
            }
            shifts.add(new Shift(shiftName, rate));
        }

        // Process employee data (skip header row)
        List<Employee> employees = new ArrayList<>();
        for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null || row.getCell(1) == null || getCellStringValue(row.getCell(1)).isEmpty()) continue;

            Employee emp = new Employee(
                    getCellStringValue(row.getCell(1)),
                    getCellStringValue(row.getCell(2))
            );

            double excelTotalSalary = getCellValue(row.getCell(16));
            double calculatedTotal = 0;

            // Process daily records (dynamic days)
            for (int day = 1; day <= 31; day++) {
                int colStart = 18 + (day - 1) * shifts.size()  * 2;
                DailyRecord dailyRecord = new DailyRecord(day);
                boolean hasWork = false;

                for (Shift shift : shifts) {
                    Cell dayCell = row.getCell(colStart + shifts.indexOf(shift) * 2);
                    Cell nightCell = row.getCell(colStart + shifts.indexOf(shift) * 2 + 1);

                    double dayHours = getCellValue(dayCell);
                    double nightHours = getCellValue(nightCell);

                    if (dayHours > 0) {
                        dailyRecord.shiftHours.put(shift.name + "-Day", dayHours);
                        dailyRecord.dailyTotal += dayHours * shift.rate;
                        hasWork = true;
                    }
                    if (nightHours > 0) {
                        dailyRecord.shiftHours.put(shift.name + "-Night", nightHours);
                        dailyRecord.dailyTotal += nightHours * shift.rate;
                        hasWork = true;
                    }
                }

                if (hasWork) {
                    emp.dailyRecords.add(dailyRecord);
                    calculatedTotal += dailyRecord.dailyTotal;
                }
            }

            emp.totalSalary = calculatedTotal;
            employees.add(emp);

            // Validate total salary
            if (Math.abs(excelTotalSalary - calculatedTotal) > 0.01) {
                System.out.println("Warning: Salary mismatch for " + emp.name +
                        ". Excel: " + excelTotalSalary + ", Calculated: " + calculatedTotal);
            }
        }

        // Print results
        for (Employee emp : employees) {
            System.out.println("\nEmployee: " + emp.name + " (" + emp.id + ")");
            System.out.println("Working Days:");
            for (DailyRecord record : emp.dailyRecords) {
                System.out.println("  Day " + record.day + ":");
                System.out.println("    Total Hours: " + record.shiftHours.values().stream().mapToDouble(Double::doubleValue).sum());
                System.out.println("    Shifts: " + record.shiftHours);
                System.out.println("    Daily Salary: " + record.dailyTotal);
            }
            System.out.println("Total Salary: " + emp.totalSalary);
        }

        workbook.close();
        fis.close();
    }

    private static double getCellValue(Cell cell) {
        if (cell == null) return 0;
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                String value = cell.getStringCellValue().trim();
                if (value.isEmpty() || value.equals("-")) return 0;
                try {
                    return Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    return 0; // Skip non-numeric strings like "Tổng lương"
                }
            default:
                return 0;
        }
    }

    private static String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }

    public static void main(String[] args) {
        try {
            analyzeExcel("src/main/resources/BangCong.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}