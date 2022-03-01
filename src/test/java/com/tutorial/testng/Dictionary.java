/**
REI SDET Homework Problem 
General Instructions
1.	Plan to spend about 2 hours completing this exercise.
2.	Please document any assumptions made.
3.	This is a real world exercise.  You may use libraries and frameworks as you see fit.  Solutions found by Google Search or on Stack Overflow are acceptable – as long as you understand them.
4.	You do not need to connect to a real, online dictionary.  The idea is to create a mock object to simulate this.
5.	Please send your solution in a link to your private GitHub or Bitbucket repository, so that we can evaluate your work.

The Problem
Using Java, find all of the English words in a given String.  For example, if you are given the word WORKING, you can easily find WORK and KING, but ROW, RING and KNOW are also in there.  You have access to a utility class called Dictionary, which has one method, isEnglishWord(String word).  Dictionary.isEnglishWord(String word) connects to a (mocked) online dictionary and returns Boolean true if the String passed to it is an English word, return false otherwise.
Instructions
1.	Use Maven to create a project to answer this problem.
2.	You will need to create the Dictionary class.
3.	You will need to mock Dictionary.isEnglishWord(String word) for your solution and tests.
4.	The output of your primary method should be a collection of Strings without duplicates.
5.	Create tests that exercise your class and methods.
6.	Please complete the instructions to the best of your ability and understanding and come prepared to discuss the design decisions you chose.v
*/
package com.tutorial.testng;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Tina
 *
 */
public class Dictionary {
	// Function to print all sub strings
	public static ArrayList<String> wordSubStrings(String inputString) {
		int inputStringLength = inputString.length();
		ArrayList<String> subStringList = null;
		String str = "";
		for (int i = 0; i < inputStringLength; i++) {
			for (int j = i + 1; j <= inputStringLength; j++) {
				try {
					str = inputString.substring(i, j).toString();
					if (isEnglishWord(str)) {
						System.out.println(str);
						subStringList.add(str);
					}
				} catch (Exception e) {
					System.out.println("Not a String! To be ingored");
				}
			}
		}
		// Get collection without duplicate i.e. distinct only
		return subStringList;
	}

	// Function to remove duplicates from an ArrayList
	public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
		// Create a new ArrayList
		ArrayList<T> noDuplicatesList = new ArrayList<T>();
		// Traverse through the first list
		try {
			for (T element : list)
				if (!noDuplicatesList.contains(element))
					noDuplicatesList.add(element);
		} catch (Exception e) {
			System.out.println("Not a String! To be ingored");
		}
		// return the new list
		return noDuplicatesList;
	}

	// Function to mock Dictionary.isEnglishWord(String word)
	public static boolean isEnglishWord(String word) {
		if ((new Random().nextInt()) % 2 == 0)
			return true;
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(wordSubStrings("abcdefg"));
		}
		System.out.println(removeDuplicates(wordSubStrings("ahahahahhahaha")));
		for (int i = 0; i < 10; i++) {
			System.out.println(isEnglishWord("hahahah"));
		}
		// TODO Auto-generated method stub
	}
}
