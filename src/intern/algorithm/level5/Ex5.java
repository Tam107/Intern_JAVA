package intern.algorithm.level5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ex5 {
    public static Map<Object, List<Map<String, Object>>> groupBy(List<Map<String, Object>> collection, String field) {
        // Kiểm tra đầu vào
        if (collection == null || field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Collection không được null và field không được null hoặc rỗng");
        }

        // Tạo map kết quả
        Map<Object, List<Map<String, Object>>> result = new HashMap<>();

        // Duyệt qua từng đối tượng trong collection
        for (Map<String, Object> item : collection) {
            // Lấy giá trị của trường cần nhóm
            Object key = item.get(field);

            // Nếu key chưa tồn tại trong result, tạo một danh sách mới
            if (!result.containsKey(key)) {
                result.put(key, new ArrayList<>());
            }

            // Thêm đối tượng hiện tại vào danh sách tương ứng với key
            result.get(key).add(item);
        }

        return result;
    }

    /**
     * Phương thức main để chạy thử nghiệm
     */
    public static void main(String[] args) {
        // Tạo dữ liệu mẫu
        List<Map<String, Object>> collection = new ArrayList<>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("a", 1);
        item1.put("b", 2);
        collection.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("a", 1);
        item2.put("b", 3);
        collection.add(item2);

        Map<String, Object> item3 = new HashMap<>();
        item3.put("a", 2);
        item3.put("b", 2);
        collection.add(item3);

        // Nhóm theo trường 'a'
        Map<Object, List<Map<String, Object>>> resultA = groupBy(collection, "a");
        System.out.println("groupBy(collection, 'a'):");
        printResult(resultA);

        // Nhóm theo trường 'b'
        Map<Object, List<Map<String, Object>>> resultB = groupBy(collection, "b");
        System.out.println("\ngroupBy(collection, 'b'):");
        printResult(resultB);
    }

    /**
     * Phương thức hỗ trợ để in kết quả dễ đọc hơn
     */
    private static void printResult(Map<Object, List<Map<String, Object>>> result) {
        System.out.println("{");
        for (Object key : result.keySet()) {
            System.out.print("  " + key + ": [");
            List<Map<String, Object>> items = result.get(key);
            for (int i = 0; i < items.size(); i++) {
                System.out.print(mapToString(items.get(i)));
                if (i < items.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
        System.out.println("}");
    }

    /**
     * Chuyển đổi Map thành chuỗi dễ đọc
     */
    private static String mapToString(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append(": ").append(entry.getValue());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }
}
