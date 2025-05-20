package Math;

public class ClimbingStair {
    public static void main(String[] args) {
        int n = 3;

    }

    public static int climbStair(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;

        // initialize dynamic program array
        int[] dp = new int[n + 1]; // way to climb 1
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];


    }
}
