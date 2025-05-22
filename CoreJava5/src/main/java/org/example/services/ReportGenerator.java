package org.example.services;




import org.example.model.Employee;
import org.example.model.Shift;
import org.example.model.WorkDay;
import org.example.model.WorkSummary;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class ReportGenerator {

    public String generateReport(WorkSummary summary) {
        StringBuilder report = new StringBuilder();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        report.append("BÁO CÁO CHẤM CÔNG\n");
        report.append("=================\n\n");

        // Thông tin định nghĩa ca làm việc
        report.append("ĐỊNH NGHĨA CA LÀM VIỆC:\n");
        report.append("----------------------\n");
        for (Map.Entry<String, Shift> entry : summary.getShiftDefinitions().entrySet()) {
            report.append(String.format("%-5s: %s/giờ\n",
                    entry.getKey(),
                    currencyFormat.format(entry.getValue().getRate())
            ));
        }
        report.append("\n");

        // Báo cáo theo từng nhân viên
        for (Employee employee : summary.getEmployees()) {
            report.append(String.format("NHÂN VIÊN: %s (%s)\n", employee.getName(), employee.getId()));
            report.append("----------------------------------------\n");

            // Thông tin từng ngày làm việc
            for (Map.Entry<String, WorkDay> entry : employee.getWorkDays().entrySet()) {
                String date = entry.getKey();
                WorkDay workDay = entry.getValue();

                report.append(String.format("Ngày %s:\n", date));
                report.append(String.format("  - Tổng số giờ: %.1f\n", workDay.getTotalHours()));
                report.append("  - Chi tiết ca làm việc:\n");

                for (Map.Entry<String, Shift> shiftEntry : workDay.getShifts().entrySet()) {
                    Shift shift = shiftEntry.getValue();
                    report.append(String.format("    + %s: %.1f giờ (%s)\n",
                            shift.getCode(),
                            shift.getHours(),
                            currencyFormat.format(shift.calculateAmount())
                    ));
                }

                report.append(String.format("  - Tổng tiền trong ngày: %s\n",
                        currencyFormat.format(workDay.getTotalAmount())
                ));
                report.append("\n");
            }

            // Tổng lương
            report.append(String.format("TỔNG LƯƠNG: %s\n",
                    currencyFormat.format(employee.getTotalSalary())
            ));
            report.append(String.format("TỔNG LƯƠNG (BÁO CÁO): %s\n",
                    currencyFormat.format(employee.getReportedTotalSalary())
            ));
            report.append("\n");

            // So sánh
            double difference = employee.getTotalSalary() - employee.getReportedTotalSalary();
            if (Math.abs(difference) > 0.01) {
                report.append(String.format("CHÊNH LỆCH: %s (%s%%)\n",
                        currencyFormat.format(difference),
                        String.format("%.2f", difference / employee.getReportedTotalSalary() * 100)
                ));
            } else {
                report.append("CHÊNH LỆCH: Không có\n");
            }

            report.append("========================================\n\n");
        }

        return report.toString();
    }
}
