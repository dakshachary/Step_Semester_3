import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PairSumUnsortedArray {
    static boolean pairBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean pairHashSet(int[] nums, int target) {
        Set<Integer> seen = new HashSet<>();
        for (int value : nums) {
            if (seen.contains(target - value)) {
                return true;
            }
            seen.add(value);
        }
        return false;
    }

    static boolean pairSortedTwoPointers(int[] nums, int target) {
        int[] copy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copy);
        int left = 0;
        int right = copy.length - 1;
        while (left < right) {
            long sum = (long) copy[left] + copy[right];
            if (sum == target) {
                return true;
            }
            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {2, 7, 11, 15};
        int[] b = {3, 4, 6};
        int[] c = {-3, 4, 3, 90};
        System.out.println(pairBruteForce(a, 9) + " " + pairHashSet(a, 9) + " " + pairSortedTwoPointers(a, 9));
        System.out.println(pairBruteForce(b, 20) + " " + pairHashSet(b, 20) + " " + pairSortedTwoPointers(b, 20));
        System.out.println(pairBruteForce(c, 0) + " " + pairHashSet(c, 0) + " " + pairSortedTwoPointers(c, 0));
    }
}
