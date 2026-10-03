public class MaximizeContainerArea {
    static long maxContainerAreaBruteForce(int[] heights) {
        long best = 0;
        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {
                best = Math.max(best, (long) Math.min(heights[i], heights[j]) * (j - i));
            }
        }
        return best;
    }

    static long maxContainerArea(int[] heights) {
        int left = 0;
        int right = heights.length - 1;
        long best = 0;
        while (left < right) {
            long area = (long) Math.min(heights[left], heights[right]) * (right - left);
            best = Math.max(best, area);
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxContainerArea(heights));
        System.out.println(maxContainerAreaBruteForce(heights));
    }
}
