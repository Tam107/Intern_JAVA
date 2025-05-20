package Number;


import java.util.Scanner;

/*
* The Greatest Common Divisor (GCD) of two numbers is the largest positive integer
* that perfectly divides both numbers without leaving a remainder.
*  Calculating the GCD is a fundamental concept in mathematics
* and has various applications in algorithms and computational mathematics.
* This blog post will explore a Java program to find the GCD of two numbers
* using Euclid's algorithm
* , a popular and efficient method for calculating GCDs.*/
public class GDCNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the first number: ");
        int num1 =  sc.nextInt();
        System.out.println("Enter the second number: " );
        int num2 = sc.nextInt();

        // calculating and displaying the GCD
        int gcd = findGCD(num1, num2);
        System.out.println("The GCD of "+num1 + " and "+num2+ " "+gcd);
        sc.close();
    }

    private static int findGCD(int num1, int num2) {
        if (num2 == 0){
            return num1;
        }

        return findGCD(num2, num1 % num2);
    }
}
