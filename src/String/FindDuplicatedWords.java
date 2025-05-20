package String;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Finding duplicate words in a string is a common task in text processing and data analysis.
 * This guide will show you how to create a Java program that identifies and displays duplicate words in a given string.
 *
 * Problem Statement
 * Create a Java program that:
 *
 * Takes a string as input.
 * Finds and displays all duplicate words in the string.
 * Example 1:
 * Input: "This is a test. This test is easy."
 * Output: Duplicate words: This, is, test
 * Example 2:
 * Input: "Java is great and Java is powerful"
 * Output: Duplicate words: Java, is*/

public class FindDuplicatedWords {

    public static void main(String[] args) {
        String input = "This is a test. This test is easy.";
        String result  = findDuplicatedWords(input);
        System.out.println(result);
    }

    private static String findDuplicatedWords(String input) {
        if (input == null || input.isEmpty()) {
            return "Input string is empty or null";
        }

        String[] str = input.toLowerCase().split("\\W");

        Map<String, Integer> map = new LinkedHashMap<>();

        for(String word : str){
            if (word.length() > 0) {
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
        }

        StringBuilder duplicates = new StringBuilder();
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            if(entry.getValue() > 1){
                duplicates.append(entry.getKey()).append(" ");
            }
        }

        return duplicates.toString();
    }
}
