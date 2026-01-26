package org.concepts.generics;

public class GenericsExample1 {

    static void main() {
        System.out.println("Generics Example");
        PrintInt i = new PrintInt(10);
        i.print();
        PrintDouble d = new PrintDouble(10.5);
        d.print();
    }
}
