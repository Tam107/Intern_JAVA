package intern.algorithm.level5;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ex9 {
    public static void main(String[] args) {
        List<Map<String, Object>> arr = Arrays.asList(
                new HashMap<String, Object>() {{
                    put("a", 2);
                    put("b", 10);
                }},
                new HashMap<String, Object>() {{
                    put("a", 12);
                    put("c", 11);
                }},
                new HashMap<String, Object>() {{
                    put("a", 8);
                    put("b", 14);
                    put("d", 20);
                }},
                new HashMap<String, Object>() {{
                    put("a", "8");
                }}
        );
        Map<String, Integer> result = sumAll(arr);
        System.out.println(result);
    }

    private static Map<String, Integer> sumAll(List<Map<String, Object>> arr) {
        Map<String, Integer> result = new HashMap<>();

        for(Map<String, Object> item: arr){
            // iterate each value in the object?
            for(Map.Entry<String, Object>entry : item.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();

                // transform to int
                int intValue;
                if(value instanceof Integer){
                    intValue = (Integer) value;
                }else {
                    continue;
                }

                // add to result
                result.put(key, result.getOrDefault(key, 0) + intValue);
            }
        }
        return result;
    }
}
