package tree;

import struct.ListNode;
import struct.NextNode;
import struct.TreeNode;

import java.util.*;

/**
 * 二叉树
 * <p>
 * 2020/7/7
 */
public class Binary {

    /**
     * 寻找二叉树的最大深度（自底向上递归）
     *
     * @param root 根节点
     * @return 最大深度
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 对称二叉树（自顶向下递归）
     *
     * @param root 根节点
     * @return 是否为对称二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetricHelper(root, root);
    }

    private boolean isSymmetricHelper(TreeNode le, TreeNode ri) {
        if (le == null && ri == null) {
            return true;
        }
        if (le == null || ri == null) {
            return false;
        }
        return le.val == ri.val
                && isSymmetricHelper(le.left, ri.right)
                && isSymmetricHelper(le.right, ri.left);
    }

    /**
     * 路径和
     *
     * @param root 根节点
     * @param sum  待查找的路径和
     * @return 是否存在
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        sum -= root.val;
        if (root.left == null && root.right == null) {
            return sum == 0;
        }
        return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
    }

    /**
     * 根据中序和后序遍历得到二叉树
     *
     * @param inorder   中序遍历数组
     * @param postorder 后续遍历数组
     * @return 二叉树的根节点
     */
    public TreeNode buildTreeByInAndPost(int[] inorder, int[] postorder) {
        assert inorder.length == postorder.length;

        int len = inorder.length;
        if (len == 0) {
            return null;
        }
        if (len == 1) {
            return new TreeNode(inorder[0]);
        }
        /*---递归索引方式---*/
        return buildTreeByInAndPostHelper(inorder, postorder, 0, len - 1, 0, len - 1);
    }

    @SuppressWarnings("DuplicatedCode")
    private TreeNode buildTreeByInAndPostHelper(
            int[] inorder,
            int[] postorder,
            int inorderLeft,
            int inorderRight,
            int postorderLeft,
            int postorderRight) {
        if (inorderLeft > inorderRight || postorderLeft > postorderRight) {
            return null;
        }
        // 后序遍历的最后一个节点是根节点
        int rootVal = postorder[postorderRight];
        // 找到中序遍历的根节点的位置
        int dividePoint = 0;
        for (int i = inorderLeft; i <= inorderRight; i++) {
            if (inorder[i] == rootVal) {
                dividePoint = i;
                break;
            }
        }
        // 创建根节点
        TreeNode root = new TreeNode(rootVal);
        // 左边递归
        root.left =
                buildTreeByInAndPostHelper(
                        inorder,
                        postorder,
                        inorderLeft,
                        dividePoint - 1,
                        postorderLeft,
                        dividePoint - 1 - inorderLeft + postorderLeft);
        // 右边递归
        root.right =
                buildTreeByInAndPostHelper(
                        inorder,
                        postorder,
                        dividePoint + 1,
                        inorderRight,
                        postorderRight + dividePoint - inorderRight,
                        postorderRight - 1);
        return root;
    }

    /**
     * 先序遍历和中序遍历得到二叉树
     *
     * @param preorder 先序遍历
     * @param inorder  中序遍历
     * @return 二叉树根节点
     */
    public TreeNode buildTreeByPreAndIn(int[] preorder, int[] inorder) {
        assert preorder.length == inorder.length;
        int len = inorder.length;
        if (len == 0) {
            return null;
        }
        if (len == 1) {
            return new TreeNode(inorder[0]);
        }

        return buildTreeHelperByPreAndIn(preorder, inorder, 0, len - 1, 0, len - 1);
    }

    @SuppressWarnings("DuplicatedCode")
    private TreeNode buildTreeHelperByPreAndIn(
            int[] preorder,
            int[] inorder,
            int preorderLeft,
            int preorderRight,
            int inorderLeft,
            int inorderRight) {
        if (preorderLeft > preorderRight || inorderLeft > inorderRight) {
            return null;
        }
        // 前序遍历的第一个节点是根节点
        int rootVal = preorder[preorderLeft];
        int dividePoint = 0;
        for (int i = inorderLeft; i <= inorderRight; i++) {
            if (inorder[i] == rootVal) {
                dividePoint = i;
                break;
            }
        }
        TreeNode root = new TreeNode(rootVal);
        root.left =
                buildTreeHelperByPreAndIn(
                        preorder,
                        inorder,
                        preorderLeft + 1,
                        preorderLeft + dividePoint - inorderLeft,
                        inorderLeft,
                        dividePoint - 1);
        root.right =
                buildTreeHelperByPreAndIn(
                        preorder,
                        inorder,
                        preorderRight - inorderRight + dividePoint + 1,
                        preorderRight,
                        dividePoint + 1,
                        inorderRight);
        return root;
    }

