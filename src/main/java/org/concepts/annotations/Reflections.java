package org.concepts.annotations;

import org.concepts.Polymorphism.Cat;

import java.util.Arrays;

public class Reflections {
    static Cat cat = new Cat();

    static void main() {
        cat.makeSound();
        System.out.println(Arrays.toString(cat.getClass().getDeclaredFields()));
        System.out.println(Arrays.toString(cat.getClass().getAnnotations()));
        System.out.println(Arrays.toString(cat.getClass().getConstructors()));
        System.out.println(Arrays.toString(cat.getClass().getMethods()));
    }
}
