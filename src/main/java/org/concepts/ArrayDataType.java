package org.concepts;

//Everything about Array data type
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
        //reverse the array
        for (int i = numbers.length - 1; i >= 0; i--) {
            System.out.println(numbers[i]);
        }
        //reverse number sequences of the array
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
        arrArr[0] = arr1;
        arrArr[1] = arr2;
        //traverse the array and print
        for (int[] arr : arrArr) {
            for (int i : arr) {
                System.out.println(i);
            }
        }
    }
}
