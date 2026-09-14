public class GymProblem4 {

    static class GymMember {
        protected String memberId;
        protected int monthlyFee;
        protected int sessionsAttended;

        public GymMember(String memberId, int monthlyFee) {
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
            this.sessionsAttended = 0;
        }

        int getSessionsAttended() {
            return sessionsAttended;
        }

        String displayInfo() {
            return "Standard | Sessions: " + sessionsAttended;
        }
    }

    static class PremiumMember extends GymMember {
        private String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        String getTrainerName() {
            return trainerName;
        }

        @Override
        String displayInfo() {
            return "Premium | Trainer: " + trainerName + " | Sessions: " + sessionsAttended;
        }
    }

    static String batchPrint(GymMember[] members) {
        StringBuilder sb = new StringBuilder();
        for (GymMember member : members) {
            sb.append(member.displayInfo());
            if (member instanceof PremiumMember) {
                PremiumMember premium = (PremiumMember) member;
                sb.append(" [Trainer via downcast: ").append(premium.getTrainerName()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        GymMember[] members = {
                new GymMember("MEM6", 1000),
                new PremiumMember("MEM7", 2000, "Coach Riya")
        };
        System.out.println(batchPrint(members));

        GymMember plain = new GymMember("MEM8", 1000);
        PremiumMember bad = (PremiumMember) plain;
        System.out.println(bad);
    }
}
