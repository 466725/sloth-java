package org.concepts.generics;

import org.concepts.Polymorphism.Animal;
import org.concepts.Polymorphism.Cat;
import org.concepts.Polymorphism.Dog;

public class GenericsExample2 {

    static void main() {
        GenericsPrinter<Integer> ip = new GenericsPrinter<>(10);
        ip.print();
        GenericsPrinter<Double> dp = new GenericsPrinter<>(10.5);
        dp.print();
        GenericsPrinter<String> sp = new GenericsPrinter<>("Hello World!");
        sp.print();
        GenericsPrinter<Boolean> bp = new GenericsPrinter<>(true);
        bp.print();
        GenericsPrinter<Character> cp = new GenericsPrinter<>('a');
        cp.print();
        GenericsPrinter<Animal> cat = new GenericsPrinter<>(new Cat());
        cat.print();
        GenericsPrinter<Animal> dog = new GenericsPrinter<>(new Dog());
        dog.print();
        GenericsPrinter<Void> vp = new GenericsPrinter<>(null);
        vp.print();
        GenericsPrinter<Object> op = new GenericsPrinter<>(new Object());
        op.print();
    }
}
