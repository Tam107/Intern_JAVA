package org.example;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.model.Employee;
import org.example.service.EmployeeProcessingService;
import org.example.service.ExcelReaderService;
import org.example.service.ShiftMappingService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Main1 {


    private static final ExcelReaderService excelReaderService = new ExcelReaderService();
    private static final EmployeeProcessingService employeeProcessingService = new EmployeeProcessingService();
    private static final ShiftMappingService shiftMappingService = new ShiftMappingService();

    public static void main(String[] args) throws IOException {

        String excelFilePath = "src/main/resources/BangCong.xlsx"; // Thay đường dẫn file Excel cho phù hợp

        try(InputStream fs = new FileInputStream(new File(excelFilePath))){
//            List<Employee> employees = new ArrayList<>();


            Workbook workbook = excelReaderService.getWorkbook(fs, excelFilePath);
            Sheet sheet = workbook.getSheetAt(0);

            List<Employee> employees = employeeProcessingService.processEmployee(sheet, shiftMappingService);

            // Print results
            for (Employee employee : employees) {
                System.out.println(employee.toString());
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }


}