package intern.JAVACORE2;

import java.util.HashSet;
import java.util.Scanner;

public class Ex17 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        HashSet<Integer> set1 = new HashSet<>();

        System.out.println("Enter the number of elements of set 1:");
        int n1 = Integer.parseInt(sc.next());
        System.out.println("Enter the element: ");
        for(int i = 0; i< n1; i++){
            int num = Integer.parseInt(sc.next());
            set1.add(num);
        }
        System.out.println(set1);
    }
}
