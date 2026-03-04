package org.concepts.Polymorphism;

/**
 * Dog implementation of {@link Animal}.
 */
public class Dog extends Animal {
    public Dog() {
        super("Dog", 1, "Brown");
    }

    public Dog(String name, int age, String color) {
        super(name, age, color);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Woof");
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats meat.");
    }

    private void sleep() {
        System.out.println(getName() + " sleeps privately.");
    }
}
