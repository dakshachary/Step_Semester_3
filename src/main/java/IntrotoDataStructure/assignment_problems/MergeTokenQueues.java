import java.util.Arrays;

public class MergeTokenQueues {
    static int[] mergeTokens(int[] counterA, int[] counterB) {
        int[] merged = new int[counterA.length + counterB.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < counterA.length && j < counterB.length) {
            if (counterA[i] <= counterB[j]) {
                merged[k++] = counterA[i++];
            } else {
                merged[k++] = counterB[j++];
            }
        }
        while (i < counterA.length) {
            merged[k++] = counterA[i++];
        }
        while (j < counterB.length) {
            merged[k++] = counterB[j++];
        }
        return merged;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeTokens(new int[]{3, 8, 15, 20}, new int[]{5, 8, 12})));
        System.out.println(Arrays.toString(mergeTokens(new int[]{}, new int[]{4, 9})));
    }
}
