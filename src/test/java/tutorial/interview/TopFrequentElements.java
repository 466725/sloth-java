package tutorial.interview;
// https://www.jointaro.com/interviews/questions/top-k-frequent-elements/?src=taro75

import java.util.*;

/**
 *
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 * <p>
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * <p>
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 */

public class TopFrequentElements {

    public static void main(String[] args) {

        // Example 1
        runTest(new int[]{1, 1, 1, 2, 2, 3}, 2, new int[]{1, 2}, "Test 1");

        // Example 2
        runTest(new int[]{1}, 1, new int[]{1}, "Test 2");

        // Multiple same frequency
        runTest(new int[]{4, 4, 1, 1, 2, 2}, 2, new int[]{4, 1, 2}, "Test 3");

        // k equals number of unique elements
        runTest(new int[]{5, 6, 7}, 3, new int[]{5, 6, 7}, "Test 4");

        // k larger than unique count
        runTest(new int[]{8, 8, 9}, 5, new int[]{8, 9}, "Test 5");

        // Negative numbers
        runTest(new int[]{-1, -1, -2, -3, -3, -3}, 2, new int[]{-3, -1}, "Test 6");

        // Empty input
        runTest(new int[]{}, 2, new int[]{}, "Test 7");

        // All same numbers
        runTest(new int[]{10, 10, 10, 10}, 1, new int[]{10}, "Test 8");
    }

    private static void runTest(int[] input, int k, int[] expectedOptions, String testName) {
        int[] result = frequentEle(input, k);

        if (matchesIgnoringOrder(result, expectedOptions)) {
            System.out.println(testName + " ✅ PASS");
        } else {
            System.out.println(testName + " ❌ FAIL");
            System.out.println("Input:    " + Arrays.toString(input));
            System.out.println("k:        " + k);
            System.out.println("Expected: " + Arrays.toString(expectedOptions));
            System.out.println("Actual:   " + Arrays.toString(result));
        }
    }

    // Compare ignoring order
    private static boolean matchesIgnoringOrder(int[] actual, int[] expectedPool) {
        Set<Integer> expectedSet = new HashSet<>();
        for (int num : expectedPool) {
            expectedSet.add(num);
        }

        for (int num : actual) {
            if (!expectedSet.contains(num)) {
                return false;
            }
        }

        return true;
    }

    public static int[] frequentEle(int[] input, int top) {
        if (input == null || input.length == 0 || top <= 0) {
            return new int[0];
        }

        HashMap<Integer, Integer> frequentMap = countWithHashMap(input);

        // Convert map entries to a list
        List<Map.Entry<Integer, Integer>> entryList =
                new ArrayList<>(frequentMap.entrySet());

        // Sort by frequency descending
        entryList.sort((a, b) -> b.getValue() - a.getValue());

        // If top is larger than unique elements, adjust
        top = Math.min(top, entryList.size());

        int[] returnInt = new int[top];

        // Take first 'top' elements
        for (int i = 0; i < top; i++) {
            returnInt[i] = entryList.get(i).getKey();
        }

        return returnInt;
    }

    private static HashMap<Integer, Integer> countWithHashMap(int[] input) {
        HashMap<Integer, Integer> frequentMap = new HashMap<>();

        for (int num : input) {
            frequentMap.put(num, frequentMap.getOrDefault(num, 0) + 1);
        }

        return frequentMap;
    }
}
