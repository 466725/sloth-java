package org.concepts.generics;

import org.concepts.Polymorphism.Animal;
import org.concepts.Polymorphism.Cat;
import org.concepts.Polymorphism.Dog;

import java.util.ArrayList;
import java.util.List;

public class GenericsExample3 {
    static void main() {
        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(10);
        intList.add(20);
        System.out.println(intList.get(0));
        System.out.println(intList.get(1));
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Hello");
        stringList.add("World");
        System.out.println(stringList.get(0));
        System.out.println(stringList.get(1));
        ArrayList<Animal> animalList = new ArrayList<>();
        animalList.add(new Cat());
        animalList.add(new Dog());
        System.out.println(animalList.get(0).getName());
        System.out.println(animalList.get(1).getName());

        List<Integer> integerList = new ArrayList<>();
        integerList.add(10);
        integerList.add(99);
        print(integerList);

        ArrayList<Animal> anythingList = new ArrayList<>();
        anythingList.add(new Cat());
        anythingList.add(new Dog());
        System.out.println(anythingList.get(0).getName());
        System.out.println(anythingList.get(1).getName());
        List<? super Animal> anythingList2 = new ArrayList<>();
        anythingList2.add(new Cat());
        anythingList2.add(new Dog());
        print(anythingList2);

        List<Cat> catList = new ArrayList<>();
        catList.add(new Cat());
        catList.add(new Cat());
        printAnimalChild(catList);
    }

    static <T> void print(List<?> myList) {
        System.out.println(myList);
    }

    static <T> void printAnimalChild(List<? extends Animal> myList) {
        System.out.println(myList);
    }
}
