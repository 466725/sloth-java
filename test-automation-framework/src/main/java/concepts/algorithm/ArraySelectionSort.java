package concepts.algorithm;

import java.util.Random;

public class ArraySelectionSort {
    private static final int ARRAY_SIZE = 999;
    private static final int MAX_RANDOM_VALUE = 999;
    private static final int[] array = new int[ARRAY_SIZE];
    private static final Random RANDOM = new Random();

    public void selectionSort(int[] values) {
        for (int i = 0; i < values.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] < values[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = values[i];
            values[i] = values[minIndex];
            values[minIndex] = temp;
        }

    }

    // Fill an array with random numbers.
    public void fillWithRandomNumbers(int[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = RANDOM.nextInt(MAX_RANDOM_VALUE);
        }
    }

    public static void main(String[] args) {
        ArraySelectionSort sorter = new ArraySelectionSort();

        sorter.fillWithRandomNumbers(array);
        for (int j : array) {
            System.out.println(j);
        }

        System.out.println("=================================================");
        System.out.println("==================After sorting==================");
        System.out.println("=================================================");

        sorter.selectionSort(array);
        for (int j : array) {
            System.out.println(j);
        }
    }
}
