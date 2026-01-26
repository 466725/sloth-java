package org.concepts.generics;

import org.concepts.Polymorphism.Animal;
import org.concepts.Polymorphism.Cat;
import org.concepts.Polymorphism.Dog;

public class GenericsExample2 {

    static void main() {
        PrintAnything<Integer> i = new PrintAnything<>(10);
        i.print();
        PrintAnything<Double> d = new PrintAnything<>(10.5);
        d.print();
        PrintAnything<String> s = new PrintAnything<>("Hello World!");
        s.print();
        PrintAnything<Boolean> b = new PrintAnything<>(true);
        b.print();
        PrintAnything<Character> c = new PrintAnything<>('a');
        c.print();
        PrintAnything<Animal> cat = new PrintAnything<>(new Cat());
        cat.print();
        PrintAnything<Animal> dog = new PrintAnything<>(new Dog());
        dog.print();
        PrintAnything<Void> v = new PrintAnything<>(null);
        v.print();
        PrintAnything<Object> o = new PrintAnything<>(new Object());
        o.print();
    }
}
