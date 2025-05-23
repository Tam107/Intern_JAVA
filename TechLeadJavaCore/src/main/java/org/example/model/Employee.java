package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String id;
    private String name;
    private double totalSalary;
    private double calculatedTotalSalary;
    private Map<String, Double> shiftsRate;
    private List<WorkingDay> workingDays;

    public Employee(String id, String name, double totalSalary) {
        this.id = id;
        this.name = name;
        this.totalSalary = totalSalary;
        this.shiftsRate = new LinkedHashMap<>();
        this.workingDays = new ArrayList<>();
    }
    public void addWorkingDay(WorkingDay workingDay){
        workingDays.add(workingDay);
        calculatedTotalSalary += workingDay.getTotalAmount();
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Employee: ").append(id).append(" - ").append(name).append("\n");
        sb.append("Total Salary: ").append(String.format("%.2f", totalSalary)).append("\n");

        for (WorkingDay workingDay : workingDays) {
            if (workingDay.getTotalHours() > 0) {
                sb.append("  Day ").append(workingDay.getDate()).append(": Total Salary Per Day=").append(workingDay.getTotalHours())
                        .append(", Shifts=").append(workingDay.getShifts()).append("\n");
            }
        }

        return sb.toString();
    }
}
