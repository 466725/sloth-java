package tutorial.interview;
// https://www.jointaro.com/interviews/questions/find-words-that-can-be-formed-by-characters/?company=karat

/**
 *
 * You are given an array of strings words and a string chars.
 * <p>
 * A string is good if it can be formed by characters from chars (each character can only be used once for each word in words).
 * <p>
 * Return the sum of lengths of all good strings in words.
 * <p>
 * Example 1:
 * Input: words = ["cat","bt","hat","tree"], chars = "atach"
 * Output: 6
 * Explanation: The strings that can be formed are "cat" and "hat" so the answer is 3 + 3 = 6.
 * <p>
 * Example 2:
 * Input: words = ["hello","world","leetcode"], chars = "welldonehoneyr"
 * Output: 10
 * Explanation: The strings that can be formed are "hello" and "world" so the answer is 5 + 5 = 10.
 *
 */
public class WordsCanFormedByChars {
    public static void main(String[] args) {
        System.out.println("========== WordsCanFormedByChars Test Harness ==========\n");

        runAllTests();

        System.out.println("\n========== All Tests Completed ==========");
    }

    // =====================================================
    // =============== TEST HARNESS SECTION =================
    // =====================================================

    private static void runAllTests() {
        testExample1();
        testExample2();
        testEmptyInputs();
        testNullInputs();
        testRepeatedCharacters();
        testWordLongerThanChars();
        testNoMatchingWords();
    }

    private static void testExample1() {
        System.out.println("Running testExample1...");

        String[] words = {"cat", "bt", "hat", "tree"};
        String chars = "atach";

        int result = sumLengthOfWordsFormedByCharacters(words, chars);
        assertEqual(result, 6);
    }

    private static void testExample2() {
        System.out.println("Running testExample2...");

        String[] words = {"hello", "world", "leetcode"};
        String chars = "welldonehoneyr";

        int result = sumLengthOfWordsFormedByCharacters(words, chars);
        assertEqual(result, 10);
    }

    private static void testEmptyInputs() {
        System.out.println("Running testEmptyInputs...");

        assertEqual(sumLengthOfWordsFormedByCharacters(new String[]{}, "abc"), 0);
        assertEqual(sumLengthOfWordsFormedByCharacters(new String[]{"a"}, ""), 0);
    }

    private static void testNullInputs() {
        System.out.println("Running testNullInputs...");

        assertEqual(sumLengthOfWordsFormedByCharacters(null, "abc"), 0);
        assertEqual(sumLengthOfWordsFormedByCharacters(new String[]{"a"}, null), 0);
    }

    private static void testRepeatedCharacters() {
        System.out.println("Running testRepeatedCharacters...");

        String[] words = {"aa", "aaa", "b"};
        String chars = "aab";

        // "aa" (2) + "b" (1) = 3
        int result = sumLengthOfWordsFormedByCharacters(words, chars);
        assertEqual(result, 3);
    }

    private static void testWordLongerThanChars() {
        System.out.println("Running testWordLongerThanChars...");

        String[] words = {"abcd"};
        String chars = "abc";

        assertEqual(sumLengthOfWordsFormedByCharacters(words, chars), 0);
    }

    private static void testNoMatchingWords() {
        System.out.println("Running testNoMatchingWords...");

        String[] words = {"xyz", "zzz"};
        String chars = "abc";

        assertEqual(sumLengthOfWordsFormedByCharacters(words, chars), 0);
    }

    private static void assertEqual(int actual, int expected) {
        if (actual == expected) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL → expected: " + expected + ", actual: " + actual);
        }
    }

    // =====================================================
    // ================= SOLUTION SECTION ===================
    // =====================================================

    public static int sumLengthOfWordsFormedByCharacters(String[] words, String chars) {
        if (words == null || chars == null) return 0;

        int[] availableChars = buildFreq(chars);
        int total = 0;

        for (String word : words) {
            if (word != null && enoughCharsToFormWord(word, availableChars)) {
                total += word.length();
            }
        }
        return total;
    }

    private static boolean enoughCharsToFormWord(String word, int[] availableChars) {
        int[] need = new int[26];

        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';

            if (idx < 0 || idx >= 26) {
                return false; // invalid character
            }

            need[idx]++;
            if (need[idx] > availableChars[idx]) {
                return false;
            }
        }
        return true;
    }

    private static int[] buildFreq(String chars) {
        int[] countTable = new int[26];

        for (int i = 0; i < chars.length(); i++) {
            int idx = chars.charAt(i) - 'a';
            if (idx >= 0 && idx < 26) {
                countTable[idx]++;
            }
        }
        return countTable;
    }
}
