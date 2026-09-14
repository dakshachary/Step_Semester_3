public class GymProblem5 {

    static class GymMember {
        private static int membersEnrolled = 0;

        public final String membershipNumber;
        protected int monthlyFee;
        protected int feesPaid;
        protected String lastPaymentMode;

        public GymMember(int monthlyFee) {
            membersEnrolled++;
            this.membershipNumber = "GYM-" + (2000 + membersEnrolled);
            this.monthlyFee = monthlyFee;
            this.feesPaid = 0;
        }

        void payFee(int amount) {
            feesPaid += amount;
        }

        void payFee(int amount, String mode) {
            this.lastPaymentMode = mode;
            payFee(amount);
        }

        int getFeesPaid() {
            return feesPaid;
        }

        static boolean isValidReferralCode(String code) {
            if (code == null || code.length() != 4) {
                return false;
            }
            if (code.charAt(0) != 'G') {
                return false;
            }
            if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2))) {
                return false;
            }
            if (!Character.isUpperCase(code.charAt(3))) {
                return false;
            }
            return true;
        }

        static int getMembersEnrolled() {
            return membersEnrolled;
        }
    }

    static class GroupClassMember extends GymMember {
        private String className;

        public GroupClassMember(int monthlyFee, String className) {
            super(monthlyFee);
            this.className = className;
        }
    }

    static String processWeeklyCheckIn(GymMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (GymMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (member instanceof GroupClassMember) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        GymMember m1 = new GymMember(1000);
        System.out.println(m1.membershipNumber);
        System.out.println(GymMember.getMembersEnrolled());

        System.out.println(GymMember.isValidReferralCode("G45B"));
        System.out.println(GymMember.isValidReferralCode("G4B"));
        System.out.println(GymMember.isValidReferralCode("X45B"));

        m1.payFee(500);
        m1.payFee(500, "UPI");
        System.out.println(m1.getFeesPaid());

        GymMember[] batch = {
                new GroupClassMember(1500, "Zumba"),
                null,
                new GymMember(1000)
        };
        System.out.println(processWeeklyCheckIn(batch));
    }
}
