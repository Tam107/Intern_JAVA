package intern.algorithm.level3;

public class Ex5 {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 7, 8, 20};
        int findTheSmallest = findSmallest(numbers);
        System.out.println(findTheSmallest);
    }

    private static int findSmallest(int[] numbers) {

        int smallest = 1;

        for(int num : numbers){
            if (num > smallest){
                break;
            }
            smallest += num;
        }
        return smallest;

    }
}
