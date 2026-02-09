package org.concepts.Polymorphism;

public class Dog extends Animal{
    public Dog() {
        super();
        System.out.println("Dog constructor called, no parameters");
    }
    public Dog(String name, int age, String color) {
        super(name, age, color);
        System.out.println("Dog constructor called, with parameters");
    }
    @Override
    public void makeSound() {
        System.out.println("Woof");
    }
    public void eat(){
        System.out.println("Dog eats meat");
    }
    private void sleep(){
        System.out.println("Dog sleeps privately");
    }
}
