package tutorial.interview.taro75;
// https://www.jointaro.com/interviews/questions/merge-two-sorted-lists/?src=taro75

/**
 *
 * You are given the heads of two sorted linked lists list1 and list2.
 * <p>
 * Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.
 * <p>
 * Return the head of the merged linked list.
 *
 */
public class MergeTwoSortedLinkedLists {
    // =========================
    // Definition for singly-linked list
    // =========================
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    // =========================
    // Merge Two Sorted Lists (Iterative - Optimal)
    // =========================
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        ListNode dummy = new ListNode(-1);
        ListNode tail = dummy;

        while (list1 != null && list2 != null) {

            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }

            tail = tail.next;
        }

        // Attach remaining nodes
        tail.next = (list1 != null) ? list1 : list2;

        return dummy.next;
    }

    // =========================
    // Helper: Build Linked List
    // =========================
    public static ListNode buildList(int[] values) {

        if (values == null || values.length == 0) {
            return null;
        }

        ListNode head = new ListNode(values[0]);
        ListNode current = head;

        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }

        return head;
    }

    // =========================
    // Helper: Print Linked List
    // =========================
    public static void printList(ListNode head) {

        if (head == null) {
            System.out.println("null");
            return;
        }

        ListNode current = head;

        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }

        System.out.println();
    }

    // =========================
    // ✅ Test Harness
    // =========================
    public static void main(String[] args) {

        MergeTwoSortedLinkedLists solver =
                new MergeTwoSortedLinkedLists();

        int[][] list1Cases = {
                {1, 2, 4},
                {},
                {},
                {5, 10, 15},
                {1, 1, 2}
        };

        int[][] list2Cases = {
                {1, 3, 4},
                {0},
                {},
                {2, 3, 20},
                {1, 3, 4}
        };

        System.out.println("===== Merge Two Sorted Lists Tests =====");

        for (int i = 0; i < list1Cases.length; i++) {

            System.out.println("------------------------------------");
            System.out.println("Test Case " + (i + 1));

            ListNode list1 = buildList(list1Cases[i]);
            ListNode list2 = buildList(list2Cases[i]);

            System.out.print("List 1: ");
            printList(list1);

            System.out.print("List 2: ");
            printList(list2);

            ListNode merged = solver.mergeTwoLists(list1, list2);

            System.out.print("Merged: ");
            printList(merged);
        }

        System.out.println("========================================");
    }
}
