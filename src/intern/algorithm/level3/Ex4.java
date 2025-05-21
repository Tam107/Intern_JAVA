package intern.algorithm.level3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Ex4 {
    public static void main(String[] args) {
        String[] strings = {"hello", "world", "foobar", "barfoo", "he", "llo"};

        String[] result = findStringsWithLargestOverlap(strings);

        //print the result
        System.out.println("Hai chuỗi có nhiều ký tự trùng nhau nhất là: " + Arrays.toString(result));

    }

    private static String[] findStringsWithLargestOverlap(String[] strings) {
        int maxOverlap = 0;
        String[] result = new String[2];

        for (int i = 0; i < strings.length; i++) {
            for (int j = i + 1; j < strings.length; j++) {
                int overlap = countCommonCharacters(strings[i], strings[j]);
                if (overlap > maxOverlap) {
                    maxOverlap = overlap;
                    result[0] = strings[i];
                    result[1] = strings[j];
                }
            }
        }
        return result;
    }

    private static int countCommonCharacters(String s1, String s2) {
        // Set for saving character with first string
        Set<Character> set1 = new HashSet<>();
        for(char c : s1.toCharArray()){
            set1.add(c);
        }
        int count = 0;
        for(char c : s2.toCharArray()){
            if (set1.contains(c)){
                count++;
            }
        }
        return count;
    }
}
