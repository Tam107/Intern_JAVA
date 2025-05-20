package intern.algorithm.level1;

import java.util.Scanner;

public class Ex5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the length of the Strings: ");
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] arr = new String[n];

        for (int i = 0; i < arr.length;i++){
            System.out.println("Enter String: ");
            arr[i] = scanner.nextLine();
        }

        displayShortestString(arr);
    }

    private static void displayShortestString(String[] arr) {
        if (arr.length < 1){
            return;
        }

        String shortest = arr[0];
        for(int i = 0; i< arr.length;i++){
            if (arr[i].length() < shortest.length()){
                shortest = arr[i];
            }
        }
        System.out.println("The shortest string is: "+shortest);
    }
}
