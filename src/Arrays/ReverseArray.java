package Arrays;

import java.util.Arrays;

/**
 * Create a Java program that:
 * <p>
 * Takes an array of integers as input.
 * Reverses the array in-place without using another array.
 * Returns and displays the reversed array.
 * Example 1:
 * Input: [1, 2, 3, 4, 5]
 * Output: [5, 4, 3, 2, 1]
 */

public class ReverseArray {
    public static void main(String[] args) {
        // Step 1 initial the array
        int[] array = {1, 2, 3, 4, 5};

        reverseArray(array);

        System.out.println("Reversed array: " + Arrays.toString(array));
    }

    private static void reverseArray(int[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            // swap the element at the right pointers
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;

            // mover the pointers towar
            left++;
            right--;
        }
    }

}
