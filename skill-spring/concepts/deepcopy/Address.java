package concepts.deepcopy;

import java.util.Objects;

/**
 * Simple mutable value object used in deep-copy demonstrations.
 */
public class Address {
    private String city;

    public Address(String city) {
        this.city = Objects.requireNonNull(city, "city must not be null");
    }

    // Copy constructor.
    public Address(Address other) {
        this.city = Objects.requireNonNull(other, "other must not be null").city;
    }

    public void setCity(String city) {
        this.city = Objects.requireNonNull(city, "city must not be null");
    }

    @Override
    public String toString() {
        return "Address{city='" + city + "'}";
    }
}
