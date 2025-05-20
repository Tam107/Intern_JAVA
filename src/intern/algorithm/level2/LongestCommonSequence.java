package intern.algorithm.level2;

import java.util.Scanner;

//Ex3

public class LongestCommonSequence {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();

        System.out.println("Find longest subsequence: "+findLCS(s1, s2));
    }

    public static String findLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // Store length of LCS for all subproblems
        int[][] lengths = new int[m + 1][n + 1];

        // Build the lengths table in bottom-up fashion
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    lengths[i][j] = 0;
                } else if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    lengths[i][j] = lengths[i - 1][j - 1] + 1;
                } else {
                    lengths[i][j] = Math.max(lengths[i - 1][j], lengths[i][j - 1]);
                }
            }
        }

        // Create a character array to store the LCS
        int lcsLength = lengths[m][n];
        char[] lcs = new char[lcsLength];

        // Start from the bottom right corner and collect characters
        int i = m, j = n;
        int index = lcsLength - 1;

        while (i > 0 && j > 0) {
            // If current characters match, they are part of LCS
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                lcs[index] = text1.charAt(i - 1);
                i--;
                j--;
                index--;
            }
            // If not, move in the direction of larger value
            else if (lengths[i - 1][j] > lengths[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return new String(lcs);
    }
}
