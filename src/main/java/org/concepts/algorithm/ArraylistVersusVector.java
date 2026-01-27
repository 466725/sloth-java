package org.concepts.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

// Compare ArrayList and Vector
public class ArraylistVersusVector {
    static int size = 1000000;
    static List<Integer> arrList = new ArrayList<>(size);
    static List<Integer> vecList = new Vector<>(size);

    static List<Integer> arrListMultiThread = new ArrayList<>(size);
    static List<Integer> vecListMultiThread = new Vector<>(size);

    static void main() {
        // Insert random numbers into both arraylist and vector, and then compare the time taken to add numbers.
        Random random = new Random();
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arrList.add(random.nextInt());
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken to insert 10000 numbers into ArrayList: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            vecList.add(random.nextInt());
        }
        end = System.currentTimeMillis();
        System.out.println("Time taken to insert 10000 numbers into Vector: " + (end - start) + " ms");

        // Do the same but with multiple threads.
        start = System.currentTimeMillis();
        try {
            Thread t1 = new Thread(() -> {
                for (int i = 0; i < size; i++) {
                    arrListMultiThread.add(random.nextInt());
                }
            });
            Thread t2 = new Thread(() -> {
                for (int i = 0; i < size; i++) {
                    arrListMultiThread.add(random.nextInt());
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
        System.out.println("Time taken to insert 10000 numbers into arrListMultiThread: " + (end - start) + " ms");
        System.out.println("Size of arrListMultiThread: " + arrListMultiThread.size());

        start = System.currentTimeMillis();
        try {
            Thread t1 = new Thread(() -> {
                for (int i = 0; i < size; i++) {
                    vecListMultiThread.add(random.nextInt());
                }
            });
            Thread t2 = new Thread(() -> {
                for (int i = 0; i < size; i++) {
                    vecListMultiThread.add(random.nextInt());
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
        System.out.println("Time taken to insert 10000 numbers into vecListMultiThread: " + (end - start) + " ms");
        System.out.println("Size of vecListMultiThread: " + vecListMultiThread.size());
    }
}
