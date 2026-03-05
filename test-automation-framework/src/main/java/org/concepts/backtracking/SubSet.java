package org.concepts.backtracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SubSet {
    public static void main(String[] args) {
        char[] letters = new char[]{'a', 'b', 'c'};
        System.out.println(subSet(letters));
    }

    private static List<String> subSet(char[] letters) {
        List<String> result = new ArrayList<>();
        backTrack(letters, 0, new LinkedList<>(), result);
        return result;
    }

    private static void backTrack(char[] letters, int index, LinkedList<String> path, List<String> result) {
        result.add(String.join("", path));
        for (int i = index; i < letters.length; i++) {
            path.add(String.valueOf(letters[i]));
            backTrack(letters, i + 1, path, result);
            path.removeLast();
        }
    }
}
