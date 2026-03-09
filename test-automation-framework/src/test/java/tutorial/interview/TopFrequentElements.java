package tutorial.interview;
// https://www.jointaro.com/interviews/questions/top-k-frequent-elements/?src=taro75

import java.util.*;

/**
 *
 * Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 * <p>
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 * <p>
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 */

public class TopFrequentElements {
    static int[] topKFrequent(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        // Step 1: Build frequency map
        Map<Integer, Integer> frequencyMap = buildFrequencyMap(nums);

        // Step 2: Create buckets indexed by frequency
        List<Integer>[] buckets = buildBuckets(frequencyMap, nums.length);

        // Step 3: Collect top k elements
        return collectTopK(buckets, k);
    }

    private static Map<Integer, Integer> buildFrequencyMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums)
            map.put(num, map.getOrDefault(num, 0) + 1);
        return map;
    }

    @SuppressWarnings("unchecked")
    private static List<Integer>[] buildBuckets(Map<Integer, Integer> frequencyMap, int maxSize) {
        List<Integer>[] buckets = new List[maxSize + 1];
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int number = entry.getKey();
            int frequency = entry.getValue();
            if (buckets[frequency] == null) buckets[frequency] = new ArrayList<>();
            buckets[frequency].add(number);
        }
        return buckets;
    }

    private static int[] collectTopK(List<Integer>[] buckets, int k) {
        int[] result = new int[k];
        int index = 0;
        for (int freq = buckets.length - 1; freq >= 1 && index < k; freq--)
            if (buckets[freq] != null)
                for (int num : buckets[freq]) {
                    result[index++] = num;
                    if (index == k) break;
                }

        return result;
    }

    // ---------------- Test Harness ----------------

    private static void test(int[] nums, int k) {
        int[] result = topKFrequent(nums, k);
        System.out.println("================================");
        System.out.println("nums = " + Arrays.toString(nums));
        System.out.println("k = " + k);
        System.out.println("result = " + Arrays.toString(result));
    }

    public static void main(String[] args) {
        test(new int[]{1, 1, 1, 2, 2, 3}, 2);   // expected [1,2]
        test(new int[]{1}, 1);              // expected [1]
        // additional cases
        test(new int[]{4, 4, 4, 6, 6, 7, 7, 7, 7}, 2);
        test(new int[]{5, 5, 6, 6, 6, 7}, 1);
        test(new int[]{1, 2, 3, 4, 5}, 3);
    }
}