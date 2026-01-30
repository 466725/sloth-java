package org.concepts.deepcopy;

public class CopyDemo {
    static void main() {
        Person original = new Person("Ava", new Address("Paris"), new int[]{10, 20});
        // Print memory address of original
        System.out.println("Original memory address: " + original.hashCode());
        // Shallow copy
        Person shallow = new Person(original);
        // Print memory address of shallow
        System.out.println("Shallow memory address: " + shallow.hashCode());
        // Deep copy
        Person deep = Person.deepCopyOf(original);
        // Mutate nested fields in the copies
        shallow.address.city = "London";
        shallow.scores[0] = 99;
        deep.address.city = "Tokyo";
        deep.scores[1] = 77;
        // Print memory address of deep
        //System.out.println("Deep memory address: " + deep.hashCode());
        System.out.println("Original: " + original);
        System.out.println("Shallow : " + shallow);
        System.out.println("Deep    : " + deep);

        int x = 10;
        int y = x;
        y = 20;
        System.out.println(x);
        System.out.println(y);

        Person p1 = new Person("Ava", new Address("Paris"), new int[]{10, 20});
        Person p2 = new Person(p1);
        p2.name = "John";
        System.out.println(p1.name);
        System.out.println(p2.name);
        p2 = p1;
        p2.name = "Andy";
        System.out.println(p1.name);
        System.out.println(p2.name);
    }
}
