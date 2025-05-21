package intern.algorithm.level4;

import java.util.Arrays;
import java.util.List;

public class Ex6 {
    public static void main(String[] args) {
        // Test cases
        List<Integer> test1 = Arrays.asList(-10, -5, 0, 1, 2, 3, 4);
        List<Integer> test2 = Arrays.asList(1, 2, 3, 4);
        List<Integer> test3 = Arrays.asList(-4, -3, -2, 1, 60);
        List<Integer> test4 = Arrays.asList(-100, -2, 0, 1);

        System.out.println(maxProductOfThree(test1)); // Expected: 200
        System.out.println(maxProductOfThree(test2)); // Expected: 24
        System.out.println(maxProductOfThree(test3)); // Expected: 720
        System.out.println(maxProductOfThree(test4)); // Expected: 200
    }

    public static long maxProductOfThree(List<Integer> numbers) {
        // Handle edge cases
        if (numbers == null || numbers.size() < 3) {
            throw new IllegalArgumentException("List must contain at least three numbers");
        }

        // Convert list to array and sort
        int[] arr = numbers.stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(arr);
        int n = arr.length;

        // Maximum product is either:
        // 1. Three largest numbers
        // 2. Two smallest numbers (if negative) and the largest number
        return Math.max(
                (long) arr[n - 1] * arr[n - 2] * arr[n - 3],
                (long) arr[0] * arr[1] * arr[n - 1]
        );
    }
}
