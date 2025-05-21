package intern.algorithm.level3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ex6 {
    public static void main(String[] args) {
        List<Integer> arr1 = List.of(1,2,3);
        List<Integer> arr2 = List.of(4,5,6);

        List<Integer> combinedList = new ArrayList<>(arr1);
        combinedList.addAll(arr2);
        Collections.sort(combinedList);
        int size = combinedList.size();
        int median = 0;
        if(size % 2 == 1){
            median = combinedList.get(size / 2);
        }
        else {
            int mid1 = combinedList.get(size / 2 -1);
            int mid2 = combinedList.get(size / 2);
            median = (mid1 + mid2) /2;
        }
        System.out.println(median);
    }
}
