package intern.algorithm.level5;

import java.util.*;
import java.util.stream.Collectors;

public class Ex4 {
    public static void main(String[] args) {
        List<Map<String, Integer>> input = Arrays.asList(
                new HashMap<String, Integer>() {{
                    put("x", 1);
                    put("y", 2);
                }},
                new HashMap<String, Integer>() {{
                    put("x", 2);
                    put("y", 1);
                }},
                new HashMap<String, Integer>() {{
                    put("y", 2);
                    put("x", 1);
                }}
        );

        List<Map<String, Integer>> result = uniqueArrayObject(input);
        System.out.println(result);
    }

    // java 8
    static List<Map<String, Integer>> uniqueArrayObject(List<Map<String, Integer>> arr) {
        if (arr == null || arr.isEmpty()) {
            return new ArrayList<>();
        }

        Set<String> seen = new HashSet<>();
        List<Map<String, Integer>> result = new ArrayList<>();
        for (Map<String, Integer> map : arr) {
            String normalized = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> entry.getKey() + ":" + entry.getValue())
                    .collect(Collectors.joining(","));
            if (seen.add(normalized)) {
                result.add(map);
            }
        }
        return result;
    }

    // java core
    private static List<Map<String, Integer>> uniqueArrayObj(List<Map<String, Integer>> arr) {
        if (arr == null || arr.size() == 0) {
            return new ArrayList<>();
        }
        HashSet<String> seen = new HashSet<>();
        List<Map<String, Integer>> result = new ArrayList<>();

//      Duyệt qua từng Map
        for (int i = 0; i < arr.size(); i++) {
            Map<String, Integer> map = arr.get(i);

            // Chuẩn hóa Map: sắp xếp key và tạo chuỗi key:value
            TreeSet<String> sortedKeys = new TreeSet<>(map.keySet());
            StringBuilder normalized = new StringBuilder();
            boolean first = true;
            for (String key : sortedKeys) {
                if (!first) {
                    normalized.append(",");
                }
                normalized.append(key).append(":").append(map.get(key));
                first = false;
            }

            String normalizedStr = normalized.toString();
            if (!seen.contains(normalizedStr)) {
                seen.add(normalizedStr);
                result.add(map);
            }
        }

        return result;
    }
}
