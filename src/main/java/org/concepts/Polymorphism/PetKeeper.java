package org.concepts.Polymorphism;

public class PetKeeper {
    private Animal pet;
    public PetKeeper(){
    }
    public PetKeeper(Animal pet) {
        this.pet = pet;
    }
    public void feedPet(){
        pet.makeSound();
        if(pet instanceof Cat){
            ((Cat) pet).eat();
        }else if(pet instanceof Dog){
            ((Dog) pet).eat();
        }
    }
    public void setPet(Animal pet) {
        this.pet = pet;
    }
    public Animal getPet() {
        return pet;
    }
}
