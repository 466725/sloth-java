package tutorial.interview;
// https://www.jointaro.com/interviews/questions/longest-substring-without-repeating-characters/?src=taro75

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Given a string s, find the length of the longest substring without duplicate characters.
 * <p>
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * <p>
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * <p>
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public static void main(String[] args) {
        String[] testCases = {
                "abcabcbb",   // 3
                "bbbbb",      // 1
                "pwwkew",     // 3
                "",           // 0
                " ",          // 1
                "dvdf",       // 3
                "abba",       // 2
                "tmmzuxt"     // 5
        };
        System.out.println("===== Longest Substring Tests =====");
        for (int i = 0; i < testCases.length; i++) {
            String input = testCases[i];
            int brute = lengthOfLongestSubstringBruteForce(input);
            int optimal = lengthOfLongestSubstringSlidingWindow(input);
            System.out.println("-----------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input: \"" + input + "\"");
            System.out.println("Brute Force Result: " + brute);
            System.out.println("Sliding Window Result: " + optimal);
        }
        System.out.println("===================================");
    }

    // O(n^2) approach for longest substring without repeating characters.
    public static int lengthOfLongestSubstringBruteForce(String input) {
        int maxLength = 0;
        for (int i = 0; i < input.length(); i++) {
            StringBuilder window = new StringBuilder();
            for (int j = i; j < input.length(); j++) {
                if (window.indexOf(String.valueOf(input.charAt(j))) != -1)
                    break;
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
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            Integer lastIndex = charIndexMap.get(currentChar);
            if (lastIndex != null && lastIndex >= left)
                left = lastIndex + 1;
            charIndexMap.put(currentChar, i);
            maxLength = Math.max(maxLength, i - left + 1);
        }
        return maxLength;
    }
}
