package intern.algorithm.level2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Ex4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the length of the list: ");
        int length = sc.nextInt();
        sc.nextLine();
        int[] list = new int[length];
        for (int i = 0; i< length;i++){
            System.out.println("Enter the number: ");
            list[i] = sc.nextInt();
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i = 0; i< length; i++){
            if (list[i] % 3 == 0 && list[i] % 5 == 0){
                arrayList.add(list[i]);
            }
        }
        System.out.println("The number are divisible for both 3 and 5 is: "+arrayList.toString());
    }
}
