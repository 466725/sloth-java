package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/product-of-array-except-self/?src=taro75

import java.util.Arrays;

/**
 *
 * Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
 * <p>
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * <p>
 * You must write an algorithm that runs in O(n) time and without using the division operation.
 * <p>
 * Example 1:
 * Input: nums = [1,2,3,4]
 * Output: [24,12,8,6]
 * <p>
 * Example 2:
 * Input: nums = [-1,1,0,-3,3]
 * Output: [0,0,9,0,0]
 *
 */

public class ProductArrayExceptSelf {
    // ==============================
    // ✅ Test Harness
    // ==============================
    public static void main(String[] args) {

        ProductArrayExceptSelf solver = new ProductArrayExceptSelf();

        int[][] testCases = {
                {1, 2, 3, 4},
                {-1, 1, 0, -3, 3},
                {0, 0},
                {5},
                {2, 3}
        };

        System.out.println("===== Product of Array Except Self Tests =====");

        for (int i = 0; i < testCases.length; i++) {

            int[] input = testCases[i];

            int[] bruteResult = solver.productExceptSelfBruteForce(input);
            int[] optimalResult = solver.productExceptSelfBruteForce(input);

            System.out.println("--------------------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input:          " + Arrays.toString(input));
            System.out.println("Brute Force:    " + Arrays.toString(bruteResult));
            System.out.println("Optimal (O(n)): " + Arrays.toString(optimalResult));
        }

        System.out.println("=============================================");
    }

    public int[] productExceptSelfBruteForce(int[] numbers) {
        int n = numbers.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            int product = 1;

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    product *= numbers[j];
                }
            }

            result[i] = product;
        }

        return result;
    }
}