    /**
     * 填充每个节点的下一个右侧节点指针（完美二叉树条件下）
     *
     * @param root 根节点
     * @return 修改后的根节点
     */
    public NextNode connectPerfectBinaryTree(NextNode root) {
        /*---递归方法（自顶向下递归）---*/
        if (root != null && root.left != null) {
            // 连接同一父节点下的左右节点
            root.left.next = root.right;
            // 连接相邻父节点下的右左节点
            if (root.next != null) {
                root.right.next = root.next.left;
            }
            connectPerfectBinaryTree(root.left);
            connectPerfectBinaryTree(root.right);
        }
        return root;
    }

    public NextNode connectPerfectBinaryTreeV2(NextNode root) {
        /*---非递归方法---*/
        if (root == null) {
            return null;
        }
        Queue<NextNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            NextNode pre = null;
            for (int i = 0; i < size; i++) {
                NextNode cur = queue.poll();
                if (i > 0) {
                    pre.next = cur;
                }
                pre = cur;
                assert cur != null;
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
        }
        return root;
    }

    /**
     * 填充每个节点的下一个右侧节点指针（非完美二叉树）
     *
     * @param root 根节点
     * @return 修改后的根节点
     */
    public NextNode connect(NextNode root) {
        NextNode cur = root;
        while (cur != null) {
            NextNode dummy = new NextNode();
            NextNode tail = dummy;
            while (cur != null) { // 遍历上一层节点
                if (cur.left != null) {
                    tail.next = cur.left;
                    tail = tail.next;
                }
                if (cur.right != null) {
                    tail.next = cur.right;
                    tail = tail.next;
                }
                cur = cur.next;
            }
            cur = dummy.next; // 移动到下一层
        }
        return root;
    }

    /**
     * 二叉树的最近公共祖先（二叉树中节点值不同，q和p一定在树中存在）
     *
     * @param root 根节点
     * @param p    节点p
     * @param q    节点q
     * @return 公共祖先节点
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        lowestCommonAncestorHelper(root, p, q);
        return res;
    }

    private TreeNode res = null;

    private boolean lowestCommonAncestorHelper(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return false;
        }
        // 左子树是否有p或q
        int left = lowestCommonAncestorHelper(node.left, p, q) ? 1 : 0;
        // 右子树是否有p或q
        int right = lowestCommonAncestorHelper(node.right, p, q) ? 1 : 0;
        // 该节点是否为p或者q
        int mid = (node == p || node == q) ? 1 : 0;
        if (left + right + mid >= 2) {
            res = node;
        }
        return left + right + mid > 0;
    }

    /**
     * 时间超时 树的序列化（前序遍历递归方式）
     *
     * @param root 根节点
     * @return String字符串
     */
    public String serialize(TreeNode root) {
        return serialize(root, "");
    }

    private String serialize(TreeNode node, String str) {
        if (node == null) {
            str += "null,";
        } else {
            str += node.val + ",";
            str = serialize(node.left, str);
            str = serialize(node.right, str);
        }
        return str;
    }

    /**
     * 反序列化（前序遍历递归）
     *
     * @param data 字符串
     * @return 树的根节点
     */
    public TreeNode deserialize(String data) {
        String[] dataArray = data.split(",");
        List<String> list = new LinkedList<>(Arrays.asList(dataArray));
        return deserialize(list);
    }

    private TreeNode deserialize(List<String> list) {
        if ("null".equals(list.get(0))) {
            list.remove(0);
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(list.get(0)));
        list.remove(0);
        node.left = deserialize(list);
        node.right = deserialize(list);
        return node;
    }

    /**
     * 110 给定一个二叉树，判断它是否是高度平衡的二叉树
     */
    public boolean isBalanced(TreeNode root) {
        isBalancedHelper(root);
        return balanced;
    }

    private boolean balanced = true;

