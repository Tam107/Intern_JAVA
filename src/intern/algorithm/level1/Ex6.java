package intern.algorithm.level1;

import java.util.Arrays;
import java.util.Scanner;

public class Ex6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the length of array:");
        int length = scanner.nextInt();
        int[] arr = new int[length];
        for (int i = 0; i< length; i++){
            System.out.println("Enter the number: ");
            arr[i] = scanner.nextInt();
        }

        Arrays.sort(arr);
        System.out.println("Display the sorted asc array: "+Arrays.toString(arr));
    }


}
