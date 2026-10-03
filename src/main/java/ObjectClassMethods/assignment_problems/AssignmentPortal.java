import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

abstract class Assignment {
    private final String title;
    private final int maxMarks;
    private final LocalDate dueDate;

    Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    String getTitle() {
        return title;
    }

    int getMaxMarks() {
        return maxMarks;
    }

    LocalDate getDueDate() {
        return dueDate;
    }

    abstract double getPenaltyRatePerDay();

    double getPenaltyFraction(long daysLate) {
        return Math.min(1.0, getPenaltyRatePerDay() * daysLate);
    }

    double applyLatePenalty(double awardedMarks, long daysLate) {
        double result = awardedMarks * (1 - getPenaltyFraction(daysLate));
        return Math.round(result * 100) / 100.0;
    }
}

class CodingAssignment extends Assignment {
    CodingAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    double getPenaltyRatePerDay() {
        return 0.10;
    }
}

class WrittenAssignment extends Assignment {
    WrittenAssignment(String title, int maxMarks, LocalDate dueDate) {
        super(title, maxMarks, dueDate);
    }

    double getPenaltyRatePerDay() {
        return 0.20;
    }
}

class Student {
    private final String name;

    Student(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}

enum SubmissionStatus {
    SUBMITTED("Submitted"), GRADED("Graded");

    private final String label;

    SubmissionStatus(String label) {
        this.label = label;
    }

    String getLabel() {
        return label;
    }
}

class Submission {
    private final Student student;
    private final Assignment assignment;
    private final LocalDate submissionDate;
    private SubmissionStatus status = SubmissionStatus.SUBMITTED;
    private double finalMarks;

    Submission(Student student, Assignment assignment, LocalDate submissionDate) {
        this.student = student;
        this.assignment = assignment;
        this.submissionDate = submissionDate;
    }

    Student getStudent() {
        return student;
    }

    Assignment getAssignment() {
        return assignment;
    }

    SubmissionStatus getStatus() {
        return status;
    }

    double getFinalMarks() {
        return finalMarks;
    }

    long getDaysLate() {
        return Math.max(0, ChronoUnit.DAYS.between(assignment.getDueDate(), submissionDate));
    }

    boolean grade(double awardedMarks) {
        if (status != SubmissionStatus.SUBMITTED) {
            return false;
        }
        finalMarks = assignment.applyLatePenalty(awardedMarks, getDaysLate());
        status = SubmissionStatus.GRADED;
        return true;
    }
}

class SubmissionPortal {
    private final Map<String, Submission> submissions = new HashMap<>();

    private String key(Student student, Assignment assignment) {
        return student.getName() + "|" + assignment.getTitle();
    }

    private static String fmt(double value) {
        if (value == Math.floor(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    void submit(Student student, Assignment assignment, LocalDate date) {
        Submission existing = submissions.get(key(student, assignment));
        if (existing != null && existing.getStatus() == SubmissionStatus.GRADED) {
            System.out.println("Cannot resubmit: '" + assignment.getTitle() + "' has already been graded.");
            return;
        }
        Submission submission = new Submission(student, assignment, date);
        submissions.put(key(student, assignment), submission);
        long late = submission.getDaysLate();
        String timing = late == 0 ? "on time" : late + (late == 1 ? " day late" : " days late");
        System.out.println(student.getName() + "'s submission for '" + assignment.getTitle() + "' received ("
                + timing + "). Status: " + submission.getStatus().getLabel() + ".");
    }

    void grade(Student student, Assignment assignment, double awardedMarks) {
        Submission submission = submissions.get(key(student, assignment));
        if (submission == null) {
            System.out.println("No submission found for " + student.getName() + " on '" + assignment.getTitle() + "'.");
            return;
        }
        if (!submission.grade(awardedMarks)) {
            System.out.println("Cannot grade: '" + assignment.getTitle() + "' has already been graded.");
            return;
        }
        long late = submission.getDaysLate();
        String text = student.getName() + " graded: " + fmt(submission.getFinalMarks()) + "/" + assignment.getMaxMarks();
        if (late > 0) {
            long percent = Math.round(assignment.getPenaltyFraction(late) * 100);
            text += " after " + percent + "% late penalty";
        }
        System.out.println(text + ". Status: " + submission.getStatus().getLabel() + ".");
    }
}

public class AssignmentPortal {
    public static void main(String[] args) {
        Assignment lab = new CodingAssignment("Linked List Lab", 50, LocalDate.of(2025, 3, 10));
        Assignment essay = new WrittenAssignment("Design Essay", 50, LocalDate.of(2025, 3, 12));
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        SubmissionPortal portal = new SubmissionPortal();

        portal.submit(asha, lab, LocalDate.of(2025, 3, 10));
        portal.submit(ravi, essay, LocalDate.of(2025, 3, 14));
        portal.grade(asha, lab, 45);
        portal.grade(ravi, essay, 40);
        portal.submit(asha, lab, LocalDate.of(2025, 3, 11));
    }
}
