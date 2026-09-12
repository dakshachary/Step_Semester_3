public class PalindromeChecker {

    static boolean isPalindromeIterative(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    static boolean isPalindromeRecursive(String text) {
        if (text.length() <= 1) {
            return true;
        }
        if (text.charAt(0) != text.charAt(text.length() - 1)) {
            return false;
        }
        return isPalindromeRecursive(text.substring(1, text.length() - 1));
    }

    static boolean isPalindromeArrayReversal(String text) {
        char[] original = text.toCharArray();
        char[] reversed = new char[original.length];
        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }
        return new String(reversed).equals(text);
    }

    static String label(boolean result) {
        return result ? "Palindrome" : "Not Palindrome";
    }

    public static void main(String[] args) {
        String[] inputs = {"madam", "hello"};
        for (String input : inputs) {
            boolean iterative = isPalindromeIterative(input);
            boolean recursive = isPalindromeRecursive(input);
            boolean arrayReversal = isPalindromeArrayReversal(input);
            System.out.println("Iterative: " + label(iterative) + " | Recursive: " + label(recursive) + " | Array Reversal: " + label(arrayReversal));
        }
    }
}
