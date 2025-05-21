package intern.algorithm.level5;

import java.util.*;
import java.util.stream.Collectors;

public class Ex7 {
    public static void main(String[] args) {
        // Tạo mảng keys
        String[] keys = {"b", "a", "c"};

        // Tạo danh sách collections
        List<Map<String, Integer>> collections = Arrays.asList(
                new HashMap<String, Integer>() {{
                    put("a", 1);
                    put("b", 1);
                    put("c", 2);
                    put("d", 4);
                    put("e", 5);
                }},
                new HashMap<String, Integer>() {{
                    put("a", 2);
                    put("b", 1);
                    put("c", 5);
                    put("d", 4);
                    put("e", 5);
                }},
                new HashMap<String, Integer>() {{
                    put("d", 4);
                    put("e", 5);
                    put("a", 22);
                    put("b", 11);
                    put("c", 51);
                }}
        );

        // Gọi hàm mapKeys

        List<Map<String, Integer>> result = mapKeys(keys, collections);

        // In kết quả
        System.out.println(result);
    }

    private static List<Map<String, Integer>> mapKeys(String[] keys, List<Map<String, Integer>> collections) {

        return collections.stream().map(
                item -> {
                    Map<String, Integer> newMap = new LinkedHashMap<>();
                    for (String key : keys) {
                        if (item.containsKey(key)) {
                            newMap.put(key, item.get(key));
                        }
                    }
                    return newMap;
                }
        ).collect(Collectors.toList());

    }
}
