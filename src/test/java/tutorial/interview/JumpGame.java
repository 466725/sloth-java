package tutorial.interview;
// https://www.jointaro.com/interviews/questions/jump-game/?src=taro75

import java.util.Arrays;

/**
 *
 * You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.
 * <p>
 * Return true if you can reach the last index, or false otherwise.
 * <p>
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 * <p>
 * Example 2:
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
 *
 */
public class JumpGame {
    // ==============================
    // ✅ Test Harness
    // ==============================
    public static void main(String[] args) {
        JumpGame solver = new JumpGame();
        int[][] testCases = {
                {2, 3, 1, 1, 4},
                {3, 2, 1, 0, 4},
                {0},
                {2, 0, 0},
                {1, 1, 0, 1}
        };
        System.out.println("===== Jump Game Tests =====");
        for (int i = 0; i < testCases.length; i++) {
            int[] input = testCases[i];
            boolean result = solver.canJump(input);
            System.out.print("Test Case " + (i + 1) + ": ");
            System.out.println(Arrays.toString(input) + " -> " + result);
        }
    }

    public boolean canJump(int[] nums) {
        int lastGoodPosition = nums.length - 1;
        // Iterate backwards to find positions that can reach the end
        for (int i = nums.length - 2; i >= 0; i--)
            // Check can reach
            if (i + nums[i] >= lastGoodPosition)
                // Update the last good position
                lastGoodPosition = i;
        //If first position is 'good', we can reach the end.
        return lastGoodPosition == 0;
    }
}

