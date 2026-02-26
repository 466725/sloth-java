package org.concepts;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrates common Java String operations and related patterns.
 */
public class StringDataType {
    // Toggles letter case while leaving digits/symbols unchanged.
    public static String toggleCase(String input) {
        StringBuilder builder = new StringBuilder(input);
        for (int i = 0; i < builder.length(); i++) {
            char c = builder.charAt(i);
            if (Character.isLowerCase(c)) {
                builder.setCharAt(i, Character.toUpperCase(c));
            } else if (Character.isUpperCase(c)) {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }
        return builder.toString();
    }

    // Counts how many times a character appears in a string.
    public static int countOccurrences(String input, char target) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == target) {
                count++;
            }
        }
        return count;
    }

    // Reverses a string recursively.
    public static String reverseRecursively(String input) {
        if (input.length() <= 1) {
            return input;
        }
        return reverseRecursively(input.substring(1)) + input.charAt(0);
    }

    // O(n^2) approach for longest substring without repeating characters.
    public static int lengthOfLongestSubstringBruteForce(String input) {
        int maxLength = 0;
        for (int i = 0; i < input.length(); i++) {
            StringBuilder window = new StringBuilder();
            for (int j = i; j < input.length(); j++) {
                if (window.indexOf(String.valueOf(input.charAt(j))) != -1) {
                    break;
                }
                window.append(input.charAt(j));
                maxLength = Math.max(maxLength, window.length());
            }
        }
        return maxLength;
    }

    // O(n) sliding-window solution with map of last seen indexes.
    public static int lengthOfLongestSubstringSlidingWindow(String input) {
        int maxLength = 0;
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int left = 0;
        for (int right = 0; right < input.length(); right++) {
            char currentChar = input.charAt(right);
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                left = charIndexMap.get(currentChar) + 1;
            }
            charIndexMap.put(currentChar, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

    // Alternative O(n) sliding-window variant using String#indexOf.
    public static int lengthOfLongestSubstringWithIndexOf(String input) {
        int maxLength = 0;
        for (int right = 0, left = 0; right < input.length(); right++) {
            int firstIndexInWindow = input.indexOf(input.charAt(right), left);
            if (firstIndexInWindow != right) {
                left = firstIndexInWindow + 1;
            }
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        String sample = "abcabcadefgbb";
        printSection("Longest Non-Repeating Substring Length");
        System.out.println(lengthOfLongestSubstringBruteForce(sample));
        System.out.println(lengthOfLongestSubstringSlidingWindow(sample));
        System.out.println(lengthOfLongestSubstringWithIndexOf(sample));

        printSection("Basic String Operations");
        System.out.println(toggleCase("AFAJLFsgfGASKFADSJL001"));
        System.out.println(countOccurrences("bmvuhfjk#$%%^900jio002", 'b'));
        System.out.println(reverseRecursively("myString001"));

        printSection("String Reference vs Value");
        String s1 = "111";
        String s2 = "222";
        String s3 = "111";
        printComparison(s1, s2, s3);

        // Creating with new String usually creates a different reference.
        s1 = new String("111");
        printComparison(s1, s2, s3);

        printSection("String Concatenation Performance");
        System.out.println(buildUsingStringConcatenation());
        System.out.println(buildUsingStringBuilder());
    }

    private static void printComparison(String s1, String s2, String s3) {
        // == compares references, equals compares content.
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1.equals(s3));
        System.out.println(s1.compareTo(s3));
        System.out.println(s1.hashCode());
    }

    private static String buildUsingStringConcatenation() {
        String result = "";
        for (int i = 1; i <= 999; i++) {
            result += i;
        }
        return result;
    }

    private static String buildUsingStringBuilder() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= 999; i++) {
            builder.append(i);
        }
        return builder.toString();
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}
