public class ComparableDemo {
    public static void compareByAge(String[] args) {
        List<Person> people = Arrays.asList(
                new Person(30),
                new Person(20),
                new Person(40)
        );

        Comparator<Person> byAgeDescending =
                (p1, p2) -> Integer.compare(p2.getAge(), p1.getAge());

        Collections.sort(people, byAgeDescending);
        System.out.println(people);
    }

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person(30),
                new Person(20),
                new Person(40)
        );

        Collections.sort(people);   // uses compareTo()
        System.out.println(people);
    }
}