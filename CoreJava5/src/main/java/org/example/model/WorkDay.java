package org.example.model;


import java.util.HashMap;
import java.util.Map;

public class WorkDay {
    private String date;
    private Map<String, Shift> shifts;
    private double totalHours;
    private double totalAmount;

    public WorkDay(String date) {
        this.date = date;
        this.shifts = new HashMap<>();
        this.totalHours = 0.0;
        this.totalAmount = 0.0;
    }

    public void addShift(Shift shift) {
        shifts.put(shift.getCode(), shift);
        totalHours += shift.getHours();
        totalAmount += shift.calculateAmount();
    }

    // Getters
    public String getDate() {
        return date;
    }

    public Map<String, Shift> getShifts() {
        return shifts;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
