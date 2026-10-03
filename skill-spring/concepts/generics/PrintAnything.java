package concepts.generics;

/**
 * Generic printer that can print values of any reference type.
 *
 * @param <T> value type
 */
public class PrintAnything<T> {
    private final T value;

    public PrintAnything(T value) {
        this.value = value;
    }

    public void print() {
        System.out.println(value);
    }
}
