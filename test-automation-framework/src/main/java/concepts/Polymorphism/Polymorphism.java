package concepts.Polymorphism;

/**
 * Simple runtime polymorphism demo.
 */
public class Polymorphism {
    public static void main(String[] args) {
        Animal priorityAnimal = new Dog("Ani", 3, "Brown");

        PetKeeper keeper = new PetKeeper(priorityAnimal);
        keeper.feedPet();

        keeper.setPet(new Cat("Milo", 2, "Orange"));
        keeper.feedPet();
    }
}
