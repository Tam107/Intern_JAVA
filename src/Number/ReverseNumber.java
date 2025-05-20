package Number;

public class ReverseNumber {
    public static void main(String[] args) {
        int num = 12345;

        System.out.println("reverse num: " + reverseNumber(num));
    }

    private static int reverseNumber(int num) {
        int tmp = 0;
        while (num > 0) {
            tmp *= 10 ;
            tmp += num % 10;
            num /= 10;
        }

        return tmp;
    }
}
