package tutorial.interview;
// https://www.jointaro.com/interviews/questions/valid-anagram/?src=taro75

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 * <p>
 * Example 1:
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 * <p>
 * Example 2:
 * Input: s = "rat", t = "car"
 * Output: false
 *
 */
public class ValidAnagram {
    // ==========================
    // ✅ Test Harness
    // ==========================
    public static void main(String[] args) {
        ValidAnagram solver = new ValidAnagram();
        String[][] testCases = {
                {"anagram", "nagaram"},
                {"rat", "car"},
                {"", ""},
                {"aacc", "ccac"},
                {"Listen", "Silent"} // case-sensitive
        };

        System.out.println("==== Valid Anagram (HashMap) Tests ====");

        for (int i = 0; i < testCases.length; i++) {

            String s = testCases[i][0];
            String t = testCases[i][1];

            boolean result = solver.isAnagram(s, t);

            System.out.println("-------------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("First:  " + s);
            System.out.println("Second: " + t);
            System.out.println("Result: " + result);
        }

        System.out.println("======================================");
    }

    public boolean isAnagram(String firstWord, String secondWord) {
        if (firstWord.length() != secondWord.length()) {
            return false;
        }

        // We use an array to store letter counts, assuming ASCII
        int[] firstWordLetterCounts = new int[256];

        for (int i = 0; i < firstWord.length(); i++) {
            firstWordLetterCounts[firstWord.charAt(i)]++;
        }

        // Decrement counts based on the second word.
        for (int i = 0; i < secondWord.length(); i++) {
            firstWordLetterCounts[secondWord.charAt(i)]--;
        }

        // If it is an anagram, all counts should be zero
        for (int count : firstWordLetterCounts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagramHashMap(String firstWord, String secondWord) {

        if (firstWord.length() != secondWord.length()) {
            return false;
        }

        Map<Character, Integer> frequencyMap = new HashMap<>();

        // Count characters from first word
        for (char c : firstWord.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        // Decrease counts using second word
        for (char c : secondWord.toCharArray()) {

            if (!frequencyMap.containsKey(c)) {
                return false;
            }

            int newCount = frequencyMap.get(c) - 1;

            if (newCount == 0) {
                frequencyMap.remove(c);
            } else {
                frequencyMap.put(c, newCount);
            }
        }

        return frequencyMap.isEmpty();
    }
}
