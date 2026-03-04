package org.concepts.algorithm;

import java.util.Random;

public class ArrayInsertionSort {
    private static final int ARRAY_SIZE = 9;
    private static final int MAX_RANDOM_VALUE = 999;
    private static final int[] array = new int[ARRAY_SIZE];
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        ArrayInsertionSort sorter = new ArrayInsertionSort();
        // Fill the array with unsorted random values.
        sorter.fillWithRandomNumbers(array);

        System.out.println("=================================================");
        System.out.println("=================Before sorting==================");
        System.out.println("=================================================");
        for (int j : array) {
            System.out.println(j);
        }

        System.out.println("=================================================");
        System.out.println("==================After sorting==================");
        System.out.println("=================================================");

        // Run insertion sort in ascending order.
        sorter.insertionSort(array);
        for (int j : array) {
            System.out.println(j);
        }
    }

    public void insertionSort(int[] values) {
        // Expand the sorted left partition one element at a time.
        for (int i = 1; i < values.length; i++) {
            int key = values[i];
            int j = i - 1;
            // Shift larger values to make room for key.
            while (j >= 0 && values[j] > key) {
                values[j + 1] = values[j];
                j--;
            }
            values[j + 1] = key;
        }
    }

    // Fill an array with random numbers.
    public void fillWithRandomNumbers(int[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = RANDOM.nextInt(MAX_RANDOM_VALUE);
        }
    }
}
