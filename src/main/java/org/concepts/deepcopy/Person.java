package org.concepts.deepcopy;

import java.util.Arrays;

class Person {
    String name;
    Address address;     // nested mutable object
    int[] scores;        // nested mutable array

    Person(String name, Address address, int[] scores) {
        this.name = name;
        this.address = address;
        this.scores = scores;
    }

    // SHALLOW copy constructor:
    // - copies references to address and scores (shared!)
    Person(Person other) {
        this.name = other.name;
        this.address = other.address; // shared reference
        this.scores = other.scores;   // shared reference
    }

    // DEEP copy factory:
    // - creates new Address and new array (independent)
    static Person deepCopyOf(Person other) {
        return new Person(
                other.name,
                new Address(other.address),                 // copy nested object
                Arrays.copyOf(other.scores, other.scores.length) // copy array
        );
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', address=" + address + ", scores=" + Arrays.toString(scores) + "}";
    }
}