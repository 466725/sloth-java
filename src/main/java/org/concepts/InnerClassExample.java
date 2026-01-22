package org.concepts;

public class InnerClassExample {
    interface MyInterface {
        void method();
    }

    class InnerClass {
        public InnerClass() {
            System.out.println("First inner class created");
        }
    }

    public void outerMethod() {
        InnerClass inner = new InnerClass();
    }

    // anonymous inner class example
    public static void main() {
        Runnable task = () -> System.out.println("Running from second anonymous inner class!");
        Thread t = new Thread(task);
        t.start();
    }

    // anonymous inner class example
    public static void main(boolean b) {
        if (!b) {
            System.out.println("Exiting...");
            return;
        }
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Running from third anonymous inner class!");
            }
        };

        Thread t = new Thread(task);
        t.start();
    }

    public static void main(String[] args) {
        main();
        main(true);

        new InnerClassExample().outerMethod();
        MyInterface mi = () -> System.out.println("Hello from interface, good luck! ");
        mi.method();
    }
}
