package selfhealing;

/**
 * Simple Levenshtein distance implementation for similarity scoring.
 * <p>
 * Levenshtein usually refers to the Levenshtein distance, a concept in computer science used to measure how different two strings (words/text) are.
 * <p>
 * It answers the question:
 * <p>
 * How many single-character edits are needed to change one word into another?
 * <p>
 * The allowed edits are:
 * <p>
 * Insertion – add a character
 * <p>
 * Deletion – remove a character
 * <p>
 * Substitution – replace one character with another
 * <p>
 * Simple Example
 * <p>
 * Compare the words:
 * <p>
 * kitten → sitting
 * <p>
 * Steps required:
 * <p>
 * kitten → sitten (substitute k → s)
 * <p>
 * sitten → sittin (substitute e → i)
 * <p>
 * sittin → sitting (insert g)
 * <p>
 * Total edits = 3
 * <p>
 * So the Levenshtein distance = 3
 * <p>
 * Why It Is Useful
 * <p>
 * Levenshtein distance is widely used in:
 * <p>
 * 1️⃣ Spell Checking
 * <p>
 * Example:
 * User types "recieve"
 * <p>
 * Algorithm finds closest word:
 * <p>
 * receive
 * recipe
 * recite
 * <p>
 * "receive" has the smallest distance, so it is suggested.
 * <p>
 * Used in tools like:
 * <p>
 * Microsoft Word spell check
 * <p>
 * Google Search "Did you mean?" suggestions
 * <p>
 * 2️⃣ Fuzzy Search
 * <p>
 * Finding similar names:
 * <p>
 * Jon
 * John
 * Johan
 * <p>
 * Very useful in databases and search engines.
 * <p>
 * 3️⃣ AI / NLP / Data Matching
 * <p>
 * Used in:
 * <p>
 * duplicate record detection
 * <p>
 * customer name matching
 * <p>
 * chatbot text similarity
 * <p>
 * bioinformatics DNA comparison
 *
 */
public final class Levenshtein {
    private Levenshtein() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int distance(String a, String b) {
        if (a == null) {
            a = "";
        }
        if (b == null) {
            b = "";
        }
        if (a.equals(b)) {
            return 0;
        }

        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= b.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[a.length()][b.length()];
    }
}

