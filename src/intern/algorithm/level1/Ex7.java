package intern.algorithm.level1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ex7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> strings = new ArrayList<>();
        try{
            System.out.println("Enter the number of the strings: ");
            int n = scanner.nextInt();
            scanner.nextLine();
            if (n <= 0){
                return;
            }
            // Enter each string
            for (int i = 0; i < n; i++) {
                System.out.println("Enter string: ");
                String input = scanner.nextLine();
                // validate the input
                if (input != null && !input.trim().isEmpty()) {
                    strings.add(input);
                }
            }

            // validate empty string
            if (strings.isEmpty()) {
                System.out.println("No valid strings to sort.");
                return;
            }

            Collections.sort(strings, String.CASE_INSENSITIVE_ORDER);
            System.out.println("Alphabet order: ");
            for(String s : strings){
                System.out.print(s+" ");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
