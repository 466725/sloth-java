package org.concepts.generics;

import java.util.Objects;

/**
 * Non-generic printer that only supports {@link Integer}.
 */
public class PrintInt {
    private final Integer value;

    public PrintInt(Integer value) {
        this.value = Objects.requireNonNull(value, "value must not be null");
    }

    public void print() {
        System.out.println(value);
    }
}
