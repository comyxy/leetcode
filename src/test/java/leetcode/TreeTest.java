package leetcode;

import struct.ListNode;
import struct.NextNode;
import struct.TreeNode;
import tree.Binary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** 2020/7/7 */
class TreeTest {

  static Binary binary;

  static TreeNode root;

  static TreeNode symmetricRoot;

  @BeforeAll
  static void init() {
    binary = new Binary();

    root = new TreeNode(3);
    TreeNode node2 = new TreeNode(9);
    TreeNode node3 = new TreeNode(20);
    TreeNode node4 = new TreeNode(15);
    TreeNode node5 = new TreeNode(7);
    root.left = node2;
    root.right = node3;
    node3.left = node4;
    node3.right = node5;

    symmetricRoot = new TreeNode(10);
    TreeNode node6 = new TreeNode(3);
    TreeNode node7 = new TreeNode(3);
    symmetricRoot.left = node6;
    symmetricRoot.right = node7;
  }

  @Test
  void testMaxDepth() {
    assertEquals(3, binary.maxDepth(root));
  }

  @Test
  void testIsSymmetric() {
    assertFalse(binary.isSymmetric(root));
    assertTrue(binary.isSymmetric(symmetricRoot));
  }

  @Test
  void testHasPathSum() {
    assertTrue(binary.hasPathSum(root, 12));
  }

  @Test
  void testIsBalanced() {
    assertTrue(binary.isBalanced(root));
  }

  @Test
  void testBuildTree() {
    int[] inorder = {9, 3, 15, 20, 7};
    int[] postorder = {9, 15, 7, 20, 3};
    int[] preorder = {3, 9, 20, 15, 7};
    TreeNode rootByInAndPost = binary.buildTreeByInAndPost(inorder, postorder);
    assertEquals(9, rootByInAndPost.left.val);
    TreeNode rootByPreAndIn = binary.buildTreeByPreAndIn(preorder, inorder);
    assertEquals(9, rootByPreAndIn.left.val);
  }

  @Test
  void testNextNodeFromPerfect() {
    NextNode root = new NextNode(3);
    NextNode n1 = new NextNode(9);
    NextNode n2 = new NextNode(20);
    NextNode n3 = new NextNode(15);
    NextNode n4 = new NextNode(7);
    NextNode n5 = new NextNode(11);
    NextNode n6 = new NextNode(12);
    root.left = n1;
    root.right = n2;
    n1.left = n5;
    n1.right = n6;
    n2.left = n3;
    n2.right = n4;

    NextNode rootNextNode = binary.connectPerfectBinaryTree(root);
    assertEquals(7, rootNextNode.left.left.next.next.next.val);
  }

  @Test
  void testNextNode() {
    NextNode root = new NextNode(3);
    NextNode n1 = new NextNode(9);
    NextNode n2 = new NextNode(20);
    NextNode n3 = new NextNode(15);
    NextNode n4 = new NextNode(7);
    NextNode n5 = new NextNode(23);
    root.left = n1;
    root.right = n2;
    n2.left = n3;
    n2.right = n4;
    n1.left = n5;

    NextNode rootNextNode = binary.connect(root);
    assertEquals(7, rootNextNode.left.left.next.next.val);
  }

  @Test
  void testSerialize() {
    String serialize = binary.serialize(root);
    System.out.println(serialize);
    TreeNode deserialize = binary.deserialize(serialize);
    assertEquals(7, deserialize.right.right.val);
    assertNotEquals(root, deserialize);
  }

  @Test
  void testLowestCommonAncestor() {
    TreeNode p = root.left;
    TreeNode q = root.right.right;
    assertEquals(root, binary.lowestCommonAncestor(root, p, q));
  }

  @Test
  void testLevelOrderBottom() {
    List<List<Integer>> result = binary.levelOrderBottom(root);
    System.out.println(result);
    assertEquals(15, result.get(0).get(0));
  }

  @Test
  void testFlatten() {
    // 原地展开 避免破坏 单独一个树
    TreeNode node1 = new TreeNode(1);
    TreeNode node2 = new TreeNode(2);
    TreeNode node3 = new TreeNode(3);
    TreeNode node4 = new TreeNode(4);
    TreeNode node5 = new TreeNode(5);
    TreeNode node6 = new TreeNode(6);
    node1.left = node2;
    node1.right = node5;
    node2.left = node3;
    node2.right = node4;
    node5.right = node6;

    binary.flatten(node1);
    while (node1 != null) {
      System.out.print(node1.val + "-->");
      node1 = node1.right;
    }
  }

  @Test
  void testRecoverTree() {
    TreeNode node1 = new TreeNode(1);
    TreeNode node2 = new TreeNode(2);
    TreeNode node3 = new TreeNode(3);
    TreeNode node4 = new TreeNode(4);
    TreeNode node5 = new TreeNode(5);
    TreeNode node6 = new TreeNode(6);

    node4.left = node2;
    node4.right = node1;
    node2.left = node5;
    node2.right = node3;
    node1.right = node6;

    binary.recoverTree(node4);
    assertEquals(5, node4.right.val);
  }

  @Test
  void testRecoverTree2() {
    TreeNode node1 = new TreeNode(1);
    TreeNode node2 = new TreeNode(2);
    TreeNode node3 = new TreeNode(3);
    TreeNode node4 = new TreeNode(4);
    TreeNode node5 = new TreeNode(5);
    TreeNode node6 = new TreeNode(6);

    node4.left = node2;
    node4.right = node1;
    node2.left = node5;
    node2.right = node3;
    node1.right = node6;

    binary.recoverTree2(node4);
    assertEquals(5, node4.right.val);
  }

  @Test
  void testSortedListToBST() {
    ListNode l1 = new ListNode(1);
    ListNode l2 = new ListNode(2);
    ListNode l3 = new ListNode(3);
    ListNode l4 = new ListNode(4);
    ListNode l5 = new ListNode(5);
    l1.next = l2;
    l2.next = l3;
    l3.next = l4;
    l4.next = l5;

    TreeNode listToBST = binary.sortedListToBST(l1);

    TreeNode listToBST2 = binary.sortedListToBST2(l1);

    assertEquals(listToBST.val, listToBST2.val);
  }
}
