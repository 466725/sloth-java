package org.concepts.deepcopy;

class Address {
    String city;

    Address(String city) {
        this.city = city;
    }

    // Deep-copy helper (copy constructor)
    Address(Address other) {
        this.city = other.city;
    }

    @Override
    public String toString() {
        return "Address{city='" + city + "'}";
    }
}