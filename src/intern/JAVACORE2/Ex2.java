package intern.JAVACORE2;

import java.util.HashSet;

public class Ex2 {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();


        // 1. Add countries
        addCountries(set, "Vietnam", "Japan", "Brazil", "France");

        // 2. Display set
        displaySet(set);

        //3. check existing element
        String element = "Vietnam";
        System.out.println(checkExisting(set, element));

        // 4. remove the specific element
        removeElement(set, element);

        // 5. calculate the countries
        calculation(set);
    }

    private static void calculation(HashSet<String> set) {
        System.out.println("There are "+set.size()+" countries in the set");
    }

    private static void removeElement(HashSet<String> set, String element) {
        if (checkExisting(set, element)) {
            set.remove(element);
            System.out.println("removed");
            displaySet(set);
            System.out.println();
        } else {
            System.out.println("No element found");
        }

    }

    private static boolean checkExisting(HashSet<String> set, String element) {
        for (String str : set) {
            if (str.toLowerCase().equals(element.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private static void displaySet(HashSet<String> set) {
        for (String str : set) {
            System.out.print(str + " ");
        }
    }

    private static void addCountries(HashSet<String> set, String... str) {
        for (String countries : str) {
            set.add(countries);
        }
    }


}
