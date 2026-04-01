package tutorial.interview;
// https://www.jointaro.com/interviews/questions/longest-consecutive-sequence/?src=taro75

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 * <p>
 * You must write an algorithm that runs in O(n) time.
 * <p>
 * Example 1:
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore, its length is 4.
 * <p>
 * Example 2:
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 * <p>
 * Example 3:
 * Input: nums = [1,0,1,2]
 * Output: 3
 *
 */
public class LongestConsecutiveSequence {
    // ==============================
    // ✅ Test Harness
    // ==============================
    public static void main(String[] args) {
        LongestConsecutiveSequence solver = new LongestConsecutiveSequence();
        int[][] testCases = {{100, 4, 200, 1, 3, 2}, {0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, {1, 0, 1, 2}, {}, {5}, {-2, -3, -1, -5, -4}};
        System.out.println("===== Longest Consecutive Sequence Tests =====");
        for (int i = 0; i < testCases.length; i++) {
            int[] input = testCases[i];
            int result = solver.longestConsecutive(input);
            System.out.println("--------------------------------------------");
            System.out.println("Test Case " + (i + 1));
            System.out.println("Input:  " + Arrays.toString(input));
            System.out.println("Output: " + result);
        }
        System.out.println("=============================================");
    }

    public int longestConsecutive(int[] numbers) {
        HashSet<Integer> numberSet = new HashSet<>();
        for (int number : numbers)
            numberSet.add(number);
        int longest = 0;
        for (int number : numbers) {
            //If the number is not the start, skip
            if (numberSet.contains(number - 1)) continue;
            int currentNumber = number;
            int currentLength = 1;
            //Build the sequence from the start.
            while (numberSet.contains(currentNumber + 1)) {
                currentNumber++;
                currentLength++;
            }
            //Keep track of the longest sequence.
            longest = Math.max(longest, currentLength);
        }
        return longest;
    }
}
