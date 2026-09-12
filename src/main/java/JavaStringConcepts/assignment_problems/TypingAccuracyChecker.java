public class TypingAccuracyChecker {

    static void checkTypingAccuracy(String original, String typed) {
        int length = original.length();
        int matched = 0;
        int firstMismatchIndex = -1;

        for (int i = 0; i < length; i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else if (firstMismatchIndex == -1) {
                firstMismatchIndex = i;
            }
        }

        double accuracy = (matched * 100.0) / length;
        String accuracyText = String.format("%.2f", accuracy);

        if (firstMismatchIndex == -1) {
            System.out.println("Matched: " + matched + "/" + length + " | Accuracy: " + accuracyText + "% | No Mismatches");
        } else {
            System.out.println("Matched: " + matched + "/" + length + " | Accuracy: " + accuracyText + "% | First Mismatch at position " + (firstMismatchIndex + 1) + " ('" + original.charAt(firstMismatchIndex) + "' vs '" + typed.charAt(firstMismatchIndex) + "')");
        }
    }

    public static void main(String[] args) {
        checkTypingAccuracy("hello world", "hello worlt");
        checkTypingAccuracy("coding", "coding");
    }
}
