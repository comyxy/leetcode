package backtrace;

import struct.GraphNode;
import struct.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 2021/7/22 22:48
 */
public class Recursion {

    private final Map<TreeNode, TreeNode> cloneTreeNodeMap = new HashMap<>();

    public TreeNode cloneTree(TreeNode node) {
        if(node == null) {
            return null;
        }
        if(cloneTreeNodeMap.containsKey(node)) {
            return cloneTreeNodeMap.get(node);
        }
        TreeNode newNode = new TreeNode(node.val);
        this.cloneTreeNodeMap.put(node, newNode);

        newNode.left = this.cloneTree(node.left);
        newNode.right = this.cloneTree(node.right);

        return newNode;
    }

    public static void printTree(TreeNode node) {
        if(node == null) {
            return;
        }
        System.out.print(node.val + " ");
        printTree(node.left);
        printTree(node.right);
    }

    private final Map<GraphNode, GraphNode> cloneGraphVisited = new HashMap<>();

    /**
     * LeetCode133 克隆图
     *
     */
    public GraphNode cloneGraph(GraphNode node) {
        if (node == null) {
            return null;
        }
        // 已经被克隆过 返回克隆的节点
        if (cloneGraphVisited.containsKey(node)) {
            return cloneGraphVisited.get(node);
        }

        GraphNode cloneNode = new GraphNode(node.val, new ArrayList<>());
        cloneGraphVisited.put(node, cloneNode);

        for (GraphNode neighborNode : node.neighbors) {
            // dfs 递归深度优先搜索
            cloneNode.neighbors.add(cloneGraph(neighborNode));
        }

        return cloneNode;
    }

}
