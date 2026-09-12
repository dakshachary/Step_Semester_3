public class BankTransactionReferenceGenerator {

    static String normalizeReference(String raw) {
        String trimmed = raw.trim();
        if (trimmed.length() < 3) {
            return trimmed.toUpperCase();
        }
        String bankCode = trimmed.substring(0, 3).toUpperCase();
        String rest = trimmed.substring(3);
        return bankCode + rest;
    }

    static String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: reference must be exactly 14 characters";
        }

        String bankCode = reference.substring(0, 3);
        for (int i = 0; i < bankCode.length(); i++) {
            if (!Character.isLetter(bankCode.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        String body = reference.substring(3);
        for (int i = 0; i < body.length(); i++) {
            if (!Character.isDigit(body.charAt(i))) {
                return "Invalid: reference body must be digits";
            }
        }

        String day = reference.substring(3, 5);
        String month = reference.substring(5, 7);
        String year = reference.substring(7, 9);
        String sequence = reference.substring(9, 14);

        StringBuilder display = new StringBuilder();
        display.append("[").append(bankCode).append("] DATE: ").append(day).append("/").append(month).append("/").append(year).append(" | SEQ: ").append(sequence);
        return display.toString();
    }

    public static void main(String[] args) {
        String normalized1 = normalizeReference(" hdf03022600042 ");
        System.out.println(validateAndFormat(normalized1));

        String normalized2 = normalizeReference("12F03022600042");
        System.out.println(validateAndFormat(normalized2));
    }
}
