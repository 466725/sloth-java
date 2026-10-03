package tutorial.interview;
// https://www.jointaro.com/interviews/questions/subarray-sum-equals-k/?src=taro75

import java.util.Arrays;

/**
 *
 * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
 * <p>
 * A subarray is a contiguous non-empty sequence of elements within an array.
 * <p>
 * Example 1:
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 * <p>
 * Example 2:
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 *
 */
public class SubarraySumEqualsK {
    /**
     * Brute-force solution
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    public static int subarraySumEqualsK(int[] numbers, int target) {
        if (numbers == null || numbers.length == 0) return 0;
        int result = 0;
        // Iterate through all possible start indices
        for (int i = 0; i < numbers.length; i++) {
            int sum = 0;
            // Expand subarray to the right
            for (int j = i; j < numbers.length; j++) {
                sum += numbers[j];
                if (sum == target)
                    result++;
            }
        }
        return result;
    }

    // ---------------- Test Harness ----------------
    private static void test(int[] nums, int k) {
        int result = subarraySumEqualsK(nums, k);
        System.out.println("=================================");
        System.out.println("nums = " + Arrays.toString(nums));
        System.out.println("k = " + k);
        System.out.println("subarray count = " + result);
    }

    public static void main(String[] args) {
        test(new int[]{1, 1, 1}, 2);      // expected 2
        test(new int[]{1, 2, 3}, 3);      // expected 2
        test(new int[]{3, 4, 7, 2, -3, 1, 4, 2}, 7);  // expected 4
        // edge cases
        test(new int[]{1}, 1);          // expected 1
        test(new int[]{1, 2, 1, 2, 1}, 3);  // expected 4
    }
}
