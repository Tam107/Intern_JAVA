package intern.JAVACORE2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Ex10 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<Double, Integer> scoreCount = new HashMap<>();

        // Nhập điểm số
        System.out.println("Nhập danh sách điểm số (nhập ký tự không phải số để kết thúc):");
        while (scanner.hasNext()) {
            try {
                double score = scanner.nextDouble();
                if (score >= 0 && score <= 10) {
                    scoreCount.put(score, scoreCount.getOrDefault(score, 0) + 1);
                } else {
                    System.out.println("Điểm phải từ 0 đến 10. Bỏ qua điểm " + score);
                }
            } catch (Exception e) {

                scanner.next();
                break;
            }
        }

        if (scoreCount.isEmpty()) {
            System.out.println("Danh sách điểm số rỗng!");
            scanner.close();
            return;
        }

        // Đếm số lượng sinh viên theo nhóm
        int highScoreCount = 0; // >= 8.0
        int averageScoreCount = 0; // >= 5.0 và < 8.0
        int failScoreCount = 0; // < 5.0

        for (Map.Entry<Double, Integer> entry : scoreCount.entrySet()) {
            double score = entry.getKey();
            int count = entry.getValue();

            if (score >= 8.0) {
                highScoreCount += count;
            } else if (score >= 5.0) {
                averageScoreCount += count;
            } else {
                failScoreCount += count;
            }
        }

        // Hiển thị kết quả
        System.out.println("\nPhân phối điểm số:");
        System.out.println("Số sinh viên đạt điểm cao (>= 8.0): " + highScoreCount);
        System.out.println("Số sinh viên đạt điểm trung bình (>= 5.0 và < 8.0): " + averageScoreCount);
        System.out.println("Số sinh viên trượt (< 5.0): " + failScoreCount);


        System.out.println("\nChi tiết điểm số:");
        for (Map.Entry<Double, Integer> entry : scoreCount.entrySet()) {
            System.out.printf("Điểm %.1f: %d sinh viên%n", entry.getKey(), entry.getValue());
        }


        scanner.close();
    }
}
