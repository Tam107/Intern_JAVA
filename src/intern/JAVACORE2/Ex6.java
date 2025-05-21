package intern.JAVACORE2;

public class Ex6 {
    public static int maxProfit(int[] prices) {
        // Kiểm tra nếu mảng rỗng hoặc chỉ có 1 phần tử
        if (prices == null || prices.length < 2) {
            return 0;
        }

        // Khởi tạo giá mua thấp nhất và lợi nhuận tối đa
        int minPrice = prices[0];
        int maxProfit = 0;

        // Duyệt qua mảng từ ngày thứ 2
        for (int i = 1; i < prices.length; i++) {
            // Cập nhật giá mua thấp nhất
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                // Tính lợi nhuận nếu bán ở ngày hiện tại
                int currentProfit = prices[i] - minPrice;
                // Cập nhật lợi nhuận tối đa nếu cần
                maxProfit = Math.max(maxProfit, currentProfit);
            }
        }

        return maxProfit;
    }

    public static void main(String[] args) {
        // Ví dụ đầu vào
        int[] prices = {7, 1, 5, 3, 6, 4};

        // Gọi hàm maxProfit
        int result = maxProfit(prices);

        // In kết quả
        System.out.println("Output: " + result);
    }
}
