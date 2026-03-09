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
 */
public class GenerateParentheses {
    public static void main(String[] args) {
        GenerateParentheses solver = new GenerateParentheses();
        test(solver, 1);
        test(solver, 2);
        test(solver, 3);
        test(solver, 4);
        test(solver, 0); // edge case
    }

    private static void test(GenerateParentheses solver, int n) {
        List<String> result = solver.generateParentheses(n);
        System.out.println("================================");
        System.out.println("n = " + n);
        System.out.println("Generated: " + result);
        System.out.println("Count: " + result.size());
    }

    // Public API
    public List<String> generateParentheses(int n) {
        List<String> result = new ArrayList<>();
        build(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    /**
     * Backtracking helper
     *
     * @param result  stores valid combinations
     * @param current current parentheses string being built
     * @param left    number of '(' used
     * @param right   number of ')' used
     * @param total   total pairs allowed
     */
    private void build(List<String> result,
                       StringBuilder current,
                       int left,
                       int right,
                       int total) {
        // Base case: full valid string formed
        if (left == total && right == total) {
            result.add(current.toString());
            return;
        }

        // Add '(' if we still have some left
        if (left < total) {
            current.append('(');
            build(result, current, left + 1, right, total);
            current.deleteCharAt(current.length() - 1); // backtrack
        }

        // Add ')' only if it won't invalidate the string
        if (right < left) {
            current.append(')');
            build(result, current, left, right + 1, total);
            current.deleteCharAt(current.length() - 1); // backtrack
        }
    }
}
