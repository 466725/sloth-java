package org.concepts.algorithm;

import java.util.Random;

public class ArraylistQuickSort {
    private static final int[] arr = new int[9];

    public void quickSort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int leftPointer = partition(arr, low, high, high);
        quickSort(arr, low, leftPointer - 1);
        quickSort(arr, leftPointer + 1, high);
    }

    private static int partition(int[] arr, int low, int high, int pivot) {
        int leftPointer = low;
        int rightPointer = high;

        while (leftPointer < rightPointer) {
            // Find a bigger one on the left side
            while (leftPointer < rightPointer && arr[leftPointer] <= arr[pivot]) {
                leftPointer++;
            }
            // Find a smaller one on the right side
            while (leftPointer < rightPointer && arr[rightPointer] >= arr[pivot]) {
                rightPointer--;
            }
            swap(arr, leftPointer, rightPointer);
        }
        swap(arr, leftPointer, pivot);
        return leftPointer;
    }

    private static void swap(int[] arr, int low, int high) {
        int temp = arr[low];
        arr[low] = arr[high];
        arr[high] = temp;
    }

    // Insert to arr with a series of random number
    public void insertRandomNumber() {
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(999);
        }
    }

    static void main() {
        ArraylistQuickSort sorter = new ArraylistQuickSort();

        sorter.insertRandomNumber();
        for (int j : arr) {
            System.out.println(j);
        }

        System.out.println("=================================================");
        System.out.println("==================After sorting==================");
        System.out.println("=================================================");

        sorter.quickSort(arr, 0, arr.length - 1);
        for (int j : arr) {
            System.out.println(j);
        }
    }
}
