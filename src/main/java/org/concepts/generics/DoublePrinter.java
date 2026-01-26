package org.concepts.generics;

public class DoublePrinter {
    Double intToPrint;

    public DoublePrinter(Double doubleToPrint) {
        this.intToPrint = doubleToPrint;
    }

    public void print() {
        System.out.println(intToPrint);
    }
}