package org.example.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shift {
    private String shiftName;
    private double hours;
    private double amount;


}
