public class HotWeatherAlerts {
    static int countAlerts(int[] readings, int k, int threshold) {
        long target = (long) k * threshold;
        long sum = 0;
        for (int i = 0; i < k; i++) {
            sum += readings[i];
        }
        int alerts = sum >= target ? 1 : 0;
        for (int i = k; i < readings.length; i++) {
            sum += readings[i];
            sum -= readings[i - k];
            if (sum >= target) {
                alerts++;
            }
        }
        return alerts;
    }

    public static void main(String[] args) {
        int[] readings = {2, 2, 2, 2, 5, 5, 5, 8};
        System.out.println(countAlerts(readings, 3, 4));
    }
}
