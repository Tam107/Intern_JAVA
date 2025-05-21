package intern.algorithm.level5;

import java.util.Arrays;

public class Ex6 {
    public static void main(String[] args) {
        String s1 = "    hello     world    ";
        String s2 = "  I    am    good      ";
        String newStr1 = trimSpace(s1);
        String newStr2 = trimSpace(s2);
        System.out.println(newStr1);
        System.out.println(newStr2);
    }

    private static String trimSpace(String s1) {
        String[] arrStr1 = s1.trim().split("\\s+");
        String newStr = "";
        for(int i = 0; i< arrStr1.length;i++){
            if (i == arrStr1.length -1){
                newStr+=arrStr1[i];
            }
            else {
                newStr+=arrStr1[i]+" ";
            }

        }
        return newStr;
    }
}
