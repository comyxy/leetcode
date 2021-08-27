package backtrace;

import struct.GraphNode;
import struct.TreeNode;

import java.util.*;

/**
 * @since 2021/7/22 22:48
 */
public class Recursion {

    /**
     * 所有可能的路径
     * https://leetcode-cn.com/problems/all-paths-from-source-to-target/
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        final int n = graph.length;
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tmp = new LinkedList<>();
        tmp.add(0);
        allPathsSourceTargetHelper(0, n - 1, graph, tmp, res);
        return res;
    }

    private void allPathsSourceTargetHelper(int u, int target, int[][] graph, List<Integer> tmp, List<List<Integer>> res) {
        if (u == target) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        int[] vs = graph[u];
        for (int v : vs) {
            tmp.add(v);
            allPathsSourceTargetHelper(v, target, graph, tmp, res);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * 括号生成
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        List<Character> tmp = new LinkedList<>();
        generateParenthesisHelper(n, n, tmp, res);
        return res;
    }

    /**
     * @param left  左括号剩余个数
     * @param right 右括号剩余个数
     * @param tmp   临时数组
     * @param res   结果
     */
    private void generateParenthesisHelper(int left, int right, List<Character> tmp, List<String> res) {
        if (left == 0 && right == 0) {
            StringBuilder sb = new StringBuilder();
            for (Character t : tmp) {
                sb.append(t);
            }
            res.add(sb.toString());
            return;
        }
        if (right < left) {
            // 右括号剩余个数小于左括号剩余个数
            return;
        }
        if (left > 0) {
            tmp.add('(');
            generateParenthesisHelper(left - 1, right, tmp, res);
            tmp.remove(tmp.size() - 1);
        }
        if (right > 0) {
            tmp.add(')');
            generateParenthesisHelper(left, right - 1, tmp, res);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * 二分图 LeetCode785
     */
    public boolean isBipartite(int[][] graph) {
        final int m = graph.length;
        // 0 没有染色 1 红色 2 黑色
        int[] colors = new int[m];
        for (int i = 0; i < m; i++) {
            boolean canBipartite = true;
            if (colors[i] == 0) {
                canBipartite = isBipartiteHelper(i, 1/*红色*/, colors, graph);
            }
            if (!canBipartite) {
                return false;
            }
        }
        return true;
    }

    private boolean isBipartiteHelper(int idx, int color, int[] colors, int[][] graph) {
        colors[idx] = color;
        int nextColor = color == 1 ? 2 : 1;
        for (int next : graph[idx]) {
            boolean canBipartite = true;
            if (colors[next] == 0) {
                canBipartite = isBipartiteHelper(next, nextColor, colors, graph);
            } else if (colors[next] != nextColor) {
                // 遇见相邻节点已经涂色 但是颜色不正确
                canBipartite = false;
            }
            if (!canBipartite) {
                // fast return
                return false;
            }
        }
        return true;
    }

    /**
     * 子集（幂集） 回溯 深度优先dfs
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tmp = new LinkedList<>();
        subsetsHelper(nums, 0, tmp, res);
        return res;
    }

    private static void subsetsHelper(int[] nums, int depth, List<Integer> tmp, List<List<Integer>> res) {
        res.add(new ArrayList<>(tmp));
        for (int i = depth; i < nums.length; i++) {
            tmp.add(nums[i]);
            subsetsHelper(nums, depth + 1, tmp, res);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * 克隆树
     */
    public TreeNode cloneTree(TreeNode node) {
        if (node == null) {
            return null;
        }
        if (cloneTreeNodeMap.containsKey(node)) {
            return cloneTreeNodeMap.get(node);
        }
        TreeNode newNode = new TreeNode(node.val);
        cloneTreeNodeMap.put(node, newNode);

        newNode.left = cloneTree(node.left);
        newNode.right = cloneTree(node.right);

        return newNode;
    }

    private final Map<TreeNode, TreeNode> cloneTreeNodeMap = new HashMap<>();

    /**
     * LeetCode133 克隆图
     */
    public GraphNode cloneGraph(GraphNode node) {
        if (node == null) {
            return null;
        }
        // 已经被克隆过 返回克隆的节点
        if (cloneGraphNodeMap.containsKey(node)) {
            return cloneGraphNodeMap.get(node);
        }

        GraphNode cloneNode = new GraphNode(node.val, new ArrayList<>());
        cloneGraphNodeMap.put(node, cloneNode);

        for (GraphNode neighborNode : node.neighbors) {
            // dfs 递归深度优先搜索
            cloneNode.neighbors.add(cloneGraph(neighborNode));
        }

        return cloneNode;
    }

    private final Map<GraphNode, GraphNode> cloneGraphNodeMap = new HashMap<>();

}
