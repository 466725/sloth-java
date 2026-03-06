package concepts.backtrack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CharSubsetsLinkedList {
    public static void main(String[] args) {
        char[] letters = new char[]{'a', 'b', 'c', 'd', 'e'};
        List<String> result = new ArrayList<>();
        backTrack(letters, 0, new LinkedList<>(), result);
        System.out.println(result);
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
