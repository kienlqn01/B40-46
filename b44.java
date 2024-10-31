import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class b44 {

    // Phương thức nhapXau để nhận chuỗi từ người dùng
    public static String nhapXau() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder result = new StringBuilder();
        try {
            while (true) {
                System.out.print("Nhập xâu ký tự (nhập '#' để kết thúc): ");
                String input = scanner.nextLine();

                if (input.equals("#")) {
                    result.append("Kết thúc nhập");
                    break; // Thoát khỏi vòng lặp nếu người dùng nhập "#"
                }

                // In ra xâu đã nhập
                System.out.println("Bạn vừa nhập: " + input);
                result.append(input).append("\n");
            }
        } finally {
            scanner.close(); // Đảm bảo đóng Scanner sau khi sử dụng
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // Sử dụng CompletableFuture để chạy nhapXau bất đồng bộ
        CompletableFuture<String> future = CompletableFuture.supplyAsync(b44::nhapXau)
                .thenApply(result -> {
                    System.out.println("Xử lý kết quả cuối cùng...");
                    return result;
                });

        // In ra kết quả cuối cùng
        try {
            String finalResult = future.get(); // Chờ cho đến khi CompletableFuture hoàn thành
            System.out.println("Kết quả cuối cùng: \n" + finalResult);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Chương trình kết thúc.");
    }
}
