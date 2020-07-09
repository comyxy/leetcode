package leetcode.tree;

import leetcode.node.NextNode;
import leetcode.node.TreeNode;

import java.util.*;

/**
 * 二叉树
 * @date 2020/7/7
 */
public class Binary {

    /**
     * 寻找二叉树的最大深度（自底向上递归）
     * @param root 根节点
     * @return 最大深度
     */
    public int maxDepth(TreeNode root){
        if(root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 对称二叉树（自顶向下递归）
     * @param root 根节点
     * @return 是否为对称二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetricHelper(root, root);
    }
    private boolean isSymmetricHelper(TreeNode le, TreeNode ri){
        if(le == null && ri == null){
            return true;
        }
        if(le == null || ri == null){
            return false;
        }
        return le.val == ri.val
                && isSymmetricHelper(le.left, ri.right) && isSymmetricHelper(le.right, ri.left);
    }

    /**
     * 路径和
     * @param root 根节点
     * @param sum 待查找的路径和
     * @return 是否存在
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null){
            return false;
        }
        sum -= root.val;
        if(root.left == null && root.right == null){
            return sum == 0;
        }
        return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
    }

    /**
     * 根据中序和后序遍历得到二叉树
     * @param inorder 中序遍历数组
     * @param postorder 后续遍历数组
     * @return 二叉树的根节点
     */
    public TreeNode buildTreeByInAndPost(int[] inorder, int[] postorder) {
        assert inorder.length == postorder.length;

        int len = inorder.length;
        if(len == 0) {
            return null;
        }
        if(len == 1) {
            return new TreeNode(inorder[0]);
        }
        /*---递归复制数组方式---*/
//        int rootVal = postorder[len - 1];
//        int dividePoint = 0;
//        for(int i=0;i<len;i++){
//            if(inorder[i] == rootVal){
//                dividePoint = i;
//                break;
//            }
//        }
//        TreeNode rootNode = new TreeNode(rootVal);
//        rootNode.left = buildTreeByInAndPost(Arrays.copyOfRange(inorder, 0, dividePoint),
//                Arrays.copyOfRange(postorder, 0, dividePoint));
//        rootNode.right = buildTreeByInAndPost(Arrays.copyOfRange(inorder, dividePoint+1, len),
//                Arrays.copyOfRange(postorder, dividePoint, len -1));
//        return rootNode;

        /*---递归索引方式---*/
        return buildTreeByInAndPostHelper(inorder, postorder, 0, len - 1,
                0, len - 1);
    }
    @SuppressWarnings("DuplicatedCode")
    private TreeNode buildTreeByInAndPostHelper(int[] inorder, int[] postorder, int inorderLeft, int inorderRight,
                                                int postorderLeft, int postorderRight){
        if(inorderLeft > inorderRight || postorderLeft > postorderRight) {
            return null;
        }
        int rootVal = postorder[postorderRight];
        int dividePoint = 0;
        for(int i = inorderLeft;i <= inorderRight;i++){
            if(inorder[i] == rootVal){
                dividePoint = i;
                break;
            }
        }
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTreeByInAndPostHelper(inorder, postorder, inorderLeft, dividePoint - 1,
                postorderLeft, dividePoint - 1 - inorderLeft + postorderLeft);
        root.right = buildTreeByInAndPostHelper(inorder, postorder, dividePoint + 1, inorderRight,
                postorderRight + dividePoint - inorderRight, postorderRight - 1);
        return root;
    }

    /**
     * 先序遍历和中序遍历得到二叉树
     * @param preorder  先序遍历
     * @param inorder 中序遍历
     * @return 二叉树根节点
     */
    public TreeNode buildTreeByPreAndIn(int[] preorder, int[] inorder) {
        assert preorder.length == inorder.length;
        int len = inorder.length;
        if(len == 0) {
            return null;
        }
        if(len == 1) {
            return new TreeNode(inorder[0]);
        }

        return buildTreeHelperByPreAndIn(preorder, inorder, 0, len - 1, 0, len -1);
    }
    @SuppressWarnings("DuplicatedCode")
    private TreeNode buildTreeHelperByPreAndIn(int[] preorder, int[] inorder, int preorderLeft, int preorderRight,
                                               int inorderLeft, int inorderRight){
        if(preorderLeft > preorderRight || inorderLeft > inorderRight) {
            return null;
        }
        int rootVal = preorder[preorderLeft];
        int dividePoint = 0;
        for(int i = inorderLeft;i <= inorderRight;i++){
            if(inorder[i] == rootVal){
                dividePoint = i;
                break;
            }
        }
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTreeHelperByPreAndIn(preorder, inorder,
                preorderLeft + 1, preorderLeft + dividePoint - inorderLeft,
                inorderLeft, dividePoint - 1);
        root.right = buildTreeHelperByPreAndIn(preorder, inorder,
                preorderRight - inorderRight + dividePoint + 1, preorderRight,
                dividePoint + 1, inorderRight);
        return root;
    }

    /**
     * 填充每个节点的下一个右侧节点指针（完美二叉树）
     * @param root 根节点
     * @return 修改后的根节点
     */
    public NextNode connectPerfectBinaryTree(NextNode root) {
        /*---非递归方法---*/
//        if(root == null) return null;
//        Queue<Node> queue = new LinkedList<>();
//        queue.offer(root);
//        while(!queue.isEmpty()){
//            int size = queue.size();
//            Node pre = null;
//            for(int i=0;i<size;i++) {
//                Node cur = queue.poll();
//                if(i > 0){
//                    pre.next = cur;
//                }
//                pre = cur;
//                assert cur != null;
//                if(cur.left != null)  queue.offer(cur.left);
//                if(cur.right != null) queue.offer(cur.right);
//            }
//        }
        /*---递归方法（自顶向下递归）---*/
        if(root != null && root.left != null){
            root.left.next = root.right;
            if(root.next != null){
                root.right.next = root.next.left;
            }
            connectPerfectBinaryTree(root.left);
            connectPerfectBinaryTree(root.right);
        }
        return root;
    }

    /**
     * 填充每个节点的下一个右侧节点指针（非完美二叉树）
     * @param root 根节点
     * @return 修改后的根节点
     */
    public NextNode connect(NextNode root) {
        /*---非递归方法---*/
//        if(root == null) return null;
//        Queue<Node> queue = new LinkedList<>();
//        queue.offer(root);
//        while(!queue.isEmpty()){
//            int size = queue.size();
//            Node pre = null;
//            for(int i=0;i<size;i++) {
//                Node cur = queue.poll();
//                if(i > 0){
//                    pre.next = cur;
//                }
//                pre = cur;
//                assert cur != null;
//                if(cur.left != null)  queue.offer(cur.left);
//                if(cur.right != null) queue.offer(cur.right);
//            }
//        }
        /*---额外空间优化（cur指针上一层与tail指针当前层）---*/
        NextNode cur = root;
        while(cur != null){
            NextNode dummy = new NextNode();
            NextNode tail = dummy;
            while(cur != null){//遍历上一层节点
                if(cur.left != null){
                    tail.next = cur.left;
                    tail = tail.next;
                }
                if(cur.right != null){
                    tail.next = cur.right;
                    tail = tail.next;
                }
                cur = cur.next;
            }
            cur = dummy.next;//移动到下一层
        }
        return root;
    }

    /**
     * 二叉树的最近公共祖先（二叉树中节点值不同，q和p一定在树中存在）
     * @param root 根节点
     * @param p 节点p
     * @param q 节点q
     * @return 公共祖先节点
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        lowestCommonAncestorHelper(root, p, q);
        return res;
    }
    private TreeNode res = null;
    private boolean lowestCommonAncestorHelper(TreeNode node, TreeNode p, TreeNode q){
        if(node == null) {
            return false;
        }
        int left = lowestCommonAncestorHelper(node.left, p, q) ? 1 : 0;
        int right = lowestCommonAncestorHelper(node.right, p, q) ? 1 : 0;
        int mid = (node == p || node == q) ? 1 : 0;
        if(left + right + mid >= 2){
            res = node;
        }
        return left + right + mid > 0;
    }

    /** 时间超时
     * 树的序列化（前序遍历递归方式）
     * @param root 根节点
     * @return String字符串
     */
    public String serialize(TreeNode root) {
        return serialize(root, "");
    }
    private String serialize(TreeNode node, String str){
        if(node == null) {
            str += "null,";
        } else{
            str += String.valueOf(node.val) + ",";
            str = serialize(node.left, str);
            str = serialize(node.right, str);
        }
        return str;
    }

    /**
     * 反序列化（前序遍历递归）
     * @param data 字符串
     * @return 树的根节点
     */
    public TreeNode deserialize(String data) {
        String[] dataArray = data.split(",");
        List<String> list = new LinkedList<>(Arrays.asList(dataArray));
        return deserialize(list);
    }
    private TreeNode deserialize(List<String> list){
        if("null".equals(list.get(0))){
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

}
