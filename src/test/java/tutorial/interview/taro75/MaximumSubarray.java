package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/maximum-subarray/?src=taro75

/**
 *
 * Given an integer array nums, find the subarray with the largest sum, and return its sum.
 * <p>
 * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: The subarray [4,-1,2,1] has the largest sum 6.
 * <p>
 * Example 2:
 * Input: nums = [1]
 * Output: 1
 * Explanation: The subarray [1] has the largest sum 1.
 * <p>
 * Example 3:
 * Input: nums = [5,4,-1,7,8]
 * Output: 23
 * Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
 *
 */
public class MaximumSubarray {
    public int maxSubArrayBruteForce(int[] numbers) {
        int maximumSubarraySum = Integer.MIN_VALUE;

        // Iterate through all possible starting positions
        for (int startIndex = 0; startIndex < numbers.length; startIndex++) {

            int currentSubarraySum = 0;

            // Iterate through all possible ending positions for current start
            for (int endIndex = startIndex; endIndex < numbers.length; endIndex++) {

                // Accumulate sum to find the current subarray's sum.
                currentSubarraySum += numbers[endIndex];

                // Keep track of the largest subarray sum seen so far
                if (currentSubarraySum > maximumSubarraySum) {
                    maximumSubarraySum = currentSubarraySum;
                }
            }
        }

        return maximumSubarraySum;
    }
}
