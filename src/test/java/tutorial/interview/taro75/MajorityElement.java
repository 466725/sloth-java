package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/majority-element/?src=taro75

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * Given an array nums of size n, return the majority element.
 * <p>
 * The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.
 * <p>
 * Example 1:
 * Input: nums = [3,2,3]
 * Output: 3
 * <p>
 * Example 2:
 * Input: nums = [2,2,1,1,1,2,2]
 * Output: 2
 *
 */
public class MajorityElement {
    // ==============================
    // ✅ Test Harness
    // ==============================
    public static void main(String[] args) {

        MajorityElement solver = new MajorityElement();

        int[][] testCases = {
                {3, 2, 3},
                {2, 2, 1, 1, 1, 2, 2},
                {1},
                {5, 5, 5, 2, 5, 3, 5, 5},
                {4, 4, 4, 4, 2, 3}
        };

        System.out.println("===== Majority Element Tests =====");

        for (int i = 0; i < testCases.length; i++) {

            int[] input = testCases[i];
            int result = solver.majorityElement(input);

            System.out.println("-----------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Array: " + Arrays.toString(input));
            System.out.println("Majority Element: " + result);
        }

        System.out.println("===================================");
    }

    public int majorityElement(int[] numbers) {

        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }

        int threshold = numbers.length / 2;

        HashMap<Integer, Integer> frequencyMap = new HashMap<>();

        for (int num : numbers) {

            int newCount = frequencyMap.getOrDefault(num, 0) + 1;
            frequencyMap.put(num, newCount);

            if (newCount > threshold) {
                return num;
            }
        }

        // Since problem guarantees existence, this should never happen
        throw new IllegalStateException("Majority element not found");
    }
}
