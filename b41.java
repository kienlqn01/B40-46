import java.util.Scanner;

public class b41 {

    // Phương thức static "nhapSoVaTinhTong" để nhập số và tính tổng
    public static void nhapSoVaTinhTong() {
        Scanner scanner = new Scanner(System.in); // Mở Scanner
        try {
            while (true) {
                System.out.print("Nhập một số nguyên dương (nhập số chia hết cho 102024 để kết thúc): ");
                int n = scanner.nextInt(); // Nhận số nguyên từ người dùng

                if (n <= 0) {
                    System.out.println("Vui lòng nhập số nguyên dương.");
                    continue; // Yêu cầu nhập lại nếu n không phải số nguyên dương
                }

                // Kiểm tra điều kiện kết thúc
                if (n % 102024 == 0) {
                    System.out.println("Kết thúc nhập số.");
                    break; // Thoát khỏi vòng lặp nếu số chia hết cho 102024
                }

                // Tính tổng từ 1 đến n
                int sum = 0;
                for (int i = 1; i <= n; i++) {
                    sum += i;
                }
                System.out.println("Tổng các số từ 1 đến " + n + " là: " + sum);
            }
        } finally {
            scanner.close(); // Đảm bảo đóng Scanner
        }}}