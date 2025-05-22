package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class AttendanceAnalyzer {

    // Class to represent a shift with name and rate
    private static class Shift {
        String name;
        double rate;

        Shift(String name, double rate) {
            this.name = name;
            this.rate = rate;
        }
    }

    // Class to store daily attendance data
    private static class DailyRecord {
        String day;
        Map<String, Double> shiftHours = new HashMap<>();
        double totalHours = 0;
        double dailyPay = 0;
    }

    // Class to represent an employee and their attendance data
    private static class Employee {
        String id;
        String name;
        List<DailyRecord> daysWorked = new ArrayList<>();
        double totalPay = 0;

        // Method to print employee details
        void printDetails(double fileTotalPay) {
            System.out.println("Employee: " + name + " (" + id + ")");
            for (DailyRecord record : daysWorked) {
                System.out.println("  Day: " + record.day);
                System.out.println("    Total Hours: " + record.totalHours);
                System.out.println("    Shifts: " + record.shiftHours);
                System.out.println("    Daily Pay: " + String.format("%.2f", record.dailyPay));
            }
            System.out.println("  Total Pay: " + String.format("%.2f", totalPay));
            System.out.println("  File Total Pay: " + String.format("%.2f", fileTotalPay));
            System.out.println("  Pay Match: " + (Math.abs(totalPay - fileTotalPay) < 0.01 ? "Yes" : "No"));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String filePath = "src/main/resources/BangCong.xlsx";
        try {
            analyzeAttendance(filePath);
        } catch (IOException e) {
            System.err.println("Error reading the Excel file: " + e.getMessage());
        }
    }

    public static void analyzeAttendance(String filePath) throws IOException {
        // Open the Excel file
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Step 1: Read shift definitions from row 1 (index 0)
        List<Shift> shifts = new ArrayList<>();
        Row shiftRow = sheet.getRow(0); // Adjust this if shifts are in a different row
        if (shiftRow == null) {
            System.out.println("Row 1 is empty. No shifts defined.");
            workbook.close();
            fis.close();
            return;
        }
        for (int i = 3; i < shiftRow.getLastCellNum(); i += 2) { // Adjust starting index if needed
            Cell nameCell = shiftRow.getCell(i);
            Cell rateCell = shiftRow.getCell(i + 1);
            if (nameCell == null || nameCell.getCellType() == CellType.BLANK) break;
            String name = nameCell.getStringCellValue();
            double rate = rateCell != null && rateCell.getCellType() == CellType.NUMERIC ? rateCell.getNumericCellValue() : 0;
            shifts.add(new Shift(name, rate));
        }
        int numShifts = shifts.size();
        if (numShifts == 0) {
            System.out.println("No shifts defined in the file.");
            workbook.close();
            fis.close();
            return;
        }

        // Step 2: Find the starting column for attendance data in row 3 (index 2)
        Row dateRow = sheet.getRow(2);
        int startCol = -1;
        for (int i = 0; i < dateRow.getLastCellNum(); i++) {
            Cell cell = dateRow.getCell(i);
            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                startCol = i;
                break;
            }
        }
        if (startCol == -1) {
            System.out.println("No attendance data found.");
            workbook.close();
            fis.close();
            return;
        }

        // Step 3: Determine the list of days
        List<String> days = new ArrayList<>();
        int m = 0;
        while (true) {
            int col = startCol + m * numShifts;
            Cell cell = dateRow.getCell(col);
            if (cell == null || cell.getCellType() != CellType.NUMERIC) break;
            String day = String.valueOf((int) cell.getNumericCellValue());
            days.add(day);
            m++;
        }

        // Step 4: Process each employee starting from row 4 (index 3)
        for (int rowIdx = 3; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null || row.getCell(1) == null) continue;

            // Read employee ID and name
            String id = row.getCell(1).toString();
            String name = row.getCell(2).toString();
            Employee employee = new Employee();
            employee.id = id;
            employee.name = name;

            // Process attendance data for each day
            for (int dayIdx = 0; dayIdx < days.size(); dayIdx++) {
                DailyRecord record = new DailyRecord();
                record.day = days.get(dayIdx);

                for (int shiftIdx = 0; shiftIdx < numShifts; shiftIdx++) {
                    int col = startCol + dayIdx * numShifts + shiftIdx;
                    Cell cell = row.getCell(col);
                    double hours = 0;

                    // Handle different cell types for hours
                    if (cell != null) {
                        if (cell.getCellType() == CellType.NUMERIC) {
                            hours = cell.getNumericCellValue();
                        } else if (cell.getCellType() == CellType.STRING) {
                            String str = cell.getStringCellValue().trim();
                            if (!str.equals("-")) {
                                try {
                                    hours = Double.parseDouble(str);
                                } catch (NumberFormatException e) {
                                    // Treat non-numeric strings (except "-") as 0
                                }
                            }
                        }
                    }

                    // Only include shifts with hours > 0
                    if (hours > 0) {
                        String shiftName = shifts.get(shiftIdx).name;
                        record.shiftHours.put(shiftName, hours);
                        record.totalHours += hours;
                        record.dailyPay += hours * shifts.get(shiftIdx).rate;
                    }
                }

                // Add the day to the employee's record if any shifts were worked
                if (!record.shiftHours.isEmpty()) {
                    employee.daysWorked.add(record);
                    employee.totalPay += record.dailyPay;
                }
            }

            // Step 5: Read the file's total pay from column Q (index 16)
            Cell totalPayCell = row.getCell(16);
            double fileTotalPay = totalPayCell != null && totalPayCell.getCellType() == CellType.NUMERIC ? totalPayCell.getNumericCellValue() : 0;

            // Step 6: Print employee details
            employee.printDetails(fileTotalPay);
        }

        // Clean up
        workbook.close();
        fis.close();
    }
}