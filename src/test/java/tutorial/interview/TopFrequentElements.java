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
        Map<Integer, Integer> elementCounts = new HashMap<>();
        for (int num : nums)
            elementCounts.put(num, elementCounts.getOrDefault(num, 0) + 1);
        List<Integer>[] frequencyBuckets = new List[nums.length + 1];
        //Populate frequency buckets
        for (int number : elementCounts.keySet()) {
            int frequency = elementCounts.get(number);
            if (frequencyBuckets[frequency] == null)
                frequencyBuckets[frequency] = new ArrayList<>();
            frequencyBuckets[frequency].add(number);
        }
        List<Integer> topKElements = new ArrayList<>();
        // Iterate from highest frequency to lowest.
        for (int frequency = frequencyBuckets.length - 1; frequency >= 1 && topKElements.size() < k; frequency--)
            // Only process if the bucket is not empty
            if (frequencyBuckets[frequency] != null)
                //Add elements to result
                topKElements.addAll(frequencyBuckets[frequency]);
        int[] result = new int[k];
        // Ensure only k elements are returned.
        for (int i = 0; i < k; i++)
            result[i] = topKElements.get(i);
        return result;
    }
}