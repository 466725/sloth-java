package tutorial.interview;
// https://www.jointaro.com/interviews/questions/median-of-two-sorted-arrays/?src=taro75

import java.util.Arrays;

/**
 *
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
 * <p>
 * The overall run time complexity should be O(log (m+n)).
 * <p>
 * Example 1:
 * Input: nums1 = [1,3], nums2 = [2]
 * Output: 2.00000
 * Explanation: merged array = [1,2,3] and median is 2.
 * <p>
 * Example 2:
 * Input: nums1 = [1,2], nums2 = [3,4]
 * Output: 2.50000
 * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 *
 */
public class MedianTwoSortedArrays {
    public static void main(String[] args) {
        MedianTwoSortedArrays solver = new MedianTwoSortedArrays();
        runTest(solver, new int[]{1, 3}, new int[]{2}, 2.0, "Test 1");
        runTest(solver, new int[]{1, 2}, new int[]{3, 4}, 2.5, "Test 2");
        runTest(solver, new int[]{0, 0}, new int[]{0, 0}, 0.0, "Test 3");
        runTest(solver, new int[]{}, new int[]{1}, 1.0, "Test 4");
        runTest(solver, new int[]{2}, new int[]{}, 2.0, "Test 5");
        runTest(solver, new int[]{-5, 3, 6}, new int[]{-2, -1, 4}, 1.0, "Test 6");
        runTest(solver, new int[]{1}, new int[]{2, 3, 4, 5, 6}, 3.5, "Test 7");
        // Edge case: both empty
        try {
            solver.findMedianSortedArrays(new int[]{}, new int[]{});
            System.out.println("Test 8 ❌ FAIL (Expected Exception)");
        } catch (Exception e) {
            System.out.println("Test 8 ✅ PASS (Exception Thrown)");
        }
    }

    private static void runTest(MedianTwoSortedArrays solver,
                                int[] nums1,
                                int[] nums2,
                                double expected,
                                String testName) {
        double result = solver.findMedianSortedArrays(nums1, nums2);
        if (Math.abs(result - expected) < 0.00001)
            System.out.println(testName + " ✅ PASS");
        else {
            System.out.println(testName + " ❌ FAIL");
            System.out.println("nums1: " + Arrays.toString(nums1));
            System.out.println("nums2: " + Arrays.toString(nums2));
            System.out.println("Expected: " + expected);
            System.out.println("Actual:   " + result);
        }
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null)
            return 0;
        if (nums1.length == 0 && nums2.length == 0)
            return 0;
        int totalLength = nums1.length + nums2.length;
        int[] combinedArray = new int[totalLength];
        // Copy elements from both arrays into the combined array
        System.arraycopy(nums1, 0, combinedArray, 0, nums1.length);
        System.arraycopy(nums2, 0, combinedArray, nums1.length, nums2.length);
        Arrays.sort(combinedArray);
        // Determine if the combined array length is even or odd
        if (totalLength % 2 == 0) {
            int right = totalLength / 2;
            int left = right - 1;
            return (double) (combinedArray[left] + combinedArray[right]) / 2;
        } else {
            int middleIndex = totalLength / 2;
            return (double) combinedArray[middleIndex];
        }
    }
}
