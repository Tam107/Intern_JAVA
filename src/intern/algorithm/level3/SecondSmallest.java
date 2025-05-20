package intern.algorithm.level3;

import java.util.Arrays;
import java.util.Scanner;

// 3.1

public class SecondSmallest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the length: ");
        int length = sc.nextInt();
        sc.nextLine();
        int[] arr = new int[length];
        for(int i = 0; i< length;i++){
            System.out.println("Enter the number: ");
            arr[i] = sc.nextInt();
        }
        sc.close();
        Arrays.sort(arr);
        System.out.println("The second smallest is: "+arr[1]);
    }
}
