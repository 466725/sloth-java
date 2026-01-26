package org.concepts.generics;

public class GenericsPrinter<T> {
    T anythingToPrint;

    public GenericsPrinter(T anythingToPrint) {
        this.anythingToPrint = anythingToPrint;
    }

    public void print() {
        System.out.println(anythingToPrint);
    }
}
