package intern.JAVACORE2;

import java.util.HashMap;
import java.util.Map;

public class Ex3 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        // 1. Thêm các cặp key-value
        System.out.println("Adding people: Alice (25), Bob (30), Charlie (22), Diana (28)");
        addPerson(map, "Alice", 25);
        addPerson(map, "Bob", 30);
        addPerson(map, "Charlie", 22);
        addPerson(map, "Diana", 28);

        //2. display
        displaySet(map);

        // 3
        String name = "Bob";
        findAgeByName(map, name);

        //4.
        removePerson(map, name);

        //5.check existing person
        checkExist(map, name);
        checkExist(map, "Diana");
    }

    private static void checkExist(HashMap<String, Integer> map, String name) {
        if (map.containsKey(name)){
            System.out.println(name+" Existing in the map");
        }else {
            System.out.println("Not existing");
        }
    }

    private static void removePerson(HashMap<String, Integer> map, String name) {
        if (map.containsKey(name)){
            map.remove(name);
            System.out.println("4. Removed "+name);
            displaySet(map);
        }
        else {
            System.out.println("Cannot found");
        }
    }

    private static void findAgeByName(HashMap<String, Integer> map, String name) {
        if (map.containsKey(name)){
            System.out.println("The age of "+name+" is: "+map.get(name));
        }
        else {
            System.out.println("Cannot found");
        }
    }

    private static void displaySet(HashMap<String, Integer> map) {
        for (Map.Entry<String, Integer> arr : map.entrySet()){
            String key = arr.getKey();
            Integer value = arr.getValue();
            System.out.println("Key: "+key+" value: "+value);
        }
    }

    private static void addPerson(HashMap<String, Integer> map, String name, int i) {
        map.put(name, i);
    }

}
