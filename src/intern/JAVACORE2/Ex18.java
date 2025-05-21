package intern.JAVACORE2;

import java.util.HashSet;
import java.util.Scanner;

public class Ex18 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        HashSet<String> set1 = new HashSet<>();

        System.out.println("Enter the number of elements of set 1:");
        int n1 = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the element: ");
        for(int i = 0; i< n1; i++){
            String s = sc.nextLine();
            set1.add(s);
        }
        System.out.println(set1.size());
    }
}
