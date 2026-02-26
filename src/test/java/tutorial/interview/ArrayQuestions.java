package tutorial.interview;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ArrayQuestions {
    private static final Random MY_RANDOM = new Random();

    public static void main(String[] args) {
        int[] arr = new int[288];
        insertRandomNumbers(arr);

        System.out.println("Array: " + Arrays.toString(arr));
        int target = 88; //promptForInteger("Please input an integer 0-99: ");
        if (target == Integer.MIN_VALUE) {
            return;
        }
        int index = findIndexOfElementInArray(arr, target);
        if (index >= 0) {
            System.out.println("Found it, index is: " + index);
        } else {
            System.out.println("Couldn't find it!");
        }

        System.out.println("There are " + countOccurrencesOfElementInArray(arr, target) + " times of occurrences");

        int[][] arr2d = {
                {3, 7, 2},
                {9, 5, 1, 2},
                {4, 8, 6, 3, 9}
        };
        int[] coordinate = findCoordinateOfFirstOccurrenceIn2DArray(arr2d, 5);
        System.out.println("Coordinate of first occurrence in 2D array: " + Arrays.toString(coordinate));

        int[][] zeroRectangleArray = {
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };

        int[] topLeftZero = findTopLeftZeroIn2DArray(zeroRectangleArray);
        System.out.println("Top-left zero coordinate: " + Arrays.toString(topLeftZero));

        int[] bottomRightZero = findBottomRightZeroIn2DArray(zeroRectangleArray);
        System.out.println("Bottom-right zero coordinate: " + Arrays.toString(bottomRightZero));

        int[][] rectangleCorners = findTopLeftAndBottomRightZeroRectangleIn2DArray(zeroRectangleArray);
        System.out.println("Zero-rectangle corners [top-left, bottom-right]: " + Arrays.deepToString(rectangleCorners));

        int[] consecutiveArray = {1, 7, 2, 3, 3, 9, 4, 6, 5, 6};
        int consecutiveSize = getSizeOfConsecutiveElementsInArray(consecutiveArray);
        System.out.println("Max size of consecutive elements: " + consecutiveSize);

        int[][] validMatrix = {
                {1, 2, 3},
                {3, 1, 2},
                {2, 3, 1}
        };
        int[][] invalidMatrix = {
                {1, 1, 1},
                {1, 2, 3},
                {1, 2, 3}
        };
        System.out.println("Valid matrix check: " + checkRowAndColumnContainsAllNumbers(validMatrix));
        System.out.println("Invalid matrix check: " + checkRowAndColumnContainsAllNumbers(invalidMatrix));
    }

    private static int promptForInteger(String promptMessage) {
        System.out.print(promptMessage);
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a whole number.");
                return Integer.MIN_VALUE;
            }
            return scanner.nextInt();
        }
    }

    // Fill array with random numbers [0, 99]
    public static void insertRandomNumbers(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = MY_RANDOM.nextInt(100);
        }
    }

    // Return the first index of target, or -1 if not found.
    public static int findIndexOfElementInArray(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == target)
                return i;
        return -1;
    }

    // Counting the occurrences of an element in an array
    public static int countOccurrencesOfElementInArray(int[] arr, int target) {
        if (arr == null || arr.length == 0)
            return 0;
        int count = 0;
        for (int value : arr)
            if (value == target)
                count++;
        return count;
    }

    // Finding the coordinate of the first occurrence of an element in a 2-dimensional array
    public static int[] findCoordinateOfFirstOccurrenceIn2DArray(int[][] arr, int target) {
        if (arr == null || arr.length == 0) {
            return new int[]{-1, -1};
        }

        for (int i = 0; i < arr.length; i++) {
            int[] row = arr[i];
            if (row == null) {
                continue; // handles jagged/null rows safely
            }
            for (int j = 0; j < row.length; j++) {
                if (row[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    //Get the max size of consecutive elements in an array.
    public static int getSizeOfConsecutiveElementsInArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        Arrays.sort(arr);

        int maxSize = 1;
        int currentSize = 1;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                continue; // ignore duplicates
            }
            if (arr[i] == arr[i - 1] + 1) { // Still consecutive
                currentSize++;
            } else { // No more consecutive
                maxSize = Math.max(maxSize, currentSize);
                currentSize = 1;
            }
        }

        return Math.max(maxSize, currentSize);
    }

    // Finding the top-left and bottom-right corners of a rectangle made of zeros in a 2-dimensional array.
    // Returns: {{topLeftRow, topLeftCol}, {bottomRightRow, bottomRightCol}}
    public static int[][] findTopLeftAndBottomRightZeroRectangleIn2DArray(int[][] arr) {
        return new int[][]{
                findTopLeftZeroIn2DArray(arr),
                findBottomRightZeroIn2DArray(arr)
        };
    }

    // Finding the top-left coordinate of zeros in a 2-dimensional array.
    public static int[] findTopLeftZeroIn2DArray(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return new int[]{-1, -1};
        }

        for (int i = 0; i < arr.length; i++) {
            int[] row = arr[i];
            if (row == null) {
                continue;
            }
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    // Finding the bottom-right coordinate of zeros in a 2-dimensional array.
    public static int[] findBottomRightZeroIn2DArray(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return new int[]{-1, -1};
        }

        int rowIndex = -1, columnIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            int[] row = arr[i];
            if (row == null) {
                continue;
            }
            // Check column by column of row i
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 0) {
                    rowIndex = i;
                    columnIndex = j;
                }
            }
        }

        return new int[]{rowIndex, columnIndex};
    }

    // https://www.jointaro.com/interviews/questions/check-if-every-row-and-column-contains-all-numbers/?company=karat
    // Check if Every Row and Column Contains All Numbers
    public static boolean checkRowAndColumnContainsAllNumbers(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (arr[i] == null || arr[i].length != n) {
                return false;
            }
        }

        return checkRowsContainAllNumbers(arr) && checkColumnsContainAllNumbers(arr);
    }

    public static boolean checkRowsContainAllNumbers(int[][] arr) {
        int n = arr.length;
        HashMap<Integer, Boolean> rowHashMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = arr[i][j];
                if (value < 1 || value > n) {
                    return false;
                }
                rowHashMap.put(value, true);
            }
            if (rowHashMap.size() != n) {
                return false;
            }
            rowHashMap.clear();
        }

        return true;
    }

    public static boolean checkColumnsContainAllNumbers(int[][] arr) {
        int n = arr.length;
        HashMap<Integer, Boolean> columnHashMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = arr[j][i];
                if (value < 1 || value > n) {
                    return false;
                }
                columnHashMap.put(value, true);
            }
            if (columnHashMap.size() != n) {
                return false;
            }
            columnHashMap.clear();
        }

        return true;
    }
}
