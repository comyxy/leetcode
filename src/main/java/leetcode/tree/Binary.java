package leetcode.tree;

import leetcode.node.TreeNode;

import java.util.*;

/**
 * 二叉树
 * @date 2020/7/7
 */
public class Binary {
    /**
     * LeetCode110
     * 给定一个二叉树，判断它是否是高度平衡的二叉树
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        int height = isBalancedHelper(root);
        return balanced;
    }

    private boolean balanced = true;

    private int isBalancedHelper(TreeNode node){
        if(node == null){
            return 0;
        }
        int leftHeight = isBalancedHelper(node.left);
        int rightHeight = isBalancedHelper(node.right);
        if(leftHeight - rightHeight > 1 || rightHeight - leftHeight > 1){
            balanced = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }


    /**
     * LeetCode107
     * 给定一个二叉树，返回其节点值自底向上的层次遍历
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;
        while (!queue.isEmpty()){
            int n = queue.size();
            List<Integer> t = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode poll = queue.poll();
                t.add(poll.val);
                if(poll.left != null)
                    queue.offer(poll.left);
                if(poll.right != null)
                    queue.offer(poll.right);
            }
            res.add(t);
        }
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        Binary binary = new Binary();
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(9);
        TreeNode node3 = new TreeNode(20);
        TreeNode node4 = new TreeNode(15);
        TreeNode node5 = new TreeNode(7);
        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;
        List<List<Integer>> result = binary.levelOrderBottom(node1);
        System.out.println(result);
    }
}
