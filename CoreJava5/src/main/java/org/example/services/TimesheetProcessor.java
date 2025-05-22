package org.example.services;




import org.example.exception.TimesheetException;
import org.example.model.Employee;
import org.example.model.WorkSummary;

public class TimesheetProcessor {

    private final ExcelReader excelReader;

    public TimesheetProcessor() {
        this.excelReader = new ExcelReader();
    }



    public WorkSummary processTimesheet(String filePath) throws TimesheetException {
        // Đọc dữ liệu từ file Excel
        WorkSummary summary = excelReader.readTimesheet(filePath);

        // Kiểm tra tính nhất quán của dữ liệu
        validateData(summary);

        return summary;
    }

    private void validateData(WorkSummary summary) throws TimesheetException {
        for (Employee employee : summary.getEmployees()) {
            // Kiểm tra tổng lương tính toán và tổng lương báo cáo
            double calculatedTotal = employee.getTotalSalary();
            double reportedTotal = employee.getReportedTotalSalary();

            // Cho phép sai số nhỏ do làm tròn
            if (Math.abs(calculatedTotal - reportedTotal) > 1.0) {
                throw new TimesheetException(
                        "Tổng lương tính toán (" + calculatedTotal + ") khác với tổng lương báo cáo (" +
                                reportedTotal + ") cho nhân viên " + employee.getName()
                );
            }
        }
    }
}
