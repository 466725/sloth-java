package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/merge-intervals/?src=taro75

import java.util.Arrays;

/**
 *
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
 * <p>
 * Example 1:
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
 * <p>
 * Example 2:
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 *
 */
public class MergeIntervals {
    public static void main(String[] args) {
        int[][] input1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] input2 = {{1, 4}, {4, 5}};
        int[][] input3 = {{8, 10}, {1, 3}, {2, 6}, {15, 18}};

        System.out.println("Input 1:  " + Arrays.deepToString(input1));
        System.out.println("Merged 1: " + Arrays.deepToString(merge(input1)));

        System.out.println("Input 2:  " + Arrays.deepToString(input2));
        System.out.println("Merged 2: " + Arrays.deepToString(merge(input2)));

        System.out.println("Input 3 (unsorted):  " + Arrays.deepToString(input3));
        System.out.println("Merged 3: " + Arrays.deepToString(merge(input3)));
    }

    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // Keep merging pairs until a full pass makes no changes.
        boolean didMergeInPass = true;
        while (didMergeInPass) {
            didMergeInPass = false;

            for (int leftIndex = 0; leftIndex < intervals.length; leftIndex++) {
                if (!isValidInterval(intervals[leftIndex])) {
                    continue;
                }
                // Important: keep previous true state within this pass.
                if (mergeWithRight(intervals, leftIndex)) {
                    didMergeInPass = true;
                }
            }
        }

        return compactNonNullIntervals(intervals);
    }

    private static boolean isOverlapping(int[] left, int[] right) {
        return left[0] <= right[1] && left[1] >= right[0];
    }

    // Merge two overlapping intervals and return the combined interval.
    private static int[] mergeRightToLeft(int[] left, int[] right) {
        int mergedStart = Math.min(left[0], right[0]);
        int mergedEnd = Math.max(left[1], right[1]);
        return new int[]{mergedStart, mergedEnd};
    }

    // Merge left interval with any interval to its right.
    private static boolean mergeWithRight(int[][] intervals, int left) {
        for (int right = left + 1; right < intervals.length; right++) {
            if (!isValidInterval(intervals[right])) {
                continue;
            }

            if (isOverlapping(intervals[left], intervals[right])) {
                intervals[left] = mergeRightToLeft(intervals[left], intervals[right]);
                intervals[right] = null;
                return true;
            }
        }
        return false;
    }

    private static boolean isValidInterval(int[] interval) {
        return interval != null && interval.length >= 2;
    }

    private static int[][] compactNonNullIntervals(int[][] intervals) {
        int validCount = 0;
        for (int[] interval : intervals) {
            if (isValidInterval(interval)) {
                validCount++;
            }
        }

        int[][] mergedArray = new int[validCount][2];
        int out = 0;
        for (int[] interval : intervals) {
            if (isValidInterval(interval)) {
                mergedArray[out++] = interval;
            }
        }
        return mergedArray;
    }

}
