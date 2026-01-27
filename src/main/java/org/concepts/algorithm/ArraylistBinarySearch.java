package org.concepts.algorithm;

import java.util.Random;

/**
 * This class provides a method for performing binary search on an ArrayList.
 */
public class ArraylistBinarySearch {
    private static int[] arr = new int[999];
    static Random random = new Random();
    static int myTarget = random.nextInt(999);

    public int binarySearch(int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // Insert to arr with a random number
    public void insertRandomNumber() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(999);
        }
    }

    public static void main(String[] args) {
        ArraylistBinarySearch searcher = new ArraylistBinarySearch();
        searcher.insertRandomNumber();
        if (searcher.binarySearch(myTarget) != -1) {
            System.out.println("Target found at index: " + searcher.binarySearch(myTarget));
        } else {
            System.out.println("Target not found in the array.");
        }
    }
}