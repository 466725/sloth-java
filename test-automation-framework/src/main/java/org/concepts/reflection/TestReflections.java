package org.concepts.reflection;

import org.concepts.Polymorphism.Dog;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Demonstrates basic Java reflection operations using the {@link Dog} class.
 */
public class TestReflections {
    private final Dog dog = new Dog();
    private final Class<?> dogClass = dog.getClass();

    @Test
    public void shouldPrintClassNames() {
        System.out.println("Dog class full name: " + dogClass.getName());
        System.out.println("Dog class simple name: " + dogClass.getSimpleName());
        Assert.assertEquals(dogClass.getName(), dog.getClass().getName());
    }

    @Test
    public void shouldPrintClassMetadata() {
        System.out.println("Dog class simple name: " + dogClass.getSimpleName());
        Assert.assertTrue(dogClass.getConstructors().length > 0, "Expected at least one constructor");
        System.out.println("Dog class constructor name: " + dogClass.getConstructors()[0].getName());
        System.out.println("Dog class annotated interfaces: " + Arrays.toString(dogClass.getAnnotatedInterfaces()));
    }

    @Test
    public void shouldDiscoverPrivateMethod() throws NoSuchMethodException {
        Method[] declaredMethods = dogClass.getDeclaredMethods();
        System.out.println("Dog class declared methods: " + Arrays.toString(declaredMethods));

        Method sleepMethod = dogClass.getDeclaredMethod("sleep");
        Assert.assertNotNull(sleepMethod, "Expected private method 'sleep' to exist");
        System.out.println("Discovered private method: " + sleepMethod.getName());
    }

    @Test
    public void shouldInvokePrivateSleepMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method sleepMethod = dogClass.getDeclaredMethod("sleep");
        sleepMethod.setAccessible(true);
        sleepMethod.invoke(dog);
        Assert.assertTrue(sleepMethod.canAccess(dog), "Expected reflective access to be enabled");
    }
}
