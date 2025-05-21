package intern.algorithm.level4;

import java.util.List;

public class Ex3 {
    public static void main(String[] args) {
        // Test cases
        List<String> test1 = List.of("abcdefg", "abcde", "abcdef", "ab", "abc");
        List<String> test2 = List.of("programming", "programmer", "program");
        List<String> test3 = List.of("hello", "world", "jelly");
        List<String> test4 = List.of("abcd", "ab", "abcd", "ab", "abcd");

        System.out.println(findLongestCommonSubstringLength(test1)); // Expected: 2
        System.out.println(findLongestCommonSubstringLength(test2)); // Expected: 7
        System.out.println(findLongestCommonSubstringLength(test3)); // Expected: 1
        System.out.println(findLongestCommonSubstringLength(test4)); // Expected: 2
    }

    public static int findLongestCommonSubstringLength(List<String> strings) {
        // Handle edge cases
        if (strings == null || strings.isEmpty()) {
            return 0;
        }
        if (strings.size() == 1) {
            return strings.get(0).length();
        }

        // Find the shortest string
        String shortest = strings.get(0);
        for (String s : strings) {
            if (s.length() < shortest.length()) {
                shortest = s;
            }
        }

        // Try all substrings of the shortest string, starting from longest
        int maxLength = 0;
        for (int len = shortest.length(); len > 0; len--) {
            // Generate all substrings of length len
            for (int start = 0; start + len <= shortest.length(); start++) {
                String substring = shortest.substring(start, start + len);
                // Check if substring exists in all strings
                boolean foundInAll = true;
                for (String s : strings) {
                    if (!s.contains(substring)) {
                        foundInAll = false;
                        break;
                    }
                }
                if (foundInAll) {
                    maxLength = len;
                    break; // Found a valid substring of this length, no need to check shorter ones
                }
            }
            if (maxLength > 0) {
                break; // Found the longest valid substring
            }
        }

        return maxLength;
    }
}
