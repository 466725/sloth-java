package tutorial.interview;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ArrayBasicQuestions {
    private static final Random MY_RANDOM = new Random();

    public static void main(String[] args) {
        int[] arr = new int[288];
        insertRandomNumbers(arr);

        System.out.println("Array: " + Arrays.toString(arr));
        int target = promptForInteger();
        int index = findIndexOfElementInArray(arr, target);
        if (index >= 0)
            System.out.println("Found it, index is: " + index);
        else
            System.out.println("Couldn't find it!");

        System.out.println("There are " + countOccurrencesOfElementInArray(arr, target) + " times of occurrences");

        int[][] arr2d = {
                {3, 7, 2},
                {9, 5, 1, 2},
                {4, 8, 6, 3, 9}
        };
        int[] coordinate = findCoordinateOfFirstOccurrenceIn2DArray(arr2d, 5);
        System.out.println("Coordinate of first occurrence in 2D array: " + Arrays.toString(coordinate));
    }

    private static int promptForInteger() {
        System.out.print("Please input an integer 0-99: ");
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
        for (int i = 0; i < arr.length; i++)
            arr[i] = MY_RANDOM.nextInt(100);
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
        if (arr == null || arr.length == 0)
            return new int[]{-1, -1};

        for (int i = 0; i < arr.length; i++) {
            int[] row = arr[i];
            if (row == null)
                continue; // handles jagged/null rows safely
            for (int j = 0; j < row.length; j++)
                if (row[j] == target)
                    return new int[]{i, j};
        }
        return new int[]{-1, -1};
    }
}
