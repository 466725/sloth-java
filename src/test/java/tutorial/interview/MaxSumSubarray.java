package tutorial.interview;
// https://www.jointaro.com/interviews/questions/maximum-subarray/?src=taro75

import java.util.Arrays;

/**
 *
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
public class MaxSumSubarray {
    public static void main(String[] args) {
        MaxSumSubarray solver = new MaxSumSubarray();
        int[][] testCases = {
                {-2, 1, -3, 4, -1, 2, 1, -5, 4},  // 6
                {1},                     // 1
                {5, 4, -1, 7, 8},            // 23
                {-1, -2, -3, -4},           // -1
                {0, 0, 0},                 // 0
                {},                      // 0 (based on implementation)
        };
        int[] expected = {
                6,
                1,
                23,
                -1,
                0,
                0
        };
        System.out.println("===== Maximum Subarray Tests =====");
        for (int i = 0; i < testCases.length; i++) {
            int[] input = testCases[i];
            int result = solver.maxSubArrayBruteForce(input);
            System.out.println("----------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input: " + Arrays.toString(input));
            System.out.println("Expected: " + expected[i]);
            System.out.println("Result: " + result);
            System.out.println(result == expected[i] ? "✅ PASS" : "❌ FAIL");
        }
        System.out.println("==================================");
    }

    public int maxSubArrayBruteForce(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        return maxSum;
    }
}
