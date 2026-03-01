package tutorial.interview;
// https://www.jointaro.com/interviews/questions/reverse-linked-list/?src=taro75

public class ReverseLinkedList {
    // =========================
    // Helper: Build Linked List from array
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

        ReverseLinkedList solver = new ReverseLinkedList();

        int[][] testCases = {
                {1, 2, 3, 4, 5},
                {1, 2},
                {7},
                {},
                {10, 20, 30}
        };

        System.out.println("===== Reverse Linked List Tests =====");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("----------------------------------");
            System.out.println("Test Case " + (i + 1));
            ListNode head = buildList(testCases[i]);

            System.out.print("Original: ");
            printList(head);
            ListNode reversed = solver.reverseList(head);

            System.out.print("Reversed: ");
            printList(reversed);
        }

        System.out.println("====================================");
    }

    // =========================
    // Reverse Linked List (Iterative)
    // =========================
    public ListNode reverseList(ListNode head) {
        ListNode previousNode = null;
        ListNode currentNode = head;

        while (currentNode != null) {
            ListNode nextNode = currentNode.next;
            currentNode.next = previousNode;
            previousNode = currentNode;
            currentNode = nextNode;
        }

        return previousNode;
    }

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
}
