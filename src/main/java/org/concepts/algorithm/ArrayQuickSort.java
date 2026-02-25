package org.concepts.algorithm;

import java.util.Random;

public class ArrayQuickSort {
    private static final int ARRAY_SIZE = 9;
    private static final int MAX_RANDOM_VALUE = 999;
    private static final int[] array = new int[ARRAY_SIZE];
    private static final Random RANDOM = new Random();

    public void quickSort(int[] values, int low, int high) {
        // Stop when the partition has zero or one element.
        if (low >= high) {
            return;
        }
        // Use the rightmost element as pivot and place it in final position.
        int pivotIndex = partition(values, low, high, high);
        // Recursively sort values smaller than pivot.
        quickSort(values, low, pivotIndex - 1);
        // Recursively sort values greater than pivot.
        quickSort(values, pivotIndex + 1, high);
    }

    private static int partition(int[] values, int low, int high, int pivot) {
        int leftPointer = low;
        int rightPointer = high;

        while (leftPointer < rightPointer) {
            // Find a bigger one on the left side
            while (leftPointer < rightPointer && values[leftPointer] <= values[pivot]) {
                leftPointer++;
            }
            // Find a smaller one on the right side
            while (leftPointer < rightPointer && values[rightPointer] >= values[pivot]) {
                rightPointer--;
            }
            // Swap out-of-place values to move them to the correct side.
            swap(values, leftPointer, rightPointer);
        }
        // Move pivot into its sorted position.
        swap(values, leftPointer, pivot);
        return leftPointer;
    }

    private static void swap(int[] values, int i, int j) {
        int temp = values[i];
        values[i] = values[j];
        values[j] = temp;
    }

    // Fill an array with random numbers.
    public void fillWithRandomNumbers(int[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = RANDOM.nextInt(MAX_RANDOM_VALUE);
        }
    }

    public static void main(String[] args) {
        ArrayQuickSort sorter = new ArrayQuickSort();

        // Print unsorted values.
        sorter.fillWithRandomNumbers(array);
        for (int j : array) {
            System.out.println(j);
        }

        System.out.println("=================================================");
        System.out.println("==================After sorting==================");
        System.out.println("=================================================");

        // Sort and print values in ascending order.
        sorter.quickSort(array, 0, array.length - 1);
        for (int j : array) {
            System.out.println(j);
        }
    }
}
