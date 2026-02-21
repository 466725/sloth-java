package org.concepts;

// Examples for Java array operations.
public class ArrayDataType {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }
        System.out.println("Enhanced for loop:");
        for (int number : numbers) {
            System.out.println(number);
        }
        // Print elements in reverse order.
        for (int i = numbers.length - 1; i >= 0; i--) {
            System.out.println(numbers[i]);
        }
        // Reverse the array in place.
        int temp = numbers[0];
        for (int i = 0; i < numbers.length / 2; i++) {
            temp = numbers[i];
            numbers[i] = numbers[numbers.length - i - 1];
            numbers[numbers.length - i - 1] = temp;
        }
        for (int number : numbers) {
            System.out.println(number);
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        int[][] arrArr = new int[2][3];
        int[] arr1 = {1, 2};
        int[] arr2 = {3, 4, 3, 4, 5};
        String[] arr3 = {"Hello", "World"};
        System.out.println(arr3.length);
        arrArr[0] = arr1;
        arrArr[1] = arr2;
        // Traverse the 2D array and print values.
        for (int[] arr : arrArr) {
            for (int i : arr) {
                System.out.println(i);
            }
        }
    }
}
