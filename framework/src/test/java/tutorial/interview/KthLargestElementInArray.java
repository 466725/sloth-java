package tutorial.interview;
// https://www.jointaro.com/interviews/questions/kth-largest-element-in-an-array/?src=taro75

/**
 *
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 * <p>
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 * <p>
 * Can you solve it without sorting?
 * <p>
 * Example 1:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 5
 * <p>
 * Example 2:
 * Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
 * Output: 4
 *
 */

public class KthLargestElementInArray {
    public int findKthLargestElement(int[] numbers, int k) {
        if (numbers == null || numbers.length == 0)
            return -1;
        if (k > numbers.length || k <= 0)
            return -1;
        int[] temp = numbers.clone();
        // Iterate k times
        for (int i = 0; i < k; i++) {
            int indexOfLargest = 0;
            // Find index of the largest
            for (int j = 1; j < temp.length; j++) {
                if (temp[j] > temp[indexOfLargest]) {
                    indexOfLargest = j;
                }
            }
            // Check kth iteration
            if (i == k - 1) {
                return temp[indexOfLargest];
            }
            // Set largest to MIN_VALUE
            temp[indexOfLargest] = Integer.MIN_VALUE;
        }
        return -1;
    }
}
