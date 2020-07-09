package leetcode.node;

/**
 * 包含next右侧指针的二叉树节点
 * @date 2020/7/8
 */
public class NextNode {
    public int val;
    public NextNode left;
    public NextNode right;
    public NextNode next;

    public NextNode() {}

    public NextNode(int _val, NextNode _left, NextNode _right, NextNode _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}
