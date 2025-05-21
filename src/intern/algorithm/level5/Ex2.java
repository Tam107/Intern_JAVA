package intern.algorithm.level5;

import java.util.Arrays;

public class Ex2 {
    public static void main(String[] args) {
        String[] arr = {"a", "b", "c", "d"};
        String[][] result = chunk(arr, 3);
        System.out.println(Arrays.deepToString(result));
    }

    private static String[][] chunk(String[] arr, int size) {

        // handle special case
        if (arr == null || arr.length == 0 || size <= 0) {
            return new String[0][];
        }

        int numChunk = (int) Math.ceil((double) arr.length / 2);
        String[][] result = new String[numChunk][];


        for (int index = 0; index < numChunk; index++) {
            int start = index * size;
            int chunkLength = Math.min(size, arr.length - start);
            // Tạo mảng con
            result[index] = Arrays.copyOfRange(arr, start, start + chunkLength);
        }

        return result;
    }
}