    private int isBalancedHelper(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = isBalancedHelper(node.left);
        int rightHeight = isBalancedHelper(node.right);
        if (leftHeight - rightHeight > 1 || rightHeight - leftHeight > 1) {
            balanced = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 107 给定一个二叉树，返回其节点值自底向上的层次遍历
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        while (!queue.isEmpty()) {
            int n = queue.size();
            List<Integer> t = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode poll = queue.poll();
                assert poll != null;
                t.add(poll.val);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
            res.add(t);
        }
        Collections.reverse(res);
        return res;
    }

    /**
     * 114 将一棵树展开为链表
     *
     * @param root 根节点
     */
    public void flatten(TreeNode root) {
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode next = cur.left;
                // 找cur的前驱节点
                TreeNode successor = next;
                while (successor.right != null) {
                    successor = successor.right;
                }
                successor.right = cur.right;
                cur.right = next;
                cur.left = null;
            }
            cur = cur.right;
        }
    }

    /**
     * Code99 恢复二叉搜索树
     *
     * @param root 根节点
     */
    public void recoverTree(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode x = null, y = null, prev = null, cur = root;

        while (!stack.isEmpty() || cur != null) {
            // 先找左边的节点
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            // 更新cur目前节点
            cur = stack.pop();
            // 确定x y
            if (prev != null && cur.val < prev.val) {
                y = cur;
                if (x == null) {
                    x = prev;
                } else {
                    break;
                }
            }
            // 更新prev上一个中序遍历节点
            prev = cur;
            // 当前节点的右子树
            cur = cur.right;
        }

        assert x != null;
        int tmp = x.val;
        x.val = y.val;
        y.val = tmp;
    }

    /**
     * Code99 恢复二叉搜索树 Morris 遍历方法
     *
     * @param root 根节点
     */
    public void recoverTree2(TreeNode root) {
        TreeNode x = null, y = null, prev = null, cur = root, predecessor;

        while (cur != null) {
            if (cur.left != null) {
                // 左子树不为空 先找predecessor
                predecessor = cur.left;
                while (predecessor.right != null && predecessor.right != cur) {
                    // 往右子树找 同时 不找连接后predecessor
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    predecessor.right = cur;
                    cur = cur.left;
                } else {
                    // cur节点的左子树已经遍历完成
                    if (prev != null && cur.val < prev.val) {
                        y = cur;
                        if (x == null) {
                            x = prev;
                        }
                    }
                    prev = cur;
                    // 断开predecessor建立的连接 同时达到cur的右子树
                    predecessor.right = null;
                    cur = cur.right;
                }
            } else {
                //////////////////////////
                if (prev != null && cur.val < prev.val) {
                    y = cur;
                    if (x == null) {
                        x = prev;
                    }
                }
                prev = cur;
                //////////////////////////
                cur = cur.right;
            }
        }
        assert x != null;
        int tmp = x.val;
        x.val = y.val;
        y.val = tmp;
    }

    /**
     * LeetCode109 有序链表转换二叉搜索树
     *
     * @param head 链表头节点
     * @return 树的头节点
     */
    public TreeNode sortedListToBST(ListNode head) {
        // 左闭右闭[left, right]
        this.gHead = head;
        int length = getLength(head);
        return sortedListToBSTHelper(0, length - 1);
    }

    private TreeNode sortedListToBSTHelper(int left, int right) {
        if (left > right) {
            return null;
        }
        int mid = (left + right + 1) / 2;
        // 先占位
        TreeNode root = new TreeNode();
        root.left = sortedListToBSTHelper(left, mid - 1);
        // 中序遍历
        root.val = this.gHead.val;
        this.gHead = gHead.next;
        root.right = sortedListToBSTHelper(mid + 1, right);
        return root;
    }

    private ListNode gHead;

    private int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

    /**
     * 897. 递增顺序搜索树
     */
    public TreeNode increasingBST(TreeNode root) {
        TreeNode dummy = new TreeNode(0);
        dummy.right = root;
        TreeNode pre = dummy;
        Stack<TreeNode> stack = new Stack<>();

        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                TreeNode cur = stack.pop();
                root = cur.right;
                cur.left = null;
                pre.right = cur;
                pre = pre.right;
            }
        }
        return dummy.right;
    }

    /**
     * 938
     */
    public int rangeSumBST(TreeNode root, int low, int high) {
        if(root == null) {
            return 0;
        }
        if(root.val > high) {
            return rangeSumBST(root.left, low, high);
        }else if(root.val < low) {
            return rangeSumBST(root.right, low, high);
        }
        return rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high) + root.val;
    }

    /**
     * 先序遍历 迭代
     */
    public List<Integer> preOrder(TreeNode node) {
        List<Integer> res = new ArrayList<>();
        if(node == null) {
            return res;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        while(node != null || !stack.isEmpty()) {
            if(node != null) {
                stack.push(node);
                res.add(node.val);
                node = node.left;
            }else {
                node = stack.pop();
                node = node.right;
            }
        }
        return res;
    }

    /**
     * 中序遍历 迭代
     */
    public List<Integer> inOrder(TreeNode node) {
        List<Integer> res = new ArrayList<>();
        if(node == null) {
            return res;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        while(node != null || !stack.isEmpty()) {
            if(node != null) {
                stack.push(node);
                node = node.left;
            }else {
                node = stack.pop();
                res.add(node.val);
                node = node.right;
            }
        }
        return res;
    }

    /**
     * 后续遍历 迭代
     */
    public List<Integer> postOrder(TreeNode node) {
        List<Integer> res = new ArrayList<>();
        if(node == null) {
            return res;
        }
        TreeNode pre = null;
        Deque<TreeNode> stack = new LinkedList<>();
        while(node != null || !stack.isEmpty()) {
            while(node != null) {
                stack.addLast(node);
                node = node.left;
            }
            node = stack.peekLast();
            if(node.right == null || node.right == pre) {
                stack.removeLast();
                res.add(node.val);
                pre = node;
                node = null;
            }else {
                node = node.right;
            }
        }
        return res;
    }
}
