package intern.JAVACORE2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Ex5 {
    public static void main(String[] args) {
        int[] arr = {2,7,11,15};

        int target = 9;
        int[] result = twoSum(arr, 9);
        // In kết quả
        System.out.println("Output: [" + result[0] + ", " + result[1] + "]");
    }

    private static int[] twoSum(int[] arr, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i< arr.length;i++){
            int complement = target - arr[i];
            if (map.containsKey(complement)){
                return new int[]{map.get(complement), i};
            }
            map.put(arr[i], i);
        }
        return new int[] {};
    }
}
