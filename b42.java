import java.util.Random;

public class b42 {
    // Biến chung lưu trữ số ngẫu nhiên, được đánh dấu volatile để các luồng nhìn thấy thay đổi ngay lập tức
    private static volatile int sharedNumber = 0;
    // Biến cờ volatile để báo hiệu cho các luồng dừng lại
    private static volatile boolean keepRunning = true;

    public static void main(String[] args) {
        // Luồng ghi liên tục tạo ra số ngẫu nhiên và gán cho sharedNumber
        Thread writerThread = new Thread(() -> {
            Random random = new Random();
            while (keepRunning) {
                sharedNumber = random.nextInt(100000000); // Tạo số ngẫu nhiên từ 0 đến 99,999,999
                System.out.println("Writer Thread: Sinh số " + sharedNumber);
                try {
                    Thread.sleep(100); // Tạm nghỉ 100ms để dễ quan sát kết quả
                } catch (InterruptedException e) {
                    System.out.println("Writer Thread bị gián đoạn: " + e.getMessage());
                }
            }
        });

        // Luồng đọc kiểm tra số chia hết cho 24102024
        Thread readerThread = new Thread(() -> {
            while (keepRunning) {
                if (sharedNumber % 24102024 == 0 && sharedNumber != 0) { // Đảm bảo không dừng khi số là 0
                    System.out.println("Reader Thread: Đọc được số " + sharedNumber + " chia hết cho 24102024. Dừng chương trình.");
                    keepRunning = false; // Cập nhật cờ để dừng cả hai luồng
                }
                try {
                    Thread.sleep(100); // Tạm nghỉ 100ms để dễ quan sát kết quả
                } catch (InterruptedException e) {
                    System.out.println("Reader Thread bị gián đoạn: " + e.getMessage());
                }
            }
        });

        // Khởi động cả hai luồng
        writerThread.start();
        readerThread.start();

        // Đợi cả hai luồng kết thúc
        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Luồng chính bị gián đoạn: " + e.getMessage());
        }

        System.out.println("Chương trình kết thúc.");
    }
}
