package concepts.generics;

import concepts.Polymorphism.Animal;
import concepts.Polymorphism.Cat;
import concepts.Polymorphism.Dog;

/**
 * Demonstrates a reusable generic class that works across many types.
 */
public class GenericsExample2 {

    public static void main(String[] args) {
        PrintAnything<Integer> integerPrinter = new PrintAnything<>(10);
        PrintAnything<Double> doublePrinter = new PrintAnything<>(10.5);
        PrintAnything<String> stringPrinter = new PrintAnything<>("Hello World!");
        PrintAnything<Boolean> booleanPrinter = new PrintAnything<>(true);
        PrintAnything<Character> charPrinter = new PrintAnything<>('a');
        PrintAnything<Animal> animalPrinter = new PrintAnything<>(new Cat());
        PrintAnything<Animal> secondAnimalPrinter = new PrintAnything<>(new Dog());

        integerPrinter.print();
        doublePrinter.print();
        stringPrinter.print();
        booleanPrinter.print();
        charPrinter.print();
        animalPrinter.print();
        secondAnimalPrinter.print();
    }
}
