package Arrays;

/**Finding the largest number in an array is a common programming task, often used in data analysis and processing. This guide will show you how to create a Java program that identifies and displays the largest number in a given array.

 Problem Statement
 Create a Java program that:

 Takes an array of integers as input.
 Finds and displays the largest number in the array.
 Example 1:
 Input: {3, 5, 7, 2, 8}
 Output: The largest number is 8
* */

public class FindTheLargestNumber {
    public static void main(String[] args) {
        int[] array = {3, 5, 7, 2, 8};
        int largestNumber = findLargestNumber(array);
    }

    private static int findLargestNumber(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }

        int largest = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > largest) {
                largest = array[i];
            }
        }
        System.out.println("The largest number is: " + largest);
        return largest;
    }
}
