package intern.algorithm.level3;

import java.util.Arrays;
import java.util.Scanner;
// 3.2

public class MaximumDiffer {
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
        int maximumDiffer = arr[length -1 ] - arr[0];
        System.out.println("Maximum differ between two number is: "+maximumDiffer);
    }
}
