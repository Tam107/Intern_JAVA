package intern.JAVACORE2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Ex9 {
    public static void main(String[] args) {
        // Tạo Scanner để đọc đầu vào
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nhập đoạn văn bản (nhấn Enter để kết thúc):");
        String text = scanner.nextLine();
        if (text.trim().isEmpty()) {
            System.out.println("Văn bản rỗng! Không có từ nào để đếm.");
            scanner.close();
            return;
        }


        HashMap<String, Integer> wordFrequency = new HashMap<>();

        String[] words = text.toLowerCase()
                .replaceAll("[^a-zA-Z0-9\\s]", "") // Loại bỏ dấu câu
                .split("\\s+");

        // Đếm tần suất từ
        for (String word : words) {
            if (!word.isEmpty()) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }

        // Hiển thị kết quả
        if (wordFrequency.isEmpty()) {
            System.out.println("Không có từ hợp lệ để đếm!");
        } else {
            System.out.println("\nSố lần xuất hiện của các từ:");
            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                System.out.printf("Từ '%s': %d lần%n", entry.getKey(), entry.getValue());
            }
        }

        scanner.close();
    }
}
