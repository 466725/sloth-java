package org.concepts;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple HashMap usage demo.
 */
public class HashmapDatatype {
    public static void main(String[] args) {
        Map<String, Integer> dataMap = new HashMap<>();

        demonstrateBasicOperations(dataMap);
        printSeparator();
        demonstratePutIfAbsent(dataMap);
    }

    private static void demonstrateBasicOperations(Map<String, Integer> dataMap) {
        dataMap.put("key1", 1);
        dataMap.put("key2", 2);

        System.out.println("Value for key1: " + dataMap.get("key1"));
        System.out.println("Contains key1? " + dataMap.containsKey("key1"));
        System.out.println("Contains Andy? " + dataMap.containsKey("Andy"));
        System.out.println("Contains value 6? " + dataMap.containsValue(6));

        dataMap.remove("key1");
        printMap("After removing key1", dataMap);

        dataMap.put("key3", 3);
        printMap("After adding key3", dataMap);

        dataMap.put("key1", 11);
        printMap("After re-adding key1", dataMap);
    }

    private static void demonstratePutIfAbsent(Map<String, Integer> dataMap) {
        dataMap.putIfAbsent("key4", 4);
        printMap("After putIfAbsent(key4, 4)", dataMap);

        dataMap.putIfAbsent("key4", 44);
        printMap("After putIfAbsent(key4, 44)", dataMap);
    }

    private static void printMap(String label, Map<String, Integer> dataMap) {
        System.out.println(label + ": " + dataMap);
    }

    private static void printSeparator() {
        System.out.println("------------------------------------------------------------");
    }
}
