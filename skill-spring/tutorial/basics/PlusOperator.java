package tutorial.basics;

import java.util.Scanner;

/**
 * Demonstrates how Java's '+' behaves for numbers, chars, and strings.
 */
public class PlusOperator {
    public static void main(String[] args) {
        printBasicPlusExamples();
        printCharAndStringExamples();
        readNumberAndAdd();
    }

    private static void printBasicPlusExamples() {
        // Numeric addition when both operands are numbers.
        System.out.println("Hello, World!");
        System.out.println(1 + 1);

        // String concatenation when either operand is a string.
        System.out.println(2.5 + "abc");
        System.out.println(2 + 2 + "abc");
        System.out.println("abc" + true);
    }

    private static void printCharAndStringExamples() {
        // char is promoted to int during numeric addition.
        char c = 'a';
        System.out.println(c + 1);
        System.out.println('a' + 1);

        // If one side is a string, result is concatenation.
        System.out.println("a" + 1);
        System.out.println('a' + "abc");
        System.out.println("a" + "abc");
    }

    private static void readNumberAndAdd() {
        // Simple input demo to show arithmetic with user input.
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter a number:");
            int number = scanner.nextInt();
            System.out.println("The number entered is: " + number);
            System.out.println("The number entered + 666 is: " + (number + 666));
        }
    }
}
