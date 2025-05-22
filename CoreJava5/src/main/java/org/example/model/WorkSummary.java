package org.example.model;

import java.util.List;
import java.util.Map;

public class WorkSummary {
    private List<Employee> employees;
    private Map<String, Shift> shiftDefinitions;

    public WorkSummary(List<Employee> employees, Map<String, Shift> shiftDefinitions) {
        this.employees = employees;
        this.shiftDefinitions = shiftDefinitions;
    }

    // Getters
    public List<Employee> getEmployees() {
        return employees;
    }

    public Map<String, Shift> getShiftDefinitions() {
        return shiftDefinitions;
    }
}

