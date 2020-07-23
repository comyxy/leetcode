package leetcode.backtrace;


import leetcode.node.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * @date 2020/7/8
 */
public class Base {

    /**
     * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树
     *
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }
        return generateTrees(1, n);
    }

    private List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> res = new LinkedList<>();
        if (start > end) {
            res.add(null);
            return res;
        }
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftChild = generateTrees(start, i - 1);
            List<TreeNode> rightChild = generateTrees(i + 1, end);
            for (TreeNode leftNode : leftChild) {
                for (TreeNode rightNode : rightChild) {
                    TreeNode newNode = new TreeNode(i);
                    newNode.left = leftNode;
                    newNode.right = rightNode;
                    res.add(newNode);
                    System.out.println("adding");
                }
            }
        }
        return res;
    }
}
