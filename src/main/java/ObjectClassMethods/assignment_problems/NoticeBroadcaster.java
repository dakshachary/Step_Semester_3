import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

interface NotificationChannel {
    String getName();

    void send(Student student, String message);
}

class EmailChannel implements NotificationChannel {
    public String getName() {
        return "Email";
    }

    public void send(Student student, String message) {
        System.out.println("[Email → " + student.getName() + "] " + message + ".");
    }
}

class SmsChannel implements NotificationChannel {
    public String getName() {
        return "SMS";
    }

    public void send(Student student, String message) {
        System.out.println("[SMS → " + student.getName() + "] " + message + ".");
    }
}

class AppChannel implements NotificationChannel {
    public String getName() {
        return "App";
    }

    public void send(Student student, String message) {
        System.out.println("[App → " + student.getName() + "] " + message + ".");
    }
}

class Student {
    private final String name;
    private final String department;
    private final List<NotificationChannel> preferredChannels = new ArrayList<>();

    Student(String name, String department, NotificationChannel... channels) {
        this.name = name;
        this.department = department;
        preferredChannels.addAll(Arrays.asList(channels));
    }

    String getName() {
        return name;
    }

    String getDepartment() {
        return department;
    }

    List<NotificationChannel> getPreferredChannels() {
        return new ArrayList<>(preferredChannels);
    }

    void addChannel(NotificationChannel channel) {
        preferredChannels.add(channel);
    }
}

class Notice {
    private final String title;
    private final Set<String> targetDepartments = new LinkedHashSet<>();

    Notice(String title, String... departments) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("A title is required.");
        }
        for (String department : departments) {
            if (department != null && !department.trim().isEmpty()) {
                targetDepartments.add(department.trim());
            }
        }
        if (targetDepartments.isEmpty()) {
            throw new IllegalArgumentException("At least one target department is required.");
        }
        this.title = title.trim();
    }

    String getTitle() {
        return title;
    }

    Set<String> getTargetDepartments() {
        return new LinkedHashSet<>(targetDepartments);
    }
}

class NoticeBoard {
    private final List<Student> students = new ArrayList<>();

    void register(Student student) {
        students.add(student);
    }

    void post(Notice notice) {
        System.out.println("Notice '" + notice.getTitle() + "' posted to "
                + String.join(", ", notice.getTargetDepartments()) + ".");
        for (Student student : students) {
            if (notice.getTargetDepartments().contains(student.getDepartment())) {
                for (NotificationChannel channel : student.getPreferredChannels()) {
                    channel.send(student, notice.getTitle());
                }
            }
        }
    }
}

public class NoticeBroadcaster {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        NoticeBoard board = new NoticeBoard();
        board.register(new Student("Asha", "CSE", new EmailChannel(), new AppChannel()));
        board.register(new Student("Ravi", "ECE", new SmsChannel()));

        board.post(new Notice("Lab Closed Tomorrow", "CSE"));
        board.post(new Notice("Fee Deadline Extended", "CSE", "ECE"));
        try {
            board.post(new Notice("Sports Day"));
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot post notice: " + e.getMessage());
        }
    }
}
