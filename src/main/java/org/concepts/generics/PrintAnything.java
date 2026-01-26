package org.concepts.generics;

public class PrintAnything<T> {
    T anythingToPrint;

    public PrintAnything(T anythingToPrint) {
        this.anythingToPrint = anythingToPrint;
    }

    public void print() {
        System.out.println(anythingToPrint);
    }
}
