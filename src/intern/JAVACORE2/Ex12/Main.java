package intern.JAVACORE2.Ex12;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", "SP001", 1500.00, LocalDate.of(2023, 5, 10)));
        products.add(new Product("Điện thoại", "SP002", 800.00, LocalDate.of(2024, 1, 15)));
        products.add(new Product("Máy tính bảng", "SP003", 800.00, LocalDate.of(2023, 12, 20)));
        products.add(new Product("Tai nghe", "SP004", 200.00, LocalDate.of(2022, 8, 5)));

        System.out.println("Sort by name: ");
        Collections.sort(products, Comparator.comparing(Product::getName));
        products.forEach(System.out::println);

        System.out.println("Sort by price: ");
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
        products.forEach(System.out::println);

        System.out.println("Sort by Manufacturer date");
        Collections.sort(products, Comparator.comparing(Product::getManufactureDate));
        products.forEach(System.out::println);

        System.out.println("Sort by price and date: ");
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice).thenComparing(Product::getManufactureDate));
        products.forEach(System.out::println);
    }
}
