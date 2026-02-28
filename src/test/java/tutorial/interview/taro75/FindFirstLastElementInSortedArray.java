package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/find-first-and-last-position-of-element-in-sorted-array/?src=taro75

import java.util.Arrays;

/**
 *
 * Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.
 * <p>
 * If target is not found in the array, return [-1, -1].
 * <p>
 * You must write an algorithm with O(log n) runtime complexity.
 * <p>
 * Example 1:
 * Input: nums = [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 * <p>
 * Example 2:
 * Input: nums = [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 * <p>
 * Example 3:
 * Input: nums = [], target = 0
 * Output: [-1,-1]v
 *
 */
public class FindFirstLastElementInSortedArray {
    public static void main(String[] args) {

        FindFirstLastElementInSortedArray solver =
                new FindFirstLastElementInSortedArray();

        runTest(solver, new int[]{5, 7, 7, 8, 8, 10}, 8, new int[]{3, 4}, "Test 1");
        runTest(solver, new int[]{5, 7, 7, 8, 8, 10}, 6, new int[]{-1, -1}, "Test 2");
        runTest(solver, new int[]{}, 0, new int[]{-1, -1}, "Test 3");
        runTest(solver, new int[]{1}, 1, new int[]{0, 0}, "Test 4");
        runTest(solver, new int[]{2, 2, 2, 2}, 2, new int[]{0, 3}, "Test 5");
        runTest(solver, new int[]{1, 2, 3, 4, 5}, 1, new int[]{0, 0}, "Test 6");
        runTest(solver, new int[]{1, 2, 3, 4, 5}, 5, new int[]{4, 4}, "Test 7");
        runTest(solver, new int[]{1, 3, 3, 3, 5, 7}, 3, new int[]{1, 3}, "Test 8");
    }

    private static void runTest(FindFirstLastElementInSortedArray solver,
                                int[] nums,
                                int target,
                                int[] expected,
                                String testName) {

        int[] result = solver.searchRange(nums, target);

        System.out.println("================================");
        System.out.println(testName);
        System.out.println("Array:    " + Arrays.toString(nums));
        System.out.println("Target:   " + target);
        System.out.println("Expected: " + Arrays.toString(expected));
        System.out.println("Actual:   " + Arrays.toString(result));

        if (Arrays.equals(result, expected)) {
            System.out.println("Result: ✅ PASS");
        } else {
            System.out.println("Result: ❌ FAIL");
        }
    }

    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};

        result[0] = findFirst(nums, target);
        result[1] = findLast(nums, target);

        return result;
    }

    private int findFirst(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int firstPosition = -1;

        while (left <= right) {
            int middle = left + (right - left) / 2;

            if (nums[middle] < target) {
                left = middle + 1;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                // Potential first occurrence, keep searching left
                firstPosition = middle;

                right = middle - 1;

            }
        }

        return firstPosition;
    }

    private int findLast(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int lastPosition = -1;

        while (left <= right) {
            int middle = left + (right - left) / 2;

            if (nums[middle] < target) {
                left = middle + 1;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                // Potential last occurrence, keep searching right
                lastPosition = middle;

                left = middle + 1;

            }
        }

        return lastPosition;
    }
}
