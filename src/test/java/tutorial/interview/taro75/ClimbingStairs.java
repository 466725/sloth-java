package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/climbing-stairs/?src=taro75

/**
 *
 * You are climbing a staircase. It takes n steps to reach the top.
 * <p>
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 * <p>
 * Example 1:
 * Input: n = 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 * <p>
 * Example 2:
 * Input: n = 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 *
 */

public class ClimbingStairs {
    // ==============================
    // ✅ Test Harness
    // ==============================
    public static void main(String[] args) {

        ClimbingStairs solver = new ClimbingStairs();

        int[] testCases = {0, 1, 2, 3, 4, 5, 10};

        System.out.println("===== Climbing Stairs Tests =====");

        for (int n : testCases) {
            int result = solver.climbStairs(n);
            System.out.println("n = " + n + " → Ways = " + result);
        }

        System.out.println("=================================");
    }

    public int climbStairs(int numberOfSteps) {

        if (numberOfSteps <= 1) {
            return 1;
        }

        // Instead of using an array, we only keep last two results
        int oneStepBefore = 1;  // ways to reach step 1
        int twoStepsBefore = 1; // ways to reach step 0

        for (int currentStep = 2; currentStep <= numberOfSteps; currentStep++) {
            int currentWays = oneStepBefore + twoStepsBefore;

            twoStepsBefore = oneStepBefore;
            oneStepBefore = currentWays;
        }

        return oneStepBefore;
    }
}
