package Arrays;


import java.util.*;

/**
 * Finding duplicate elements in an array is a common problem in programming, especially in data processing tasks. This guide will show you how to create a Java program that identifies and displays duplicate elements in an array.
 *
 * Problem Statement
 * Create a Java program that:
 *
 * Takes an array of integers as input.
 * Finds and displays all duplicate elements in the array.
 * Example 1:
 * Input: {1, 2, 3, 4, 2, 3, 5}
 * Output: Duplicate elements: 2, 3
 * */


public class FindDuplicateArray {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4, 2, 3, 5};
//        findDuplicates(array);
//        System.out.println("Duplicate elements: " + findDuplicates(array));
//        System.out.println("Duplicate elements: " + findDuplicates2(array));
        Set<Integer> uniqueElements = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        for (int value : array){
//            System.out.println(!uniqueElements.add(value));
            if(!uniqueElements.add(value)){ // true then the value is duplicate
                duplicates.add(value);
            }
        }
    }

    private static String findDuplicates(int[] array) {
        if (array == null || array.length == 0) {
            return "Array is empty or null";
        }
        List<Integer> duplicates = new ArrayList<>();
        for (int i = 0; i < array.length; i++){
            for(int j= i + 1; j < array.length; j++){
                if(array[i] == array[j]){
                    duplicates.add(array[i]);
                }
            }
        }
        return duplicates.toString();
    }

    private static String findDuplicates2(int[] array) {
        if (array == null || array.length == 0) {
            return null; // Return an empty array if the input is null or empty
        }

        Set<Integer> uniqueElements = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>();
        for (int value : array){
            if(!uniqueElements.add(value)){
                duplicates.add(value);
            }
        }
        return duplicates.toString(); // Return the set of duplicate elements

    }
}
