package tutorial.interview;
// https://www.jointaro.com/interviews/questions/generate-parentheses/?src=taro75

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * <p>
 * Example 1:
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 * <p>
 * Example 2:
 * Input: n = 1
 * Output: ["()"]
 *
 *
 */
public class GenerateParentheses {
    public static void main(String[] args) {
        GenerateParentheses solver = new GenerateParentheses();

        runTest(solver, 1, 1, "Test 1");
        runTest(solver, 2, 2, "Test 2");
        runTest(solver, 3, 5, "Test 3");
        runTest(solver, 4, 14, "Test 4");
        runTest(solver, 0, 1, "Test 5"); // edge case
    }

    private static void runTest(GenerateParentheses solver,
                                int n,
                                int expectedSize,
                                String testName) {

        List<String> result = solver.generateParenthesis(n);

        System.out.println("================================");
        System.out.println(testName);
        System.out.println("n = " + n);
        System.out.println("Generated: " + result);
        System.out.println("Count: " + result.size());

        if (result.size() == expectedSize) {
            System.out.println("Result: ✅ PASS");
        } else {
            System.out.println("Result: ❌ FAIL (Expected size: " + expectedSize + ")");
        }
    }

    public List<String> generateParenthesis(int n) {
        List<String> resultList = new ArrayList<>();
        backtrackFunction(resultList, "", 0, 0, n);
        return resultList;
    }

    private void backtrackFunction(List<String> currentResultList, String currentString, int openCount, int closeCount, int maxPairs) {
        // If the current string has reached the desired length, add it to results.
        if (currentString.length() == maxPairs * 2) {
            currentResultList.add(currentString);
            return;
        }
        // We can add an opening parenthesis if we haven't reached the maximum allowed.
        if (openCount < maxPairs) {
            backtrackFunction(currentResultList, currentString + "(", openCount + 1, closeCount, maxPairs);
        }
        // We can add a closing parenthesis only if it balances a preceding opening parenthesis.
        if (closeCount < openCount) {
            backtrackFunction(currentResultList, currentString + ")", openCount, closeCount + 1, maxPairs);
        }
    }
}
