package tutorial.interview;
// https://www.jointaro.com/interviews/questions/subarray-sum-equals-k/?src=taro75

/**
 *
 * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
 * <p>
 * A subarray is a contiguous non-empty sequence of elements within an array.
 * <p>
 * Example 1:
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 * <p>
 * Example 2:
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 *
 */
public class SubarraySumEqualsK {
    public int subarraySumEqualsK(int[] numbers, int target) {
        int numberOfSubarrays = 0;

        // Iterate through all possible start indices
        for (int startIndex = 0; startIndex < numbers.length; startIndex++) {
            //Iterate through all possible end indices for each start index
            int currentSubarraySum = 0;
            for (int endIndex = startIndex; endIndex < numbers.length; endIndex++) {
                currentSubarraySum += numbers[endIndex];

                //Check if the sum equals the target
                if (currentSubarraySum == target) {
                    numberOfSubarrays++;
                }
            }
        }

        return numberOfSubarrays;
    }
}
