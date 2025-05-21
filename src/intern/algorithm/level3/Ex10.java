package intern.algorithm.level3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ex10 {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("apple", "banana", "orange", "kiwi", "strawberry");
        List<String> sortedWords = sortByDistinctCharacters(words);
        System.out.println(sortedWords);
    }

    public static List<String> sortByDistinctCharacters(List<String> words) {
        words.sort((word1, word2) -> {
            int distinctCount1 = countDistinctCharacters(word1);
            int distinctCount2 = countDistinctCharacters(word2);
            if (distinctCount1 != distinctCount2) {
                return Integer.compare(distinctCount1, distinctCount2);
            } else {
                return 0;
            }
        });
        return words;
    }

    public static int countDistinctCharacters(String word) {
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : word.toCharArray()) {
            uniqueChars.add(c);
        }
        return uniqueChars.size();
    }
    }
