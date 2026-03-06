package concepts.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

// Compare ArrayList and Vector
public class ArrayListVersusVector {
    private static final int SIZE = 1_000_000;
    private static final List<Integer> arrayList = new ArrayList<>(SIZE);
    private static final List<Integer> vectorList = new Vector<>(SIZE);

    private static final List<Integer> arrayListMultiThread = new ArrayList<>(SIZE);
    private static final List<Integer> vectorListMultiThread = new Vector<>(SIZE);

    public static void main(String[] args) {
        // Insert random numbers into both arraylist and vector, and then compare the time taken to add numbers.
        Random random = new Random();
        long start = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arrayList.add(random.nextInt());
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken to insert " + SIZE + " numbers into ArrayList: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            vectorList.add(random.nextInt());
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken to insert " + SIZE + " numbers into Vector: " + (end - start) + " ms");

        // Do the same but with multiple threads.
        start = System.currentTimeMillis();
        try {
            Thread t1 = new Thread(() -> {
                for (int i = 0; i < SIZE; i++) {
                    arrayListMultiThread.add(random.nextInt());
                }
            });
            Thread t2 = new Thread(() -> {
                for (int i = 0; i < SIZE; i++) {
                    arrayListMultiThread.add(random.nextInt());
                }
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken to insert " + SIZE + " numbers into arrayListMultiThread: " + (end - start) + " ms");
        System.out.println("Size of arrayListMultiThread: " + arrayListMultiThread.size());

        start = System.currentTimeMillis();
        try {
            Thread t1 = new Thread(() -> {
                for (int i = 0; i < SIZE; i++) {
                    vectorListMultiThread.add(random.nextInt());
                }
            });
            Thread t2 = new Thread(() -> {
                for (int i = 0; i < SIZE; i++) {
                    vectorListMultiThread.add(random.nextInt());
                }
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken to insert " + SIZE + " numbers into vectorListMultiThread: " + (end - start) + " ms");
        System.out.println("Size of vectorListMultiThread: " + vectorListMultiThread.size());
    }
}
