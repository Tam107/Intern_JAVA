package org.example.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingDay {

    private String date;
    private double totalHours;
    private double totalAmount;
    private List<Shift> shifts;

    public WorkingDay(String day){
        this.date = day;
        this.totalHours = 0.0;
        this.totalAmount = 0.0;
        this.shifts = new ArrayList<>();
    }

    public void addShift(Shift shift, double hours, double amount){
        shifts.add(shift);
        totalHours += shift.getHours();
        totalHours += shift.getAmount();
    }

    public double totalHoursCal() {
        double total = 0.0;
        for (Shift shift : shifts) {
            total += shift.getHours(); // Cộng số giờ của từng ca
        }
        return total;
    }

}
