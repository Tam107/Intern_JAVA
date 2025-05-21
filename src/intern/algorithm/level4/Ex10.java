package intern.algorithm.level4;

import java.util.Arrays;
import java.util.List;

public class Ex10 {
    public static void main(String[] args) {
        List<String> testStrings = Arrays.asList(
                "programming in java is fun",
                "java programming is interesting",
                "algorithms are important",
                "data structures and algorithms"
        );

        int k = 4;
        String[] result = findLargestOverlap(testStrings, k);
    }

    private static String[] findLargestOverlap(List<String> testStrings,int k) {
        if (testStrings == null || testStrings.size() < 2 || k < 1){
            return new String[]{"","",""};
        }

        String string1 = "";
        String string2 = "";
        String largestCommonString ="";
        int largestOverlap = 0;
        for(int i = 0; i< testStrings.size(); i++){
            for (int j = i + 1; j< testStrings.size();j++){
                String s1 = testStrings.get(i);
                String s2 = testStrings.get(j);

                // find the largest common substring of at least k
                String commonSubString = findLongestCommonSubstring(s1, s2, k);
                if (commonSubString.length() > largestOverlap){
                    largestOverlap = commonSubString.length();
                    largestCommonString = commonSubString;
                    string1 = s1;
                    string2 = s2;
                }
            }
        }
        return new String[]{string1, string2, largestCommonString};
    }

    /**
     * Find the longest common substring between two strings that is at least k characters long.
     *
     * @param s1 First string
     * @param s2 Second string
     * @param k Minimum length of substring
     * @return The longest common substring, or empty string if none exists
     */
    private static String findLongestCommonSubstring(String s1, String s2, int k) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        int maxLength = 0;
        int endIndex = 0;

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                    if (dp[i][j] > maxLength && dp[i][j] >= k) {
                        maxLength = dp[i][j];
                        endIndex = i;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        return maxLength >= k ? s1.substring(endIndex - maxLength, endIndex) : "";
    }

}
