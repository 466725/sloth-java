package org.concepts.reflection;

import org.concepts.Polymorphism.Dog;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestReflections {
    Dog dog = new Dog();
    Class<?> dogClass = dog.getClass();

    @Test
    public void testPrintDogClass() {
        System.out.println("Dog class full name: " + dogClass.getName());
        System.out.println("Dog class full name: " + dog.getClass().getName());
    }

    @Test
    public void testPrintDogClassMethod() {
        System.out.println("Dog class simple name: " + dogClass.getSimpleName());
        System.out.println("Dog class constructor name: " + dogClass.getConstructors()[0].getName());
        System.out.println("Dog class annotated interfaces: " + Arrays.toString(dogClass.getAnnotatedInterfaces()));
    }

    @Test
    public void testDogClassPrivateMethod() {
        // Print all private methods
        System.out.println("Dog class private methods: " + Arrays.toString(dogClass.getDeclaredMethods()));
        System.out.println("Dog class private method: " + dogClass.getDeclaredMethods()[0].getName());
    }

    @Test
    public void testInvokePrivateSleepMethod() throws Exception {
        Method sleep = dogClass.getDeclaredMethod("sleep"); // private void sleep()
        sleep.setAccessible(true);                          // bypass private access
        sleep.invoke(dog);                                  // calls dog.sleep()
    }
}
