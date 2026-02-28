package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/move-zeroes/?src=taro75

import java.util.Arrays;

/**
 *
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 * <p>
 * Note that you must do this in-place without making a copy of the array.
 * <p>
 * Example 1:
 * Input: nums = [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 * <p>
 * Example 2:
 * Input: nums = [0]
 * Output: [0]
 *
 */
public class MoveZeroes {
    // ==================================
    // ✅ Test Harness
    // ==================================
    public static void main(String[] args) {
        MoveZeroes solver = new MoveZeroes();

        int[][] testCases = {
                {0, 1, 0, 3, 12},   // Expected: [1, 3, 12, 0, 0]
                {0},                // Expected: [0]
                {1, 2, 3},          // Expected: [1, 2, 3]
                {0, 0, 0},          // Expected: [0, 0, 0]
                {4, 0, 5, 0, 0, 3}  // Expected: [4, 5, 3, 0, 0, 0]
        };

        System.out.println("==== Move Zeroes Tests ====");

        for (int i = 0; i < testCases.length; i++) {
            int[] input = Arrays.copyOf(testCases[i], testCases[i].length);

            System.out.println("---------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Before: " + Arrays.toString(input));

            solver.moveZeroes(input);

            System.out.println("After:  " + Arrays.toString(input));
        }

        System.out.println("=================================");
    }

    public void moveZeroes(int[] nums) {
        int insertPos = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[insertPos];
                nums[insertPos] = nums[i];
                nums[i] = temp;
                insertPos++;
            }
        }
    }
}
