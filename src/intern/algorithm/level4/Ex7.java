package intern.algorithm.level4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Ex7 {
    public static void main(String[] args) {
        List<String> test1 = Arrays.asList("the quick brown fox", "the quick", "brown fox jumps", "the");
        List<String> test2 = Arrays.asList("a b c", "a b", "a b c d", "a");
        List<String> test3 = Arrays.asList("", "word", "word word", "a b a b");

        System.out.println(sortByDistinctWords(test1));
        System.out.println(sortByDistinctWords(test2));
        System.out.println(sortByDistinctWords(test3));
    }

    private static List<String> sortByDistinctWords(List<String> strings) {
        if (strings == null){
            return Collections.emptyList();
        }

        return strings.stream()
                .sorted((s1, s2)->{
                    int distinctWords1 = countDistinctWords(s1);
                    int distinctWords2 = countDistinctWords(s1);

                    if (distinctWords1 != distinctWords2){
                        return Integer.compare(distinctWords2, distinctWords1);
                    }
                    if (s1.length() != s2.length()){
                        return Integer.compare(s2.length(), s1.length());
                    }
                    return s1.compareTo(s2); // return equals or greater or less 0
                }).collect(Collectors.toList());
    }

    private static int countDistinctWords(String s) {
        if (s == null || s.trim().isEmpty()){
            return 0;
        }
        String[] words = s.trim().toLowerCase().split("\\s+");
        return (int) Arrays.stream(words)
                .filter(word -> !word.isEmpty())
                .distinct().count();
    }
}
