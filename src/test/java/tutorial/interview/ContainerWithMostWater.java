package tutorial.interview;
// https://www.jointaro.com/interviews/questions/container-with-most-water/?src=taro75

import java.util.Arrays;

import static java.lang.Integer.max;
import static java.lang.Math.min;

/**
 *
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
 * <p>
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 * <p>
 * Return the maximum amount of water a container can store.
 * <p>
 * Notice that you may not slant the container.
 * <p>
 * Example 1:
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
 * <p>
 * Example 2:
 * Input: height = [1,1]
 * Output: 1
 *
 */
public class ContainerWithMostWater {
    // ==================================
    // ✅ Test Harness
    // ==================================
    public static void main(String[] args) {

        ContainerWithMostWater solver = new ContainerWithMostWater();

        int[][] testCases = {
                {1, 8, 6, 2, 5, 4, 8, 3, 7},   // Expected: 49
                {1, 1},                 // Expected: 1
                {4, 3, 2, 1, 4},           // Expected: 16
                {1, 2, 1},               // Expected: 2
                {2, 3, 10, 5, 7, 8, 9},      // Expected: 36
                {1, 2, 3, 4, 5},           // Increasing
                {5, 4, 3, 2, 1}            // Decreasing
        };

        System.out.println("==== Container With Most Water Tests ====");

        for (int i = 0; i < testCases.length; i++) {

            int[] input = Arrays.copyOf(testCases[i], testCases[i].length);

            System.out.println("------------------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input:  " + Arrays.toString(input));

            int result = solver.maxArea(input);

            System.out.println("Output: " + result);
        }

        System.out.println("==========================================");
    }

    public int maxArea(int[] heightArray) {
        int leftPointer = 0;
        int rightPointer = heightArray.length - 1;
        int maxWater = 0;
        if (heightArray == null || heightArray.length < 2) {
            return 0;
        }
        while (leftPointer < rightPointer) {
            // We want to maximize the area, which depends on height and width.
            maxWater = max(maxWater, (rightPointer - leftPointer) * min(heightArray[rightPointer], heightArray[leftPointer]));

            // Move the pointer of the shorter bar inwards.
            // This is because moving the taller bar won't increase height and decreases width.
            if (heightArray[leftPointer] < heightArray[rightPointer]) {
                leftPointer++;
            } else {
                rightPointer--;
            }
        }

        // The loop terminates when pointers meet, having explored all potentially optimal pairs.
        return maxWater;
    }
}
