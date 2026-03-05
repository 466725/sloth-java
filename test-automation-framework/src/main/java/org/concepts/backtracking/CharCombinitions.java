package org.concepts.backtracking;

public class CharCombinitions {
    public static void main(String[] args) {
        char[] letters = "abcd".toCharArray();
        backtrack(letters, 0, new StringBuilder());
    }

    private static void backtrack(char[] letters, int start, StringBuilder path) {
        for (int i = start; i < letters.length; i++) {
            // record current subset
            System.out.println(path.toString());
            // choose
            path.append(letters[i]);
            // explore
            backtrack(letters, i + 1, path);
            // undo (backtrack)
            path.deleteCharAt(path.length() - 1);
        }
    }
}