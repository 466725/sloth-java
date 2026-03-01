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
        System.out.println();
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
        // Best length seen so far.
        int maxLength = 0;
        // Last index where each character appeared.
        Map<Character, Integer> charIndexMap = new HashMap<>();
        // Left boundary of current duplicate-free window [left..i].
        int left = 0;
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            // If duplicate is inside current window, move left past previous occurrence.
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                left = charIndexMap.get(currentChar) + 1;
            }
            // Update latest index for this character.
            charIndexMap.put(currentChar, i);
            // Current window length = i - left + 1.
            maxLength = Math.max(maxLength, i - left + 1);
        }
        return maxLength;
    }
}
