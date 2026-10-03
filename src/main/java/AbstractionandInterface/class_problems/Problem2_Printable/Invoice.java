public class Invoice implements Printable {
    private final String invoiceNumber;

    public Invoice(String invoiceNumber) {
        if (invoiceNumber == null || invoiceNumber.trim().isEmpty()) throw new IllegalArgumentException("invoiceNumber must not be blank");
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String printLabel() {
        return "Invoice label: " + invoiceNumber;
    }
}
