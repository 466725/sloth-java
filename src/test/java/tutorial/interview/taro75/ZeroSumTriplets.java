package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/3sum/?src=taro75

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
    public static java.util.List<java.util.List<Integer>> threeSumBruteForce(int[] numbers) {
        java.util.Set<java.util.List<Integer>> resultSet = new java.util.HashSet<>();
        int numbersLength = numbers.length;

        // Iterate through all possible combinations of three numbers.
        for (int firstIndex = 0; firstIndex < numbersLength; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < numbersLength; secondIndex++) {
                for (int thirdIndex = secondIndex + 1; thirdIndex < numbersLength; thirdIndex++) {
                    // Check if the sum of the three numbers is equal to zero.
                    if (numbers[firstIndex] + numbers[secondIndex] + numbers[thirdIndex] == 0) {

                        java.util.List<Integer> triplet = java.util.Arrays.asList(numbers[firstIndex], numbers[secondIndex], numbers[thirdIndex]);

                        // Sort the triplet to handle duplicate combinations.
                        java.util.Collections.sort(triplet);

                        // Add the sorted triplet to the result set.
                        resultSet.add(triplet);

                    }
                }
            }
        }

        // Convert the set of triplets to a list and return it.
        return new java.util.ArrayList<>(resultSet);
    }
}
