package tutorial.interview.taro75;
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
 *
 */

public class KthLargestElementInArray {
    public int findKthLargestElement(int[] numbers, int k) {
        int[] temporaryArray = numbers.clone();

        // Iterate k times to find the kth largest element
        for (int i = 0; i < k; i++) {
            int indexOfLargest = 0;

            // Find index of the largest element in the remaining array
            for (int j = 1; j < temporaryArray.length; j++) {
                if (temporaryArray[j] > temporaryArray[indexOfLargest]) {
                    indexOfLargest = j;
                }
            }

            // If this is the kth iteration, return the element
            if (i == k - 1) {
                return temporaryArray[indexOfLargest];
            }

            // Set largest element to smallest possible int so
            // it won't be selected again
            temporaryArray[indexOfLargest] = Integer.MIN_VALUE;

        }

        return -1;
    }
}
