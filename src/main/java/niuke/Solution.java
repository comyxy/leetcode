package niuke;

import java.util.*;

/**
 * @author comyxy
 */
public class Solution {
    /**
     * 在一个二维数组中（每个一维数组的长度相同），
     * 每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     *
     * @param target 目标整数
     * @param array  二维数组
     * @return 是否找到
     */
    public boolean find(int target, int[][] array) {
        final int nRow = array.length;
        if (nRow == 0) {
            return false;
        }
        final int nCol = array[0].length;
        int row = 0, col = nCol - 1;
        while (row < nRow && col >= 0) {
            int res = array[row][col];
            if (res == target) {
                return true;
            }
            if (res > target) {
                col -= 1;
            }
            if (res < target) {
                row += 1;
            }
        }
        return false;
    }

    /**
     * 替换空格
     *
     * @param str 字符串
     * @return 修改后字符串
     */
    public String replaceSpace(StringBuffer str) {
        return str.toString().replace(" ", "%20");
    }

    /**
     * 从链表反序得到列表
     *
     * @param listNode 链表根节点
     * @return 列表
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        while (!stack.empty()) {
            list.add(stack.pop());
        }
        return list;
    }

    /**
     * 中序和前序构建二叉树
     *
     * @param pre 前序
     * @param in  中序
     * @return 二叉树根节点
     */
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        assert pre.length == in.length;
        int len = in.length;
        if (len == 0) {
            return null;
        }
        if (len == 1) {
            return new TreeNode(in[0]);
        }
        return reConstructBinaryTree(pre, in, 0, len - 1, 0, len - 1);
    }

    /**
     * @param pre      前序
     * @param in       中序
     * @param preLeft  前序左边界
     * @param preRight 前序右边界
     * @param inLeft   中序左边界
     * @param inRight  中序右边界
     * @return 递归
     */
    private TreeNode reConstructBinaryTree(int[] pre, int[] in,
                                           int preLeft, int preRight,
                                           int inLeft, int inRight) {
        if (preLeft > preRight || inLeft > inRight) {
            return null;
        }
        int rootVal = pre[preLeft];
        int dividePoint = 0;
        for (int i = inLeft; i <= inRight; i++) {
            if (in[i] == rootVal) {
                dividePoint = i;
                break;
            }
        }
        TreeNode root = new TreeNode(rootVal);
        root.left = reConstructBinaryTree(pre, in,
                preLeft + 1, dividePoint - inLeft + preLeft,
                inLeft, dividePoint - 1);
        root.right = reConstructBinaryTree(pre, in,
                preRight - inRight + dividePoint + 1, preRight,
                dividePoint + 1, inRight);
        return root;
    }

    /*---两个栈实现一个队列---*/
