import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class b45 {

    // Phương thức nhapSoNguyenDuong để nhập số nguyên dương
    public static int nhapSoNguyenDuong() {
        Scanner scanner = new Scanner(System.in);
        int number = -1;
        try {
            while (number <= 0) { // Kiểm tra nếu là số nguyên dương
                System.out.print("Nhập một số nguyên dương: ");
                number = scanner.nextInt();
                if (number <= 0) {
                    System.out.println("Số nhập vào không phải số nguyên dương. Vui lòng thử lại.");
                }
            }
        } finally {
            scanner.close(); // Đảm bảo đóng Scanner sau khi sử dụng
        }
        return number;
    }

    public static void main(String[] args) {
        // Sử dụng CompletableFuture để chạy nhapSoNguyenDuong bất đồng bộ
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(b45::nhapSoNguyenDuong)
                .thenApply(n -> n * n) // Biến đổi kết quả n thành n*n
                .thenAccept(result -> System.out.println("Kết quả n*n = " + result)) // In kết quả
                .exceptionally(ex -> {
                    System.out.println("Đã xảy ra lỗi: " + ex.getMessage());
                    return null; // Trả về null để xử lý ngoại lệ
                });

        // Đợi CompletableFuture hoàn thành
        future.join();

        System.out.println("Chương trình kết thúc.");
    }
}
