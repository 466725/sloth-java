package org.concepts.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * This class provides a method for performing binary search on an int[].
 */
public class ArrayBinarySearch {
    private static final int ARRAY_SIZE = 999;
    private static final int MAX_RANDOM_VALUE = 999;
    private static final Random RANDOM = new Random();
    private static final int[] array = new int[ARRAY_SIZE];
    private static final int target = RANDOM.nextInt(MAX_RANDOM_VALUE);

    public static void main(String[] args) {
        ArrayBinarySearch searcher = new ArrayBinarySearch();
        searcher.fillWithRandomNumbers(array);
        // Binary search requires the array to be sorted first.
        searcher.sortArray(array);
        int index = searcher.binarySearch(array, target);
        if (index == -1)
            System.out.println("Target not found in the array.");
        else
            System.out.println("Target found at index: " + index);
    }

    public int binarySearch(int[] values, int target) {
        int low = 0;
        int high = values.length - 1;
        // Iterative binary search on an ascending-sorted array.
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (values[mid] == target)
                return mid;
            else if (values[mid] < target)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return -1;
    }

    // Fill an array with random numbers.
    public void fillWithRandomNumbers(int[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = RANDOM.nextInt(MAX_RANDOM_VALUE);
        }
    }

    public void sortArray(int[] values) {
        Arrays.sort(values);
    }
}
