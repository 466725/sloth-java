package tutorial.interview;
// https://www.jointaro.com/interviews/questions/merge-intervals/?src=taro75

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        if (intervals == null || intervals.length <= 1)
            return intervals;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> result = new ArrayList<>();
        int[] current = intervals[0];
        for (int i = 1; i < intervals.length; i++)
            if (current[1] >= intervals[i][0])
                current[1] = Math.max(current[1], intervals[i][1]);
            else {
                result.add(current);
                current = intervals[i];
            }
        result.add(current);
        return result.toArray(new int[result.size()][]);
    }
}
