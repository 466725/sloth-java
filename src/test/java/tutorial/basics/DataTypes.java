package tutorial.basics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Demonstrates common Java reference-type usage:
 * collections and mutable string classes.
 */
public class DataTypes {
    public static void main(String[] args) {
        printSection("HashSet Example");
        demonstrateHashSet();

        printSection("HashMap Example");
        demonstrateHashMap();

        printSection("StringBuilder Example");
        demonstrateStringBuilder();

        printSection("StringBuffer Example");
        demonstrateStringBuffer();
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }

    private static void demonstrateHashSet() {
        Set<String> fruits = new HashSet<>();

        // Add items (duplicates are ignored)
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("orange");
        fruits.add("apple"); // duplicate -> will not be added

        System.out.println("Fruits: " + fruits); // order is not guaranteed

        // Check membership
        System.out.println("Contains banana? " + fruits.contains("banana"));

        // Remove an item
        fruits.remove("orange");

        // Iterate
        for (String fruit : fruits) {
            System.out.println("Item: " + fruit);
        }

        // Size
        System.out.println("Count: " + fruits.size());
    }

    private static void demonstrateHashMap() {
        Map<String, Integer> inventory = new HashMap<>();

        // Put key-value pairs (adding/updating)
        inventory.put("apple", 10);
        inventory.put("banana", 5);
        inventory.put("orange", 8);

        // Updating an existing key overwrites the value
        inventory.put("banana", 12);

        // Get a value by key
        System.out.println("Bananas in stock: " + inventory.get("banana"));

        // Safe lookup with default if key doesn't exist
        System.out.println("Pears in stock: " + inventory.getOrDefault("pear", 0));

        // Check if a key exists
        System.out.println("Has oranges? " + inventory.containsKey("orange"));

        // Remove an entry
        inventory.remove("orange");

        // Iterate over entries
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Size
        System.out.println("Item types: " + inventory.size());
    }

    // StringBuilder is not synchronized and is typically faster in single-threaded code.
    private static void demonstrateStringBuilder() {
        StringBuilder builder = new StringBuilder();
        builder.append("Hello ").append("World!");
        System.out.println(builder);
    }

    // StringBuffer is synchronized and can be safer in shared multi-threaded contexts.
    private static void demonstrateStringBuffer() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Hello ").append("World!");
        System.out.println(buffer);
    }
}
