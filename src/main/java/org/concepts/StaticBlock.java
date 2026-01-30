package org.concepts;

public class StaticBlock {
    static void main() {
        StaticBlock obj = new StaticBlock();
        System.out.println("Main method is executed");
    }

    public StaticBlock() {
        System.out.println("Constructor is called");
    }

    static {
        System.out.println("Static block is executed first");
    }
}
