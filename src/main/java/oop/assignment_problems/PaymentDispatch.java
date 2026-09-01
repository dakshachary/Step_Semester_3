public class PaymentDispatch {

    static double totalCollected = 0;

    static class Payment {
        void pay(double amount) {
            System.out.println("Paid (cash): Rs " + amount);
        }
    }

    static class CardPayment extends Payment {
        double payWithProcessingFee(double amount) {
            double totalCharged = amount + (amount * 0.02);
            System.out.println("Charged (card, incl. fee): Rs " + totalCharged);
            return totalCharged;
        }
    }

    static void processTransaction(Payment payment, double amount) {
        if (payment instanceof CardPayment) {
            CardPayment cardPayment = (CardPayment) payment;
            totalCollected += cardPayment.payWithProcessingFee(amount);
        } else {
            payment.pay(amount);
            totalCollected += amount;
        }
    }

    public static void main(String[] args) {
        Payment[] payments = {
            new CardPayment(),
            new Payment(),
            new CardPayment(),
            new Payment(),
            new CardPayment()
        };
        double[] amounts = {100, 50, 200, 75, 120};

        for (int i = 0; i < payments.length; i++) {
            processTransaction(payments[i], amounts[i]);
        }

        System.out.println("Total Collected: Rs " + totalCollected);
    }
}
