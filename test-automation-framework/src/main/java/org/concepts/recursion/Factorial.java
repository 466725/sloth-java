package org.concepts.recursion;

public class Factorial {
    static int factorial(int n) {
        if (n == 1)
            return 1;
        else
            return (n * factorial(n - 1));
    }

    void main() {
        System.out.println("Factorial of 5 is: " + factorial(5));
    }
}