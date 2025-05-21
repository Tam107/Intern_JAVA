package intern.JAVACORE2;

import java.util.HashSet;
import java.util.Scanner;

public class Ex14 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();

        System.out.println("Enter the number of elements of set 1:");
        int n1 = Integer.parseInt(sc.nextLine());
        System.out.println("Enter the element: ");
        for(int i = 0; i< n1; i++){
            int num = Integer.parseInt(sc.nextLine());
            set1.add(num);
        }

        System.out.println("Enter the number of elements of set 12:");
        int n2 = Integer.parseInt(sc.nextLine());
        System.out.println("Enter the element: ");
        for(int i = 0; i< n2; i++){
            int num = Integer.parseInt(sc.nextLine());
            set2.add(num);
        }
        HashSet<Integer> intersection  = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("set 1: "+set1);
        System.out.println("set 2: "+set2);
        System.out.println("Intersection: "+intersection);
    }
}
