package intern.algorithm.level4;

public class Ex2 {
    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3, 4};
        int findDistinct = findDistinctSub(arr, 5);
        System.out.println(findDistinct);
    }

    private static int findDistinctSub(int[] arr, int target) {
        int count = 0;
        for(int j = 0; j < arr.length; j++){
            for (int x = j ; x < arr.length; x++){
                if (arr[j] + arr[x] == target){
                    count++;
                }
            }
        }

        return count;
    }
}
