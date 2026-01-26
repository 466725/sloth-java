package org.concepts.generics;

import org.concepts.Polymorphism.Cat;

public class GenericsExample4 {
    // Generic methods
    static void main() {
        print("Hello, Generics!");
        print(42);
        print(3.14);
        print(new Cat());

        print("Hello", "World");
        print(10, 20);
        print("Hello", 42);
    }

    static <T> void print(T t) {
        System.out.println(t);
    }

    static <T, V> void print(T t, V v) {
        System.out.println(t + " " + v);
    }
}
