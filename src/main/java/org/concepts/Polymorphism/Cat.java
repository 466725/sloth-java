package org.concepts.Polymorphism;

public class Cat extends Animal{
    public Cat() {
        super();
        System.out.println("Cat constructor called, no parameters");
    }
    public Cat(String name, int age, String color) {
        super(name, age, color);
        System.out.println("Cat constructor called, no parameters");
    }
    @Override
    public void makeSound() {
        System.out.println("Meow");
    }
    public void eat(){
        System.out.println("Cat eats fish");
    }
}
