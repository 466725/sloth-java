public class LongestWordInTheGivenString {

    public static String longestWordInTheGivenString(String sentence) {
        if (sentence == null || sentence.isBlank()) {
            return "";
        }

        int left = 0;
        int maxLength = 0;
        String longestWord = "";

        for (int right = 0; right <= sentence.length(); right++) {
            if (right < sentence.length() && !Character.isWhitespace(sentence.charAt(right))) {
                continue;
            }

            if (left < right) {
                int currentLength = right - left;
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    longestWord = sentence.substring(left, right);
                }
            }

            left = right + 1;
            while (left < sentence.length() && Character.isWhitespace(sentence.charAt(left))) {
                left++;
            }
        }

        return longestWord;
    }
}
