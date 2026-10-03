package concepts.dynamicprogramming;
// https://leetcode.com/problems/unique-paths/description/

/**
 *
 * 62. Unique Paths
 * <p>
 * There is a robot on an m x n grid. The robot is initially located in the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
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
public class UniquePaths {
    // ==============================
    // ✅ Test Harness
    // ==============================
    public static void main(String[] args) {
        UniquePaths solver = new UniquePaths();
        int[][] testCases = {
                {3, 7},
                {3, 2},
                {1, 1},
                {1, 5},
                {5, 1},
                {3, 3}
        };

        System.out.println("===== Unique Paths Tests =====");
        for (int index = 0; index < testCases.length; index++) {
            int rows = testCases[index][0];
            int cols = testCases[index][1];
            int totalPaths = solver.uniquePaths(rows, cols);

            System.out.println(
                    "Test Case " + (index + 1)
                            + ": m=" + rows
                            + ", n=" + cols
                            + " -> " + totalPaths
            );
        }
    }

    // ==============================
    // Core Solution (Dynamic Programming)
    // ==============================
    public int uniquePaths(int m, int n) {
        int[][] paths = new int[m][n];

        // Only one way to move along the top row: always move right.
        for (int col = 0; col < n; col++) {
            paths[0][col] = 1;
        }

        // Only one way to move along the first column: always move down.
        for (int row = 0; row < m; row++) {
            paths[row][0] = 1;
        }

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                paths[row][col] = paths[row - 1][col] + paths[row][col - 1];
            }
        }

        return paths[m - 1][n - 1];
    }
}
