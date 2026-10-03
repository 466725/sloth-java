package concepts.generics;

/**
 * Demonstrates the limitation of non-generic classes.
 */
public class GenericsExample1 {

    public static void main(String[] args) {
        System.out.println("Non-generic printers:");

        PrintInt integerPrinter = new PrintInt(10);
        integerPrinter.print();

        PrintDouble doublePrinter = new PrintDouble(10.5);
        doublePrinter.print();
    }
}
