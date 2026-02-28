package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/palindrome-number/?src=taro75

/**
 *
 * Given an integer x, return true if x is a palindrome, and false otherwise.
 * <p>
 * Example 1:
 * Input: x = 121
 * Output: true
 * Explanation: 121 reads as 121 from left to right and from right to left.
 * <p>
 * Example 2:
 * Input: x = -121
 * Output: false
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
 * <p>
 * Example 3:
 * Input: x = 10
 * Output: false
 * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 *
 */
public class PalindromeNumber {
    // ================================
    // ✅ Test Harness
    // ================================
    public static void main(String[] args) {

        PalindromeNumber solver = new PalindromeNumber();

        int[] testCases = {121,        // true
                -121,       // false
                10,         // false
                0,          // true
                1,          // true
                1221,       // true
                12321,      // true
                123,        // false
                2147447412  // true (large palindrome)
        };

        System.out.println("==== Palindrome Number Tests ====");

        for (int i = 0; i < testCases.length; i++) {
            int input = testCases[i];
            boolean result = solver.isPalindrome(input);

            System.out.println("---------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input:    " + input);
            System.out.println("Result:   " + result);
        }

        System.out.println("=================================");
    }

    public boolean isPalindrome(int originalNumber) {
        // Negative numbers are not palindromes, nor are numbers ending in 0 (unless it's just 0).
        if (originalNumber < 0 || (originalNumber % 10 == 0 && originalNumber != 0)) {
            return false;
        }

        int revertedHalf = 0;
        // We only need to revert half the number to avoid potential integer overflow.
        while (originalNumber > revertedHalf) {
            int lastDigit = originalNumber % 10;
            revertedHalf = revertedHalf * 10 + lastDigit;
            originalNumber /= 10;
        }

        // For odd-length numbers, the middle digit doesn't affect the palindrome check.
        return originalNumber == revertedHalf || originalNumber == revertedHalf / 10;
    }

    public boolean isPalindromeBruteForce(int number) {
        // Negative numbers cannot be palindromes as the '-' sign breaks the symmetry.
        if (number < 0) {
            return false;
        }

        long reversedNumber = 0;
        int tempNum = number;
        // Reverse the number by repeatedly taking the last digit and adding it to the reversed number.
        while (tempNum > 0) {
            int lastDigit = tempNum % 10;
            reversedNumber = reversedNumber * 10 + lastDigit;
            tempNum = tempNum / 10;
        }

        // The number is a palindrome if its reversed form is identical to the original.
        return reversedNumber == number;
    }
}
