public class M3_LateFees {

    static class Account {
        private String regNo;
        private double totalFee;

        public Account(String regNo, double totalFee) {
            this.regNo = regNo;
            this.totalFee = totalFee;
        }

        // Locked down formula - cannot be overridden by any subclass
        final double calculateLateFee(int daysLate) {
            return totalFee * 0.01 * daysLate; // 1% of total fee per day late
        }

        final void printSummary(int daysLate) {
            if (daysLate <= 0) {
                System.out.println(regNo + " - On time, no late fee");
                return;
            }
            double lateFee = calculateLateFee(daysLate);
            System.out.println(regNo + " | Total Fee: Rs " + totalFee + " | Late Fee: Rs " + lateFee);
        }
    }

    public static void main(String[] args) {
        String[] regNos = {"RA001", "RA002", "RA003", "RA004"};
        double[] totalFees = {200000, 150000, 180000, 220000};
        int[] daysLate = {10, 0, -2, 5};

        for (int i = 0; i < regNos.length; i++) {
            Account account = new Account(regNos[i], totalFees[i]);
            account.printSummary(daysLate[i]);
        }
    }
}