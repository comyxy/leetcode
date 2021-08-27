package backtrace;

import org.junit.jupiter.api.Test;
import struct.TreeNode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since 2021/7/22 22:53
 */
class RecursionTest {

    Recursion recursion = new Recursion();

    public static void printTree(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + " ");
        printTree(node.left);
        printTree(node.right);
    }

    @Test
    void testCloneTree() {
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
        printTree(clone);
    }

    @Test
    void testSubsets() {
        List<List<Integer>> result = recursion.subsets(new int[]{1, 2, 3});
        System.out.println(result);
        assertEquals(16, result.size());
    }

    @Test
    void generateParenthesis() {
        List<String> result = recursion.generateParenthesis(3);
        System.out.println(result);
        assertEquals(5, result.size());
    }

    @Test
    void allPathsSourceTarget() {
        int[][] graph = {{4, 3, 1}, {3, 2, 4}, {3}, {4}, {}};
        List<List<Integer>> result = recursion.allPathsSourceTarget(graph);
        System.out.println(result);
        assertEquals(5, result.size());
    }
}