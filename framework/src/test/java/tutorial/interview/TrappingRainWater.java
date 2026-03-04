package tutorial.interview;
// https://www.jointaro.com/interviews/questions/trapping-rain-water/?src=taro75

import java.util.Arrays;

/**
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.
 * <p>
 * Example 1:
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
 * <p>
 * Example 2:
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 *
 */
public class TrappingRainWater {
    public static void main(String[] args) {
        TrappingRainWater solution = new TrappingRainWater();
        // Example 1
        runTest(solution,
                new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1},
                6,
                "Test 1");
        // Example 2
        runTest(solution,
                new int[]{4, 2, 0, 3, 2, 5},
                9,
                "Test 2");
        // No trapping possible
        runTest(solution,
                new int[]{1, 2, 3, 4, 5},
                0,
                "Test 3");
        // Flat surface
        runTest(solution,
                new int[]{3, 3, 3, 3},
                0,
                "Test 4");
        // Simple valley
        runTest(solution,
                new int[]{2, 0, 2},
                2,
                "Test 5");
        // Deep valley
        runTest(solution,
                new int[]{5, 0, 0, 0, 5},
                15,
                "Test 6");
        // Single bar
        runTest(solution,
                new int[]{5},
                0,
                "Test 7");
        // Two bars
        runTest(solution,
                new int[]{5, 1},
                0,
                "Test 8");
        // Empty array
        runTest(solution,
                new int[]{},
                0,
                "Test 9");
        // Complex case
        runTest(solution,
                new int[]{3, 0, 1, 3, 0, 5},
                8,
                "Test 10");
    }

    private static void runTest(TrappingRainWater solution,
                                int[] input,
                                int expected,
                                String testName) {
        int result = solution.trap(input);
        if (result == expected)
            System.out.println(testName + " ✅ PASS");
        else {
            System.out.println(testName + " ❌ FAIL");
            System.out.println("Input:    " + Arrays.toString(input));
            System.out.println("Expected: " + expected);
            System.out.println("Actual:   " + result);
        }
    }

    public int trap(int[] heights) {
        int totalTrappedWater = 0;
        int totalBars = heights.length;
        if (heights == null || totalBars < 3)
            return 0;
        for (int i = 0; i < totalBars; i++) {
            // Find the tallest wall to the left
            int leftTallestBar = 0;
            for (int j = 0; j < i; j++)
                leftTallestBar = Math.max(leftTallestBar, heights[j]);
            // Find the tallest wall to the right
            int rightTallestBar = 0;
            for (int j = i + 1; j < totalBars; j++)
                rightTallestBar = Math.max(rightTallestBar, heights[j]);
            if (heights[i] < Math.min(leftTallestBar, rightTallestBar))
                totalTrappedWater += Math.min(leftTallestBar, rightTallestBar) - heights[i];
        }
        return totalTrappedWater;
    }
}
