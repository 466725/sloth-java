package org.concepts.generics;

import java.util.Objects;

/**
 * Non-generic printer that only supports {@link Double}.
 */
public class PrintDouble {
    private final Double value;

    public PrintDouble(Double value) {
        this.value = Objects.requireNonNull(value, "value must not be null");
    }

    public void print() {
        System.out.println(value);
    }
}
