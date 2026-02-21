package org.concepts;

import java.util.Scanner;

// Examples for Java '+' operator behavior.
public class PlusOperator {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println(1+1);
        System.out.println(2.5+"abc");
        System.out.println(2+2+"abc");
        System.out.println("abc" + true);
        char c = 'a';
        System.out.println(c + 1);
        System.out.println('a' +1);
        System.out.println("a" +1);
        System.out.println('a' + "abc");
        System.out.println("a" + "abc");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter a number:");
        int num = sc.nextInt();
        System.out.println("The number entered is: " + num);
        System.out.println("The number entered + 666 is: " + (num + 666));
        sc.close();
    }
}
