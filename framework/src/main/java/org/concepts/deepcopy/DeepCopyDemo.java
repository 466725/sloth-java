package org.concepts.deepcopy;

public class DeepCopyDemo {
    public static void main(String[] args) {
        Person original = new Person("Ava", new Address("Paris"), new int[]{10, 20});

        // Shallow copy: nested objects are shared.
        Person shallow = new Person(original);

        // Deep copy: nested objects are copied.
        Person deep = Person.deepCopyOf(original);

        System.out.println("Object identities:");
        System.out.println("original = " + System.identityHashCode(original));
        System.out.println("shallow  = " + System.identityHashCode(shallow));
        System.out.println("deep     = " + System.identityHashCode(deep));
        System.out.println();

        // Mutate nested fields in copied objects.
        shallow.getAddress().setCity("London");
        shallow.setScoreAt(1, 99);
        deep.getAddress().setCity("Tokyo");
        deep.setScoreAt(1, 77);

        System.out.println("After mutation:");
        System.out.println("original -> " + original);
        System.out.println("shallow  -> " + shallow);
        System.out.println("deep     -> " + deep);
    }
}
