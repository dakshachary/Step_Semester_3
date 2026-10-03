import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

abstract class MembershipPlan {
    static final double BASE_RATE = 1000;

    abstract String getName();

    abstract int getMonths();

    abstract double getDiscount();

    double calculateFee() {
        return BASE_RATE * getMonths() * (1 - getDiscount());
    }
}

class MonthlyPlan extends MembershipPlan {
    String getName() {
        return "Monthly";
    }

    int getMonths() {
        return 1;
    }

    double getDiscount() {
        return 0;
    }
}

class QuarterlyPlan extends MembershipPlan {
    String getName() {
        return "Quarterly";
    }

    int getMonths() {
        return 3;
    }

    double getDiscount() {
        return 0.10;
    }
}

class AnnualPlan extends MembershipPlan {
    String getName() {
        return "Annual";
    }

    int getMonths() {
        return 12;
    }

    double getDiscount() {
        return 0.25;
    }
}

enum MembershipStatus {
    ACTIVE("Active"), FROZEN("Frozen"), EXPIRED("Expired");

    private final String label;

    MembershipStatus(String label) {
        this.label = label;
    }

    String getLabel() {
        return label;
    }
}

class Member {
    private final String name;

    Member(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    Membership buy(MembershipPlan plan) {
        Membership membership = new Membership(this, plan);
        System.out.println(String.format(Locale.US, "%s membership created for %s. Fee: ₹%,.2f. Status: %s.",
                plan.getName(), name, membership.getFee(), membership.getStatus().getLabel()));
        return membership;
    }
}

class Membership {
    private final Member member;
    private final MembershipPlan plan;
    private final double fee;
    private MembershipStatus status = MembershipStatus.ACTIVE;

    Membership(Member member, MembershipPlan plan) {
        this.member = member;
        this.plan = plan;
        this.fee = plan.calculateFee();
    }

    MembershipStatus getStatus() {
        return status;
    }

    double getFee() {
        return fee;
    }

    MembershipPlan getPlan() {
        return plan;
    }

    boolean checkIn() {
        if (status != MembershipStatus.ACTIVE) {
            System.out.println("Check-in denied: " + member.getName() + "'s membership is " + status.getLabel() + ".");
            return false;
        }
        System.out.println(member.getName() + " checked in successfully.");
        return true;
    }

    boolean freeze() {
        if (status == MembershipStatus.EXPIRED) {
            System.out.println("Cannot freeze an Expired membership.");
            return false;
        }
        if (status == MembershipStatus.FROZEN) {
            System.out.println(member.getName() + "'s membership is already Frozen.");
            return false;
        }
        status = MembershipStatus.FROZEN;
        System.out.println(member.getName() + "'s membership frozen. Status: " + status.getLabel() + ".");
        return true;
    }

    boolean unfreeze() {
        if (status == MembershipStatus.EXPIRED) {
            System.out.println("Cannot unfreeze an Expired membership.");
            return false;
        }
        if (status == MembershipStatus.ACTIVE) {
            System.out.println(member.getName() + "'s membership is not Frozen.");
            return false;
        }
        status = MembershipStatus.ACTIVE;
        System.out.println(member.getName() + "'s membership unfrozen. Status: " + status.getLabel() + ".");
        return true;
    }

    boolean expire() {
        if (status == MembershipStatus.EXPIRED) {
            System.out.println(member.getName() + "'s membership is already Expired.");
            return false;
        }
        status = MembershipStatus.EXPIRED;
        System.out.println(member.getName() + "'s membership expired. Status: " + status.getLabel() + ".");
        return true;
    }
}

public class GymMembership {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        Membership ashaMembership = asha.buy(new QuarterlyPlan());
        Membership raviMembership = ravi.buy(new MonthlyPlan());
        ashaMembership.checkIn();
        ashaMembership.freeze();
        ashaMembership.checkIn();
        raviMembership.expire();
        raviMembership.freeze();
    }
}
