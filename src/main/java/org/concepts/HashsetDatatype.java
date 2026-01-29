package org.concepts;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashsetDatatype {
    static Set<String> mySet = new HashSet<>();

    static void main() {
        mySet.add("apple");
        mySet.add("banana");
        mySet.add("cherry");
        System.out.println(mySet);
        mySet.remove("banana");
        System.out.println(mySet);
        //mySet.clear();
        mySet.add("banana");
        mySet.add("banana");
        mySet.add("cherry");
        System.out.println(mySet);

        Iterator<String> iterator = mySet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
