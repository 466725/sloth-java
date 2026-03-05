package tutorial.interview;
// https://leetcode.com/problems/unique-paths/description/

/**
 *
 * 62. Unique Paths
 * <p>
 * There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
 * <p>
 * Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 * <p>
 * The test cases are generated so that the answer will be less than or equal to 2 * 109.
 * <p>
 * Example 1:
 * Input: m = 3, n = 7
 * Output: 28
 * <p>
 * Example 2:
 * Input: m = 3, n = 2
 * Output: 3
 * <p>
 * Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Down -> Down
 * 2. Down -> Down -> Right
 * 3. Down -> Right -> Down
 *
 */
public class UniquePath {
    // ==============================
    // ✅ Test Harness
    // ==============================
    public static void main(String[] args) {
        UniquePath solver = new UniquePath();
        int[][] testCases = {
                {3, 7},
                {3, 2},
                {1, 1},
                {1, 5},
                {5, 1},
                {3, 3}
        };
        System.out.println("===== Unique Paths Tests =====");
        for (int i = 0; i < testCases.length; i++) {
            int m = testCases[i][0];
            int n = testCases[i][1];
            int result = solver.uniquePaths(m, n);
            System.out.println(
                    "Test Case " + (i + 1) +
                            ": m=" + m +
                            ", n=" + n +
                            " -> " + result
            );
        }
    }

    // ==============================
    // Core Solution (Dynamic Programming)
    // ==============================
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        // First column
        for (int i = 0; i < m; i++)
            dp[i][0] = 1;

        // First row
        for (int j = 0; j < n; j++)
            dp[0][j] = 1;

        // Fill the DP table
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }

        return dp[m - 1][n - 1];
    }
}
