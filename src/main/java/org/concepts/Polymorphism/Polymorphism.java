package org.concepts.Polymorphism;

//Everything about Polymorphism
public class Polymorphism {
    public static void main(String[] args) {
        Animal priorityAnimal = new Dog("Ani", 3, "Brown");
        PetKeeper keeper = new PetKeeper(priorityAnimal);
        keeper.feedPet();
    }
}
