package concepts.backtrack;

import java.util.LinkedList;

public class CharSubsets {
    public static void main(String[] args) {
        char[] letters = new char[]{'a', 'b', 'c', 'd', 'e'};
        backTrack(letters, 0, new LinkedList<>());
    }

    private static void backTrack(char[] letters, int index, LinkedList<String> path) {
        for (int i = 0; i < path.size(); i++)
            System.out.print(path.get(i));
        System.out.println();
        for (int i = index; i < letters.length; i++) {
            path.add(String.valueOf(letters[i]));
            backTrack(letters, i + 1, path);
            path.removeLast();
        }
    }
}