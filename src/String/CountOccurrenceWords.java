package String;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CountOccurrenceWords {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a string: ");
        String input = scanner.nextLine();

        String[] words = input.toLowerCase().split("\\s");

        Map<String, Integer> wordCountMap = new LinkedHashMap<>();

        for (String word : words) {
            wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
        }

        System.out.println("Word frequencies: ");
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
