package org.concepts.Polymorphism;

/**
 * Cat implementation of {@link Animal}.
 */
public class Cat extends Animal {
    public Cat() {
        super("Cat", 1, "Gray");
    }

    public Cat(String name, int age, String color) {
        super(name, age, color);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Meow");
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats fish.");
    }
}
