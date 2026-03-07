package tutorial.interview;
// https://leetcode.com/problems/minimum-path-sum/description/

import java.util.Arrays;

/**
 *
 * 64. Minimum Path Sum
 * <p>
 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.
 * <p>
 * Note: You can only move either down or right at any point in time.
 * <p>
 * Example 1:
 * Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
 * Output: 7
 * Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
 * <p>
 * Example 2:
 * Input: grid = [[1,2,3],[4,5,6]]
 * Output: 12
 *
 */

public class MinimumPathSum {
    // -------------------------
    // Test Harness
    // -------------------------
    public static void main(String[] args) {
        MinimumPathSum solver = new MinimumPathSum();
        int[][] grid1 = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        int[][] grid2 = {
                {1, 2, 3},
                {4, 5, 6}
        };
        int[][] grid3 = {
                {1}
        };
        runTest(solver, grid1, 7);
        runTest(solver, grid2, 12);
        runTest(solver, grid3, 1);
    }

    private static void runTest(MinimumPathSum solver, int[][] grid, int expected) {
        System.out.println("Grid:");
        printGrid(grid);
        int result = solver.minPathSum(grid);
        System.out.println("Result   : " + result);
        System.out.println("Expected : " + expected);
        System.out.println(result == expected ? "PASS" : "FAIL");
        System.out.println("---------------------------");
    }

    private static void printGrid(int[][] grid) {
        for (int[] row : grid)
            System.out.println(Arrays.toString(row));
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];

        dp[0] = grid[0][0];
        for (int j = 1; j < n; j++)
            dp[j] = dp[j-1] + grid[0][j];

        for (int i = 1; i < m; i++) {
            dp[0] += grid[i][0];
            for (int j = 1; j < n; j++)
                dp[j] = Math.min(dp[j], dp[j-1]) + grid[i][j];
        }

        return dp[n-1];
    }
}
