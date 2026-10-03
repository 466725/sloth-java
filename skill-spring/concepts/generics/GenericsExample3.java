package concepts.generics;

import concepts.Polymorphism.Animal;
import concepts.Polymorphism.Cat;
import concepts.Polymorphism.Dog;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates wildcard bounds:
 * - {@code ? extends T} for producers (read)
 * - {@code ? super T} for consumers (write)
 */
public class GenericsExample3 {
    public static void main(String[] args) {
        ArrayList<Animal> animalList = new ArrayList<>();
        animalList.add(new Cat());
        animalList.add(new Dog());
        printAnimalNames(animalList);

        List<? super Animal> animalConsumer = new ArrayList<>();
        addSampleAnimals(animalConsumer);
        System.out.println(animalConsumer);

        List<Cat> catList = new ArrayList<>();
        catList.add(new Cat());
        catList.add(new Cat());
        printAnimalNames(catList);
    }

    static void addSampleAnimals(List<? super Animal> animals) {
        animals.add(new Cat());
        animals.add(new Dog());
    }

    static void printAnimalNames(List<? extends Animal> animals) {
        for (Animal animal : animals) {
            System.out.println(animal.getName());
        }
    }
}
