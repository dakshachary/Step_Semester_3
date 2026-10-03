public class ClassTopperFinder {
    static int[] findTopper(int[][] marks) {
        int bestRow = 0;
        int bestTotal = -1;
        for (int i = 0; i < marks.length; i++) {
            int total = 0;
            for (int j = 0; j < marks[i].length; j++) {
                total += marks[i][j];
            }
            if (total > bestTotal) {
                bestTotal = total;
                bestRow = i;
            }
        }
        return new int[]{bestRow, bestTotal};
    }

    public static void main(String[] args) {
        int[][] marks = {{78, 85, 90}, {88, 92, 79}, {65, 70, 95}};
        int[] result = findTopper(marks);
        System.out.println("(" + result[0] + ", " + result[1] + ")");

        int[][] tie = {{50, 50}, {60, 40}, {10, 10}};
        int[] tieResult = findTopper(tie);
        System.out.println("(" + tieResult[0] + ", " + tieResult[1] + ")");
    }
}
