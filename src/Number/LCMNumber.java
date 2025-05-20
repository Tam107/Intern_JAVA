package Number;

import java.util.Scanner;

/**
 * The Least Common Multiple (LCM) of two numbers is the smallest number
 * that is a multiple of both. Finding the LCM is a common problem in mathematics
 * and computer science,
 * often solved using the Greatest Common Divisor (GCD) as part of the solution.
 */
public class LCMNumber {
    public static void main(String[] args) {
        int num1 = 15;
        int num2 = 20;
        int lcm = lcm(num1, num2);
        System.out.println("LCM "+lcm);
    }

    private static int lcm(int num1, int num2) {


        return (num1 * num2) / gcd(num1, num2);
    }

    private static int gcd(int num1, int num2) {
        if (num2 == 0) {
            return num1;
        }

        return gcd(num2, num1 % num2);
    }
}
