package concepts.backtrack;

import java.util.ArrayList;
import java.util.List;

public class CharSubsetsStringBuilder {
    public static void main(String[] args) {
        char[] letters = "abcde".toCharArray();
        List<String> result = new ArrayList<>();
        backTrack(letters, 0, new StringBuilder(), result);
        System.out.println(result);
    }

    private static void backTrack(char[] letters, int index, StringBuilder path, List<String> result) {
        result.add(path.toString());
        for (int i = index; i < letters.length; i++) {
            // DO
            path.append(letters[i]);
            // explore
            backTrack(letters, i + 1, path, result);
            // undo
            path.deleteCharAt(path.length() - 1);
        }
    }
}
