package tutorial.interview;
// https://www.jointaro.com/interviews/questions/contains-duplicate/?src=taro75

/**
 *
 * Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct.
 * <p>
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: true
 * Explanation:
 * The element 1 occurs at the indices 0 and 3.
 * <p>
 * Example 2:
 * Input: nums = [1,2,3,4]
 * Output: false
 * Explanation:
 * All elements are distinct.
 * <p>
 * Example 3:
 * Input: nums = [1,1,1,3,3,4,3,2,4,2]
 * Output: true
 *
 */
public class ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        java.util.HashSet<Integer> numberSet = new java.util.HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            // Use HashSet to check for duplicates
            if (numberSet.contains(nums[i])) {
                return true;
            }
            // Add the number to the set
            numberSet.add(nums[i]);
        }

        return false;
    }
}
