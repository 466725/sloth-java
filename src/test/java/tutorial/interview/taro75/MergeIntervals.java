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

        System.out.println("Input 1:  " + Arrays.deepToString(input1));
        System.out.println("Merged 1: " + Arrays.deepToString(merge(input1)));

        System.out.println("Input 2:  " + Arrays.deepToString(input2));
        System.out.println("Merged 2: " + Arrays.deepToString(merge(input2)));
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
                if (intervals[leftIndex] == null) {
                    continue;
                }
                // Try merging this left interval with any interval to its right.
                if (mergeWithRight(intervals, leftIndex)) {
                    didMergeInPass = true;
                }
            }
        }

        return compactNonNullIntervals(intervals);
    }

    private static boolean isOverlapping(int[] first, int[] second) {
        int firstStart = first[0];
        int firstEnd = first[1];
        int secondStart = second[0];
        int secondEnd = second[1];
        return firstStart <= secondEnd && secondStart <= firstEnd;
    }

    private static int[] mergeTwo(int[] first, int[] second) {
        int mergedStart = Math.min(first[0], second[0]);
        int mergedEnd = Math.max(first[1], second[1]);
        return new int[]{mergedStart, mergedEnd};
    }

    private static boolean mergeWithRight(int[][] intervals, int leftIndex) {
        for (int rightIndex = leftIndex + 1; rightIndex < intervals.length; rightIndex++) {
            if (intervals[rightIndex] == null) {
                continue;
            }

            if (isOverlapping(intervals[leftIndex], intervals[rightIndex])) {
                intervals[leftIndex] = mergeTwo(intervals[leftIndex], intervals[rightIndex]);
                intervals[rightIndex] = null;
                return true;
            }
        }
        return false;
    }

    private static int[][] compactNonNullIntervals(int[][] intervals) {
        int validCount = 0;
        for (int[] interval : intervals) {
            if (interval != null) {
                validCount++;
            }
        }

        int[][] result = new int[validCount][2];
        int out = 0;
        for (int[] interval : intervals) {
            if (interval != null) {
                result[out++] = interval;
            }
        }
        return result;
    }

}
