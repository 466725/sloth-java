package tutorial.interview;
// https://leetcode.com/problems/remove-nth-node-from-end-of-list/description/

/**
 *
 * Remove Nth Node From End of List
 * <p>
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 * <p>
 * Example 1:
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 * <p>
 * Example 2:
 * Input: head = [1], n = 1
 * Output: []
 * <p>
 * Example 3:
 * Input: head = [1,2], n = 1
 * Output: [1]
 *
 */
public class RemoveNthNodeFromEndList {
    public static ListNode removeNthFromEnd(ListNode input, int n) {
        if (input == null) return null;

        ListNode dummy = new ListNode(0);
        dummy.next = input;
        ListNode left = dummy;
        ListNode right = dummy;

        // move right pointer n steps
        for (int i = 0; i < n; i++) {
            if (right.next == null)
                return dummy.next;
            right = right.next;
        }

        // move both pointers
        while (right.next != null) {
            right = right.next;
            left = left.next;
        }

        // remove node
        left.next = left.next.next;

        return dummy.next;
    }

    // ---------- Helper Classes & Methods ----------
    static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int v : arr) {
            curr.next = new ListNode(v);
            curr = curr.next;
        }
        return dummy.next;
    }

    static void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val);
            if (curr.next != null) System.out.print(" -> ");
            curr = curr.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Example 1
        ListNode list1 = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Input:  ");
        printList(list1);

        ListNode result1 = removeNthFromEnd(list1, 2);
        System.out.print("Output: ");
        printList(result1);

        System.out.println();

        // Example 2
        ListNode list2 = buildList(new int[]{1});
        System.out.print("Input:  ");
        printList(list2);

        ListNode result2 = removeNthFromEnd(list2, 1);
        System.out.print("Output: ");
        printList(result2);

        System.out.println();

        // Example 3
        ListNode list3 = buildList(new int[]{1, 2});
        System.out.print("Input:  ");
        printList(list3);

        ListNode result3 = removeNthFromEnd(list3, 1);
        System.out.print("Output: ");
        printList(result3);
    }
}
