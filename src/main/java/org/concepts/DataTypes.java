package org.concepts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Example class to demonstrate data types in Java
public class DataTypes {
    // HashSet Example
    public static void hashSetExample() {
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

    // HashMap Example
    public static void hashMapExample() {
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

    // StringBuilder Example (Not Synchronized)
    public static void stringBuilderExample() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append("World!");
        System.out.println(sb);
    }

    // StringBuffer Example (Synchronized)
    public static void stringBufferExample() {
        StringBuffer sb = new StringBuffer();
        sb.append("Hello ").append("World!");
        System.out.println(sb);
    }

    static void main() {
        hashSetExample();
        hashMapExample();
        stringBuilderExample();
        stringBufferExample();
    }
}
