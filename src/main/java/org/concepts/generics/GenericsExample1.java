package org.concepts.generics;

public class GenericsExample1 {

    static void main() {
        System.out.println("Generics Example");
        IntegerPrinter ip = new IntegerPrinter(10);
        ip.print();
        DoublePrinter dp = new DoublePrinter(10.5);
        dp.print();
    }
}
