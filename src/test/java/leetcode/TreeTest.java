package leetcode;

import leetcode.node.TreeNode;
import leetcode.tree.Binary;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @date 2020/7/7
 */
class TreeTest {

    @Test
    void testIsBalanced() {
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
        assertTrue(binary.isBalanced(node1));
    }

    @Test
    void testLevelOrderBottom() {
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

    @Test
    void testFlatten() {
        Binary binary = new Binary();
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(15);
        TreeNode node5 = new TreeNode(21);
        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;
        binary.flatten(node1);
        System.out.println(node1);
    }
}