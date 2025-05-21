package intern.JAVACORE2.Ex8;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductManager manager = new ProductManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Quản lý danh sách sản phẩm ===");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Hiển thị tất cả sản phẩm");
            System.out.println("3. Tìm sản phẩm theo mã");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Cập nhật sản phẩm");
            System.out.println("6. Thoát");
            System.out.print("Chọn chức năng (1-6): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            if (choice == 6) {
                System.out.println("Đã thoát chương trình.");
                break;
            }

            switch (choice) {
                case 1: // Thêm sản phẩm
                    System.out.print("Nhập mã sản phẩm: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập tên sản phẩm: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập giá sản phẩm: ");
                    double price;
                    try {
                        price = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Giá không hợp lệ!");
                        break;
                    }
                    System.out.print("Nhập số lượng: ");
                    int quantity;
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Số lượng không hợp lệ!");
                        break;
                    }
                    manager.addProduct(id, name, price, quantity);
                    break;

                case 2: // Hiển thị tất cả
                    manager.displayAllProduct();
                    break;

                case 3: // Tìm sản phẩm
                    System.out.print("Nhập mã sản phẩm: ");
                    String searchId = scanner.nextLine();
                    manager.findProduct(searchId);
                    break;

                case 4: // Xóa sản phẩm
                    System.out.print("Nhập mã sản phẩm: ");
                    String removeId = scanner.nextLine();
                    manager.removeProduct(removeId);
                    break;

                case 5: // Cập nhật sản phẩm
                    System.out.print("Nhập mã sản phẩm: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Nhập tên sản phẩm mới: ");
                    String newName = scanner.nextLine();
                    System.out.print("Nhập giá mới: ");
                    double newPrice;
                    try {
                        newPrice = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Giá không hợp lệ!");
                        break;
                    }
                    System.out.print("Nhập số lượng mới: ");
                    int newQuantity;
                    try {
                        newQuantity = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Số lượng không hợp lệ!");
                        break;
                    }
                    manager.updateProduct(updateId, newName, newPrice, newQuantity);
                    break;

                default:
                    System.out.println("Chức năng không hợp lệ!");
            }
        }
        scanner.close();
    }

}
