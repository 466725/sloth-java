package concepts.Polymorphism;

import java.util.Objects;

/**
 * Demonstrates interacting with pets through the base Animal type.
 */
public class PetKeeper {
    private Animal pet;

    public PetKeeper(Animal pet) {
        this.pet = Objects.requireNonNull(pet, "pet must not be null");
    }

    public void feedPet() {
        Objects.requireNonNull(pet, "pet must not be null");
        System.out.println("Feeding pet: " + pet);
        pet.makeSound();
        pet.eat();
    }

    public Animal getPet() {
        return pet;
    }

    public void setPet(Animal pet) {
        this.pet = Objects.requireNonNull(pet, "pet must not be null");
    }
}
