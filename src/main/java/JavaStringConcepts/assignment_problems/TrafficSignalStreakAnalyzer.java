public class TrafficSignalStreakAnalyzer {

    static void findLongestStreak(String signalLog) {
        int maxLength = 1;
        char maxChar = signalLog.charAt(0);
        int currentLength = 1;
        char currentChar = signalLog.charAt(0);

        for (int i = 1; i < signalLog.length(); i++) {
            if (signalLog.charAt(i) == currentChar) {
                currentLength++;
            } else {
                currentChar = signalLog.charAt(i);
                currentLength = 1;
            }
            if (currentLength > maxLength) {
                maxLength = currentLength;
                maxChar = currentChar;
            }
        }

        System.out.println("Longest Streak: '" + maxChar + "' repeated " + maxLength + " times");
    }

    public static void main(String[] args) {
        findLongestStreak("RRGGGYRR");
        findLongestStreak("RRRRYYGG");
    }
}
