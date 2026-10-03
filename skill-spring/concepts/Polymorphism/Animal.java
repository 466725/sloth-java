package concepts.Polymorphism;

import java.util.Objects;

/**
 * Base type for polymorphism demos.
 */
public abstract class Animal {
    private final String color;
    private String name;
    private int age;

    public Animal(String name, int age, String color) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.age = age;
        this.color = Objects.requireNonNull(color, "color must not be null");
    }

    public abstract void makeSound();

    /**
     * Subclass-specific feeding behavior.
     */
    public abstract void eat();

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "', age=" + age + ", color='" + color + "'}";
    }
}
