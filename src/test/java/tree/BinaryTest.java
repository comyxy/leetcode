package tree;

import org.junit.jupiter.api.Test;
import struct.TreeNode;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTest {
    Binary binary = new Binary();

    @Test
    public void testRangeSumBST() {
        TreeNode n3 = new TreeNode(3);
        TreeNode n7 = new TreeNode(7);
        TreeNode n5 = new TreeNode(5, n3, n7);
        TreeNode n18 = new TreeNode(18);
        TreeNode n15 = new TreeNode(15, null, n18);
        TreeNode n10 = new TreeNode(10, n5, n15);

        assertEquals(32, binary.rangeSumBST(n10, 7, 15));
    }

    @Test
    void testPreAndInAndPostOrder() {
        TreeNode n3 = new TreeNode(3);
        TreeNode n7 = new TreeNode(7);
        TreeNode n5 = new TreeNode(5, n3, n7);
        TreeNode n18 = new TreeNode(18);
        TreeNode n15 = new TreeNode(15, null, n18);
        TreeNode n10 = new TreeNode(10, n5, n15);

        List<Integer> pre = binary.preOrder(n10);
        System.out.println(pre);

        List<Integer> in = binary.inOrder(n10);
        System.out.println(in);

        List<Integer> post = binary.postOrder(n10);
        System.out.println(post);
    }

    @Test
    void pathInZigZagTree() {
        List<Integer> expected = Arrays.asList(1, 3, 4, 14);
        List<Integer> actual = binary.pathInZigZagTree(14);
        System.out.println(actual);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}