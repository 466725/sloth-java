package tutorial.interview;
// https://www.jointaro.com/interviews/questions/contains-duplicate/?src=taro75

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.
 * <p>
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: true
 * Explanation:
 * The element 1 occurs at the indices 0 and 3.
 * <p>
 * Example 2:
 * Input: nums = [1,2,3,4]
 * Output: false
 * Explanation:
 * All elements are distinct.
 * <p>
 * Example 3:
 * Input: nums = [1,1,1,3,3,4,3,2,4,2]
 * Output: true
 *
 */
public class ContainsDuplicate {
    public static void main(String[] args) {
        ContainsDuplicate solver = new ContainsDuplicate();
        int[][] testCases = {
                {1, 2, 3, 1},                // true
                {1, 2, 3, 4},                // false
                {1, 1, 1, 3, 3, 4, 3, 2, 4, 2}, // true
                {},                          // false
                {42},                        // false
                {5, 6, 7, 8, 9, 5}           // true
        };
        System.out.println("==== Contains Duplicate Tests ====");
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            boolean result = solver.containsDuplicate(nums);
            System.out.println("----------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input:  " + Arrays.toString(nums));
            System.out.println("Output: " + result);
        }
        System.out.println("==================================");
    }

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> numberSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            // Use HashSet to check for duplicates
            if (numberSet.contains(nums[i]))
                return true;
            // Add the number to the set
            numberSet.add(nums[i]);
        }
        return false;
    }
}
