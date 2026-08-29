public class M5_AccountBatchPayments {

    static class FeeAccount {
        // base type - day scholar account
    }

    static class HostelFeeAccount extends FeeAccount {
        // hostel account
    }

    static class PaymentProcessor {
        private int hostelCount = 0;
        private int dayScholarCount = 0;

        void processPayment(FeeAccount account, double amount) {
            if (account instanceof HostelFeeAccount) {
                hostelCount++;
                System.out.println("Paid in two installments (hostel account)");
            } else {
                dayScholarCount++;
                System.out.println("Paid in one go (day-scholar account)");
            }
        }

        void printCounters() {
            System.out.println("Hostel accounts processed: " + hostelCount
                    + " | Day-scholar accounts processed: " + dayScholarCount);
        }
    }

    public static void main(String[] args) {
        FeeAccount[] accounts = {
                new HostelFeeAccount(),
                new HostelFeeAccount(),
                new FeeAccount(),
                new FeeAccount()
        };
        double amount = 60000;

        PaymentProcessor processor = new PaymentProcessor();
        for (FeeAccount account : accounts) {
            processor.processPayment(account, amount);
        }
        processor.printCounters();
    }
}