package tutorial.interview;
// https://www.jointaro.com/interviews/questions/find-peak-element/?src=taro75

import java.util.Arrays;

/**
 *
 * A peak element is an element that is strictly greater than its neighbors.
 * <p>
 * Given a 0-indexed integer array nums, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.
 * <p>
 * You may imagine that nums[-1] = nums[n] = -∞. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.
 * <p>
 * You must write an algorithm that runs in O(log n) time.
 * <p>
 * Example 1:
 * Input: nums = [1,2,3,1]
 * <p>
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 * <p>
 * Example 3:
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 5
 * Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
 *
 */
public class FindPeakElement {
    // ==============================
    // ✅ Test Harness
    // ==============================
    public static void main(String[] args) {
        FindPeakElement solver = new FindPeakElement();
        int[][] testCases = {
                {1, 2, 3, 1, 9},
                {1, 2, 3, 1, 0},
                {8, 1, 2, 3, 1},
                {0, 1, 2, 3, 1},
                {1, 2, 3, 1},                 // Peak in middle
                {1, 2, 1, 3, 5, 6, 4},        // Multiple peaks
                {1},                          // Single element
                {1, 2, 3, 4, 5},              // Strictly increasing
                {5, 4, 3, 2, 1},              // Strictly decreasing
                {2, 1},                       // Two elements
                {1, 2}                        // Two elements increasing
        };
        System.out.println("===== Find Peak Element Tests =====");
        for (int i = 0; i < testCases.length; i++) {
            int[] input = testCases[i];
            int peakIndex = solver.findPeakElement(input);
            System.out.println("-----------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Array: " + Arrays.toString(input));
            System.out.println("Peak Index: " + peakIndex);
            if (peakIndex != -1) {
                System.out.println("Peak Value: " + input[peakIndex]);
            }
        }
        System.out.println("===================================");
    }

    public int findPeakElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] > nums[middle + 1]) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }
        return left;
    }
}
