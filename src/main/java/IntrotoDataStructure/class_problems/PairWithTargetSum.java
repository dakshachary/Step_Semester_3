import java.util.HashSet;
import java.util.Set;

public class PairWithTargetSum {
    static boolean hasPairBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean hasPairWithSum(int[] nums, int target) {
        Set<Integer> seen = new HashSet<>();
        for (int value : nums) {
            if (seen.contains(target - value)) {
                return true;
            }
            seen.add(value);
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {2, 7, 11, 15};
        int[] b = {3, 4, 6};
        int[] c = {4, 5, 1};
        int[] d = {4, 4, 1};
        System.out.println(hasPairWithSum(a, 9) + " " + hasPairBruteForce(a, 9));
        System.out.println(hasPairWithSum(b, 20) + " " + hasPairBruteForce(b, 20));
        System.out.println(hasPairWithSum(c, 8) + " " + hasPairBruteForce(c, 8));
        System.out.println(hasPairWithSum(d, 8) + " " + hasPairBruteForce(d, 8));
    }
}
