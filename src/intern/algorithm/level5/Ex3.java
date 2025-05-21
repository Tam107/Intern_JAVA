package intern.algorithm.level5;

import java.util.HashMap;

public class Ex3 {
    public static void main(String[] args) {
        int[] arr = { 1, 2, 5, 1, 7, 2, 4, 2 };
        int n = arr.length;
        removeDups(arr, n);
    }

    private static void removeDups(int[] arr, int n) {

        HashMap<Integer, Boolean> mp = new HashMap<>();
        for(int i = 0; i< n;i++){
            if(mp.get(arr[i]) == null){
                System.out.print(arr[i]+" ");
            }

            //insert the element in the hashmap
            mp.put(arr[i], true);
        }

    }
}
