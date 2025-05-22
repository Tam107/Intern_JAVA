package org.example.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {
    private String id;
    private String name;
    private Map<String, WorkDay> workDays;
    private double totalSalary;
    private double reportedTotalSalary; // Tổng lương ghi trong file

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
        this.workDays = new HashMap<>();
        this.totalSalary = 0.0;
    }

    public void addWorkDay(String date, WorkDay workDay) {
        workDays.put(date, workDay);
        totalSalary += workDay.getTotalAmount();
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, WorkDay> getWorkDays() {
        return workDays;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setReportedTotalSalary(double reportedTotalSalary) {
        this.reportedTotalSalary = reportedTotalSalary;
    }

    public double getReportedTotalSalary() {
        return reportedTotalSalary;
    }
}