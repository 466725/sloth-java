package org.concepts;

//Everything about java for loop
public class ForLoop {
    public static int i=3;
    public static void main(String[] args) {
        int sum=0;
        // sum all numbers from 1 to i
        for (int j = 1; j < i+1; j++) {
            System.out.println(j);
            sum += j;
            System.out.println(j);
        }
        System.out.println("Sum of numbers from 1 to " + i + " is: " + sum);

        sum=0;
        // sum odd numbers from 1 to i
        for (int j = 1; j < i+1; j++) {
            if (j % 2 != 0) {
                sum += j;
            }
        }
        System.out.println("Sum of odd numbers from 1 to " + i + " is: " + sum);
    }
}
