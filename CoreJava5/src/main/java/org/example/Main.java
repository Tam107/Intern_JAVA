//package org.example;
//
//import org.example.model.Employee;
//import org.example.services.ExcelAnalyzerService;
//import org.example.services.ExcelDataChecker;
//
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args) {
//        try {
//            String filePath = "src/main/resources/BangCong.xlsx";
//            ExcelDataChecker checker = new ExcelDataChecker();
//            ExcelAnalyzerService analyzerService = new ExcelAnalyzerService(checker);
//
//            // Đọc và phân tích dữ liệu từ file Excel
//            Map<String, List<Employee>> sheetEmployees = analyzerService.analyzeExcel(filePath);
//
//            // Hiển thị thông tin nhân viên
//            for (Map.Entry<String, List<Employee>> entry : sheetEmployees.entrySet()) {
//                System.out.println("=== " + entry.getKey() + " ===");
//                for (Employee employee : entry.getValue()) {
//                    System.out.println(employee);
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Lỗi khi phân tích file Excel: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}