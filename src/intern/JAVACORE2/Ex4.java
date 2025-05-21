package intern.JAVACORE2;

import java.util.ArrayList;
import java.util.Collections;

public class Ex4 {
    public static void main(String[] args) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("hehe");
        arr.add("aaa");
        arr.add("bbb");
        arr.add("ccc");
        Collections.sort(arr);
        System.out.println(arr);
    }
}
