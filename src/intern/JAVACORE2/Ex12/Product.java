package intern.JAVACORE2.Ex12;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Product {
    private String name;
    private String code;
    private double price;
    private LocalDate manufactureDate;

    public Product(String name, String code, double price, LocalDate manufactureDate) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.manufactureDate = manufactureDate;
    }

    // Getter
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    @Override
    public String toString() {
        return String.format("Mã: %s, Tên: %s, Giá: %.2f, Ngày sản xuất: %s",
                code, name, price, manufactureDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
