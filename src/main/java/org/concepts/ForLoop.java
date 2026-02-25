package org.concepts;

/**
 * Simple examples of using Java for-loops.
 */
public class ForLoop {
    private static final int UPPER_BOUND = 3;

    public static void main(String[] args) {
        printNumbersFromOneTo(UPPER_BOUND);
        int totalSum = sumFromOneTo(UPPER_BOUND);
        System.out.println("Sum of numbers from 1 to " + UPPER_BOUND + " is: " + totalSum);

        int oddSum = sumOddNumbersFromOneTo(UPPER_BOUND);
        System.out.println("Sum of odd numbers from 1 to " + UPPER_BOUND + " is: " + oddSum);
    }

    private static void printNumbersFromOneTo(int upperBound) {
        for (int number = 1; number <= upperBound; number++) {
            System.out.println(number);
        }
    }

    private static int sumFromOneTo(int upperBound) {
        int sum = 0;
        for (int number = 1; number <= upperBound; number++) {
            sum += number;
        }
        return sum;
    }

    private static int sumOddNumbersFromOneTo(int upperBound) {
        int sum = 0;
        for (int number = 1; number <= upperBound; number++) {
            if (number % 2 != 0) {
                sum += number;
            }
        }
        return sum;
    }
}
