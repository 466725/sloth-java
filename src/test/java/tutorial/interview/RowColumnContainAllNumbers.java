package tutorial.interview;
// https://www.jointaro.com/interviews/questions/check-if-every-row-and-column-contains-all-numbers/?company=karat
// Check if Every Row and Column Contains All Numbers

/**
 *
 * An n x n matrix is valid if every row and every column contains all the integers from 1 to n (inclusive).
 * <p>
 * Given an n x n integer matrix, return true if the matrix is valid. Otherwise, return false.
 * <p>
 * Example 1:
 * Input: matrix = [[1,2,3],[3,1,2],[2,3,1]]
 * Output: true
 * Explanation: In this case, n = 3, and every row and column contains the numbers 1, 2, and 3.
 * Hence, we return true.
 * <p>
 * Example 2:
 * Input: matrix = [[1,1,1],[1,2,3],[1,2,3]]
 * Output: false
 * Explanation: In this case, n = 3, but the first row and the first column do not contain the numbers 2 or 3.
 * Hence, we return false.
 *
 */
public class RowColumnContainAllNumbers {
    public static void main(String[] args) {
        RowColumnContainAllNumbers checker = new RowColumnContainAllNumbers();
        runTest(
                "Example 1 valid matrix",
                checker,
                new int[][]{{1, 2, 3}, {3, 1, 2}, {2, 3, 1}},
                true
        );
        runTest(
                "Example 2 invalid duplicates",
                checker,
                new int[][]{{1, 1, 1}, {1, 2, 3}, {1, 2, 3}},
                false
        );
        runTest(
                "Out-of-range value",
                checker,
                new int[][]{{1, 2, 4}, {2, 3, 1}, {3, 1, 2}},
                false
        );
        runTest(
                "Not n x n matrix",
                checker,
                new int[][]{{1, 2}, {2, 1}, {1, 2}},
                false
        );
    }

    private static void runTest(String name, RowColumnContainAllNumbers checker, int[][] matrix, boolean expected) {
        boolean actual = checker.checkValid(matrix);
        String status = actual == expected ? "PASS" : "FAIL";
        System.out.println(status + " - " + name + " | expected=" + expected + ", actual=" + actual);
    }

    public boolean checkValid(int[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return false;
        int numberOfRows = matrix.length;
        for (int i = 0; i < numberOfRows; i++)
            if (matrix[i] == null || matrix[i].length != numberOfRows)
                return false;
        // Check each row for validity
        for (int i = 0; i < numberOfRows; i++) {
            boolean[] rowValues = new boolean[numberOfRows + 1];
            for (int j = 0; j < numberOfRows; j++) {
                int value = matrix[i][j];
                if (value < 1 || value > numberOfRows || rowValues[value])
                    return false;
                rowValues[value] = true;
            }
        }
        // Check each column for validity
        for (int i = 0; i < numberOfRows; i++) {
            boolean[] columnValues = new boolean[numberOfRows + 1];
            for (int j = 0; j < numberOfRows; j++) {
                int value = matrix[j][i];
                if (value < 1 || value > numberOfRows || columnValues[value])
                    return false;
                columnValues[value] = true;
            }
        }
        return true;
    }
}
