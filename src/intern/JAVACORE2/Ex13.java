package intern.JAVACORE2;

import java.util.HashSet;
import java.util.Scanner;

public class Ex13 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập số lượng phần tử của mảng: ");
        int n;
        try {
            n = Integer.parseInt(scanner.nextLine());
            if (n < 0) {
                System.out.println("Kích thước mảng không hợp lệ!");
                scanner.close();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập số hợp lệ!");
            scanner.close();
            return;
        }

        // Nhập mảng
        int[] array = new int[n];
        System.out.println("Nhập các phần tử của mảng:");
        for (int i = 0; i < n; i++) {
            try {
                array[i] = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Phần tử không hợp lệ! Gán giá trị 0.");
                array[i] = 0;
            }
        }

        // Tìm phần tử trùng lặp
        HashSet<Integer> seen = new HashSet<>();
        HashSet<Integer> duplicates = new HashSet<>();

        for (int num : array) {
            if (!seen.add(num)) { // Nếu không thêm được (đã tồn tại)
                duplicates.add(num);
            }
        }

        if (duplicates.isEmpty()) {
            System.out.println("Không có phần tử trùng lặp trong mảng.");
        } else {
            System.out.println("Các phần tử trùng lặp: " + duplicates);
        }

        scanner.close();
    }

}
