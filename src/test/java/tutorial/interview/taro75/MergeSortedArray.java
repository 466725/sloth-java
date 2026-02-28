package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/merge-sorted-array/?src=taro75

import java.util.Arrays;

/**
 *
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n, representing the number of elements in nums1 and nums2 respectively.
 * <p>
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 * <p>
 * The final sorted array should not be returned by the function, but instead be stored inside the array nums1. To accommodate this, nums1 has a length of m + n, where the first m elements denote the elements that should be merged, and the last n elements are set to 0 and should be ignored. nums2 has a length of n.
 * <p>
 * Example 1:
 * Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * Output: [1,2,2,3,5,6]
 * Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
 * The result of the merge is [1,2,2,3,5,6] with the underlined elements coming from nums1.
 * <p>
 * Example 2:
 * Input: nums1 = [1], m = 1, nums2 = [], n = 0
 * Output: [1]
 * Explanation: The arrays we are merging are [1] and [].
 * The result of the merge is [1].
 * <p>
 * Example 3:
 * Input: nums1 = [0], m = 0, nums2 = [1], n = 1
 * Output: [1]
 * Explanation: The arrays we are merging are [] and [1].
 * The result of the merge is [1].
 * Note that because m = 0, there are no elements in nums1. The 0 is only there to ensure the merge result can fit in nums1.
 *
 *
 */
public class MergeSortedArray {
    public static void main(String[] args) {

        MergeSortedArray solver = new MergeSortedArray();

        runTest(solver,
                new int[]{1, 2, 3, 0, 0, 0}, 3,
                new int[]{2, 5, 6}, 3,
                new int[]{1, 2, 2, 3, 5, 6},
                "Test 1");

        runTest(solver,
                new int[]{1}, 1,
                new int[]{}, 0,
                new int[]{1},
                "Test 2");

        runTest(solver,
                new int[]{0}, 0,
                new int[]{1}, 1,
                new int[]{1},
                "Test 3");

        runTest(solver,
                new int[]{4, 5, 6, 0, 0, 0}, 3,
                new int[]{1, 2, 3}, 3,
                new int[]{1, 2, 3, 4, 5, 6},
                "Test 4");

        runTest(solver,
                new int[]{1, 2, 3, 0, 0, 0}, 3,
                new int[]{4, 5, 6}, 3,
                new int[]{1, 2, 3, 4, 5, 6},
                "Test 5");

        runTest(solver,
                new int[]{1, 3, 5, 0, 0, 0}, 3,
                new int[]{2, 4, 6}, 3,
                new int[]{1, 2, 3, 4, 5, 6},
                "Test 6");

        runTest(solver,
                new int[]{2, 2, 3, 0, 0, 0}, 3,
                new int[]{2, 2, 5}, 3,
                new int[]{2, 2, 2, 2, 3, 5},
                "Test 7");
    }

    private static void runTest(MergeSortedArray solver,
                                int[] nums1,
                                int m,
                                int[] nums2,
                                int n,
                                int[] expected,
                                String testName) {

        // Make a copy so original input isn’t destroyed for printing
        int[] nums1Copy = Arrays.copyOf(nums1, nums1.length);

        solver.merge(nums1Copy, m, nums2, n);

        System.out.println("================================");
        System.out.println(testName);
        System.out.println("After merge: " + Arrays.toString(nums1Copy));
        System.out.println("Expected:    " + Arrays.toString(expected));

        if (Arrays.equals(nums1Copy, expected)) {
            System.out.println("Result: ✅ PASS");
        } else {
            System.out.println("Result: ❌ FAIL");
        }
    }

    public void merge(int[] nums1, int nums1Length,
                      int[] nums2, int nums2Length) {
        int mergedArrayLastIndex = nums1Length + nums2Length - 1;
        int nums1LastIndex = nums1Length - 1;
        int nums2LastIndex = nums2Length - 1;

        // Iterate backwards through the merged array
        while (nums1LastIndex >= 0 && nums2LastIndex >= 0) {
            // Choose the larger element from nums1 or nums2.
            if (nums1[nums1LastIndex] > nums2[nums2LastIndex]) {
                nums1[mergedArrayLastIndex] = nums1[nums1LastIndex];
                nums1LastIndex--;
            } else {
                nums1[mergedArrayLastIndex] = nums2[nums2LastIndex];
                nums2LastIndex--;
            }
            mergedArrayLastIndex--;
        }

        // Copy remaining elements from nums2 to nums1 if any.
        // This is needed if nums2 has elements remaining.
        while (nums2LastIndex >= 0) {
            nums1[mergedArrayLastIndex] = nums2[nums2LastIndex];
            nums2LastIndex--;
            mergedArrayLastIndex--;
        }
    }
}
