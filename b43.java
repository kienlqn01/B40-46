import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class b43 {
    private static final Lock lock = new ReentrantLock();
    private static final Condition evenCondition = lock.newCondition(); // Điều kiện cho luồng in số chẵn
    private static final Condition oddCondition = lock.newCondition();  // Điều kiện cho luồng in số lẻ
    private static int number = 1; // Biến chung để in số

    public static void main(String[] args) {
        Thread evenThread = new Thread(() -> {
            while (number <= 20) {
                lock.lock();
                try {
                    while (number % 2 != 0) { // Chờ nếu là số lẻ
                        evenCondition.await();
                    }
                    if (number <= 20) {
                        System.out.println("Even Thread: " + number);
                        number++;
                        oddCondition.signal(); // Báo hiệu cho luồng in số lẻ
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread oddThread = new Thread(() -> {
            while (number <= 20) {
                lock.lock();
                try {
                    while (number % 2 == 0) { // Chờ nếu là số chẵn
                        oddCondition.await();
                    }
                    if (number <= 20) {
                        System.out.println("Odd Thread: " + number);
                        number++;
                        evenCondition.signal(); // Báo hiệu cho luồng in số chẵn
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        // Khởi động cả hai luồng
        evenThread.start();
        oddThread.start();

        // Đợi cả hai luồng kết thúc
        try {
            evenThread.join();
            oddThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Chương trình kết thúc.");
    }
}
