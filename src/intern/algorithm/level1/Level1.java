package intern.algorithm.level1;

import java.util.Scanner;

public class Level1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();
//        1.1 display sum
        displaySum(num1, num2);

//        1.2 display String's length
        String input = sc.nextLine();
        displayStringLength(input);

//        1.3 display the square
        displaySquareCalculation(num1);

//        1.4
        System.out.println("Enter the number for array's length: ");
        int arrLength = sc.nextInt();
        int[] arr = new int[arrLength];
        for (int i = 0; i< arr.length;i++){
            System.out.println("Enter the number: ");
            arr[i] = sc.nextInt();
        }
        System.out.println("The largest number in the array is "+displayTheLargestNum(arr));

    }

//    1.1
    public static void displaySum(int num1, int num2){
        int total = num1+num2;
        System.out.println("The sum of two numbers is: "+total);
    }

//    1.2
    public static void displayStringLength(String input){
        System.out.println("The String's length is "+input.length());
    }

//    1.3 calculate square
    public static void displaySquareCalculation(int num){
        System.out.println("The square of number "+num+" is "+(num * num));
    }

//    1.4 take list of numbers as input and find the largest
    public static int displayTheLargestNum(int[] arr){
        if (arr.length < 1){
            return 0;
        }
        int max = arr[0];
        for (int i = 0; i< arr.length;i++){
            if (arr[i] > max){
                max =  arr[i];
            }
        }
        return max;
    }


}
