package org.concepts.Polymorphism;

// Simple polymorphism example.
public class Polymorphism {
    static void main() {
        Animal priorityAnimal = new Dog("Ani", 3, "Brown");
        PetKeeper keeper = new PetKeeper(priorityAnimal);
        keeper.feedPet();
    }
}
