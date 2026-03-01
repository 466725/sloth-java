package tutorial.interview;

// https://www.jointaro.com/interviews/questions/two-sum/?src=taro75

import java.util.Arrays;
import java.util.HashMap;

/**
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 * <p>
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * <p>
 * You can return the answer in any order.
 * <p>
 * Example 1:
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 * <p>
 * Example 2:
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * <p>
 * Example 3:
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 */
public class TwoSum {
    public static void main(String[] args) {
        runCase(new int[]{2, 7, 11, 15}, 9);   // expected [0, 1]
        runCase(new int[]{3, 2, 4}, 6);        // expected [1, 2]
        runCase(new int[]{3, 3}, 6);           // expected [0, 1]
        runCase(new int[]{1, 2, 3}, 10);       // expected [-1, -1]
    }

    public static int[] sumTwo(int[] numArray, int target) {
        if (numArray == null || numArray.length <= 1) {
            return new int[]{-1, -1};
        }
        HashMap<Integer, Integer> complementToIndexMap = new HashMap<>();
        for (int i = 0; i < numArray.length; i++) {
            if (complementToIndexMap.containsKey(numArray[i])) {
                return new int[]{complementToIndexMap.get(numArray[i]), i};
            }
            complementToIndexMap.put(target - numArray[i], i);
        }
        return new int[]{-1, -1};
    }

    private static void runCase(int[] nums, int target) {
        int[] answer = sumTwo(nums, target);
        System.out.println(
                "nums=" + Arrays.toString(nums)
                        + ", target=" + target
                        + ", result=" + Arrays.toString(answer)
        );
    }
}
