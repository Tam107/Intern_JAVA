package intern.algorithm.level5;

import java.util.Arrays;
import java.util.List;

// Lớp Item để biểu diễn đối tượng với id và order
class Item {
    private int id;
    private int order;

    public Item(int id, int order) {
        this.id = id;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "{id: " + id + ", order: " + order + "}";
    }
}

public class Ex8 {
    // Hàm switchOrder để thay đổi thứ tự
    public static void switchOrder(List<Item> arr, int id, int newOrder) {
        // Tìm đối tượng có id và oldOrder
        Item target = null;
        int oldOrder = -1;
        for (Item item : arr) {
            if (item.getId() == id) {
                target = item;
                oldOrder = item.getOrder();
                break;
            }
        }

        // Kiểm tra hợp lệ
        if (target == null || newOrder < 0 || newOrder >= arr.size() || oldOrder == newOrder) {
            return; // Không làm gì nếu id không tồn tại, newOrder không hợp lệ, hoặc không cần thay đổi
        }

        // Điều chỉnh order của các đối tượng bị ảnh hưởng
        if (newOrder < oldOrder) {
            // Di chuyển lên: tăng order của các đối tượng trong khoảng [newOrder, oldOrder-1]
            for (Item item : arr) {
                if (item.getOrder() >= newOrder && item.getOrder() < oldOrder) {
                    item.setOrder(item.getOrder() + 1);
                }
            }
        } else {
            // Di chuyển xuống: giảm order của các đối tượng trong khoảng [oldOrder+1, newOrder]
            for (Item item : arr) {
                if (item.getOrder() > oldOrder && item.getOrder() <= newOrder) {
                    item.setOrder(item.getOrder() - 1);
                }
            }
        }

        // Cập nhật order của đối tượng mục tiêu
        target.setOrder(newOrder);
    }

    public static void main(String[] args) {
        // Tạo danh sách mẫu
        List<Item> arr = Arrays.asList(
                new Item(10, 0),
                new Item(12, 1),
                new Item(9, 2),
                new Item(11, 3)
        );


        System.out.println("Before: " + arr);

        switchOrder(arr, 9, 1);

        System.out.println("After: " + arr);
    }
}