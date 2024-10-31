import java.util.Scanner;

public class b40 {

    // Phương thức static "nhapXau" để nhập và xử lý chuỗi
    public static void nhapXau() {
        Scanner scanner = new Scanner(System.in); // Mở Scanner
        try {
            while (true) {
                System.out.print("Nhập xâu ký tự (nhập '#' để kết thúc): ");
                String input = scanner.nextLine(); // Nhận chuỗi nhập vào từ người dùng

                if (input.equals("#")) {
                    System.out.println("Kết thúc nhập xâu.");
                    break; // Thoát khỏi vòng lặp nếu nhập vào '#'
                }

                System.out.println("Bạn đã nhập: " + input); // Hiển thị xâu nhập vào
            }
        } finally {
            scanner.close(); // Đảm bảo đóng Scanner
        }
    }

    public static void main(String[] args) {
        // Tạo một luồng mới và đặt "nhapXau" làm nhiệm vụ của luồng
        Thread thread = new Thread(b40::nhapXau);

        // Khởi động luồng
        thread.start();

        // Đợi cho luồng kết thúc
        try {
            thread.join(); // Đợi luồng kết thúc
        } catch (InterruptedException e) {
            System.out.println("Luồng bị gián đoạn: " + e.getMessage());
        }

        System.out.println("Chương trình kết thúc.");
    }
}
