package org.concepts.backtracking;

import java.util.ArrayList;
import java.util.List;

public class CharactersCombinations {
    public static void main(String[] args) {
        char[] letters = "abc".toCharArray();
        List<String> result = new ArrayList<>();
        backTracking(letters, 0, new StringBuilder(), result, 0);
    }

    private static void backTracking(char[] letters, int start, StringBuilder path, List<String> result, int depth) {
        System.out.println("Outside of for loop: " + depth);
        printIndent(depth);
        for (int i = start; i < letters.length; i++) {
            System.out.println("Inside for loop: " + i + ", path: " + path);
            printIndent(depth);
            // record current combination
            result.add(path.toString());
            System.out.println("Number " + i + " times: " + path);
            // DO (choose)
            path.append(letters[i]);
            // explore
            System.out.println("Before recurse: " + path);
            backTracking(letters, i + 1, path, result, depth + 1);
            System.out.println("After recurse: " + path);
            // undo (backtrack)
            path.deleteCharAt(path.length() - 1);
        }
    }

    private static void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}