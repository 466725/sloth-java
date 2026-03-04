package tutorial.interview;
// https://www.jointaro.com/interviews/questions/3sum/?src=taro75

import java.util.*;

/**
 *
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * <p>
 * Notice that the solution set must not contain duplicate triplets.
 * <p>
 * Example 1:
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Explanation:
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
 * The distinct triplets are [-1,0,1] and [-1,-1,2].
 * Notice that the order of the output and the order of the triplets does not matter.
 * <p>
 * Example 2:
 * Input: nums = [0,1,1]
 * Output: []
 * Explanation: The only possible triplet does not sum up to 0.
 * <p>
 * Example 3:
 * Input: nums = [0,0,0]
 * Output: [[0,0,0]]
 * Explanation: The only possible triplet sums up to 0.
 *
 */
public class ZeroSumTriplets {
    public static List<List<Integer>> threeSumBruteForce(int[] numbers) {
        Set<List<Integer>> resultSet = new HashSet<>();
        int numbersLength = numbers.length;
        // Iterate through all possible combinations of three numbers.
        for (int i = 0; i < numbersLength; i++)
            for (int j = i + 1; j < numbersLength; j++)
                for (int k = j + 1; k < numbersLength; k++)
                    if (numbers[i] + numbers[j] + numbers[k] == 0) {
                        List<Integer> triplet = Arrays.asList(numbers[i], numbers[j], numbers[k]);
                        // Sort the triplet to handle duplicate combinations.
                        Collections.sort(triplet);
                        // Add the sorted triplet to the result set.
                        resultSet.add(triplet);
                    }
        return new ArrayList<>(resultSet);
    }
}
