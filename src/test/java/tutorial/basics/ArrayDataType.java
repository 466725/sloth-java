package tutorial.basics;

import java.util.Arrays;

// Examples for Java array operations.
public class ArrayDataType {
    public static void main(String[] args) {
        demonstrateOneDimensionalArray();
        System.out.println("------------------------------------------------------------");
        demonstrateTwoDimensionalArray();
    }

    private static void demonstrateOneDimensionalArray() {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        System.out.println("Original array: " + Arrays.toString(numbers));

        System.out.println("Indexed traversal:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("numbers[" + i + "] = " + numbers[i]);
        }

        System.out.println("Enhanced for loop traversal:");
        for (int number : numbers) {
            System.out.println(number);
        }

        System.out.println("Reverse order traversal:");
        for (int i = numbers.length - 1; i >= 0; i--) {
            System.out.println(numbers[i]);
        }

        reverseInPlace(numbers);
        System.out.println("Array after in-place reverse: " + Arrays.toString(numbers));
    }

    private static void reverseInPlace(int[] values) {
        for (int left = 0, right = values.length - 1; left < right; left++, right--) {
            int temp = values[left];
            values[left] = values[right];
            values[right] = temp;
        }
    }

    private static void demonstrateTwoDimensionalArray() {
        int[][] matrix = new int[2][];
        int[] firstRow = {1, 2};
        int[] secondRow = {3, 4, 3, 4, 5};
        String[] words = {"Hello", "World"};

        matrix[0] = firstRow;
        matrix[1] = secondRow;

        System.out.println("words length: " + words.length);
        System.out.println("2D array traversal:");
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + "");
            }
            System.out.println("");
        }
    }
}
