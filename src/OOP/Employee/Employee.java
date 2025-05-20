package OOP.Employee;

// hra da, gross

public class Employee {

    private final double basic;

    public Employee(double basic){
        this.basic = basic;
    }

    public double hra(double basic){
        if (basic == 0){
            return 0;
        }
        return basic * 0.10;
    }

    public double da(double basic){
        return basic * 0.08;
    }

    public double grossSalary(double basic){
        return hra(basic) + da(basic) + basic;
    }

    public double tax(double basic){
        return 0.05 * grossSalary(basic);
    }

    public double netSalary(double basic){
        return grossSalary(basic) - tax(basic);
    }

    public double getBasic() {
        return basic;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "basic=" + basic +
                '}';
    }


}
