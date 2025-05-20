package intern.algorithm.level2;

import java.util.Arrays;
import java.util.Scanner;

//Ex 1

public class SecondLargest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the length of arr: ");
        int length = sc.nextInt();
        sc.nextLine();
        int[] arr = new int[length];
        for(int i = 0; i< length;i++){
            arr[i] = sc.nextInt();
        }

        Arrays.sort(arr);
        System.out.println("The second largest of the list is: "+arr[length-2]);
    }
}
