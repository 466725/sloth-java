package concepts.deepcopy;

import java.util.Arrays;
import java.util.Objects;

/**
 * Person model used to demonstrate shallow copy vs deep copy.
 */
public class Person {
    private String name;
    private final Address address; // nested mutable object
    private final int[] scores;    // nested mutable array

    public Person(String name, Address address, int[] scores) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.address = Objects.requireNonNull(address, "address must not be null");
        this.scores = Arrays.copyOf(Objects.requireNonNull(scores, "scores must not be null"), scores.length);
    }

    // Shallow copy constructor: nested references are shared.
    public Person(Person other) {
        Objects.requireNonNull(other, "other must not be null");
        this.name = other.name;
        this.address = other.address;
        this.scores = other.scores;
    }

    // Deep copy factory: nested references are independent.
    public static Person deepCopyOf(Person other) {
        Objects.requireNonNull(other, "other must not be null");
        return new Person(
                other.name,
                new Address(other.address),
                Arrays.copyOf(other.scores, other.scores.length)
        );
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public void setScoreAt(int index, int value) {
        scores[index] = value;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', address=" + address + ", scores=" + Arrays.toString(scores) + "}";
    }
}
