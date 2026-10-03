package tutorial.interview;
// https://leetcode.com/problems/add-two-numbers/

/**
 *
 * Add Two Numbers
 * <p>
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 * <p>
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * <p>
 * Example 1:
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 * <p>
 * Example 2:
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 * <p>
 * Example 3:
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 *
 */
public class AddTwoNumbers {
    public static void main(String[] args) {
        // Test case 1
        ListNode l1 = buildList(new int[]{2, 4, 3});
        ListNode l2 = buildList(new int[]{5, 6, 4});
        System.out.print("Input:  [2,4,3] + [5,6,4] -> Output: ");
        printList(addTwoNumbers(l1, l2));

        // Test case 2
        l1 = buildList(new int[]{0});
        l2 = buildList(new int[]{0});
        System.out.print("Input:  [0] + [0] -> Output: ");
        printList(addTwoNumbers(l1, l2));

        // Test case 3
        l1 = buildList(new int[]{9, 9, 9, 9, 9, 9, 9});
        l2 = buildList(new int[]{9, 9, 9, 9});
        System.out.print("Input:  [9,9,9,9,9,9,9] + [9,9,9,9] -> Output: ");
        printList(addTwoNumbers(l1, l2));
    }

    // ---------- Test Harness Helpers ----------
    public static ListNode buildList(int[] nums) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int n : nums) {
            curr.next = new ListNode(n);
            curr = curr.next;
        }
        return dummy.next;
    }

    public static void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.val);
            if (node.next != null) System.out.print(" -> ");
            node = node.next;
        }
        System.out.println();
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;
            int sum = val1 + val2 + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        return dummy.next;
    }
}
