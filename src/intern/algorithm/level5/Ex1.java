package intern.algorithm.level5;

import java.util.Arrays;

public class Ex1 {
    public static void main(String[] args) {
        String[] arr = {"a", "b", "c", "d"};
        int length = arr.length -1;
        for(int i = 0; i < arr.length / 2;i++){
            String tmp = arr[i];
            arr[i] = arr[length];
            arr[length] = tmp;
            length--;
        }
        System.out.println(Arrays.toString(arr));
    }
}
