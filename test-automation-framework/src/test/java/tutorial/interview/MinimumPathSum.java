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
        if (grid == null || grid.length == 0 || grid[0].length == 0)
            return 0;

        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        // Initialize DP with large values
        for (int i = 0; i < m; i++)
            Arrays.fill(dp[i], Integer.MAX_VALUE);

        dp[0][0] = grid[0][0];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (i > 0 && dp[i - 1][j] != Integer.MAX_VALUE)
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + grid[i][j]);
                if (j > 0 && dp[i][j - 1] != Integer.MAX_VALUE)
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + grid[i][j]);
            }

        return dp[m - 1][n - 1];
    }
}
