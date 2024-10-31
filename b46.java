import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Request {
    private final String department;
    private final String action;
    private final String studentName;

    public Request(String department, String action, String studentName) {
        this.department = department;
        this.action = action;
        this.studentName = studentName;
    }

    public String getDepartment() {
        return department;
    }

    public String getAction() {
        return action;
    }

    public String getStudentName() {
        return studentName;
    }
}

class Dispatcher {
    private final BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();

    public void dispatchRequest(Request request) {
        try {
            requestQueue.put(request);
            System.out.println("Dispatcher: Đã nhận yêu cầu từ " + request.getDepartment() + " và chuyển tiếp.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Request getNextRequest() {
        try {
            return requestQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}

class ExaminationOffice implements Runnable {
    private final Dispatcher dispatcher;

    public ExaminationOffice(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Request request = dispatcher.getNextRequest();
                if (request == null) break;

                System.out.println("Phòng Khảo thí: Đang xử lý yêu cầu từ " + request.getDepartment()
                        + " cho sinh viên " + request.getStudentName() + " - " + request.getAction());
                Thread.sleep(1000); // Giả lập thời gian xử lý yêu cầu
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Department implements Runnable {
    private final Dispatcher dispatcher;
    private final String departmentName;
    private final String action;
    private final String studentName;

    public Department(Dispatcher dispatcher, String departmentName, String action, String studentName) {
        this.dispatcher = dispatcher;
        this.departmentName = departmentName;
        this.action = action;
        this.studentName = studentName;
    }

    @Override
    public void run() {
        Request request = new Request(departmentName, action, studentName);
        System.out.println(departmentName + ": Gửi yêu cầu - " + action + " cho sinh viên " + studentName);
        dispatcher.dispatchRequest(request);
    }
}

public class b46 {
    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();

        // Tạo và khởi động thread cho Phòng Khảo thí
        Thread examinationOfficeThread = new Thread(new ExaminationOffice(dispatcher));
        examinationOfficeThread.start();

        // Tạo các phòng ban và gửi yêu cầu
        Thread trainingDept = new Thread(new Department(dispatcher, "Phòng Đào tạo", "cho phép dự thi", "Nguyen Van A"));
        Thread studentAffairsDept = new Thread(new Department(dispatcher, "Phòng Công tác Sinh viên", "xác nhận đủ điều kiện thi", "Tran Thi B"));
        Thread financeDept = new Thread(new Department(dispatcher, "Phòng Tài chính", "đề nghị thi lại", "Le Van C"));

        // Khởi động các thread cho các phòng ban
        trainingDept.start();
        studentAffairsDept.start();
        financeDept.start();

        // Đợi các phòng ban gửi yêu cầu xong
        try {
            trainingDept.join();
            studentAffairsDept.join();
            financeDept.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Dừng thread của Phòng Khảo thí sau khi tất cả yêu cầu đã được gửi
        examinationOfficeThread.interrupt();
    }
}
