package org.concepts.algorithm;

import java.util.Random;

public class ArraylistSelectionSort {
    private static int[] arr = new int[999];

    public void sort() {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }

    }

    // Insert to arr with a random number
    public void insertRandomNumber() {
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(999);
        }
    }

    static void main() {
        ArraylistSelectionSort sorter = new ArraylistSelectionSort();

        sorter.insertRandomNumber();
        for (int j : arr) {
            System.out.println(j);
        }

        System.out.println("=================================================");
        System.out.println("==================After sorting==================");
        System.out.println("=================================================");

        sorter.sort();
        for (int j : arr) {
            System.out.println(j);
        }
    }
}
