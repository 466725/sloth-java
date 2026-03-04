package org.concepts.generics;

import org.concepts.Polymorphism.Cat;

/**
 * Demonstrates generic methods with one and two type parameters.
 */
public class GenericsExample4 {
    public static void main(String[] args) {
        printValue("Hello, Generics!");
        printValue(42);
        printValue(3.14);
        printValue(new Cat());

        printPair("Hello", "World");
        printPair(10, 20);
        printPair("Hello", 42);
    }

    static <T> void printValue(T value) {
        System.out.println(value);
    }

    static <T, U> void printPair(T first, U second) {
        System.out.println(first + " " + second);
    }
}
