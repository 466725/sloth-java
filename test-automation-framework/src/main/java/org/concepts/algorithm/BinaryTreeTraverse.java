package org.concepts.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BinaryTreeTraverse {
    public List<Integer> inorderTraverse(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                TreeNode node = stack.pop();
                result.add(node.val);
                cur = node.right;
            }
        }
        return result;
    }

    public List<Integer> inorderTraverseRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        traverse(root, result);
        return result;
    }

    private void traverse(TreeNode node, List<Integer> result) {
        if (node == null)
            return;
        // 1. left
        traverse(node.left, result);
        // 2. root
        result.add(node.val);
        // 3. right
        traverse(node.right, result);
    }
}
