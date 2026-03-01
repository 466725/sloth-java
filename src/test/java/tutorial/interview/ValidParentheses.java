package tutorial.interview;
// https://www.jointaro.com/interviews/questions/valid-parentheses/?src=taro75

import java.util.Stack;

/**
 *
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * <p>
 * An input string is valid if:
 * <p>
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Every close bracket has a corresponding open bracket of the same type.
 * <p>
 * Example 1:
 * Input: s = "()"
 * Output: true
 * <p>
 * Example 2:
 * Input: s = "()[]{}"
 * Output: true
 * <p>
 * Example 3:
 * Input: s = "(]"
 * Output: false
 * <p>
 * Example 4:
 * Input: s = "([])"
 * Output: true
 * <p>
 * Example 5:
 * Input: s = "([)]"
 * Output: false
 *
 */
public class ValidParentheses {
    public static void main(String[] args) {
        String[] tests = {
                "",                 // 1. empty
                "()",               // 2. simple valid
                "()[]{}",           // 3. multiple valid
                "(]",               // 4. mismatch
                "([])",             // 5. nested valid
                "([)]",             // 6. wrong order
                "{[]}",             // 7. nested valid
                "((()))",           // 8. deep nested
                "((())",            // 9. missing close
                "())",              // 10. extra close
                "]",                // 11. single close
                "[",                // 12. single open
                "{[()]}",           // 13. complex valid
                "{[(])}",           // 14. complex invalid
                "(((((((((())))))))))", // 15. long valid
                "((((((((((",       // 16. long invalid
                "))))))))))",       // 17. long invalid
                "()[{}]({[]})",     // 18. multiple nested groups
                "()[{}]({[})",      // 19. subtle invalid
                "(([]){})"          // 20. mixed valid
        };

        for (String test : tests) {
            System.out.println(
                    String.format("Input: %-25s Result: %s",
                            "\"" + test + "\"",
                            checkParentheses(test))
            );
        }
    }

    public static boolean checkPair(char left, char right) {
        if (left == '(' && right == ')') {
            return true;
        } else if (left == '[' && right == ']') {
            return true;
        } else if (left == '{' && right == '}') {
            return true;
        } else
            return false;
    }

    public static boolean checkParentheses(String input) {
        if (input == null) return false;

        Stack<Character> stack = new Stack<>();

        for (char c : input.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;

                char top = stack.pop();
                if (!checkPair(top, c)) return false;
            }
        }

        return stack.isEmpty();
    }
}
