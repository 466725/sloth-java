package tutorial.basics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Simple HashSet usage demo.
 */
public class HashsetDatatype {
    public static void main(String[] args) {
        Set<String> fruits = new HashSet<>();

        demonstrateBasicOperations(fruits);
        printSeparator();
        demonstrateIteration(fruits);
    }

    private static void demonstrateBasicOperations(Set<String> fruits) {
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("cherry");
        printSet("After initial adds", fruits);

        fruits.remove("banana");
        printSet("After removing banana", fruits);

        // Duplicate values are ignored by HashSet.
        fruits.add("banana");
        fruits.add("banana");
        fruits.add("cherry");
        printSet("After adding duplicates", fruits);
    }

    private static void demonstrateIteration(Set<String> fruits) {
        System.out.println("Iterating over set values:");
        Iterator<String> iterator = fruits.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void printSet(String label, Set<String> values) {
        System.out.println(label + ": " + values);
    }

    private static void printSeparator() {
        System.out.println("------------------------------------------------------------");
    }
}
