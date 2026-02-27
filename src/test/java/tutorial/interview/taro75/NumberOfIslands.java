package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/number-of-islands/?src=taro75

/**
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
 * <p>
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 * <p>
 * Example 1:
 * Input: grid = [
 * ["1","1","1","1","0"],
 * ["1","1","0","1","0"],
 * ["1","1","0","0","0"],
 * ["0","0","0","0","0"]
 * ]
 * Output: 1
 * <p>
 * Example 2:
 * Input: grid = [
 * ["1","1","0","0","0"],
 * ["1","1","0","0","0"],
 * ["0","0","1","0","0"],
 * ["0","0","0","1","1"]
 * ]
 * Output: 3
 *
 */

public class NumberOfIslands {

    public static void main(String[] args) {
        NumberOfIslands solution = new NumberOfIslands();

        // Test 1: Example 1 (Expected: 1)
        char[][] grid1 = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        runTest(solution, grid1, 1, "Test 1");

        // Test 2: Example 2 (Expected: 3)
        char[][] grid2 = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
        runTest(solution, grid2, 3, "Test 2");

        // Test 3: Single cell island (Expected: 1)
        char[][] grid3 = {
                {'1'}
        };
        runTest(solution, grid3, 1, "Test 3");

        // Test 4: Single cell water (Expected: 0)
        char[][] grid4 = {
                {'0'}
        };
        runTest(solution, grid4, 0, "Test 4");

        // Test 5: All water (Expected: 0)
        char[][] grid5 = {
                {'0', '0', '0'},
                {'0', '0', '0'},
                {'0', '0', '0'}
        };
        runTest(solution, grid5, 0, "Test 5");

        // Test 6: All land (Expected: 1)
        char[][] grid6 = {
                {'1', '1', '1'},
                {'1', '1', '1'},
                {'1', '1', '1'}
        };
        runTest(solution, grid6, 1, "Test 6");

        // Test 7: Diagonal islands (Expected: 4)
        char[][] grid7 = {
                {'1', '0', '0'},
                {'0', '1', '0'},
                {'0', '0', '1'}
        };
        runTest(solution, grid7, 3, "Test 7");
    }

    private static void runTest(NumberOfIslands solution, char[][] grid, int expected, String testName) {
        char[][] copy = deepCopy(grid);
        int result = solution.numIslands(copy);

        System.out.println(testName + " -> Expected: " + expected + ", Actual: " + result +
                (result == expected ? " ✅ PASS" : " ❌ FAIL"));
    }

    private static char[][] deepCopy(char[][] original) {
        char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int numberOfRows = grid.length;
        int numberOfColumns = grid[0].length;
        int islandCount = 0;

        for (int currentRowIndex = 0; currentRowIndex < numberOfRows; currentRowIndex++) {
            for (int currentColumnIndex = 0; currentColumnIndex < numberOfColumns; currentColumnIndex++) {
                if (grid[currentRowIndex][currentColumnIndex] == '1') {
                    islandCount++;
                    sinkConnectedLand(grid, currentRowIndex, currentColumnIndex);
                }
            }
        }

        return islandCount;
    }

    private void sinkConnectedLand(char[][] grid, int row, int column) {
        int numberOfRows = grid.length;
        int numberOfColumns = grid[0].length;

        if (row < 0 || row >= numberOfRows || column < 0 || column >= numberOfColumns) {
            return;
        }

        if (grid[row][column] != '1') {
            return;
        }

        grid[row][column] = '0';

        sinkConnectedLand(grid, row + 1, column);
        sinkConnectedLand(grid, row - 1, column);
        sinkConnectedLand(grid, row, column + 1);
        sinkConnectedLand(grid, row, column - 1);
    }
}
