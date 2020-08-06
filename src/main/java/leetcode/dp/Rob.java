package leetcode.dp;

import leetcode.node.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 2020/8/5
 */
public class Rob {

    /**
     * f(o) 表示选择 o 节点的情况下，o节点的子树上被选择的节点的最大权值和
     */
    private Map<TreeNode, Integer> f = new HashMap<>();
    /**
     * g(o) 表示不选择 o 节点的情况下，o节点的子树上被选择的节点的最大权值和
     */
    private Map<TreeNode, Integer> g = new HashMap<>();

    public int rob3(TreeNode root) {
        rob3dfs(root);
        return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));
    }

    private void rob3dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        rob3dfs(node.left);
        rob3dfs(node.right);
        f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));
        g.put(node,
                Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0)) +
                        Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0))
        );
    }

    public int rob3_1(TreeNode root) {
        int[] totalMoney = rob3_1dfs(root);
        return Math.max(totalMoney[0], totalMoney[1]);
    }

    private int[] rob3_1dfs(TreeNode node) {
        if(node == null){
            return new int[]{0, 0};
        }
        // [f,g]
        int[] left = rob3_1dfs(node.left);
        int[] right = rob3_1dfs(node.right);
        int select = node.val + left[1] + right[1];
        int not_select = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return new int[]{select, not_select};
    }
}
