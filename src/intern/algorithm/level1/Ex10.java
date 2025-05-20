package intern.algorithm.level1;

import java.util.Scanner;

public class Ex10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the length: ");
        int length = sc.nextInt();
        sc.nextLine();
        String[] list = new String[length];
        for(int i = 0;i < length;i++){
            System.out.println("Enter the word: ");
            list[i] = sc.nextLine();
        }
        sc.close();
        int count = 0;
        for(int i = 0; i< length; i++){
            if(list[i] != null && list[i].contains("a")){
                count++;
            }
        }
        System.out.println("The number of words contain letter a is: "+count);
    }
}
