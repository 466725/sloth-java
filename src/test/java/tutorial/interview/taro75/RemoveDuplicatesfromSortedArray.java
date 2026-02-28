package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/remove-duplicates-from-sorted-array/?src=taro75

import java.util.Arrays;

/**
 *
 * Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once. The relative order of the elements should be kept the same. Then return the number of unique elements in nums.
 * <p>
 * Consider the number of unique elements of nums to be k, to get accepted, you need to do the following things:
 * <p>
 * Change the array nums such that the first k elements of nums contain the unique elements in the order they were present in nums initially. The remaining elements of nums are not important as well as the size of nums.
 * Return k.
 *
 */
public class RemoveDuplicatesfromSortedArray {
    // =========================
    // ✅ Test Harness
    // =========================
    public static void main(String[] args) {

        RemoveDuplicatesfromSortedArray solver =
                new RemoveDuplicatesfromSortedArray();

        int[][] testCases = {
                {0, 0, 1, 1, 1, 2, 2, 3, 3, 4},
                {1, 1, 2},
                {1, 2, 3},
                {1, 1, 1, 1},
                {},
                {5}
        };

        System.out.println("===== Remove Duplicates Tests =====");

        for (int i = 0; i < testCases.length; i++) {

            int[] input = Arrays.copyOf(testCases[i], testCases[i].length);

            System.out.println("--------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Original: " + Arrays.toString(input));

            int k = solver.removeDuplicates(input);

            System.out.println("k (unique count): " + k);

            System.out.print("Modified array (first k elements): [");
            for (int j = 0; j < k; j++) {
                System.out.print(input[j]);
                if (j < k - 1) System.out.print(", ");
            }
            System.out.println("]");
        }

        System.out.println("===================================");
    }

    // =========================
    // Remove Duplicates (O(n), O(1))
    // =========================
    public int removeDuplicates(int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        int writeIndex = 0;

        for (int readIndex = 1; readIndex < nums.length; readIndex++) {

            if (nums[readIndex] != nums[writeIndex]) {
                writeIndex++;
                nums[writeIndex] = nums[readIndex];
            }
        }

        return writeIndex + 1;
    }
}
