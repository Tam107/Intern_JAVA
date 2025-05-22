package org.example.model;



public class Shift {
    private String code;
    private double rate;
    private double hours;

    public Shift(String code, double rate) {
        this.code = code;
        this.rate = rate;
        this.hours = 0.0;
    }

    public double calculateAmount() {
        return hours * rate;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public double getRate() {
        return rate;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}
