package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/group-anagrams/?src=taro75

import java.util.*;

/**
 *
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 * <p>
 * Example 1:
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * <p>
 * Explanation:
 * There is no string in strs that can be rearranged to form "bat".
 * The strings "nat" and "tan" are anagrams as they can be rearranged to form each other.
 * The strings "ate", "eat", and "tea" are anagrams as they can be rearranged to form each other.
 * <p>
 * Example 2:
 * Input: strs = [""]
 * Output: [[""]]
 * <p>
 * Example 3:
 * Input: strs = ["a"]
 * Output: [["a"]]
 *
 */
public class GroupAnagrams {
    public static void main(String[] args) {
        GroupAnagrams solution = new GroupAnagrams();

        // Test 1: Example case
        runTest(solution,
                new String[]{"eat", "tea", "tan", "ate", "nat", "bat"},
                Arrays.asList(
                        Arrays.asList("bat"),
                        Arrays.asList("nat", "tan"),
                        Arrays.asList("ate", "eat", "tea")
                ),
                "Test 1");

        // Test 2: Single empty string
        runTest(solution,
                new String[]{""},
                Arrays.asList(
                        Arrays.asList("")
                ),
                "Test 2");

        // Test 3: Single character
        runTest(solution,
                new String[]{"a"},
                Arrays.asList(
                        Arrays.asList("a")
                ),
                "Test 3");

        // Test 4: Duplicates
        runTest(solution,
                new String[]{"abc", "bca", "abc"},
                Arrays.asList(
                        Arrays.asList("abc", "abc", "bca")
                ),
                "Test 4");

        // Test 5: No anagrams
        runTest(solution,
                new String[]{"a", "b", "c", "d"},
                Arrays.asList(
                        Arrays.asList("a"),
                        Arrays.asList("b"),
                        Arrays.asList("c"),
                        Arrays.asList("d")
                ),
                "Test 5");

        // Test 6: Empty input
        runTest(solution,
                new String[]{},
                Collections.emptyList(),
                "Test 6");
    }

    private static void runTest(GroupAnagrams solution,
                                String[] input,
                                List<List<String>> expected,
                                String testName) {

        List<List<String>> actual = solution.groupAnagrams(input);

        if (areEqualIgnoringOrder(actual, expected)) {
            System.out.println(testName + " ✅ PASS");
        } else {
            System.out.println(testName + " ❌ FAIL");
            System.out.println("Expected: " + expected);
            System.out.println("Actual:   " + actual);
        }
    }

    // Compare results ignoring order of groups and order inside groups
    private static boolean areEqualIgnoringOrder(List<List<String>> a, List<List<String>> b) {
        if (a.size() != b.size()) return false;

        List<List<String>> normalizedA = normalize(a);
        List<List<String>> normalizedB = normalize(b);

        return normalizedA.equals(normalizedB);
    }

    private static List<List<String>> normalize(List<List<String>> list) {
        List<List<String>> normalized = new ArrayList<>();

        for (List<String> group : list) {
            List<String> sortedGroup = new ArrayList<>(group);
            Collections.sort(sortedGroup);
            normalized.add(sortedGroup);
        }

        normalized.sort(Comparator.comparing(Object::toString));
        return normalized;
    }

    public List<List<String>> groupAnagrams(String[] words) {
        Map<String, List<String>> anagramGroups = new HashMap<>();

        for (String currentWord : words) {
            char[] characterArray = currentWord.toCharArray();
            Arrays.sort(characterArray);

            anagramGroups
                    .computeIfAbsent(new String(characterArray), key -> new ArrayList<>())
                    .add(currentWord);
        }

        return new ArrayList<>(anagramGroups.values());
    }
}
