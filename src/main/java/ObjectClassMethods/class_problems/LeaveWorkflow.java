import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

enum LeaveStatus {
    PENDING("Pending"), APPROVED("Approved"), REJECTED("Rejected");

    private final String label;

    LeaveStatus(String label) {
        this.label = label;
    }

    String getLabel() {
        return label;
    }
}

abstract class Employee {
    private final String name;

    Employee(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    abstract int getMaxLeaveDays();

    boolean isLeaveAllowed(int days) {
        return days > 0 && days <= getMaxLeaveDays();
    }

    LeaveRequest submitLeave(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            System.out.println("Invalid leave dates for " + name + ".");
            return null;
        }
        int days = (int) ChronoUnit.DAYS.between(start, end) + 1;
        if (!isLeaveAllowed(days)) {
            System.out.println("Leave request for " + name + " exceeds the policy limit of " + getMaxLeaveDays()
                    + " days.");
            return null;
        }
        LeaveRequest request = new LeaveRequest(this, start, end);
        System.out.println("Leave request submitted for " + name + " (" + request.getPeriod() + "). Status: "
                + request.getStatus().getLabel() + ".");
        return request;
    }
}

class FullTimeEmployee extends Employee {
    FullTimeEmployee(String name) {
        super(name);
    }

    int getMaxLeaveDays() {
        return 20;
    }
}

class PartTimeEmployee extends Employee {
    PartTimeEmployee(String name) {
        super(name);
    }

    int getMaxLeaveDays() {
        return 5;
    }
}

class Contractor extends Employee {
    Contractor(String name) {
        super(name);
    }

    int getMaxLeaveDays() {
        return 3;
    }
}

class Reviewer {
    private final String name;

    Reviewer(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void approve(LeaveRequest request) {
        request.approve(this);
    }

    void reject(LeaveRequest request) {
        request.reject(this);
    }
}

class LeaveRequest {
    private static final DateTimeFormatter MONTH_DAY = DateTimeFormatter.ofPattern("MMM d", Locale.US);

    private final Employee employee;
    private final LocalDate start;
    private final LocalDate end;
    private LeaveStatus status = LeaveStatus.PENDING;
    private Reviewer reviewer;

    LeaveRequest(Employee employee, LocalDate start, LocalDate end) {
        this.employee = employee;
        this.start = start;
        this.end = end;
    }

    LeaveStatus getStatus() {
        return status;
    }

    String getPeriod() {
        if (start.equals(end)) {
            return start.format(MONTH_DAY);
        }
        if (start.getMonth() == end.getMonth() && start.getYear() == end.getYear()) {
            return start.format(MONTH_DAY) + "-" + end.getDayOfMonth();
        }
        return start.format(MONTH_DAY) + "-" + end.format(MONTH_DAY);
    }

    boolean changeStatus(LeaveStatus target, Reviewer byReviewer) {
        if (byReviewer == null) {
            System.out.println("A reviewer is required to change the leave request status.");
            return false;
        }
        boolean validTarget = target == LeaveStatus.APPROVED || target == LeaveStatus.REJECTED;
        if (status != LeaveStatus.PENDING || !validTarget) {
            System.out.println("Cannot change leave request status from " + status.getLabel() + " to "
                    + target.getLabel() + ".");
            return false;
        }
        status = target;
        reviewer = byReviewer;
        return true;
    }

    void approve(Reviewer byReviewer) {
        if (changeStatus(LeaveStatus.APPROVED, byReviewer)) {
            System.out.println(employee.getName() + "'s leave request (" + getPeriod() + ") approved. Status: "
                    + status.getLabel() + ".");
        }
    }

    void reject(Reviewer byReviewer) {
        if (changeStatus(LeaveStatus.REJECTED, byReviewer)) {
            System.out.println(employee.getName() + "'s leave request (" + getPeriod() + ") rejected. Status: "
                    + status.getLabel() + ".");
        }
    }
}

public class LeaveWorkflow {
    public static void main(String[] args) {
        Employee john = new FullTimeEmployee("John");
        Employee jane = new PartTimeEmployee("Jane");
        Reviewer alice = new Reviewer("Alice");
        Reviewer bob = new Reviewer("Bob");

        LeaveRequest johnRequest = john.submitLeave(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5));
        alice.approve(johnRequest);
        LeaveRequest janeRequest = jane.submitLeave(LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 11));
        bob.reject(janeRequest);
        johnRequest.changeStatus(LeaveStatus.PENDING, alice);
    }
}
