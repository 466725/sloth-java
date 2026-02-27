package tutorial.interview;

public class StringQuestions {
    public static void main(String[] args) {
        String[] words1 = {"cat", "bt", "hat", "tree"};
        String chars1 = "atach";
        System.out.println("Example 1 result: " + sumLengthOfWordsFormedByCharacters(words1, chars1)); // expected 6

        String[] words2 = {"hello", "world", "leetcode"};
        String chars2 = "welldonehoneyr";
        System.out.println("Example 2 result: " + sumLengthOfWordsFormedByCharacters(words2, chars2)); // expected 10

        String[] words3 = {"aa", "ab", "b"};
        String chars3 = "aab";
        System.out.println("Custom result: " + sumLengthOfWordsFormedByCharacters(words3, chars3)); // expected 5
    }

    // https://www.jointaro.com/interviews/questions/find-words-that-can-be-formed-by-characters/?company=karat
    // Find sum length of Words That Can Be Formed by Characters
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
            need[idx]++;
            if (need[idx] > availableChars[idx]) return false;
        }
        return true;
    }

    // Creates a letter-count table for lowercase English chars
    private static int[] buildFreq(String chars) {
        int[] countTable = new int[26];
        for (int i = 0; i < chars.length(); i++) {
            countTable[chars.charAt(i) - 'a']++;
        }
        return countTable;
    }
}
