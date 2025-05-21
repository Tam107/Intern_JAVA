package intern.algorithm.level4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Ex9 {
    public static int longestIncreasingSubsequence(List<Integer> nums) {
        if (nums == null || nums.isEmpty()) {
            return 0;
        }

        int n = nums.size();
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int maxLength = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums.get(i) > nums.get(j) && nums.get(i) - nums.get(j) == 1) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        List<Integer> input1 = Arrays.asList(1, 2, 3, 8, 6, 3);


        System.out.println(longestIncreasingSubsequence(input1)); // Output: 3
    }
}
