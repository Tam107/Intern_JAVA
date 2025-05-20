package String;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Given a string, the task is to find the first character
 * that does not repeat in the entire string. If no such character exists,
 * the program should indicate that no non-repeated character was found.
 * <p>
 * Example 1:
 * Input: "swiss"
 * Output: 'w' (The first non-repeated character is 'w')
 * Example 2:
 * Input: "programming"
 * Output: 'p' (The first non-repeated character is 'p')
 * Example 3:
 * Input: "aabbcc"
 * Output: No non-repeated character found.
 */

public class FirstNonRepeatChar {
    public static void main(String[] args) {
        String input = "swiss";
        char result = findFirstNonRepeatedChar(input);

//        if (result != 0) {
//            System.out.println("The first non-repeated character is: " + result);
//        } else {
//            System.out.println("No non-repeated character found.");
//        }
    }

    private static char findFirstNonRepeatedCharUsingArr(String input) {
        int[] charCount = new int[26];

        for (char c : input.toCharArray()) {
            charCount[c - 'a']++;
        }

        // find first character with count 1
        for (char c : input.toCharArray()) {
            if (charCount[c - 'a'] == 1) {
                return c;
            }
        }

        return '\0'; // Return null character if no non-repeated character is found
    }

    private static char findFirstNonRepeatedChar(String input) {
        Map<Character, Integer> charCountMap = new LinkedHashMap<>();

        // Count the occurrences of each character
        for (char c : input.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        // find  first non-repeated character
        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            System.out.println("entry " + entry);
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        return '\0'; // Return null character if no non-repeated character is found
    }
}
