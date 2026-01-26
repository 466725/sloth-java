package org.concepts.generics;

public class PrintDouble {
    Double intToPrint;

    public PrintDouble(Double doubleToPrint) {
        this.intToPrint = doubleToPrint;
    }

    public void print() {
        System.out.println(intToPrint);
    }
}