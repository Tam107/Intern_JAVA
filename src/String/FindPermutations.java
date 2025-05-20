package String;

/**
 * Create a Java program that:
 *
 * Takes a string as input.
 * Finds and displays all the permutations(hoan vi) of that string.
 * Example 1:
 * Input: "ABC"
 * Output: ABC, ACB, BAC, BCA, CAB, CBA
 * Example 2:
 * Input: "AB"
 * Output: AB, BA
 * */

public class FindPermutations {

    public static void main(String[] args) {
        String input = "ABC";
        System.out.println("Permutations of " + input + ":");
        findPermutations(input, "");
    }

    private static void findPermutations(String input, String prefix) {
        if(input.isEmpty()){
            System.out.println(prefix);
        }else {
            for(int i = 0;i < input.length();i++){
                char ch = input.charAt(i);

                String remaining = input.substring(0,i) + input.substring(i + 1);

                findPermutations(remaining, prefix + ch);
            }
        }
    }
}
