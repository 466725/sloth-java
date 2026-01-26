package org.concepts.generics;

public class PrintInt {
    Integer intToPrint;

    public PrintInt(Integer intToPrint) {
        this.intToPrint = intToPrint;
    }

    public void print() {
        System.out.println(intToPrint);
    }
}
