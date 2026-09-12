public class WellnessReport {

    static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        }
        if (bmi < 25) {
            return "Normal";
        }
        if (bmi < 30) {
            return "Overweight";
        }
        return "Obese";
    }

    static void printWellnessReport(double[] heights, double[] weights) {
        System.out.println("Person | Height (m) | Weight (kg) | BMI | Status");
        for (int i = 0; i < heights.length; i++) {
            double bmi = weights[i] / (heights[i] * heights[i]);
            String status = getBmiStatus(bmi);
            System.out.printf("%d | %.2f | %.2f | %.2f | %s%n", (i + 1), heights[i], weights[i], bmi, status);
        }
    }

    public static void main(String[] args) {
        double[] heights = {1.75, 1.60, 1.68, 1.82, 1.55, 1.90, 1.72, 1.65, 1.78, 1.60};
        double[] weights = {70, 90, 55, 95, 48, 100, 68, 72, 60, 85};
        printWellnessReport(heights, weights);
    }
}
