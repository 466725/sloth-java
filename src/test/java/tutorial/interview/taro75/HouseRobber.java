package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/house-robber/?src=taro75


import java.util.Arrays;

/**
 *
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * <p>
 * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
 * <p>
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 * <p>
 * Example 2:
 * Input: nums = [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 * Total amount you can rob = 2 + 9 + 1 = 12.
 *
 *
 */
public class HouseRobber {
    // ================================
    // 3️⃣ Test Harness
    // ================================
    public static void main(String[] args) {

        HouseRobber solver = new HouseRobber();

        int[][] testCases = {
                {1, 2, 3, 1},        // Expected 4
                {2, 7, 9, 3, 1},     // Expected 12
                {2, 1, 1, 2},        // Expected 4
                {5},                 // Expected 5
                {}                   // Expected 0
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] input = testCases[i];

            System.out.println("=================================");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input: " + Arrays.toString(input));

            int recursiveResult = solver.robRecursive(input, 0);

            System.out.println("Recursive Result: " + recursiveResult);
        }
    }

    private int robRecursive(int[] nums, int currentHouseIndex) {
        // If we've reached the end of the street, there's nothing to rob
        if (currentHouseIndex >= nums.length) {
            return 0;
        }

        // Explore robbing the current house and skipping the next one
        int robCurrentHouse =
                nums[currentHouseIndex] + robRecursive(nums, currentHouseIndex + 2);

        // Explore skipping the current house and moving to the next one
        int skipCurrentHouse = robRecursive(nums, currentHouseIndex + 1);

        // Return the maximum loot we can get from either option
        return Math.max(robCurrentHouse, skipCurrentHouse);
    }
}
