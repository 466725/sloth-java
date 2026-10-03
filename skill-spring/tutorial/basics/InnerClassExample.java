package tutorial.basics;

/**
 * Demonstrates member inner classes, anonymous inner classes, and lambdas.
 */
public class InnerClassExample {
    interface MessagePrinter {
        void printMessage();
    }

    static class MemberInnerClass {
        public MemberInnerClass() {
            System.out.println("Member inner class instance created.");
        }
    }

    public void createMemberInnerClass() {
        new MemberInnerClass();
    }

    private static void runLambdaExample() {
        Runnable task = () -> System.out.println("Running from lambda.");
        Thread thread = new Thread(task);
        thread.start();
    }

    private static void runAnonymousInnerClassExample() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Running from anonymous inner class.");
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }

    public static void main(String[] args) {
        runLambdaExample();
        runAnonymousInnerClassExample();

        InnerClassExample example = new InnerClassExample();
        example.createMemberInnerClass();

        MessagePrinter printer = () -> System.out.println("Hello from functional interface.");
        printer.printMessage();
    }
}
