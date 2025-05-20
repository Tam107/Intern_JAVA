package intern.algorithm.level1;

import java.util.Arrays;
import java.util.Scanner;

public class Ex8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the length of the array: ");
        int length = sc.nextInt();
        sc.nextLine();
        int[] arr = new int[length];
        for(int i = 0; i< arr.length; i++){
            System.out.println("Enter the number: ");
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);

        System.out.println("The median of the array is: "+findMedian(arr));

    }

    private static int findMedian(int[] arr) {

        if(arr.length % 2 == 0){
            return (arr[arr.length/2 - 1] + arr[arr.length / 2])/2;
        }

        return arr[arr.length / 2];

    }
}
