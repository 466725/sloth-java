package org.concepts.Polymorphism;

//Everything about Polymorphism
public class Polymorphism {
    static void main() {
        Animal priorityAnimal = new Dog("Ani", 3, "Brown");
        PetKeeper keeper = new PetKeeper(priorityAnimal);
        keeper.feedPet();
    }
}
