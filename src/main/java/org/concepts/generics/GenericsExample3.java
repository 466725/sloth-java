package org.concepts.generics;

import org.concepts.Polymorphism.Animal;
import org.concepts.Polymorphism.Cat;
import org.concepts.Polymorphism.Dog;

import java.util.ArrayList;
import java.util.List;

public class GenericsExample3 {
    static void main() {
        ArrayList<Animal> animalList = new ArrayList<>();
        animalList.add(new Cat());
        animalList.add(new Dog());
        System.out.println(animalList.get(0).getName());
        System.out.println(animalList.get(1).getName());

        //List<? extends Animal> anythingList2 = animalList;
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
