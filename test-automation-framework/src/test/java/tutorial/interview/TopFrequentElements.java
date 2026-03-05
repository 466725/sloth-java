package tutorial.interview;
// https://www.jointaro.com/interviews/questions/top-k-frequent-elements/?src=taro75

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int[] topKFrequent(int[] nums, int k) {
        // Step 1: Build frequency map
        Map<Integer, Integer> frequencyMap = buildFrequencyMap(nums);
        // Step 2: Create buckets indexed by frequency
        List<Integer>[] buckets = buildBuckets(frequencyMap, nums.length);
        // Step 3: Collect top k elements
        return collectTopK(buckets, k);
    }

    private Map<Integer, Integer> buildFrequencyMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums)
            map.put(num, map.getOrDefault(num, 0) + 1);
        return map;
    }

    private List<Integer>[] buildBuckets(Map<Integer, Integer> frequencyMap, int maxSize) {
        List<Integer>[] buckets = new List[maxSize + 1];

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int number = entry.getKey();
            int frequency = entry.getValue();
            if (buckets[frequency] == null)
                buckets[frequency] = new ArrayList<>();
            buckets[frequency].add(number);
        }
        return buckets;
    }

    private int[] collectTopK(List<Integer>[] buckets, int k) {
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
}