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
     * @param open    number of '(' used
     * @param close   number of ')' used
     * @param max     total pairs allowed
     */
    private void build(List<String> result,
                       StringBuilder current,
                       int open,
                       int close,
                       int max) {
        // Base case: full valid string formed
        if (current.length() == max * 2) {
            result.add(current.toString());
            return;
        }

        // Add '(' if we still have some left
        if (open < max) {
            current.append('(');
            build(result, current, open + 1, close, max);
            current.deleteCharAt(current.length() - 1); // backtrack
        }

        // Add ')' only if it won't invalidate the string
        if (close < open) {
            current.append(')');
            build(result, current, open, close + 1, max);
            current.deleteCharAt(current.length() - 1); // backtrack
        }
    }
}
