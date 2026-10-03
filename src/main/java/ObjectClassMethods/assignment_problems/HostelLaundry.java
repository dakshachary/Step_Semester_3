import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

abstract class WashType {
    abstract String getName();

    abstract int getDurationMinutes();

    abstract double getCharge();
}

class QuickWash extends WashType {
    String getName() {
        return "Quick";
    }

    int getDurationMinutes() {
        return 30;
    }

    double getCharge() {
        return 20;
    }
}

class NormalWash extends WashType {
    String getName() {
        return "Normal";
    }

    int getDurationMinutes() {
        return 45;
    }

    double getCharge() {
        return 30;
    }
}

class HeavyWash extends WashType {
    String getName() {
        return "Heavy";
    }

    int getDurationMinutes() {
        return 60;
    }

    double getCharge() {
        return 45;
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

class WashCycle {
    private final Student student;
    private final WashingMachine machine;
    private final WashType washType;

    WashCycle(Student student, WashingMachine machine, WashType washType) {
        this.student = student;
        this.machine = machine;
        this.washType = washType;
    }

    Student getStudent() {
        return student;
    }

    WashingMachine getMachine() {
        return machine;
    }

    WashType getWashType() {
        return washType;
    }

    double calculateCharge() {
        return washType.getCharge();
    }
}

class WashingMachine {
    private final String id;
    private boolean busy;
    private WashCycle currentCycle;

    WashingMachine(String id) {
        this.id = id;
    }

    String getId() {
        return id;
    }

    boolean isBusy() {
        return busy;
    }

    WashCycle startWash(Student student, WashType washType) {
        if (busy) {
            return null;
        }
        currentCycle = new WashCycle(student, this, washType);
        busy = true;
        return currentCycle;
    }

    boolean completeCycle() {
        if (!busy) {
            return false;
        }
        currentCycle = null;
        busy = false;
        return true;
    }
}

class LaundryDesk {
    void bookWash(Student student, WashingMachine machine, WashType washType) {
        WashCycle cycle = machine.startWash(student, washType);
        if (cycle == null) {
            System.out.println("Machine " + machine.getId() + " is currently busy.");
            return;
        }
        System.out.println(String.format(Locale.US, "%s wash started on %s for %s (%d min). Charge: ₹%.2f.",
                washType.getName(), machine.getId(), student.getName(), washType.getDurationMinutes(),
                cycle.calculateCharge()));
    }

    void completeWash(WashingMachine machine) {
        if (machine.completeCycle()) {
            System.out.println(machine.getId() + " cycle completed. " + machine.getId() + " is now free.");
        } else {
            System.out.println(machine.getId() + " has no wash in progress.");
        }
    }
}

public class HostelLaundry {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");
        LaundryDesk desk = new LaundryDesk();

        desk.bookWash(asha, m1, new QuickWash());
        desk.bookWash(ravi, m1, new HeavyWash());
        desk.bookWash(ravi, m2, new HeavyWash());
        desk.completeWash(m1);
        desk.bookWash(neha, m1, new NormalWash());
    }
}
