package backtrace;

import org.junit.jupiter.api.Test;
import struct.TreeNode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/7/22 22:53
 */
class RecursionTest {

    private final Recursion recursion = new Recursion();

    @Test
    void testClone() {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        TreeNode clone = recursion.cloneTree(n1);
        assertNotEquals(clone, n1);
        Recursion.printTree(clone);
    }
}