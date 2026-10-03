package tutorial.interview;
// https://www.jointaro.com/interviews/questions/longest-common-prefix/?src=taro75

import java.util.Arrays;

/**
 *
 * Write a function to find the longest common prefix string amongst an array of strings.
 * <p>
 * If there is no common prefix, return an empty string "".
 * <p>
 * Example 1:
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 * <p>
 * Example 2:
 * Input: strs = ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 *
 */
public class LongestCommonPrefix {
    public static void main(String[] args) {
        LongestCommonPrefix solver = new LongestCommonPrefix();
        runTest(solver, new String[]{"flower", "flow", "flight"}, "fl", "Test 1");
        runTest(solver, new String[]{"dog", "racecar", "car"}, "", "Test 2");
        runTest(solver, new String[]{"apple"}, "apple", "Test 3");
        runTest(solver, new String[]{}, "", "Test 4");
        runTest(solver, new String[]{"", ""}, "", "Test 5");
        runTest(solver, new String[]{"test", "test", "test"}, "test", "Test 6");
        runTest(solver, new String[]{"interview", "internet", "internal"}, "inter", "Test 7");
        runTest(solver, new String[]{"Case", "casing"}, "", "Test 8"); // case-sensitive
        // null input
        runTest(solver, null, "", "Test 9");
    }

    private static void runTest(LongestCommonPrefix solver,
                                String[] input,
                                String expected,
                                String testName) {
        String result = solver.longestCommonPrefix(input);
        if (expected.equals(result))
            System.out.println(testName + " ✅ PASS");
        else {
            System.out.println(testName + " ❌ FAIL");
            System.out.println("Input:    " + Arrays.toString(input));
            System.out.println("Expected: \"" + expected + "\"");
            System.out.println("Actual:   \"" + result + "\"");
        }
    }

    public String longestCommonPrefix(String[] words) {
        if (words == null || words.length == 0)
            return "";
        String commonPrefix = words[0];
        for (int i = 1; i < words.length; i++)
            while (!words[i].startsWith(commonPrefix)) {
                commonPrefix = commonPrefix.substring(0, commonPrefix.length() - 1);
                if (commonPrefix.isEmpty())
                    return "";
            }
        return commonPrefix;
    }
}
