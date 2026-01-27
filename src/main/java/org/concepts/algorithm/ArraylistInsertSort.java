package org.concepts.algorithm;

import java.util.Random;

public class ArraylistInsertSort {
    private static final int[] arr = new int[9];

    public void insertSort() {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }


    // Insert to arr with a series of random number
    public void insertRandomNumber() {
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(999);
        }
    }

    static void main() {
        ArraylistInsertSort arraylistInsertSort = new ArraylistInsertSort();
        arraylistInsertSort.insertRandomNumber();
        arraylistInsertSort.insertSort();

        for (int j : arr) {
            System.out.println(j);
        }

        System.out.println("=================================================");
        System.out.println("==================After sorting==================");
        System.out.println("=================================================");

        arraylistInsertSort.insertSort();
        for (int j : arr) {
            System.out.println(j);
        }
    }
}
