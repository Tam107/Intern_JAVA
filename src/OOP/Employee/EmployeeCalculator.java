package OOP.Employee;

public class EmployeeCalculator {
    public static void main(String[] args) {
        Employee employee = new Employee(25000);
        double basic = employee.getBasic();

        System.out.println("Employee break down: ");

        System.out.println("Basic "+employee.getBasic());
        System.out.println("HRA: "+employee.hra(basic));
        System.out.println("DA: "+ employee.da(basic));
        System.out.println("Gross Salary: "+employee.grossSalary(basic));
        System.out.println("Tax: "+employee.tax(basic));
        System.out.println("Net Salary: "+employee.netSalary(basic));
    }
}
