package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/longest-palindromic-substring/?src=taro75

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Given a string s, return the longest palindromic substring in s.
 * <p>
 * Example 1:
 * Input: s = "babad"
 * Output: "bab"
 * Explanation: "aba" is also a valid answer.
 * <p>
 * Example 2:
 * Input: s = "cbbd"
 * Output: "bb"
 *
 */

public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();

        // Example 1 (two valid answers)
        runTest(solution, "babad", new String[]{"bab", "aba"}, "Test 1");

        // Example 2
        runTest(solution, "cbbd", new String[]{"bb"}, "Test 2");

        // Single character
        runTest(solution, "a", new String[]{"a"}, "Test 3");

        // Two characters - no palindrome longer than 1
        runTest(solution, "ac", new String[]{"a", "c"}, "Test 4");

        // All same characters
        runTest(solution, "aaaa", new String[]{"aaaa"}, "Test 5");

        // Even length palindrome
        runTest(solution, "abba", new String[]{"abba"}, "Test 6");

        // No repeating characters
        runTest(solution, "abcdefg",
                new String[]{"a", "b", "c", "d", "e", "f", "g"}, "Test 7");

        // Empty string
        runTest(solution, "", new String[]{""}, "Test 8");

        // Longer complex case
        runTest(solution, "forgeeksskeegfor",
                new String[]{"geeksskeeg"}, "Test 9");
    }

    private static void runTest(LongestPalindromicSubstring solution,
                                String input,
                                String[] expectedOptions,
                                String testName) {

        String result = solution.findLongestPalindroString(input);
        Set<String> expectedSet = new HashSet<>(Arrays.asList(expectedOptions));

        if (expectedSet.contains(result)) {
            System.out.println(testName + " ✅ PASS");
        } else {
            System.out.println(testName + " ❌ FAIL");
            System.out.println("Input:    \"" + input + "\"");
            System.out.println("Expected: " + expectedSet);
            System.out.println("Actual:   \"" + result + "\"");
        }
    }

    public static boolean checkPalindro(String input) {
        if (input == null) return false;
        for (int i = 0; i < input.length() / 2; i++) {
            if (input.charAt(i) != input.charAt(input.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public String findLongestPalindroString(String input) {
        if (input == null || input.length() < 2) {
            return input;
        }

        String longest = "";

        for (int start = 0; start < input.length(); start++) {
            for (int end = start + 1; end <= input.length(); end++) {
                String sub = input.substring(start, end);
                if (checkPalindro(sub) && sub.length() > longest.length()) {
                    longest = sub;
                }
            }
        }

        return longest;
    }
}