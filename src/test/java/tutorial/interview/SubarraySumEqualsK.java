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
        if (numbers == null || numbers.length == 0)
            return 0;
        int result = 0;
        // Iterate through all possible start indices
        for (int i = 0; i < numbers.length; i++) {
            //Iterate through all possible end indices for each start index
            int sum = 0;
            for (int j = i; j < numbers.length; j++) {
                sum += numbers[j];
                //Check if the sum equals the target
                if (sum == target)
                    result++;
            }
        }
        return result;
    }
}
