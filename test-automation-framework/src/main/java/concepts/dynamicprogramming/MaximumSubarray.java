package concepts.dynamicprogramming;
// https://leetcode.com/problems/maximum-subarray/description/

import java.util.Arrays;

/**
 *
 * Maximum Subarray
 * <p>
 * Given an integer array nums, find the subarray with the largest sum, and return its sum.
 * <p>
 * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: The subarray [4,-1,2,1] has the largest sum 6.
 * <p>
 * Example 2:
 * Input: nums = [1]
 * Output: 1
 * Explanation: The subarray [1] has the largest sum 1.
 * <p>
 * Example 3:
 * Input: nums = [5,4,-1,7,8]
 * Output: 23
 * Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
 *
 */
public class MaximumSubarray {
    /**
     * Kadane's Algorithm
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0)
            throw new IllegalArgumentException("Input array must not be empty");
        int current = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            max = Math.max(max, current);
        }
        return max;
    }

    // ---------------- Test Harness ----------------
    private static void test(int[] nums) {
        int result = maxSubArray(nums);
        System.out.println("Input : " + Arrays.toString(nums));
        System.out.println("Output: " + result);
        System.out.println("--------------------------------");
    }

    public static void main(String[] args) {
        test(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}); // expected 6
        test(new int[]{1});                     // expected 1
        test(new int[]{5, 4, -1, 7, 8});            // expected 23

        // additional edge cases
        test(new int[]{-3, -2, -1});              // expected -1
        test(new int[]{0, 0, 0});                 // expected 0
        test(new int[]{2, -1, 2, 3, 4, -5});         // expected 10
    }
}
