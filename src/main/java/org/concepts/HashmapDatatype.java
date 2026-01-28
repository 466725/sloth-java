package org.concepts;

import java.util.HashMap;

public class HashmapDatatype {
    static HashMap<String, Integer> dataMap = new HashMap<>();

    static void main() {
        dataMap.put("key1", 1);
        dataMap.put("key2", 2);
        System.out.println(dataMap.get("key1"));
        System.out.println(dataMap.containsKey("key1"));
        System.out.println(dataMap.containsKey("Andy"));
        System.out.println(dataMap.containsValue(6));

        dataMap.remove("key1");
        System.out.println(dataMap);
        dataMap.put("key3", 3);
        System.out.println(dataMap);
        dataMap.put("key1", 11);
        System.out.println(dataMap);

        dataMap.putIfAbsent("key4", 4);
        System.out.println(dataMap);
        dataMap.putIfAbsent("key4", 44);
        System.out.println(dataMap);
    }
}
