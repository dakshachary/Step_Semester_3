import java.util.Arrays;

public class GymProblem3 {

    static class GymMember {
        protected String memberId;
        protected int monthlyFee;
        protected int sessionsAttended;

        private final int[] lateFeeHistory = new int[10];
        private int lateFeeCount = 0;

        public GymMember(String memberId, int monthlyFee) {
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
            this.sessionsAttended = 0;
        }

        protected void chargeLateFee(int amount) {
            if (lateFeeCount < lateFeeHistory.length) {
                lateFeeHistory[lateFeeCount] = amount;
                lateFeeCount++;
            }
        }

        int[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }

        int getTotalLateFees() {
            int total = 0;
            for (int i = 0; i < lateFeeCount; i++) {
                total += lateFeeHistory[i];
            }
            return total;
        }
    }

    static class PremiumMember extends GymMember {
        private String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        @Override
        protected void chargeLateFee(int amount) {
            super.chargeLateFee(amount / 2);
        }
    }

    public static void main(String[] args) {
        PremiumMember p = new PremiumMember("MEM5", 2000, "Coach Riya");
        p.chargeLateFee(200);
        System.out.println(p.getTotalLateFees());

        int[] history = p.getLateFeeHistory();
        history[0] = 999;
        System.out.println(Arrays.toString(p.getLateFeeHistory()));
    }
}
