import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

abstract class Question {
    private final int id;
    private final String text;
    private final int points;

    Question(int id, String text, int points) {
        this.id = id;
        this.text = text;
        this.points = points;
    }

    int getId() {
        return id;
    }

    String getText() {
        return text;
    }

    int getPoints() {
        return points;
    }

    abstract boolean isCorrect(String answer);

    int evaluate(String answer) {
        return answer != null && isCorrect(answer) ? points : 0;
    }
}

class MultipleChoiceQuestion extends Question {
    private final String correctOption;

    MultipleChoiceQuestion(int id, String text, int points, String correctOption) {
        super(id, text, points);
        this.correctOption = correctOption;
    }

    boolean isCorrect(String answer) {
        return correctOption.equalsIgnoreCase(answer.trim());
    }
}

class TrueFalseQuestion extends Question {
    private final boolean correctValue;

    TrueFalseQuestion(int id, String text, int points, boolean correctValue) {
        super(id, text, points);
        this.correctValue = correctValue;
    }

    boolean isCorrect(String answer) {
        return Boolean.parseBoolean(answer.trim()) == correctValue;
    }
}

class ShortAnswerQuestion extends Question {
    private final String expected;

    ShortAnswerQuestion(int id, String text, int points, String expected) {
        super(id, text, points);
        this.expected = expected;
    }

    boolean isCorrect(String answer) {
        return expected.equalsIgnoreCase(answer.trim());
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

class Examination {
    private final String name;
    private final List<Question> questions = new ArrayList<>();
    private final Set<Student> submittedStudents = new HashSet<>();

    Examination(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void addQuestion(Question question) {
        questions.add(question);
    }

    List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    Question findQuestion(int id) {
        for (Question question : questions) {
            if (question.getId() == id) {
                return question;
            }
        }
        return null;
    }

    int getTotalPoints() {
        int total = 0;
        for (Question question : questions) {
            total += question.getPoints();
        }
        return total;
    }

    boolean hasSubmitted(Student student) {
        return submittedStudents.contains(student);
    }

    void recordSubmission(Student student) {
        submittedStudents.add(student);
    }
}

enum AttemptState {
    IN_PROGRESS, SUBMITTED
}

class Attempt {
    private final Student student;
    private final Examination exam;
    private final Map<Integer, String> answers = new LinkedHashMap<>();
    private AttemptState state = AttemptState.IN_PROGRESS;
    private int score;

    Attempt(Student student, Examination exam) {
        this.student = student;
        this.exam = exam;
    }

    AttemptState getState() {
        return state;
    }

    int getScore() {
        return score;
    }

    void answer(int questionId, String answer) {
        if (state == AttemptState.SUBMITTED) {
            System.out.println("Cannot change answers for a submitted examination.");
            return;
        }
        if (exam.findQuestion(questionId) == null) {
            System.out.println("Question " + questionId + " does not exist in " + exam.getName() + ".");
            return;
        }
        answers.put(questionId, answer);
        System.out.println("Answer recorded for Question " + questionId + ".");
    }

    void submit() {
        if (state == AttemptState.SUBMITTED) {
            System.out.println(exam.getName() + " has already been submitted by " + student.getName() + ".");
            return;
        }
        state = AttemptState.SUBMITTED;
        exam.recordSubmission(student);
        System.out.println(exam.getName() + " submitted by " + student.getName() + ".");

        List<String> parts = new ArrayList<>();
        score = 0;
        for (Question question : exam.getQuestions()) {
            int earned = question.evaluate(answers.get(question.getId()));
            score += earned;
            parts.add("Question " + question.getId() + ": " + (earned > 0 ? "Correct" : "Incorrect") + " ("
                    + earned + " points)");
        }
        System.out.println("Result: " + String.join(", ", parts) + ". Total score: " + score + "/"
                + exam.getTotalPoints() + ".");
    }
}

class ExamService {
    Attempt start(Student student, Examination exam) {
        if (exam.hasSubmitted(student)) {
            System.out.println(student.getName() + " has already submitted " + exam.getName() + ".");
            return null;
        }
        System.out.println(exam.getName() + " started by " + student.getName() + ".");
        return new Attempt(student, exam);
    }
}

public class OnlineExam {
    public static void main(String[] args) {
        Examination examA = new Examination("Exam A");
        examA.addQuestion(new MultipleChoiceQuestion(1, "Pick the correct option", 5, "C"));
        examA.addQuestion(new TrueFalseQuestion(2, "Java is purely procedural", 5, false));

        Student student1 = new Student("Student 1");
        ExamService service = new ExamService();

        Attempt attempt = service.start(student1, examA);
        attempt.answer(1, "C");
        attempt.answer(2, "True");
        attempt.submit();
        attempt.answer(1, "A");
    }
}
