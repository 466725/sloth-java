package org.concepts;

/**
 * Demonstrates execution order of static blocks, constructor, and main method.
 */
public class StaticBlockDemo {
    static {
        System.out.println("1) Static block executes when the class is loaded.");
    }

    public StaticBlockDemo() {
        System.out.println("2) Constructor executes when an object is created.");
    }

    public static void main(String[] args) {
        new StaticBlockDemo();
        System.out.println("3) Main method continues after object creation.");
    }
}
