package concepts.annotations;

/**
 * Simple domain model to demonstrate getter and setter usage.
 */
public class GetterSetterLombokDemo {
    private String name;
    private int age;

    public GetterSetterLombokDemo() {
    }

    public GetterSetterLombokDemo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        GetterSetterLombokDemo person = new GetterSetterLombokDemo("Alex Chen", 30);

        // Manual getter and setter methods.
        System.out.println("Before update: " + person);
        person.setAge(31);
        System.out.println("After age update: " + person);
        person.setName("Alan Zheng");
        System.out.println("After name update: " + person);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "GetterSetterLombokDemo{name='" + name + "', age=" + age + "}";
    }
}
