package intern.algorithm.level3;

import java.util.Arrays;

public class Ex3 {
    public static void main(String[] args) {

        int[] arr = {3, 10, 2, 1, 20};


        int length = findLongestIncreasingSubsequence(arr);
        System.out.println("Độ dài của chuỗi con tăng dần dài nhất là: " + length);
    }

    public static int findLongestIncreasingSubsequence(int[] arr) {
        int n = arr.length;
        // Tạo mảng dp để lưu độ dài của LIS tại mỗi vị trí
        int[] dp = new int[n];

        // Khởi tạo tất cả các phần tử trong dp là 1 (mỗi phần tử là LIS)
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
        }

        // Tính LIS cho từng phần tử
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j] && dp[i] < dp[j] + 1) {
                    int d = dp[j] +1;
                    dp[i] = dp[j] + 1;
                }
            }
        }

        // Tìm độ dài lớn nhất trong mảng dp
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }
}
