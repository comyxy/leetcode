package tree;

import org.junit.jupiter.api.Test;
import struct.TreeNode;

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
}