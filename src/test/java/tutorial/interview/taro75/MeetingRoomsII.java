package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/meeting-rooms-ii/?src=taro75

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
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int numberOfMeetings = intervals.length;

        for (int numberOfRooms = 1; numberOfRooms <= numberOfMeetings; numberOfRooms++) {
            if (canSchedule(intervals, numberOfRooms)) {
                return numberOfRooms;
            }
        }

        return numberOfMeetings;
    }

    private boolean canSchedule(int[][] intervals, int numberOfRooms) {
        int numberOfMeetings = intervals.length;
        int[] roomAssignments = new int[numberOfMeetings];

        return scheduleHelper(intervals, roomAssignments, 0, numberOfRooms);
    }

    private boolean scheduleHelper(int[][] intervals, int[] roomAssignments, int meetingIndex, int numberOfRooms) {
        if (meetingIndex == intervals.length) {
            return true;
        }

        // Try assigning the current meeting to each available room
        for (int roomIndex = 0; roomIndex < numberOfRooms; roomIndex++) {
            if (isSafeToSchedule(intervals, roomAssignments, meetingIndex, roomIndex)) {
                roomAssignments[meetingIndex] = roomIndex;

                // Recursively try to schedule the remaining meetings
                if (scheduleHelper(intervals, roomAssignments, meetingIndex + 1, numberOfRooms)) {
                    return true;
                }

                // Backtrack: Reset the room assignment for the current meeting
                roomAssignments[meetingIndex] = 0;
            }
        }

        // If no room can accommodate the current meeting, return false
        return false;
    }

    private boolean isSafeToSchedule(int[][] intervals, int[] roomAssignments, int currentMeetingIndex, int roomIndex) {
        // Check for overlaps with meetings already scheduled in the given room
        for (int previousMeetingIndex = 0; previousMeetingIndex < currentMeetingIndex; previousMeetingIndex++) {

            // Only check if the previous meeting is in the current room
            if (roomAssignments[previousMeetingIndex] == roomIndex) {

                // Check for overlaps
                if (intervalsOverlap(intervals[previousMeetingIndex], intervals[currentMeetingIndex])) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean intervalsOverlap(int[] intervalOne, int[] intervalTwo) {
        return !(intervalOne[1] <= intervalTwo[0] || intervalTwo[1] <= intervalOne[0]);
    }
}
