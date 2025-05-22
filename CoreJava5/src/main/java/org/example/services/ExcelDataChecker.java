//package org.example.services;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;
//import org.example.model.Employee;
//
//import java.util.List;
//import java.util.Map;
//
//public class ExcelDataChecker {
//
//    public void checkAndLogData(Employee employee, Row row, Map<String, Integer> totalMoneyColumns,
//                                int totalSalaryCol, Map<Integer, Integer> dayColumns,
//                                Map<Integer, String> shiftColumns, List<Integer> dayColIndices,
//                                Map<Integer, String> allowanceColumns) {
//        System.out.println("Checking and logging data for employee: " + employee.getName() + " (Mã NV: " + employee.getId() + ")");
//
//        // Log tổng tiền từ các cột tổng (Tổng GC, Tổng WK-D, v.v.)
//        for (Map.Entry<String, Integer> entry : totalMoneyColumns.entrySet()) {
//            String shiftType = entry.getKey();
//            int moneyCol = entry.getValue();
//            Cell moneyCell = row.getCell(moneyCol);
//            double money = 0.0;
//            if (moneyCell != null && moneyCell.getCellType() == CellType.NUMERIC) {
//                money = moneyCell.getNumericCellValue();
//            }
//            System.out.println("  Tổng tiền " + shiftType + " (Cột " + moneyCol + "): " + money + " VNĐ");
//        }
//
//        // Log tổng tiền ghi nhận
//        Cell totalMoneyCell = row.getCell(totalSalaryCol);
//        double recordedTotalMoney = 0.0;
//        if (totalMoneyCell != null && totalMoneyCell.getCellType() == CellType.NUMERIC) {
//            recordedTotalMoney = totalMoneyCell.getNumericCellValue();
//        }
//        System.out.println("  Tổng tiền ghi nhận (cột " + (totalSalaryCol == 13 ? "N" : "Q") + "): " + recordedTotalMoney + " VNĐ");
//
//        // Log tổng tiền mỗi ngày
//        for (int i = 0; i < dayColIndices.size(); i++) {
//            int colIndex = dayColIndices.get(i);
//            int day = dayColumns.get(colIndex);
//
//            double dailyTotalMoney = 0.0;
//            boolean hasWork = false;
//
//            System.out.println("  Ngày " + day + ":");
//            int nextDayCol = (i + 1 < dayColIndices.size()) ? dayColIndices.get(i + 1) : row.getLastCellNum();
//            for (int subCol = colIndex; subCol < nextDayCol; subCol++) {
//                String shiftType = shiftColumns.get(subCol);
//                if (shiftType != null) {
//                    Cell shiftCell = row.getCell(subCol);
//                    double hours = 0.0;
//                    if (shiftCell != null && shiftCell.getCellType() == CellType.NUMERIC) {
//                        hours = shiftCell.getNumericCellValue();
//                    }
//
//                    if (hours > 0) {
//                        double rate = employee.getRate(shiftType);
//                        double money = hours * rate;
//                        dailyTotalMoney += money;
//                        hasWork = true;
//                        System.out.println("    " + shiftType + " (Cột " + subCol + "): " + hours + " giờ, Tiền: " + money + " VNĐ");
//                    }
//                }
//            }
//            if (hasWork) {
//                System.out.println("    Tổng tiền ngày " + day + ": " + dailyTotalMoney + " VNĐ");
//            }
//        }
//
//        // Log phụ cấp
//        double allowances = 0.0;
//        for (Map.Entry<Integer, String> allowanceEntry : allowanceColumns.entrySet()) {
//            int allowanceCol = allowanceEntry.getKey();
//            String allowanceType = allowanceEntry.getValue();
//            Cell allowanceCell = row.getCell(allowanceCol);
//            if (allowanceCell != null && allowanceCell.getCellType() == CellType.NUMERIC) {
//                double allowanceValue = allowanceCell.getNumericCellValue();
//                allowances += allowanceValue;
//                System.out.println("  Phụ cấp " + allowanceType + " (Cột " + allowanceCol + "): " + allowanceValue + " VNĐ");
//            }
//        }
//        if (allowances > 0) {
//            System.out.println("  Tổng phụ cấp: " + allowances + " VNĐ");
//        }
//    }
//}
