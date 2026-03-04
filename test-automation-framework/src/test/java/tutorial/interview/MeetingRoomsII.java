package tutorial.interview;
// https://www.jointaro.com/interviews/questions/meeting-rooms-ii/?src=taro75

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
 * <p>
 * Example 1:
 * Input: intervals = [[0,30],[5,10],[15,20]]
 * Output: 2
 * <p>
 * Example 2:
 * Input: intervals = [[7,10],[2,4]]
 * Output: 1
 *
 */
public class MeetingRoomsII {
    public static void main(String[] args) {
        MeetingRoomsII meetingRooms = new MeetingRoomsII();
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println("Minimum number of meeting rooms required: " + meetingRooms.minMeetingRooms(intervals));
        int[][] intervalsTwo = {{7, 10}, {2, 4}};
        System.out.println("Minimum number of meeting rooms required: " + meetingRooms.minMeetingRooms(intervalsTwo));
        int[][] intervalsThree = {{1, 5}, {8, 9}, {8, 9}};
        System.out.println("Minimum number of meeting rooms required: " + meetingRooms.minMeetingRooms(intervalsThree));
        int[][] intervalsFour = {{1, 5}, {5, 8}};
        System.out.println("Minimum number of meeting rooms required: " + meetingRooms.minMeetingRooms(intervalsFour));
    }

    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;
        Map<Integer, Integer> timeline = new HashMap<>();
        // Mark every time unit
        for (int[] interval : intervals)
            for (int i = interval[0]; i < interval[1]; i++)
                timeline.put(i, timeline.getOrDefault(i, 0) + 1);
        int maxRooms = 0;
        for (int count : timeline.values())
            maxRooms = Math.max(maxRooms, count);
        return maxRooms;
    }
}
