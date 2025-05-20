package intern.algorithm.level2;

import java.util.Scanner;

//Ex 2

public class LongestWordOfTheList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the length of the list: ");
        int length = sc.nextInt();
        sc.nextLine();
        String[] list = new String[length];
        for(int i = 0; i < length;i++){
            System.out.println("Enter the word ");
            list[i] = sc.nextLine();
        }

        int longest = list[0].length();
        String longestWord = list[0];
        for(String s : list){
            if(s.length() > longest){
                longestWord = s;
                longest = s.length();
            }
        }
        System.out.println("The longest word is :"+ longestWord);
    }
}
