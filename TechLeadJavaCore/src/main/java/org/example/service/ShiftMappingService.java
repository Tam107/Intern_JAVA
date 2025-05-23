package org.example.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShiftMappingService {

    public Map<Integer, List<Integer>> getDayColumnRanges(Row dayRow, int totalSalaryCol){
        Map<Integer, List<Integer>> colDayRange = new LinkedHashMap<>();
        int currentDay = -1;

        List<Integer> dayRange = new ArrayList<>();

        for (int i = totalSalaryCol + 1; i < dayRow.getLastCellNum(); i++){
            Cell cell = dayRow.getCell(i);
            if (cell != null){
                String cellValue = "";
                if (cell.getCellType() == CellType.NUMERIC){
                    cellValue = String.valueOf((int) cell.getNumericCellValue());
                } else if (cell.getCellType() == CellType.STRING) {
                    cellValue = cell.getStringCellValue().trim();
                }
                try{
                    int dayNumber = Integer.parseInt(cellValue);
                    if(dayNumber != currentDay){
                        if (currentDay != -1 && !dayRange.isEmpty()){
                            colDayRange.put(currentDay, new ArrayList<>(dayRange));
                            dayRange.clear();
                        }
                        currentDay = dayNumber;
                    }
                    dayRange.add(i);
                }catch (NumberFormatException e){
                    if (currentDay != -1){
                        dayRange.add(i);
                    }
                }
            }
        }
        if (!dayRange.isEmpty()){
            colDayRange.put(currentDay, dayRange);
        }

        return colDayRange;
    }

    public Map<String, Double> getShiftsRate(Row row, Row shiftCodeRow, int totalSalaryColumn) {
        Map<String, Double> shiftsRate = new LinkedHashMap<>();
        for (int j = 3; j < totalSalaryColumn; j++) {
            String shiftName = shiftCodeRow.getCell(j).getStringCellValue().trim();
            if (shiftName != null && !shiftName.equalsIgnoreCase("$")) {
                if (shiftName.contains("WK")) {
                    shiftsRate.put(shiftName, row.getCell(totalSalaryColumn - 1).getNumericCellValue());
                } else {
                    shiftsRate.put(shiftName, row.getCell(j + 1).getNumericCellValue());
                }
            }
        }
        return shiftsRate;
    }
}
