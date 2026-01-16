package org.concepts;

import java.util.Random;
import java.util.Scanner;

//Everything about java while loop
public class WhileLoop {
    public static void main(String[] args) {
        int i=0;
        // sum all numbers from 1 to i
        while(i<10){
            System.out.println(i);
            i++;
        }

        // sum odd numbers from 1 to i
        i=0;
        int sum=0;
        while(i<10){
            if(i%2!=0){
                sum+=i;
            }
            i++;
        }
        System.out.println("Sum of odd numbers from 1 to 10 is: " + sum);

        // guess the random number between 1 and 100
        int targetRandNum = new Random().nextInt(1,99);
        System.out.println("Please guess and enter a number between 1 and 100: ");
        int guess = new Scanner(System.in).nextInt();
        while(guess!=targetRandNum){
            if(guess>targetRandNum){
                System.out.println("Too big. Please try again:");
            } else {
                System.out.println("Too small. Please try again:");
            }
            guess = new Scanner(System.in).nextInt();
        }
        System.out.println("Bingo!");
        System.out.println("Congratulations! You guessed the correct number!");

    }
}
