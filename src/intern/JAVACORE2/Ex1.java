package intern.JAVACORE2;

import java.util.ArrayList;
import java.util.Arrays;

public class Ex1 {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        // 1       add into arraylist
        arrayList.add(1);
        arrayList.add(3);
        arrayList.add(5);
        arrayList.add(7);
        arrayList.add(9);
        // 2 display
        displayArrayList(arrayList);

        // 3. calculate the sum
        calculateSum(arrayList);

        // 4. find the max and min
        findMaxAndMin(arrayList);

        // 5. remove in array list
        int index = 1;
        removeEle(arrayList, index);

        //6. check existing element
        existingEle(arrayList,index);
    }

    private static void existingEle(ArrayList<Integer> arrayList, int index) {
        System.out.println(arrayList.contains(index));
    }

    private static void removeEle(ArrayList<Integer> arrayList, int index) {
        arrayList.remove(index);
        displayArrayList(arrayList);
    }

    private static void findMaxAndMin(ArrayList<Integer> arrayList) {
        int min = 0;
        int max = 0;
        Arrays.sort(new ArrayList[]{arrayList});
        System.out.println("Min "+ arrayList.get(0));
        System.out.println("Max "+ arrayList.get(arrayList.size() -1));
    }

    private static void calculateSum(ArrayList<Integer> arrayList) {
        int sum = 0;
        for(Integer i : arrayList){
            sum += i;
        }
        System.out.println("total sum "+sum);
    }

    public static void displayArrayList(ArrayList<Integer> arr){
        for(Integer i : arr){
            System.out.print(i+" ");
        }
    }


}