//    /**
//     * 主栈
//     */
//    private Stack<Integer> stack1 = new Stack<Integer>();
//    /**
//     * 辅助栈
//     */
//    private Stack<Integer> stack2 = new Stack<Integer>();
//
//    /**
//     * 入栈
//     * @param node 节点
//     */
//    public void push(int node) {
//        stack1.push(node);
//    }
//
//    /**
//     * 出队（利用两个栈实现一个队列）
//     * @return
//     */
//    public int pop() {
//        while(!stack1.empty()){
//            stack2.push(stack1.pop());
//        }
//        int res = stack2.pop();
//        while(!stack2.empty()){
//            stack1.push(stack2.pop());
//        }
//        return res;
//    }

    /**
     * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * 二分查找（与最右边点比较）
     *
     * @param array 输入数组
     * @return 数组中最小值
     */
    public int minNumberInRotateArray(int[] array) {
        int len = array.length;
        if (len == 0) {
            return 0;
        }
        int left = 0, right = len - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] > array[len - 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return array[left];
    }

    /**
     * 动态规划
     *
     * @param n 斐波那契数列第n项
     * @return 第n项的值
     */
    public int fibonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int a = 1, b = 1, temp;
        for (int i = 3; i <= n; i++) {
            temp = b;
            b = a + b;
            a = temp;
        }
        return b;
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。
     * 求该青蛙跳上一个n级的台阶总共有多少种跳法
     *
     * @param target 台阶数
     * @return 跳法
     */
    public int jumpFloor(int target) {
        if (target == 1) {
            return 1;
        }
        if (target == 2) {
            return 2;
        }
        return jumpFloor(target - 1) + jumpFloor(target - 2);
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。
     * 求该青蛙跳上一个n级的台阶总共有多少种跳法。
     *
     * @param target 台阶数
     * @return 跳法
     */
    public int jumpFloorII(int target) {
        if (target <= 2) {
            return target;
        } else {
            return jumpFloorII(target - 1) * 2;
        }
    }

    /**
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
     * 请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     * 递推得到规律f(n)=f(n-1)+f(n-2) (n>2)
     *
     * @param target n
     * @return 方法数
     */
    public int rectCover(int target) {
        if (target <= 2) {
            return target;
        }
        return rectCover(target - 1) + rectCover(target - 2);
    }

    /**
     * 输入一个整数，输出该数二进制表示中1的个数。
     * 若n为1100,则n-1为1011,n&(n-1)为1000,去除了一个1
     *
     * @param n n
     * @return n的二进制有多少个0
     */
    public int numberOf1(int n) {
        int count = 0;
        while (n != 0) {
            count += 1;
            n = n & (n - 1);
        }
        return count;
    }

    /**
     * 给定一个double类型的浮点数base和int类型的整数exponent
     *
     * @param base     基数
     * @param exponent 指数
     * @return 结果
     */
    public double power(double base, int exponent) {
        if (base == 0) {
            if (exponent >= 0) {
                return 0.0;
            } else {
                throw new RuntimeException("Invalid");
            }
        } else {
            if (exponent > 0) {
                return powerHelper(base, exponent);
            } else if (exponent == 0) {
                return 1.0;
            } else {
                return 1 / powerHelper(base, -exponent);
            }
        }
    }

    /**
     * 递归计算幂指数
     *
     * @param base     基数
     * @param exponent 指数（要求大于0）
     * @return 结果
     */
    private double powerHelper(double base, int exponent) {
        assert exponent > 0;
        if (exponent == 1) {
            return base;
        }
        if ((exponent & 1) == 0) {
            double temp = powerHelper(base, exponent >> 1);
            return temp * temp;
        } else {
            double temp = powerHelper(base, (exponent - 1) >> 1);
            return temp * temp * base;
        }
    }

    /**
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
     * 所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
     *
     * @param array 数组
     */
    public void reOrderArray(int[] array) {
        int len = array.length;
        int k = 0;
        for (int i = 0; i < len; i++) {
            if ((array[i] & 1) == 1) {
                int j = i;
                //交换顺序 保证稳定性排序
                while (j > k) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                    j -= 1;
                }
                k += 1;
            }
        }
    }

    /**
     * 输入一个链表，输出该链表中倒数第k个结点。
     * 双指针法 快慢指针差k个节点
     *
     * @param head 头结点
     * @param k    k
     * @return 第k个节点
     */
    public ListNode findKthToTail(ListNode head, int k) {
        ListNode p = head, q = head;
        int i = 0;
        while (p != null) {
            if (i >= k) {
                q = q.next;
            }
            p = p.next;
            i += 1;
        }
        return i < k ? null : q;
    }

    /**
     * 输入一个链表，反转链表后，输出新链表的表头。
     * cur保存原链表的下一个节点 防止断开
     *
     * @param head 表头
     * @return 新表头
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null, curr = null;
        while (head != null) {
            curr = head.next;
            head.next = pre;
            pre = head;
            head = curr;
        }
        return pre;
    }

    /**
     * 输入两个单调递增的链表，输出两个链表合成后的链表，
     * 当然我们需要合成后的链表满足单调不减规则
     *
     * @param list1 链表1
     * @param list2 链表2
     * @return 合成后链表
     */
    public ListNode merge(ListNode list1, ListNode list2) {
        /*---非递归版本---*/
//        ListNode dummy = new ListNode(0);
//        ListNode cur = dummy;
//        while(list1 != null && list2 != null){
//            if(list1.val < list2.val){
//                cur.next = list1;
//                list1 = list1.next;
//                            }
//            else{
//                cur.next = list2;
//                list2 = list2.next;
//            }
//            cur = cur.next;
//        }
//        if(list2 != null){
//            while(list2 != null){
//                cur.next = list2;
//                list2 = list2.next;
//                cur = cur.next;
//            }
//        }
//        if(list1 != null){
//            while(list1 != null){
//                cur.next = list1;
//                list1 = list1.next;
//                cur = cur.next;
//            }
//        }
//        return dummy.next;
        /*---递归版本--*/
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.val < list2.val) {
            list1.next = merge(list1.next, list2);
            return list1;
        } else {
            list2.next = merge(list1, list2.next);
            return list2;
        }
    }

    /**
     * 输入两棵二叉树A，B，判断B是不是A的子结构。
     * 我们约定空树不是任意一个树的子结构
     *
     * @param root1 树1
     * @param root2 树2
     * @return 判断结果
     */
    public boolean hasSubtree(TreeNode root1, TreeNode root2) {
        boolean isContain = false;
        if (root1 != null && root2 != null) {
            if (root1.val == root2.val) {
                isContain = treeHasTree(root1, root2) ||
                        hasSubtree(root1.left, root2) || hasSubtree(root1.right, root2);
            }
        }
        return isContain;
    }

    private boolean treeHasTree(TreeNode p, TreeNode q) {
        if (q == null) {
            //q为空 说明包含
            return true;
        }
        if (p == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return treeHasTree(p.left, q.left) && treeHasTree(p.right, q.right);
    }

    /**
     * 操作给定的二叉树，将其变换为源二叉树的镜像。
     *
     * @param root 输入二叉树
     */
    public void mirror(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        if (root.left != null) {
            mirror(root.left);
        }
        if (root.right != null) {
            mirror(root.right);
        }
    }

    /**
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字
     *
     * @param matrix 输入矩阵
     * @return 列表输出
     */
    public ArrayList<Integer> printMatrix(int[][] matrix) {
        int nRow = matrix.length;
        if (nRow == 0) {
            return null;
        }
        int nCol = matrix[0].length;
        ArrayList<Integer> res = new ArrayList<>();
        int rowUp = 0, rowDown = nRow - 1;
        int colLeft = 0, colRight = nCol - 1;

        while (rowUp <= rowDown && colLeft <= colRight) {
            for (int j = colLeft; j <= colRight; j++) {
                res.add(matrix[rowUp][j]);
            }
            for (int i = rowUp + 1; i <= rowDown; i++) {
                res.add(matrix[i][colRight]);
            }
            if (rowUp < rowDown && colLeft < colRight) {
                for (int j = colRight - 1; j > colLeft; j--) {
                    res.add(matrix[rowDown][j]);
                }
                for (int i = rowDown; i > rowUp; i--) {
                    res.add(matrix[i][colLeft]);
                }
            }
            rowUp += 1;
            rowDown -= 1;
            colLeft += 1;
            colRight -= 1;
        }
        return res;
    }
    /*---最小栈---*/
//    private Stack<Integer> data = new Stack<>();
//    private Stack<Integer> helper = new Stack<>();
//    public void push(int node) {
//        data.push(node);
//        if(helper.empty() || helper.peek() > node){
//            helper.push(node);
//        }
//        else{
//            helper.push(helper.peek());
//        }
//    }
//    public void pop() {
//        if(!data.empty()) {
//            data.pop();
//            helper.pop();
//        }
//    }
//    public int top() {
//        if(!data.empty()){
//            return data.peek();
//        }
//        else{
//            throw new RuntimeException("null");
//        }
//    }
//    public int min() {
//        if(!helper.empty()){
//            return helper.peek();
//        }
//        else{
//            throw new RuntimeException("null");
//        }
//    }

    /**
     * 输入两个整数序列 第一个序列表示栈的压入顺序
     * 请判断第二个序列是否可能为该栈的弹出顺序
     *
     * @param pushA 栈的压入顺序
     * @param popA  栈的弹出顺序
     * @return 结果
     */
    public boolean isPopOrder(int[] pushA, int[] popA) {
        Stack<Integer> st = new Stack<>();
        int len = pushA.length;
        int i = 0;
        for (int num : pushA) {
            st.push(num);
            while (!st.empty() && i < len && st.peek() == popA[i]) {
                st.pop();
                i += 1;
            }
        }
        return i == len;
    }

    /**
     * 从上往下打印出二叉树的每个节点，同层节点从左至右打印
     *
     * @param root 根节点
     * @return list
     */
    public ArrayList<Integer> printFromTopToBottom(TreeNode root) {
        if (root == null) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode temp = queue.poll();
                assert temp != null;
                list.add(temp.val);
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }
        }
        return list;
    }

    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
     * 如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
     *
     * @param sequence 输入数组
     * @return 是否为后序遍历结果
     */
    public boolean verifySquenceOfBST(int[] sequence) {
        int len = sequence.length;
        if (len == 0) {
            return false;
        }
        return verifySquenceOfBST(sequence, 0, len - 1);
    }

    private boolean verifySquenceOfBST(int[] sequence, int left, int right) {
        if (left >= right) {
            return true;
        }
        int i = left;
        while (i < right) {
            if (sequence[i] > sequence[right]) {
                break;
            }
            i += 1;
        }
        int j = i;
        while (j < right) {
            if (sequence[j] < sequence[right]) {
                return false;
            }
            j += 1;
        }
        return verifySquenceOfBST(sequence, left, i - 1) &&
                verifySquenceOfBST(sequence, i, right - 1);
    }

    /**
     * 输入一颗二叉树的根节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
     * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
     * (注意: 在返回值的list中，数组长度大的数组靠前)
     *
     * @param root   根节点
     * @param target 目标整数
     * @return
     */
    public ArrayList<ArrayList<Integer>> findPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        findPath(root, target, new ArrayList<Integer>(), res);
        return res;
    }

    private void findPath(TreeNode root, int target, ArrayList<Integer> list, ArrayList<ArrayList<Integer>> res) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        target -= root.val;
        if (target == 0 && root.right == null && root.left == null) {
            res.add(new ArrayList<>(list));
        }
        findPath(root.left, target, list, res);
        findPath(root.right, target, list, res);
        list.remove(list.size() - 1);
    }

    /**
     * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），
     * 返回结果为复制后复杂链表的head
     *
     * @param pHead 链表头
     * @return pCloneHead复制链表头
     */
    public RandomListNode clone(RandomListNode pHead) {
        if (pHead == null) {
            return null;
        }
        //复制每个节点 插入到原来每个节点的后面
        RandomListNode currentNode = pHead;
        while (currentNode != null) {
            RandomListNode nextNode = currentNode.next;
            RandomListNode cloneNode = new RandomListNode(currentNode.label);
            currentNode.next = cloneNode;
            cloneNode.next = nextNode;
            //更新currentNode节点
            currentNode = nextNode;
        }
        //建立random连接
        currentNode = pHead;
        while (currentNode != null) {
            //如果原来的random指向null 复制的random也指向null 如果不为空 复制的random指向原来random的next
            currentNode.next.random = currentNode.random == null ? null : currentNode.random.next;
            //更新currentNode节点
            currentNode = currentNode.next.next;
        }
        //断开原来与复制的连接
        currentNode = pHead;
        RandomListNode pCloneHead = pHead.next;
        while (currentNode != null) {
            RandomListNode cloneNode = currentNode.next;
            currentNode.next = cloneNode.next;
            //复制节点指向下一个复制节点
            cloneNode.next = cloneNode.next == null ? null : cloneNode.next.next;
            currentNode = currentNode.next;
        }
        return pCloneHead;
    }

    /**
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
     * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
     * 中序遍历
     *
     * @param pRootOfTree 根节点
     * @return 头
     */
    public TreeNode convert(TreeNode pRootOfTree) {
        /*---递归写法---*/
//        if(pRootOfTree == null){
//            return null;
//        }
//        convertHelper(pRootOfTree);
//
//        TreeNode res = pRootOfTree;
//        while(res.left != null){
//            res = res.left;
//        }
//        return res;
        /*---非递归写法---*/
        if (pRootOfTree == null) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = pRootOfTree;
        TreeNode pre = null;
        boolean isFirst = true;
        while (cur != null || !stack.empty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (isFirst) {
                pRootOfTree = cur;
                pre = pRootOfTree;
                isFirst = false;
            } else {
                pre.right = cur;
                cur.left = pre;
                pre = cur;
            }
            cur = cur.right;
        }
        return pRootOfTree;
    }

    /**
     * 使用preNode记录上一个节点
     * 可以考虑把preNode做为递归的传入参数 需要传入引用 否则不能修改preNode的值
     */
    private TreeNode preNode = null;

    private void convertHelper(TreeNode currentNode) {
        if (currentNode == null) {
            return;
        }
        convertHelper(currentNode.left);

        currentNode.left = preNode;
        if (preNode != null) {
            preNode.right = currentNode;
        }
        preNode = currentNode;

        convertHelper(currentNode.right);
    }

    /**
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。
     * 例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
     *
     * @param str 输入字符串
     * @return 所有排列
     */
    public ArrayList<String> permutation(String str) {
        ArrayList<String> list = new ArrayList<>();
        if (str != null && str.length() != 0) {
            permutationHelper(str.toCharArray(), 0, list);
            Collections.sort(list);
        }
        return list;
    }

    private void permutationHelper(char[] cs, int loc, ArrayList<String> list) {
        if (loc == cs.length - 1) {
            String s = new String(cs);
            if (!list.contains(s)) {
                list.add(s);
            }
            return;
        }
        for (int j = loc; j < cs.length; j++) {
            swap(cs, loc, j);
            permutationHelper(cs, loc + 1, list);
            //回溯
            swap(cs, loc, j);
        }
    }

    private void swap(char[] cs, int i, int j) {
        char temp = cs[i];
        cs[i] = cs[j];
        cs[j] = temp;
    }

    /**
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
     *
     * @param array 输入数组
     * @return 结果
     */
    public int moreThanHalfNum(int[] array) {
        int len = array.length;
        if (len == 0) {
            return 0;
        }
        int res = array[0];
        int counter = 1;
        for (int i = 1; i < len; i++) {
            if (counter != 0) {
                if (array[i] == res) {
                    counter += 1;
                } else {
                    counter -= 1;
                }
            } else {
                res = array[i];
                counter = 1;
            }
        }
        int times = 0;
        for (int value : array) {
            if (value == res) {
                times += 1;
            }
        }
        return times > len / 2 ? res : 0;
    }

    /**
     * 输入n个整数，找出其中最小的K个数
     * 最大堆
     *
     * @param input 输入数组
     * @param k     个数k
     * @return 最小的k个数
     */
    public ArrayList<Integer> getLeastNumbers(int[] input, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        int len = input.length;
        if (k > len || k == 0) {
            return res;
        }
        //最大堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        for (Integer value : input) {
            if (maxHeap.size() < k) {
                maxHeap.offer(value);
            } else {
                try {
                    if (maxHeap.peek() > value) {
                        maxHeap.poll();
                        maxHeap.offer(value);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        res.addAll(maxHeap);
        return res;
    }

    /**
     * 给一个数组，返回它的最大连续子序列的和
     *
     * @param array 输入数组
     * @return 最大连续子序列的和
     */
    public int findGreatestSumOfSubArray(int[] array) {
        int len = array.length;
        if (len == 0) {
            return 0;
        }
        //res当前所有子数组最大的和
        int res = array[0];
        //maxSum包含array[i]的最大的和
        int maxSum = array[0];
        for (int i = 1; i < len; i++) {
            maxSum = Math.max(maxSum + array[i], array[i]);
            res = Math.max(res, maxSum);
        }
        return res;
    }

    /**
     * 求出任意非负整数区间中1出现的次数
     * 归纳法
     * k = n % (i * 10)
     * count(i) = (n / (i * 10)) * i + (if(k > i * 2 - 1) i else if(k < i) 0 else k - i + 1)
     *
     * @param n n
     * @return 1出现次数
     */
    public int numberOf1Between1AndN(int n) {
        if (n <= 0) {
            return 0;
        }
        int counter = 0;
        for (long i = 1; i <= n; i *= 10) {
            long divider = i * 10;
            long k = n % divider;
            counter += (n / divider) * i;
            if (k > 2 * i - 1) {
                counter += i;
            } else if (k >= i) {
                counter += k - i + 1;
            }
        }
        return counter;
    }

    /**
     * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     * 例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
     *
     * @param numbers 输入数组
     * @return 结果
     */
    public String printMinNumber(int[] numbers) {
        if (numbers == null) {
            return "";
        }
        int len = numbers.length;
        if (len == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String[] s = new String[len];
        for (int i = 0; i < len; i++) {
            s[i] = String.valueOf(numbers[i]);
        }
        Arrays.sort(s, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String c1 = o1 + o2;
                String c2 = o2 + o1;
                return c1.compareTo(c2);
            }
        });
        for (int i = 0; i < len; i++) {
            sb.append(s[i]);
        }
        return sb.toString();
    }

    /**
     * 把只包含质因子2、3和5的数称作丑数（Ugly Number）。
     * 例如6、8都是丑数，但14不是，因为它包含质因子7。
     * 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
     * 一个丑数一定由另一个丑数乘以2或者乘以3或者乘以5得到
     *
     * @param index N
     * @return 第N个丑数
     */
    public int getUglyNumber(int index) {
        if (index == 0) {
            return 0;
        }
        int p2 = 0, p3 = 0, p5 = 0, num = 1;
        ArrayList<Integer> list = new ArrayList<>();
        list.add(num);
        while (list.size() < index) {
            num = Math.min(list.get(p2) * 2, Math.min(list.get(p3) * 3, list.get(p5) * 5));
            if (num == list.get(p2) * 2) {
                p2 += 1;
            }
            if (num == list.get(p3) * 3) {
                p3 += 1;
            }
            if (num == list.get(p5) * 5) {
                p5 += 1;
            }
            list.add(num);
        }
        return num;
    }

    /**
     * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,
     * 并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
     *
     * @param str 字符串
     * @return 第一个只出现1次的字符
     */
    public int firstNotRepeatingChar(String str) {
        int len = str.length();
        //0-57
        int[] words = new int[58];
        for (int i = 0; i < len; i++) {
            words[(int) str.charAt(i) - 65] += 1;
        }
        for (int i = 0; i < len; i++) {
            if (words[(int) str.charAt(i) - 65] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
     * 输入一个数组,求出这个数组中的逆序对的总数P
     *
     * @param array 输入数组
     * @return P
     */
    public int inversePairs(int[] array) {
        if (array == null) {
            return 0;
        }
        int len = array.length;
        if (len == 0) {
            return 0;
        }
        mergeSort(array, 0, len - 1);
        return numberOfInversePairs;
    }

    /**
     * 归并排序
     *
     * @param a    数组
     * @param low  左边界
     * @param high 右边界
     */
    public void mergeSort(int[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = low + (high - low) / 2;
        mergeSort(a, low, mid);
        mergeSort(a, mid + 1, high);
        merge(a, low, mid, high);
    }

    private int numberOfInversePairs = 0;
    private static final int NUMBER = 1000000007;

    private void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low, j = mid + 1, k = 0;
        while (i <= mid && j <= high) {
            if (a[i] <= a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
                numberOfInversePairs += mid - i + 1;
                numberOfInversePairs = numberOfInversePairs <= NUMBER ?
                        numberOfInversePairs : numberOfInversePairs % NUMBER;
            }
        }
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        while (j <= high) {
            temp[k++] = a[j++];
        }
        System.arraycopy(temp, 0, a, low, high - low + 1);
    }

    /**
     * 输入两个链表，找出它们的第一个公共结点。
     * 长度相同有公共结点，第一次就遍历到；没有公共结点，走到尾部NULL相遇，返回NULL
     * 长度不同有公共结点，第一遍差值就出来了，第二遍一起到公共结点；没有公共，一起到结尾NULL
     *
     * @param pHead1 链表1
     * @param pHead2 链表2
     * @return 公共节点
     */
    public ListNode findFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        while (p1 != p2) {
            p1 = p1 == null ? pHead2 : p1.next;
            p2 = p2 == null ? pHead1 : p2.next;
        }
        return p1;
    }

    /**
     * 统计一个数字在排序数组中出现的次数。
     * 两次二分查找 一次左边界 一次右边界
     *
     * @param array 排序数组
     * @param k     k
     * @return k出现的次数
     */
    public int getNumberOfK(int[] array, int k) {
        if (array == null) {
            return 0;
        }
        int len = array.length;
        if (len == 0) {
            return 0;
        }
        int left = 0, right = len - 1, mid;
        while (left < right) {
            mid = left + (right - left) / 2;
            if (array[mid] >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        final int leftBorder = left;
        left = 0;
        right = len - 1;
        while (left < right) {
            mid = left + (right - left + 1) / 2;
            if (array[mid] <= k) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return array[left] == k ? left - leftBorder + 1 : 0;
    }

    /**
     * 输入一棵二叉树，求该树的深度。
     * 从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径
     * 最长路径的长度为树的深度。
     *
     * @param root 根节点
     * @return 树的深度
     */
    public int treeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = treeDepth(root.left);
        int rightDepth = treeDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 输入一棵二叉树，判断该二叉树是否是平衡二叉树
     *
     * @param root 根节点
     * @return 是否为平衡二叉树
     */
    public boolean isBalanced(TreeNode root) {
        return getDepthDiff(root) != -1;
    }

    private int getDepthDiff(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftDepthDiff = getDepthDiff(node.left);
        if (leftDepthDiff == -1) {
            return -1;
        }
        int rightDepthDiff = getDepthDiff(node.right);
        if (rightDepthDiff == -1) {
            return -1;
        }
        return Math.abs(leftDepthDiff - rightDepthDiff) > 1 ?
                -1 : Math.max(leftDepthDiff, rightDepthDiff) + 1;
    }

    /**
     * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。
     * 请写程序找出这两个只出现一次的数字。
     *
     * @param array 数组
     * @param num1  长度为1的数组 传出参数
     * @param num2  长度为1的数组 传出参数
     */
    public void findNumsAppearOnce(int[] array, int num1[], int num2[]) {
        if (array == null || num1 == null || num2 == null) {
            return;
        }
        int len = array.length;
        if (len == 0) {
            return;
        }
        int bitXor = 0, diffBit = 0;
        //bitXor异或操作 得到连个不同数组的异或值
        for (Integer x : array) {
            bitXor ^= x;
        }
        //diffBit找到哪一位不同
        while ((bitXor & 1) == 0 && diffBit < 32) {
            bitXor >>= 1;
            diffBit += 1;
        }
        for (Integer x : array) {
            //分为两组 diffBit位为0或者1
            if (((x >> diffBit) & 1) == 0) {
                num1[0] ^= x;
            } else {
                num2[0] ^= x;
            }
        }
    }

    /**
     * 输出所有和为S的连续正数序列。
     * 序列内按照从小至大的顺序，序列间按照开始数字从小到大的顺序
     *
     * @param sum 和
     * @return 所有和为sum的连续正数序列
     */
    public ArrayList<ArrayList<Integer>> findContinuousSequence(int sum) {
        /*---滑动窗口---*/
//        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
//        int low = 1, high = 2;
//        while(high > low){
//            int sumOfSequence  = (low + high)*(high - low + 1)/2;
//            if(sumOfSequence == sum){
//                ArrayList<Integer> list = new ArrayList<>();
//                for(int i=low;i<=high;i++){
//                    list.add(i);
//                }
//                res.add(list);
//                low += 1;
//            }
//            else if(sumOfSequence < sum){
//                high += 1;
//            }
//            else{
//                low += 1;
//            }
//        }
//        return res;
        /*---数学方法---*/
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        //n序列长度
        for (int n = (int) Math.sqrt(2 * sum); n >= 2; n--) {
            if (((n & 1) == 1 && sum % n == 0) || ((n & 1) == 0) && (sum % n) * 2 == n) {
                ArrayList<Integer> list = new ArrayList<>();
                int i = 0, j = (sum / n) - (n - 1) / 2;
                while (i++ < n) {
                    list.add(j++);
                }
                res.add(list);
            }
        }
        return res;
    }

    /**
     * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，
     * 如果有多对数字的和等于S，输出两个数的乘积最小的。
     *
     * @param array 输入数组
     * @param sum   和
     * @return 乘积最小的一对数
     */
    public ArrayList<Integer> findNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> res = new ArrayList<>();
        if (array == null) {
            return res;
        }
        int len = array.length;
        if (len == 0 || len == 1) {
            return res;
        }
        for (int i = 0; i < len; i++) {
            for (int j = len - 1; j > i; j--) {
                if (array[i] + array[j] == sum) {
                    res.add(array[i]);
                    res.add(array[j]);
                    return res;
                }
            }
        }
        return res;
    }

    /**
     * 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
     * 字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。
     *
     * @param str 字符串
     * @param n   移位n位
     * @return 移位结果
     */
    public String leftRotateString(String str, int n) {
        /*---新建字符串---*/
//        if(str == null){
//            return null;
//        }
//        int len = str.length();
//        if(len == 0){
//            return "";
//        }
//        if(len == 1){
//            return str;
//        }
//        n = n % len;
//        String rotateString = new String(str.substring(0, n));
//        String fixString = new String(str.substring(n));
//        return fixString + rotateString;
        /*---翻转---*/
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0 || len == 1) {
            return "";
        }
        n = n % len;
        char[] c = str.toCharArray();
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            swap(c, i, j);
        }
        for (int i = n, j = len - 1; i < j; i++, j--) {
            swap(c, i, j);
        }
        for (int i = 0, j = len - 1; i < j; i++, j--) {
            swap(c, i, j);
        }
        return new String(c);
    }

    /**
     * 翻转单词顺序
     * “student. a am I”
     * “I am a student.”
     *
     * @param str 翻转字符串
     * @return 正常字符串
     */
    public String reverseSentence(String str) {
        if (str == null) {
            return null;
        }
        if (str.trim().equals("")) {
            return str;
        }
        String[] s = str.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = s.length - 1; i >= 0; i--) {
            sb.append(s[i]);
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 扑克牌顺子
     *
     * @param numbers 牌
     * @return 是否为顺子
     */
    public boolean isContinuous(int[] numbers) {
        if (numbers == null) {
            return false;
        }
        int len = numbers.length;
        if (len != 5) {
            return false;
        }
        //不考虑花色0-13共14种
        int[] t = new int[14];
        int max = -1, min = 14;
        for (Integer x : numbers) {
            t[x] += 1;
            if (x == 0) {
                continue;
            }
            if (t[x] > 1) {
                return false;
            }
            if (x > max) {
                max = x;
            }
            if (x < min) {
                min = x;
            }
        }
        return max - min < 5;
    }

    /**
     * 约瑟夫环
     *
     * @param n 总人数
     * @param m 第m个人被选择
     * @return 最后一个人
     */
    public int lastRemaining(int n, int m) {
        if (n == 0) {
            return -1;
        }
        int winner = 0;
        for (int i = 2; i <= n; i++) {
            winner = (winner + m) % i;
        }
        return winner;
    }

    /**
     * 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case
     * 等关键字及条件判断语句（A?B:C）。
     *
     * @param n n
     * @return 和
     */
    @SuppressWarnings("DuplicatedCode")
    public int sum(int n) {
        int a = n, b = n + 1, res = 0;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;

        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;

        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;

        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;
        res += b & f(a);
        a >>= 1;
        b <<= 1;

        return res >> 1;
    }

    private int f(int x) {
        return ((x & 1) << 31) >> 31;
    }


    /**
     * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
     *
     * @param num1 num1
     * @param num2 num2
     * @return 和
     */
    public int add(int num1, int num2) {
        //num1代表不考虑进位的和
        //num2代表进位
        while (num2 != 0) {
            int temp = num1 ^ num2;
            num2 = (num1 & num2) << 1;
            num1 = temp;
        }
        return num1;
    }

    /**
     * 将一个字符串转换成一个整数(实现Integer.valueOf(string)的功能，
     * 但是string不符合数字要求时返回0)，要求不能使用字符串转换整数的库函数。
     * 数值为0或者字符串不是一个合法的数值则返回0。
     *
     * @param str 字符串
     * @return 对于的整数
     */
    public int strToInt(String str) {
        boolean negative = false;
        int i = 0, len = str.length();
        int limit = -Integer.MAX_VALUE;

        if (len > 0) {
            char firstChar = str.charAt(0);
            if (firstChar < '0') {
                if (firstChar == '-') {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                } else if (firstChar != '+') {
                    return 0;
                }

                if (len == 1) {
                    return 0;
                }
                i += 1;
            }
            int result = 0;
            int multmin = limit / 10;
            while (i < len) {
                int digit = str.charAt(i++) - '0';
                if (digit < 0 || digit > 9 || result < multmin) {
                    return 0;
                }
                result *= 10;
                if (result < limit + digit) {
                    return 0;
                }
                result -= digit;
            }
            return negative ? result : -result;
        } else {
            return 0;
        }
    }

    /**
     * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。
     * 数组中某些数字是重复的，但不知道有几个数字是重复的，也不知道每个数字重复几次。
     * 请找出数组中任意一个重复的数字。
     *
     * @param numbers     数组
     * @param length      数组长度
     * @param duplication 返回结果
     * @return 是否存在
     */
    public boolean duplicate(int numbers[], int length, int[] duplication) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < length; i++) {
            int val = numbers[i];
            if (set.contains(val)) {
                duplication[0] = val;
                return true;
            } else {
                set.add(val);
            }
        }
        return false;
    }

    /**
     * 给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],
     * 其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]
     *
     * @param A 输入数组
     * @return 输出结果
     */
    public int[] multiply(int[] A) {
        if (A == null) {
            return null;
        }
        int len = A.length;
        if (len == 0) {
            return null;
        }
        int[] res = new int[len];
        int temp = 1;
        res[0] = 1;
        for (int i = 1; i < len; i++) {
            res[i] = res[i - 1] * A[i - 1];
        }
        for (int i = len - 2; i >= 0; i--) {
            temp *= A[i + 1];
            res[i] *= temp;
        }
        return res;
    }

    /**
     * 请实现一个函数用来匹配包括'.'和'*'的正则表达式。
     * 模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。
     *
     * @param str     str
     * @param pattern 模式串
     * @return 匹配结果
     */
    public boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        return matchHelper(str, pattern, 0, 0);
    }

    private boolean matchHelper(char[] str, char[] pattern, int strIndex, int patternIndex) {
        int lenStr = str.length, lenPattern = pattern.length;
        if (lenStr == strIndex && lenPattern == patternIndex) {
            return true;
        }
        if (lenStr != strIndex && lenPattern == patternIndex) {
            return false;
        }
        //模式中的第二个字符是'*'时
        if (patternIndex + 1 < lenPattern && pattern[patternIndex + 1] == '*') {
            if ((strIndex != lenStr &&
                    (pattern[patternIndex] == str[strIndex] || pattern[patternIndex] == '.'))) {
                //字符串不动模式串右移2 || 字符右移1模式串右移2 || 字符串右移1模式串不动
                return matchHelper(str, pattern, strIndex, patternIndex + 2) ||
                        matchHelper(str, pattern, strIndex + 1, patternIndex + 2) ||
                        matchHelper(str, pattern, strIndex + 1, patternIndex);
            } else {
                return matchHelper(str, pattern, strIndex, patternIndex + 2);
            }
        }

        if ((strIndex != lenStr &&
                (pattern[patternIndex] == str[strIndex] || pattern[patternIndex] == '.'))) {
            return matchHelper(str, pattern, strIndex + 1, patternIndex + 1);
        }
        return false;
    }

    /**
     * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
     * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
     * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
     *
     * @param str 字符串
     * @return 是否为数值
     */
    public boolean isNumeric(char[] str) {
        //[+-]? 正负符号
        //\\d* 整数部分
        //(\\.\\d+)? 小数点出现则后面必有数
        //([eE][+-]?\\d+)? 指数部分eE出现则后面必有数
        String pattern = "[+-]?\\d*(\\.\\d+)?([eE][+-]?\\d+)?";
        return String.valueOf(str).matches(pattern);
    }

    /**
     * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
     *
     * @param pHead 链表头
     * @return 入口节点
     */
    public ListNode entryNodeOfLoop(ListNode pHead) {
        if (pHead == null || pHead.next == null || pHead.next.next == null) {
            return null;
        }
        ListNode fast = pHead.next.next;
        ListNode slow = pHead.next;
        while (slow != fast) {
            if (fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            } else {
                return null;
            }
        }
        fast = pHead;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 在一个排序的链表中，存在重复的结点
     * 请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。
     *
     * @param pHead 链表头
     * @return 链表头
     */
    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = pHead;
        ListNode pre = dummy, cur = dummy.next;
        while (cur != null) {
            if (cur.next != null && cur.val == cur.next.val) {
                while (cur.next != null && cur.val == cur.next.val) {
                    cur = cur.next;
                }
                pre.next = cur.next;
                cur = cur.next;
            } else {
                pre = pre.next;
                cur = cur.next;
            }
        }
        return dummy.next;
    }

    /**
     * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
     * 注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
     *
     * @param pNode 节点
     * @return 中序遍历下一个节点
     */
    public TreeLinkNode getNext(TreeLinkNode pNode) {
        if (pNode == null) {
            return null;
        }
        if (pNode.right != null) {
            pNode = pNode.right;
            while (pNode.left != null) {
                pNode = pNode.left;
            }
            return pNode;
        }
        while (pNode.next != null) {
            if (pNode.next.left == pNode) {
                return pNode.next;
            }
            pNode = pNode.next;
        }
        return null;
    }

    /**
     * 请实现一个函数，用来判断一颗二叉树是不是对称的。
     *
     * @param pRoot 根节点
     * @return 是否对称
     */
    public boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) {
            return true;
        }
        return isSymmetricalHelper(pRoot, pRoot);
    }

    private boolean isSymmetricalHelper(TreeNode p1, TreeNode p2) {
        if (p1 == null && p2 == null) {
            return true;
        }
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.val == p2.val &&
                isSymmetricalHelper(p1.left, p2.right) &&
                isSymmetricalHelper(p1.right, p2.left);
    }

    /**
     * 请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，
     * 第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印，其他行以此类推。
     *
     * @param pRoot 根节点
     * @return 之字形打印
     */
    public ArrayList<ArrayList<Integer>> print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        if (pRoot == null) {
            return res;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        boolean isOdd = false;
        list.add(pRoot);
        while (!list.isEmpty()) {
            int len = list.size();
            ArrayList<Integer> t = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                if (!isOdd) {
                    TreeNode temp = list.pollFirst();
                    t.add(temp.val);
                    if (temp.left != null) {
                        list.addLast(temp.left);
                    }
                    if (temp.right != null) {
                        list.addLast(temp.right);
                    }
                } else {
                    TreeNode temp = list.pollLast();
                    t.add(temp.val);
                    if (temp.right != null) {
                        list.addFirst(temp.right);
                    }
                    if (temp.left != null) {
                        list.addFirst(temp.left);
                    }
                }
            }
            isOdd = !isOdd;
            res.add(t);
        }
        return res;
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
     * 给定一棵二叉搜索树，请找出其中的第k小的结点。
     * @param pRoot 根节点
     * @param k k
     * @return 第k小的节点
     */
    TreeNode kthNode(TreeNode pRoot, int k) {
        if(pRoot == null){
            return null;
        }
        Stack<TreeNode> st = new Stack<>();
        TreeNode tempNode = pRoot;
        int i = 1;
        while(!st.empty() || tempNode != null){
            while(tempNode != null){
                st.push(tempNode);
                tempNode = tempNode.left;
            }
            tempNode = st.pop();
            if(i++ == k){
                return tempNode;
            }
            tempNode = tempNode.right;
        }
        return null;
    }
    /*---如何得到一个数据流中的中位数？
    如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
    如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
    ---*/
    private int count = 0;
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    });
    /**
     * 使用insert()方法读取数据流
     * @param num 数
     */
    public void insert(Integer num){
        count += 1;
        if((count & 1) == 1){
            if(!minHeap.isEmpty() && num > minHeap.peek()){
                minHeap.offer(num);
                num = minHeap.poll();
            }
            assert num != null;
            maxHeap.offer(num);
        }else{
            if(!maxHeap.isEmpty() && num < maxHeap.peek()){
                maxHeap.offer(num);
                num = maxHeap.poll();
            }
            assert num != null;
            minHeap.offer(num);
        }
    }
    /**
     * 使用getMedian()方法获取当前读取数据的中位数
     * @return 中位数
     */
    public Double getMedian(){
        if(count == 0){
            return -1.0;
        }
        assert maxHeap.peek() != null;
        assert minHeap.peek() != null;
        if((count & 1) == 1){
            return (double)maxHeap.peek();
        }else{
            return (maxHeap.peek() + minHeap.peek())/2.0;
        }
    }

    /**
     * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
     * @param num 数组
     * @param size 窗口大小
     * @return 最大值
     */
    public ArrayList<Integer> maxInWindows(int [] num, int size) {
        int len = num.length;
        ArrayList<Integer> res = new ArrayList<>();
        if(len < size || size == 0){
            return res;
        }
        LinkedList<Integer> list = new LinkedList<>();
        for(int i=0;i<len;i++){
            //滑动窗口左边界索引
            int begin = i - size + 1;
            if(list.isEmpty()){
                list.add(i);
            }
            //滑动后窗口左边界大于最大值索引
            if(begin > list.peekFirst()){
                list.pollFirst();
            }
            //从双向列表中poll出比滑动后值小的值索引
            while(!list.isEmpty() && num[list.peekLast()] <= num[i]){
                list.pollLast();
            }
            list.add(i);
            if(begin >= 0){
                res.add(num[list.peekFirst()]);
            }
        }
        return res;
    }

    /**
     * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径
     * @param matrix 矩阵
     * @param rows 行
     * @param cols 列
     * @param str 目标字符串
     * @return 路径是否存在
     */
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if(rows == 0 || cols == 0){
            return false;
        }
        assert matrix.length == rows * cols;
        boolean[] visited = new boolean[rows * cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(hasPathHelper(matrix, i, j, rows, cols, str, 0, visited)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean hasPathHelper(char[] matrix, int i, int j, int rows, int cols,
                                  char[] str, int k, boolean[] visited){
        int index = i * cols + j;
        if(i < 0 || j < 0 || i >= rows || j >= cols || matrix[index] != str[k] ||
                visited[index]){
            return false;
        }
        if(k == str.length - 1){
            return true;
        }
        visited[index] = true;
        if(hasPathHelper(matrix, i-1, j, rows, cols, str, k+1, visited) ||
        hasPathHelper(matrix, i+1, j, rows, cols, str, k+1, visited) ||
        hasPathHelper(matrix, i, j-1,rows, cols, str, k+1,visited) ||
        hasPathHelper(matrix, i, j+1, rows, cols, str, k+1, visited)){
            return true;
        }
        visited[index] = false;
        return false;
    }

    /**
     * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，
     * 每一次只能向左，右，上，下四个方向移动一格，
     * 但是不能进入行坐标和列坐标的数位之和大于k的格子。
     * 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。
     * 但是，它不能进入方格（35,38），因为3+5+3+8 = 19。
     * 请问该机器人能够达到多少个格子？
     * @param threshold
     * @param rows
     * @param cols
     * @return
     */
    public int movingCount(int threshold, int rows, int cols) {
        boolean[][] visited = new boolean[rows][cols];
        return movingCountHelper(0, 0, rows, cols, threshold, visited);
    }
    private int movingCountHelper(int i, int j, int rows, int cols, int threshold,
                                  boolean[][] visited){
        if(i < 0 || i >= rows || j < 0 || j >= cols){
            return 0;
        }
        if(numSum(i) + numSum(j) > threshold || visited[i][j]){
            return 0;
        }
        visited[i][j] = true;
        return movingCountHelper(i-1, j, rows, cols, threshold, visited) +
                movingCountHelper(i+1, j, rows, cols, threshold, visited) +
                movingCountHelper(i, j-1,rows, cols, threshold, visited) +
                movingCountHelper(i, j+1, rows, cols, threshold, visited) +
                1;
    }
    private int numSum(int num){
        int sum = 0;
        do{
            sum += num % 10;
        }while((num /= 10) > 0);
        return sum;
    }
}
