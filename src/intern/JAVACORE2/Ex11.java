package intern.JAVACORE2;

import java.util.HashMap;
import java.util.Scanner;

public class Ex11 {
    public static void main(String[] args) {
        HashMap<String, String> dictionary = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Hiển thị menu
            System.out.println("\n=== Từ điển đơn giản ===");
            System.out.println("1. Thêm từ mới");
            System.out.println("2. Tra cứu từ");
            System.out.println("3. Thoát");
            System.out.print("Chọn chức năng (1-3): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            if (choice == 3) {
                System.out.println("Đã thoát chương trình.");
                break;
            }

            switch (choice) {
                case 1: // Thêm từ mới
                    System.out.print("Nhập từ: ");
                    String word = scanner.nextLine().toLowerCase();
                    if (word.trim().isEmpty()) {
                        System.out.println("Từ không được rỗng!");
                        break;
                    }
                    System.out.print("Nhập định nghĩa: ");
                    String definition = scanner.nextLine();
                    if (definition.trim().isEmpty()) {
                        System.out.println("Định nghĩa không được rỗng!");
                        break;
                    }
                    if (dictionary.containsKey(word)) {
                        System.out.println("Từ '" + word + "' đã tồn tại! Cập nhật định nghĩa.");
                    }
                    dictionary.put(word, definition);
                    System.out.println("Đã thêm/cập nhật từ '" + word + "'.");
                    break;

                case 2: // Tra cứu từ
                    System.out.print("Nhập từ cần tra cứu: ");
                    String searchWord = scanner.nextLine().toLowerCase();
                    String result = dictionary.get(searchWord);
                    if (result == null) {
                        System.out.println("Không tìm thấy từ '" + searchWord + "' trong từ điển!");
                    } else {
                        System.out.println("Từ '" + searchWord + "': " + result);
                    }
                    break;

                default:
                    System.out.println("Chức năng không hợp lệ!");
            }
        }

        scanner.close();
    }
}
