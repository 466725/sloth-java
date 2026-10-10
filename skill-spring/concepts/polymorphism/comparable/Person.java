class Person implements Comparable<Person> {
    private int age;

    public Person(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age);  // natural order: by age
    }

    @Override
    public String toString() {
        return "Person(age=" + age + ")";
    }
}