package intern.algorithm.level2;

public class Ex5 {
    public static void main(String[] args) {
        int[] arr = {-2, -3, 4, -1, -2, 1, 5, -3};
        System.out.println("Max sum of subarray: "+maxSubarraySum(arr));
    }

    public static int maxSubarraySum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0; // Handle edge case for empty or null array
        }

        int maxCurrent = nums[0];
        int maxGlobal = nums[0];

        for (int i = 1; i < nums.length; i++) {
            maxCurrent = Math.max(nums[i], maxCurrent + nums[i]);
            if (maxCurrent > maxGlobal) {
                maxGlobal = maxCurrent;
            }
        }

        return maxGlobal;
    }
}
