package intern.algorithm.level3;

import java.util.Scanner;

public class Ex7 {
    public static void main(String[] args) {
        String s = "A man a plan a canal Panama";
        String[] s1 = s.split("\\s");
        StringBuilder newStr = new StringBuilder();
        String finalStr = " ";
        for(int i = 0; i< s1.length;i++){
            newStr.append(s1[i].toLowerCase());
            if (checkPalindrome(newStr.toString())){
                finalStr = newStr.toString();
            }
        }
        System.out.println("The final palindrome: "+finalStr);

    }

    public static boolean checkPalindrome(String input){

        StringBuilder tmp = new StringBuilder();
        tmp.append(input);
        tmp.reverse();

        return input.equals(tmp.toString());
    }
}
