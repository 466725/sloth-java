package tutorial.basics;

import java.util.Random;
import java.util.Scanner;

/**
 * Demonstrates common while-loop patterns.
 */
public class WhileLoop {
    private static final int MAX_NUMBER = 10;
    private static final int MIN_GUESS = 1;
    private static final int MAX_GUESS = 100;

    public static void main(String[] args) {
        printNumbersZeroToNine();
        printOddSumFromOneToNine();

        // Reuse a single scanner for all input operations.
        try (Scanner scanner = new Scanner(System.in)) {
            playGuessNumberGame(scanner);
        }
    }

    private static void printNumbersZeroToNine() {
        int number = 0;
        // Print numbers from 0 to 9.
        while (number < MAX_NUMBER) {
            System.out.println(number);
            number++;
        }
    }

    private static void printOddSumFromOneToNine() {
        int number = 1;
        int sum = 0;
        // Sum odd numbers from 1 to 9.
        while (number < MAX_NUMBER) {
            if (number % 2 != 0) {
                sum += number;
            }
            number++;
        }
        System.out.println("Sum of odd numbers from 1 to 9 is: " + sum);
    }

    private static void playGuessNumberGame(Scanner scanner) {
        int targetNumber = new Random().nextInt(MIN_GUESS, MAX_GUESS + 1);
        System.out.println("Please guess and enter a number between 1 and 100:");

        int guess = scanner.nextInt();
        while (guess != targetNumber) {
            if (guess > targetNumber) {
                System.out.println("Too big. Please try again:");
            } else {
                System.out.println("Too small. Please try again:");
            }
            guess = scanner.nextInt();
        }

        System.out.println("Bingo!");
        System.out.println("Congratulations! You guessed the correct number!");
    }
}
